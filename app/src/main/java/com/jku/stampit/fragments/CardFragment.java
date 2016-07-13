package com.jku.stampit.fragments;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jku.stampit.R;
import com.jku.stampit.Services.CardManager;
import com.jku.stampit.controls.StampView;
import com.jku.stampit.data.Company;
import com.jku.stampit.data.StampCard;
import com.jku.stampit.data.Store;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CardFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CardFragment extends Fragment  {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_CardId = "cardID";

    // TODO: Rename and change types of parameters
    private StampCard card;

    private OnFragmentInteractionListener mListener;

    public CardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param cardId Parameter 1.
     * @return A new instance of fragment CardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CardFragment newInstance(String cardId) {
        CardFragment fragment = new CardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CardId, cardId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            card = CardManager.getInstance().GetMyCardForID(getArguments().getString(ARG_CardId));
        }

        /*
        card = CardManager.getInstance().GetMyCardForID(cid);

        TextView stampInfo = (TextView) findViewById(R.id.card_detailInfo);
        stampInfo.setText(card.getCurrentStampCount() + " / " + card.getRequiredStampCount());

        Company comp = card.getCompany();
        TextView bonus = (TextView) findViewById(R.id.card_bonus);
        TextView storeAddress = (TextView) findViewById(R.id.card_store_adress);

        if(comp != null) {
            storeAddress.setText(comp.getCompanyAddress());
            bonus.setText(comp.getDescription());
        }

        stampView = (StampView)findViewById(R.id.stampView);
        if(card != null){
            stampView.setCircleCount(card.getRequiredStampCount());
            stampView.setBackgroundColor(Color.BLUE);
        }
        */
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View cardView = inflater.inflate(R.layout.fragment_card, container, false);

        if(card != null) {
            ImageView img = (ImageView) cardView.findViewById(R.id.card_image);
            if(img != null) {
                Company comp = card.getCompany();
                if(comp != null) {
                    img.setImageBitmap(comp.getImage());
                }

            }
        }
        return cardView;
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
