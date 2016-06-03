package com.jku.stampit.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.jku.stampit.R;
import com.jku.stampit.Services.CardManager;
import com.jku.stampit.data.Company;
import com.jku.stampit.data.Store;

/**
 * Created by user on 10/05/16.
 */
public class MarkerInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    LayoutInflater inflater=null;

    MarkerInfoWindowAdapter(LayoutInflater inflater) {
        this.inflater=inflater;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return(null);
    }

    @Override
    public View getInfoContents(Marker marker) {

        View popup=inflater.inflate(R.layout.marker_popup_store, null);
        Company comp = CardManager.getInstance().GetCompanyForID(marker.getTitle());
        Store store = null;
        TextView tvTitle =(TextView)popup.findViewById(R.id.title);
        //TextView tvSnippet =(TextView)popup.findViewById(R.id.snippet);
        ImageView img = (ImageView) popup.findViewById(R.id.icon);
        for (Store st : comp.getStores()) {
            if(st.getId() == marker.getSnippet()) {
                store = st;
            }
        }
        if(comp != null) {
            tvTitle.setText(comp.getCompanyName());
            img.setImageBitmap(comp.getImage());
        }
        if(store != null){
          //  tvSnippet.setText(store.getAddress());
        }

        return(popup);
    }
}