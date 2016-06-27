package com.jku.stampit.activities;

import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.zxing.integration.android.IntentIntegrator;
import com.jku.stampit.R;
import com.jku.stampit.Services.CardManager;
import com.jku.stampit.data.Company;
import com.jku.stampit.data.StampCard;
import com.jku.stampit.fragments.CardDetailFragment;
import com.jku.stampit.fragments.CardListFragment;

import java.util.ArrayList;

public class CompanyCardsActivity extends AppCompatActivity  implements  View.OnClickListener{
    public static final String compIDParameter = "compID";
    private Company comp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_cards);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // 1. get passed intent
        Intent intent = getIntent();
        // 2. get message value from intent
        String compID = intent.getStringExtra(compIDParameter);
        comp = CardManager.getInstance().GetCompanyForID(compID);

        setTitle(comp.getCompanyName());

        FragmentManager fragMan = getSupportFragmentManager();
        FragmentTransaction fragTransaction = fragMan.beginTransaction();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
        fab.show();

        ArrayList<StampCard> cards = new ArrayList<StampCard>();
        //TODO Get Only Company Cards
        cards.addAll(CardManager.getInstance().GetMyCards());
        CardListFragment cardListFrag =  CardListFragment.newInstance(cards);
        fragTransaction.add(R.id.companyCardList, cardListFrag , "cardListFrag");
        fragTransaction.commit();

    }
    public void onClick(View view) {
        // Intent i = new Intent(getApplicationContext(),ScanActivity.class);
        //i.putExtra("username", user.getDisplayName());
        //  startActivity(i);
        Integer s = view.getId();
        if(view.getId() == R.id.fab) {
            IntentIntegrator integrator = new IntentIntegrator(this);
            integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
            integrator.setPrompt("Scan a QR Code");
            integrator.setCameraId(0);  // Use a specific camera of the device
            integrator.setBeepEnabled(false);
            integrator.initiateScan();
        }

    }
}
