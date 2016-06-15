package com.jku.stampit.activities;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;

import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import com.jku.stampit.R;
import com.jku.stampit.Services.CardManager;
import com.jku.stampit.Services.UserManager;
import com.jku.stampit.dto.UserInfo;
import com.jku.stampit.utils.Utils;

import java.io.IOException;

//ConnectionCallbacks,
public class LoginActivity extends FragmentActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    private static final int RC_SIGN_IN = 9001;
    private static final int STATE_DEFAULT = 0;
    private static final int STATE_SIGN_IN = 1;
    private static final int STATE_IN_PROGRESS = 2;
    private static final String SAVED_PROGRESS = "sign_in_progress";
    private GoogleApiClient mGoogleApiClient;
    private int mSignInProgress;
    private PendingIntent mSignInIntent;
    private SignInButton mSignInButton;
    public static String username;
    public static int timesOnHomeScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Init Cardmanager to get cards and other stuff
        CardManager.getInstance().LoadMyStampCardsFromServer(null);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        timesOnHomeScreen = 0;
        //requestWindowFeature(Window.FEATURE_NO_TITLE);

        // dummy username for testing purposes
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        //i.putExtra("username", "Benjamin Harvey");
        //username = "Benjamin Harvey";
        //startActivity(i);

        mSignInButton = (SignInButton) findViewById(R.id.sign_in_button);
        mSignInButton.setOnClickListener(this);
        if (savedInstanceState != null) {
            mSignInProgress = savedInstanceState.getInt(
                    SAVED_PROGRESS, STATE_DEFAULT);
        }

        mGoogleApiClient = buildGoogleApiClient();

        //TODO Check if user already signed in

    }

    private GoogleApiClient buildGoogleApiClient() {

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();


        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        return new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    /*
    @Override
    protected void onStart()
    {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop()
    {
        super.onStop();

        if (mGoogleApiClient.isConnected())
        {
            mGoogleApiClient.disconnect();
        }
    }
*/
    /*
    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putInt(SAVED_PROGRESS, mSignInProgress);
    }
*/
    @Override
    public void onClick(View v) {
        if (!mGoogleApiClient.isConnecting()) {
            switch (v.getId()) {
                case R.id.sign_in_button:
                    Log.d("Login", "Button clicked");
                    //resolveSignInError();

                    //Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    //startActivity(i);

                    signIn();
                    break;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d("Login", "handleSignInResult:" + result.isSuccess() + " Status: " + result.getStatus());
        boolean debug = true;
        if (result.isSuccess() || debug) {
            // Signed in successfully, show authenticated UI.
            Log.i("LOGIN", "Google signed in succeccfully ");
            //GoogleSignInAccount acct = result.getSignInAccount();

            String token = "";// = acct.getIdToken();
            //String[] splitted = acct.getDisplayName().split(" ");


            UserInfo userinfo = new UserInfo("","","");// = new UserInfo(splitted[0], splitted[1], acct.getEmail());
            if (debug) {
                token = "testToken";
                userinfo = new UserInfo("Andreas","Lengauer","andreaslengauer94@gmail.com");
            }
            UserManager.getInstance().login("google", token, userinfo, new UserManager.UserManagerLoginCallback() {
                @Override
                public void LoginSuccessfull() {
                    updateUI(true);
                }

                @Override
                public void LoginFailed() {
                    updateUI(false);
                }
            });
            //mStatusTextView.setText(getString(R.string.signed_in_fmt, acct.getDisplayName()));
            //   mStatusTextView.setText(getString(R.string.auth_google_play_services_client_google_display_name,acct.getDisplayName()));

            //updateUI(true);
        } else {
            // Signed out, show unauthenticated UI.
            updateUI(false);

        }
    }

    private void finishLogin() {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
    }

    private void updateUI(boolean signedIn) {
        if (signedIn) {
            //Show Main Screen
            finishLogin();
        } else {
            //   mStatusTextView.setText(R.string.signed_out);
            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
            // findViewById(R.id.sign_out_and_disconnect).setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), "Login fehlgeschlagen", Toast.LENGTH_SHORT).show();
        }
    }

    /*
    @Override
    public void onConnected(Bundle connectionHint)
    {
        mSignInButton.setEnabled(false);

        Auth.GoogleSignInApi.
        //Person currentUser = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
        //username = currentUser.getDisplayName();

        //Toast toast = Toast.makeText(getApplicationContext(),
        //        "Welcome " + username + "ID:" + currentUser.getId(), Toast.LENGTH_LONG);
        //toast.show();


        //mSignInProgress = STATE_DEFAULT;
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
    }@
    */
    private void signIn() {
        Log.d("Login", "signIN Method was called");
        findViewById(R.id.sign_in_button).setVisibility(View.GONE);
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // [START_EXCLUDE]
                        updateUI(false);
                        // [END_EXCLUDE]
                    }
                });
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        /*
        if (mSignInProgress != STATE_IN_PROGRESS)
        {
            mSignInIntent = result.getResolution();
            if (mSignInProgress == STATE_SIGN_IN)
            {
                resolveSignInError();
            }
        }
        */
    }

    private void resolveSignInError() {
        if (mSignInIntent != null) {
            try {
                mSignInProgress = STATE_IN_PROGRESS;
                startIntentSenderForResult(
                        mSignInIntent.getIntentSender(), 0,
                        null, 0, 0, 0);
            } catch (IntentSender.SendIntentException e) {
                Log.i("Log error",
                        "Sign in intent could not be sent: "
                                + e.getLocalizedMessage());
                mSignInProgress = STATE_SIGN_IN;
                mGoogleApiClient.connect();
            }
        } else {
            Toast.makeText(getApplicationContext(),
                    "Error signing in", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    @Override
    public void onBackPressed() {
        //TODO do we need back?
        //Intent intent = new Intent(getApplicationContext(), SplashScreen.class);
        //startActivity(intent);
    }

}
