package com.jku.stampit.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jku.stampit.R;
import com.jku.stampit.data.Card;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 04/05/16.
 */
public class CardListAdapter extends ArrayAdapter<Card> {
    private final Context context;
    private final List<Card> values;
    public CardListAdapter(Context context, List<Card> cards) {
        super(context, 0, cards);
        this.context = context;
        this.values = cards;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Card card = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_card, parent, false);
        }
        // Lookup view for data population
        TextView cardCompany = (TextView) convertView.findViewById(R.id.card_company);
        TextView cardName = (TextView) convertView.findViewById(R.id.card_name);
        // Populate the data into the template view using the data object
        cardCompany.setText(card.getCompany().getName());
        cardName.setText(card.getName());
        // Return the completed view to render on screen
        return convertView;
    }
}