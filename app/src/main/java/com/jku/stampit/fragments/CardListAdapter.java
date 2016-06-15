package com.jku.stampit.fragments;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.jku.stampit.R;
import com.jku.stampit.data.StampCard;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 04/05/16.
 */
public class CardListAdapter extends ArrayAdapter<StampCard>  implements Filterable {
    private final Context context;
    private final List<StampCard> orig;
    private final List<StampCard> values;
    public CardListAdapter(Context context, List<StampCard> cards) {
        super(context, 0, cards);
        this.context = context;
        this.orig = new ArrayList<StampCard>();
        this.orig.addAll(cards);
        this.values = new ArrayList<StampCard>();
        this.values.addAll(cards);
    }
    public void SetCards(List<StampCard> newCards){
        orig.clear();
        orig.addAll(newCards);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return values.size();
    }

    @Override
    public StampCard getItem(int position) {
        return values.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final ArrayList<StampCard> results = new ArrayList<StampCard>();
                String constr = constraint.toString().toLowerCase();
                if (constraint != null && constraint != "") {
                    if (orig != null && orig.size() > 0) {
                        for (final StampCard cd : orig) {
                            if (cd.getProductName().toLowerCase()
                                    .contains(constr))
                                results.add(cd);
                        }
                    }
                } else {
                    if(orig != null)
                        results.addAll(orig);
                }
                oReturn.values = results;
                return oReturn;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,
                                          FilterResults results) {
                values.clear();
                values.addAll((ArrayList<StampCard>) results.values);
                notifyDataSetChanged();
            }
        };
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
        cardCompany.setText(card.getCompanyName());
        cardName.setText(card.getProductName());
        cardRequiredStamps.setText(String.valueOf(card.getRequiredStampCount()));
        cardCurrentStamps.setText(String.valueOf(card.getCurrentStampCount()));

        ImageView cardOverlay = (ImageView) convertView.findViewById(R.id.cardinfoimage);
        cardOverlay.setImageResource(android.R.color.transparent);
        if(card.isFull()){
            cardOverlay.setImageResource(R.drawable.card_full);
        }
        if(card.isInvalid()){
            cardOverlay.setImageResource(R.drawable.card_invalid);
        }
        if(card.getDeleteDate() != null) {
            cardOverlay.setImageResource(R.drawable.delete);
        }
        // Return the completed view to render on screen
        return convertView;
    }
}