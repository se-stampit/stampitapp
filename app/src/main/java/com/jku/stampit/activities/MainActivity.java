package com.jku.stampit.activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.jku.stampit.R;
import com.jku.stampit.Services.CardManager;
import com.jku.stampit.fragments.CardListFragment;
import com.jku.stampit.fragments.FindCardsMapFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements  View.OnClickListener, FindCardsMapFragment.OnFragmentInteractionListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private List<Fragment> tabs = new ArrayList<Fragment>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Crete Taps which should be shown
        CardListFragment cardList = new CardListFragment();
        FindCardsMapFragment companyMap = FindCardsMapFragment.newInstance();
        tabs.add(cardList);
        tabs.add(companyMap);
        //tabs.add(FindCardsMapFragment.newInstance("",""));

        // Create the adapter that will return a fragment for each of the
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(),tabs);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        setUpTabs(mViewPager);

        setTitle("Meine Karten");

        //Create Floating Action Button for Camera
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

    }
    public void onClick(View view) {
        // Intent i = new Intent(getApplicationContext(),ScanActivity.class);
        //i.putExtra("username", user.getDisplayName());
        //  startActivity(i);

        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt("Scan a QR Code");
        integrator.setCameraId(0);  // Use a specific camera of the device
        integrator.setBeepEnabled(false);
        integrator.initiateScan();


    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch(requestCode) {
            case IntentIntegrator.REQUEST_CODE:  {
                if (resultCode != RESULT_CANCELED) {
                    IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
                    String QrCode = scanResult.getContents();
                    Toast.makeText(getApplication(),QrCode,Toast.LENGTH_LONG).show();
                    CardManager.getInstance().addStamp(QrCode);


                } else {
                    return;
                }
                break;
            }
        }
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setUpTabs (ViewPager viewPager) {
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        List<Integer> icons = new ArrayList<Integer>();

        icons.add(R.drawable.home);
        icons.add(R.drawable.map);
        if (tabLayout != null) {
            tabLayout.setupWithViewPager(viewPager);

            int tabCount = tabLayout.getTabCount(); //Assuming you have already somewhere set the adapter for the ViewPager

            for (int i = 0; i < tabCount; i++) {
                TabLayout.Tab tab = tabLayout.getTabAt(i);
                if (tab != null){
                    ImageView customIconView = (ImageView) LayoutInflater.from(tabLayout.getContext()).inflate(R.layout.maintab_layout, null);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        customIconView.setImageDrawable(getResources().getDrawable(icons.get(i), getApplicationContext().getTheme()));
                    } else {
                        customIconView.setImageDrawable(getResources().getDrawable(icons.get(i)));
                    }
                    /*Here is where to set image if doing it dynamically
                    myCustomIcon.setImageBitmap(bitmap);

                    */
                    //Set Icons for Tabs
                    tab.setCustomView(customIconView);
                }
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        List<Fragment> fragmentList;

        public SectionsPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragmentList = fragments;
        }
        //Return Fragments
        @Override
        public Fragment getItem(int position) {
            Fragment fragment = fragmentList.get(position);
            return fragment;
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position)
        {
            //Return no Title, just image which is set in onCreate
            return "";
        }
    }
    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        //void onListFragmentInteraction(DummyContent.DummyItem item);
        //Toast.makeText(getActivity(), "this is my Toast message!!! =)",
        //Toast.LENGTH_LONG).show();
    }
}
