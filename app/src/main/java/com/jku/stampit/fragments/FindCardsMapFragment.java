package com.jku.stampit.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jku.stampit.R;
import com.jku.stampit.Services.CardManager;
import com.jku.stampit.data.Company;
import com.jku.stampit.data.Store;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FindCardsMapFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FindCardsMapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FindCardsMapFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    //private static final String ARG_PARAM1 = "param1";
    //private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    //private String mParam1;
    //private String mParam2;
    private final List<Company> companies = new ArrayList<Company>();
    private GoogleMap map;
    private OnFragmentInteractionListener mListener;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_find_cards_map, container, false);
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
        for (Company company : companies){
            for (Store store : company.getStores()) {
                //map = ((SupportMapFragment) getFragmentManager().findFragmentById(R.id.store_map))
                //        .getMap();

                //Marker st = map.addMarker(new MarkerOptions().position(new LatLng(store.getLatitude(),store.getLongitude()))
                //        .title(company.getCompanyName()).snippet(company.getCompanyAddress()));

                // .icon(BitmapDescriptorFactory.fromBitmap(company.getImage()))
            }
        }
        // Move the camera instantly to hamburg with a zoom of 15.
        //map.moveCamera(CameraUpdateFactory.newLatLngZoom(HAMBURG, 15));

        // Zoom in, animating the camera.
        //map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
    }
}
