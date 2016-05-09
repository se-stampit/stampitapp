package com.jku.stampit.fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jku.stampit.R;
import com.jku.stampit.Services.CardManager;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_cardlist,container,false);
    /*
        //GetActivity() erst ab 23
        CardListAdapter cardListAdapter = new CardListAdapter(getActivity().getApplicationContext(),CardManager.getInstance().GetMyCards());

        ListView listView = (ListView)rootView.findViewById(R.id.card_list);
        listView.setAdapter(cardListAdapter);

        //TODO Check this property
        //retain cardsfragment instance across configuration change
        setRetainInstance(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(getActivity().getApplicationContext(),
                        "Click ListItem Number " + position, Toast.LENGTH_LONG)
                        .show();
            }
        });
        */
        return rootView;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        CardListAdapter cardListAdapter = new CardListAdapter(getActivity().getApplicationContext(),CardManager.getInstance().GetMyCards());

        setListAdapter(cardListAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // TODO implement some logic
        TextView cardName = (TextView)v.findViewById(R.id.card_name);
        Toast.makeText(getActivity(),cardName.getText(),Toast.LENGTH_LONG).show();
    }

    /*
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_cards,container,false);

        //GetActivity() erst ab 23
        CardListAdapter cardListAdapter = new CardListAdapter(getActivity().getApplicationContext(),CardManager.getInstance().GetMyCards());

        ListView listView = (ListView)rootView.findViewById(R.id.card_list);
        listView.setAdapter(cardListAdapter);

        //TODO Check this property
        //retain cardsfragment instance across configuration change
        setRetainInstance(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(getActivity().getApplicationContext(),
                        "Click ListItem Number " + position, Toast.LENGTH_LONG)
                        .show();
            }
        });
        return rootView;

    }
    */
}