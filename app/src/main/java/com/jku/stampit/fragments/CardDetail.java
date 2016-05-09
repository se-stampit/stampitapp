package com.jku.stampit.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jku.stampit.R;
import com.jku.stampit.Services.CardManager;
import com.jku.stampit.data.StampCard;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CardDetail.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CardDetail#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class CardDetail extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_cardId = "card";

    // TODO: Rename and change types of parameters
    private String cardId;
    private StampCard card;
    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param cardId card to Show.
     * @return A new instance of fragment CardDetail.
     */
    // TODO: Rename and change types and number of parameters
    public static CardDetail newInstance(String cardId) {
        CardDetail fragment = new CardDetail();
        Bundle args = new Bundle();
        args.putString(ARG_cardId,cardId);
        fragment.setArguments(args);
        return fragment;
    }
    public CardDetail() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            card = CardManager.getInstance().GetMyCardForID(getArguments().getString(ARG_cardId));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View cardView = inflater.inflate(R.layout.fragment_card_detail, container, false);

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
