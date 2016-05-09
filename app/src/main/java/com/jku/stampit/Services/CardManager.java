package com.jku.stampit.Services;

import android.content.Context;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jku.stampit.data.StampCard;
import com.jku.stampit.data.Company;
import com.jku.stampit.data.Stamp;
import com.jku.stampit.dto.CardDTO;
import com.jku.stampit.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 04/05/16.
 * Class to retrieve and store all available cards for companies etc
 * this one should communicate with the backend server
 */
public class CardManager {
    private static CardManager instance;

    private final List<StampCard> mycards = new ArrayList<StampCard>();
    private final List<StampCard> availableStampCards = new ArrayList<StampCard>();
    public static CardManager getInstance()
    {
        if (instance == null)
        {
            // Create the instance
            instance = new CardManager();
        }
        return instance;
    }
    private CardManager() {
        mycards.addAll(getDummyCards());
        availableStampCards.addAll(getDummyCards());
    }
    public List<StampCard> GetMyCards() {
       return mycards;
    }
    private List<StampCard> getDummyCards() {

        Company comp = new Company("FUSSAL");
        Company comp1 = new Company("Pizza Hut");
        List<StampCard> stampCards = new ArrayList<StampCard>();
        stampCards.add(new StampCard(1,"Yogurt",comp, null ,new ArrayList<Stamp>(),10));
        stampCards.add(new StampCard(2,"Kebap",comp1, null ,new ArrayList<Stamp>(),5));
        stampCards.add(new StampCard(3,"FrozenYogurt",comp, null ,new ArrayList<Stamp>(),7));
        stampCards.add(new StampCard(4,"Pizza",comp1, null ,new ArrayList<Stamp>(),15));
        return stampCards;
    }
    public boolean addStampCard(StampCard stampCard)
    {
        for (StampCard c : mycards) {
            if(c.getId() == stampCard.getId() && !c.isFull()){
                return false;
            }
        }
        mycards.add(stampCard);
        return true;
    }
    public boolean addStamp(String qrCode){
        if(qrCode.isEmpty())
            return false;


        //if hasInternetConnection
        //parse QR Code for adding it to the correct Stampcard
        boolean newStampCard = true;
        for(StampCard card : mycards)
        {
            if(card.getId() == 1) {
                newStampCard = false;

            }


        }
        if(newStampCard)
        {

        }

        //only for test purposes
        ObjectMapper mapper = new ObjectMapper();
        //mapper.writeValueAsString(new CardDTO());

        return true;
    }
}
