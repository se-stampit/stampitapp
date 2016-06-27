package com.jku.stampit.fragments;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jku.stampit.R;
import com.jku.stampit.Services.CardManager;
import com.jku.stampit.activities.CompanyCardsActivity;
import com.jku.stampit.activities.StampcardDetailActivity;
import com.jku.stampit.data.Company;
import com.jku.stampit.data.StampCard;
import com.jku.stampit.data.Store;
import com.jku.stampit.utils.Constants;
import com.jku.stampit.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FindCardsMapFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FindCardsMapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FindCardsMapFragment extends Fragment implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, GoogleMap.OnMarkerClickListener,GoogleMap.OnInfoWindowClickListener {

    private class CompStore {
        private String companyID,storeID;

        public String getCompanyID() {
            return companyID;
        }

        public void setCompanyID(String companyID) {
            this.companyID = companyID;
        }

        public String getStoreID() {
            return storeID;
        }

        public void setStoreID(String storeID) {
            this.storeID = storeID;
        }

        public CompStore(String compId, String storeId){
            this.companyID = compId;
            this.storeID = storeID;
        }
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    //private static final String ARG_PARAM1 = "param1";
    //private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    //private String mParam1;
    //private String mParam2;
    private final List<Company> companies = new ArrayList<Company>();
    private GoogleMap googleMap;
    private OnFragmentInteractionListener mListener;
    private GoogleApiClient mGoogleApiClient;
    private Location lastLocation;
    private LayoutInflater inflater;
    private static final Location JKU = new Location("");
    private final Map<String,CompStore> markerCompStoreList = new HashMap<String,CompStore>();
    public FindCardsMapFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FindCardsMapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FindCardsMapFragment newInstance() {
        JKU.setLatitude(48.3362284);
        JKU.setLongitude(14.3178998);
        FindCardsMapFragment fragment = new FindCardsMapFragment();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //mParam1 = getArguments().getString(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }
        companies.addAll(CardManager.getInstance().GetAvailableCompanies());

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(getActivity().getApplicationContext())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }
    @Override
    public void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }
    @Override
    public void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.inflater = inflater;
        return inflater.inflate(R.layout.fragment_find_cards_map, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FragmentManager fm = getChildFragmentManager();
        Fragment fragment = (SupportMapFragment) fm.findFragmentById(R.id.map_container);
        if (fragment == null) {
            fragment = SupportMapFragment.newInstance();
            fm.beginTransaction().replace(R.id.map_container, fragment).commit();
        }

        ((SupportMapFragment) fragment).getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap map) {
                googleMap = map;
                LoadStoresFromCompanies(companies);
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (Utils.checkPermission(getContext(), Constants.ACCESS_COARSE_LOCATION)) {
            final Bundle bundle1 = bundle;
            lastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);

            if(lastLocation == null) {
                lastLocation = JKU;
            }
            if(googleMap != null && lastLocation != null) {
                LatLng lastLoc = new LatLng(lastLocation.getLatitude(),lastLocation.getLongitude());
                // Move the camera instantly to hamburg with a zoom of 15.
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lastLoc, 15));
                // Zoom in, animating the camera.
                googleMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
                googleMap.setOnMarkerClickListener(this);
                Marker myLoc = googleMap.addMarker(new MarkerOptions().position(lastLoc)
                        .title("My Position").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));

                //Set this, to receive Click event on shown Info window
                googleMap.setOnInfoWindowClickListener(this);
                // Setting a custom info window adapter for the google map
                googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

                    // Use default InfoWindow frame
                    @Override
                    public View getInfoWindow(Marker arg0) {
                        return null;
                    }

                    // Defines the contents of the InfoWindow
                    @Override
                    public View getInfoContents(Marker arg0) {
                        final CompStore compStore = getCompStoreforMarker(arg0);

                        if(compStore != null) {
                            // Getting view from the layout file info_window_layout
                            View v = getLayoutInflater(bundle1).inflate(R.layout.marker_popup_store, null);

                            Company comp = CardManager.getInstance().GetCompanyForID(compStore.getCompanyID());
                            Store store = comp.GetStoreForId(compStore.getStoreID());

                            // icon title snippet
                            ImageView icon = (ImageView) v.findViewById(R.id.icon);
                            //icon.setImageResource(R.drawable.logo);
                            icon.setImageBitmap(comp.getImage());
                            TextView title = (TextView) v.findViewById(R.id.title);
                            title.setText(comp.getCompanyName());

                            TextView storeName = (TextView) v.findViewById(R.id.comp_store_name);
                            TextView storeAddress = (TextView) v.findViewById(R.id.comp_store_address);

                            if(store != null){
                                storeName.setText(comp.getCompanyName());
                                storeAddress.setText(store.getAddress());
                            }

                            // Returning the view containing InfoWindow contents
                            return v;
                        } else {
                            // Getting view from the layout file info_window_layout
                            View v = getLayoutInflater(bundle1).inflate(R.layout.marker_popup_myposition, null);
                            return v;
                        }
                    }
                });
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void LoadStoresFromCompanies(List<Company> companies) {
        if(googleMap != null) {
            for (Company company : companies){
                for (Store store : company.getStores()) {

                    //Set company and store as title and snipped and find in MarkerInfoWindowAdapter the store for it
                    //Marker st = googleMap.addMarker(new MarkerOptions().position(new LatLng(store.getLatitude(),store.getLongitude()))
                    //        .title(company.getId()).snippet(store.getId()));

                    Marker st = googleMap.addMarker(new MarkerOptions().position(new LatLng(store.getLatitude(),store.getLongitude()))
                            .title(company.getCompanyName()).snippet(store.getAddress()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

                    markerCompStoreList.put(st.getId(),new CompStore(company.getId(),store.getId()));
                }
            }
        }
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        marker.showInfoWindow();
        return true;
    }


    public void onInfoWindowClick(Marker m) {
        Intent i = new Intent(getContext(), CompanyCardsActivity.class);
         CompStore compStore = getCompStoreforMarker(m);
        i.putExtra(CompanyCardsActivity.compIDParameter, compStore.getCompanyID());
        startActivity(i);
    }

    private CompStore getCompStoreforMarker(Marker marker) {

        Iterator it = markerCompStoreList.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            if(marker.getId().equals(pair.getKey())){
                return (CompStore)pair.getValue();
            }
        }

        return null;
    }
}
