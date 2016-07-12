package com.jku.stampit.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.jku.stampit.R;
import com.jku.stampit.Services.CardManager;
import com.jku.stampit.activities.StampcardDetailActivity;
import com.jku.stampit.data.StampCard;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class CardListFragment extends ListFragment {

    CardListAdapter cardListAdapter = null;
    private BroadcastReceiver receiver;

    public void setCards(List<StampCard> cards) {
        this.cards.clear();
        this.cards.addAll(cards);
        cardListAdapter.SetCards(cards);
    }

    private List<StampCard> cards;
    private static final String ARG_CardListID = "cardlistID";

    public static CardListFragment newInstance(ArrayList<StampCard> cards) {
        CardListFragment fragment = new CardListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_CardListID, cards);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public CardListAdapter getListAdapter() {
        return cardListAdapter;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundleArgs = getArguments();
        if(bundleArgs != null) {
            cards = bundleArgs.getParcelableArrayList(ARG_CardListID);
            cardListAdapter = new CardListAdapter(getActivity().getApplicationContext(),cards);
            setListAdapter(cardListAdapter);
            cardListAdapter.setNotifyOnChange(true);
        }

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if(action == CardManager.CARDS_UPDATE_MESSAGE) {
                    cardListAdapter.SetCards(cards);
                }
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_cardlist,container,false);
        ListView listview = (ListView) rootView.findViewById(R.id.cardlistView);
        if (listview != null) {
            listview.setTextFilterEnabled(true);
        }
        return rootView;

    }
    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        //LocalBroadcastManager.getInstance(this.getContext()).unregisterReceiver(receiver);
        super.onStop();
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Intent i = new Intent(getContext(), StampcardDetailActivity.class);
        StampCard card = cardListAdapter.getItem(position);
        i.putExtra(StampcardDetailActivity.cardIDParameter, card.getId());
        startActivity(i);
    }
    public void Filter(String text) {
        if(cardListAdapter != null) {
            cardListAdapter.getFilter().filter(text);
        }
    }
}