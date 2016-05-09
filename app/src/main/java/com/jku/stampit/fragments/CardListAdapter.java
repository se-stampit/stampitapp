package com.jku.stampit.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.jku.stampit.R;
import com.jku.stampit.data.StampCard;

import java.util.List;

/**
 * Created by user on 04/05/16.
 */
public class CardListAdapter extends ArrayAdapter<StampCard> {
    private final Context context;
    private final List<StampCard> values;
    public CardListAdapter(Context context, List<StampCard> cards) {
        super(context, 0, cards);
        this.context = context;
        this.values = cards;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        StampCard card = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_card, parent, false);
        }
        // Lookup view for data population
        TextView cardCompany = (TextView) convertView.findViewById(R.id.card_company);
        TextView cardName = (TextView) convertView.findViewById(R.id.card_name);
        TextView cardRequiredStamps = (TextView) convertView.findViewById(R.id.card_requiredStamps);
        TextView cardCurrentStamps = (TextView) convertView.findViewById(R.id.card_currentstamps);
        // Populate the data into the template view using the data object
        cardCompany.setText(card.getCompany().getCompanyName());
        cardName.setText(card.getProductName());
        //cardRequiredStamps.setText(card.getRequiredStampCount());
        //cardCurrentStamps.setText(card.getCurrentStampCount());
        // Return the completed view to render on screen
        return convertView;
    }
}