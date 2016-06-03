package com.jku.stampit.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.jku.stampit.R;
import com.jku.stampit.Services.CardManager;
import com.jku.stampit.controls.StampView;
import com.jku.stampit.data.StampCard;
import com.jku.stampit.fragments.CardDetailFragment;

public class StampcardDetailActivity extends AppCompatActivity {

    public static final String cardIDParameter = "cardID";
    private String cardId = "";
    private StampCard card;
    private StampView stampView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stampcard_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // 1. get passed intent
        Intent intent = getIntent();
        // 2. get message value from intent
        String cid = intent.getStringExtra(cardIDParameter);
        card = CardManager.getInstance().GetMyCardForID(cid);

        setTitle(card.getProductName());

        FragmentManager fragMan = getFragmentManager();
        FragmentTransaction fragTransaction = fragMan.beginTransaction();


        CardDetailFragment cardDetailFrag = CardDetailFragment.newInstance(cid);
        fragTransaction.add(R.id.cardDetailfragment, cardDetailFrag , "cardDetailfragment");
        fragTransaction.commit();

        /*
        if bundle was set to extras
        Bundle bundle = intent.getExtras();

        // 5. get status value from bundle
        String status = bundle.getString("status");
         */
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_stampcard_detail, menu);

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle(R.string.deleteCardTitle);
            builder.setMessage(R.string.deleteCardQuestion);

            builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    // Do nothing but close the dialog
                    CardManager.getInstance().DeleteCard(card.getId());
                    finish();
                }
            });

            builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Do nothing
                    dialog.dismiss();
                }
            });

            AlertDialog alert = builder.create();
            alert.show();

        }

        return super.onOptionsItemSelected(item);
    }
}
