package com.jku.stampit.fragments;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jku.stampit.R;
import com.jku.stampit.Services.CardManager;
import com.jku.stampit.controls.StampView;
import com.jku.stampit.data.Company;
import com.jku.stampit.data.StampCard;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CardDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CardDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class CardDetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_cardId = "card";

    // TODO: Rename and change types of parameters
    private String cardId;
    private StampCard card;
    private OnFragmentInteractionListener mListener;

    public StampCard getCard() {
        return card;
    }

    public void setCard(StampCard card) {
        this.card = card;
     }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param cardId card to Show.
     * @return A new instance of fragment CardDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CardDetailFragment newInstance(String cardId) {
        CardDetailFragment fragment = new CardDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_cardId,cardId);
        fragment.setArguments(args);
        return fragment;
    }
    public CardDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (this.getArguments() != null) {
            Bundle bundle = this.getArguments();
            if (bundle != null) {
                String cardID = bundle.getString(ARG_cardId);
                card = CardManager.getInstance().GetMyCardForID(getArguments().getString(ARG_cardId));
                //card.setCurrentStampCount(3);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View cardView = inflater.inflate(R.layout.fragment_card_detail, container, false);
        Company comp = null;
        if(card != null) {
            comp = card.getCompany();
        }
        TextView address = (TextView) cardView.findViewById(R.id.store_address);

        if(address != null && comp != null) {
            address.setText(comp.getCompanyAddress());
        } else {
            address.setText("");
        }
        TextView bonus = (TextView) cardView.findViewById(R.id.card_bonus);
        if(bonus != null && card != null) {
            bonus.setText(card.getBonusDescription());
        } else {
            bonus.setText("");
        }

        StampView stmpView = (StampView) cardView.findViewById(R.id.stampView);
        if(stmpView != null && card != null) {
            stmpView.setCircleCount(card.getRequiredStampCount());
            stmpView.setFilledCircleCount(card.getCurrentStampCount());
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

           // throw new RuntimeException(context.toString()+ " must implement OnFragmentInteractionListener");
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
