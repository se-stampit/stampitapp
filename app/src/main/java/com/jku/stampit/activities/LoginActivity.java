package com.jku.stampit.activities;

import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.Plus;
import com.jku.stampit.R;
import com.jku.stampit.Services.CardManager;
import com.jku.stampit.Services.UserManager;
import com.jku.stampit.Services.WebserviceReturnObject;
import com.jku.stampit.StampItApplication;
import com.jku.stampit.data.StampCard;
import com.jku.stampit.dto.LoginDTO;
import com.jku.stampit.dto.RegisterDTO;
import com.jku.stampit.dto.SessionTokenDTO;
import com.jku.stampit.dto.UserInfo;

import java.util.List;

//
public class LoginActivity extends FragmentActivity
        implements OnConnectionFailedListener,GoogleApiClient.ConnectionCallbacks, View.OnClickListener {
    private static final String TAG = "com-jku-stampit";

    private static final int RC_SIGN_IN = 9001;
    private static final int STATE_DEFAULT = 0;
    private static final int STATE_SIGN_IN = 1;
    private static final int STATE_IN_PROGRESS = 2;
    private static final String SAVED_PROGRESS = "sign_in_progress";
    private GoogleApiClient mGoogleApiClient;
    private int mSignInProgress;
    private PendingIntent mSignInIntent;
    private SignInButton mSignInButton;
    private ProgressDialog dialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Init Cardmanager to get cards and other stuff
        CardManager.getInstance().Init();

        setContentView(R.layout.activity_login);
        dialog = new ProgressDialog(LoginActivity.this);

        mSignInButton = (SignInButton) findViewById(R.id.sign_in_button);
        mSignInButton.setOnClickListener(this);
        mSignInButton.setEnabled(true);

        if (savedInstanceState != null) {
            mSignInProgress = savedInstanceState.getInt(SAVED_PROGRESS, STATE_DEFAULT);
        }

        if (savedInstanceState != null) {
            mSignInProgress = savedInstanceState.getInt(
                    SAVED_PROGRESS, STATE_DEFAULT);
        }

        mGoogleApiClient = buildGoogleApiClient();
    }

    private GoogleApiClient buildGoogleApiClient() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(LoginActivity.this.getResources().getString(R.string.server_client_id))
                .requestEmail()
                .build();

        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        return new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addOnConnectionFailedListener(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
    }

    @Override
    public void onClick(View v) {
            switch (v.getId()) {
                case R.id.sign_in_button:
                    Log.d("Login", "Button clicked");

                    signIn();
                    break;
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
        dialog = ProgressDialog.show(this, "", "registrieren und einloggen...", true);
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            Log.i("LOGIN", "Google signed in succeccfully ");
            Toast.makeText(getApplicationContext(),"Google Login successfully",Toast.LENGTH_LONG);
            GoogleSignInAccount acct = result.getSignInAccount();
            String token = acct.getIdToken();
            String[] splitted = acct.getDisplayName().split(" ");

            UserInfo userinfo;
            userinfo = new UserInfo(splitted[0], splitted[1], acct.getEmail());

            RegisterDTO registerDTO = new RegisterDTO();
            registerDTO.setAuthprovider("google");
            registerDTO.setToken(token);
            registerDTO.setUser(userinfo);
            final LoginDTO loginDTO = new LoginDTO("google",token);

            UserManager.getInstance().register(registerDTO, new UserManager.LoginCompletionHandler() {
                @Override
                public void complete(SessionTokenDTO token, WebserviceReturnObject result) {
                    //User already registered, Login instead
                    //Toast.makeText(StampItApplication.getContext(),"token:" + UserManager.getInstance().getSessionToken(),Toast.LENGTH_LONG).show();
                    if(result.getStatusCode() == 400){
                        Toast.makeText(getApplicationContext(),"Stampit-Server Login Error: statuscode: "+ result.getStatusCode() + "\n message:" + result.getReturnString(),Toast.LENGTH_LONG).show();
                        //Already registered, sign in
                        updateUI(false);
                    } else if(result.getStatusCode() == 200) {
                        //registered
                        updateUI(true);
                    } else {
                        Toast.makeText(getApplicationContext(),"Stampit-Server Login Error: statuscode: "+ result.getStatusCode() + "\n message:" + result.getReturnString(),Toast.LENGTH_LONG).show();
                        updateUI(false);
                    }
                }
            });
        } else {
            // Signed out, show unauthenticated UI.
            Toast.makeText(getApplicationContext(),"Google Login Error : statuscode: "+ result.getStatus().getStatusCode() + "\n message:" + result.getStatus().getStatusMessage(),Toast.LENGTH_LONG).show();
            updateUI(false);
        }
    }

    private void finishLogin() {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
    }

    private void updateUI(boolean signedIn) {
        if(dialog != null) {
            dialog.dismiss();
        }
        if (signedIn) {
            //Show Main Screen
            finishLogin();
        } else {
            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
            //Toast.makeText(getApplicationContext(), "Login fehlgeschlagen", Toast.LENGTH_SHORT).show();
        }
    }

    private void signIn() {
        Log.d("Login", "signIN Method was called");
        findViewById(R.id.sign_in_button).setVisibility(View.GONE);
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if (mSignInProgress != STATE_IN_PROGRESS) {
            mSignInIntent = result.getResolution();
            if (mSignInProgress == STATE_SIGN_IN) {
                resolveSignInError();
            }
        }
    }
    /*
            * onConnected is called when our Activity successfully connects to Google
            * Play services. onConnected indicates that an account was selected on the
            * device, that the selected account has granted any requested permissions
            * to our app and that we were able to establish a service connection to
            * Google Play services.
            */
    @Override
    public void onConnected(Bundle connectionHint) {
        // Reaching onConnected means we consider the user signed in.
        Log.i(TAG, "onConnected");

        // Update the user interface to reflect that the user is signed in.
        //mSignInButton.setEnabled(false);
        //mSignOutButton.setEnabled(true);
        //mRevokeButton.setEnabled(true);

        // Retrieve some profile information to personalize our app for the
        // user.
        //Person currentUser = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);

        // Indicate that the sign in process is complete.
        mSignInProgress = STATE_DEFAULT;

        //Toast.makeText(this, mStatus.getText(), Toast.LENGTH_LONG).show();
        //TODO Call Stampit Login Method

    }
    @Override
    public void onConnectionSuspended(int cause) {
        // The connection to Google Play services was lost for some reason.
        // We call connect() to attempt to re-establish the connection or get a
        // ConnectionResult that we can attempt to resolve.
        mGoogleApiClient.connect();
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
            /*
            Toast.makeText(getApplicationContext(),
                    "Error signing in", Toast.LENGTH_SHORT)
                    .show();
            */
        }
    }

    public void disconnect() {
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    public void logOut() {
        if (mGoogleApiClient.isConnected()) {
            signOut();
            revokeAccess();
            mGoogleApiClient.disconnect();
        }
    }

    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        String s = status.toString();
                        //App.logger().d(TAG, "signOut: status = " + status);
                    }
                });
    }

    private void revokeAccess() {
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        //App.logger().d(TAG, "revokeAccess: status = " + status);
                    }
                });
    }
}
