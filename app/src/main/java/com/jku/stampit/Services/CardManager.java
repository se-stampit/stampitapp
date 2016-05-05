package com.jku.stampit.Services;

import android.app.Application;
import android.content.Context;

import com.jku.stampit.R;
import com.jku.stampit.data.Card;
import com.jku.stampit.data.Company;
import com.jku.stampit.data.Stamp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 04/05/16.
 * Class to retrieve and store all available cards for companies etc
 * this one should communicate with the backend server
 */
public class CardManager {
    private static CardManager instance;

    private final List<Card> mycards = new ArrayList<Card>();
    private final List<Card> availableCards = new ArrayList<Card>();
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
        availableCards.addAll(getDummyCards());
    }
    public List<Card> GetMyCards() {
       return mycards;
    }
    private List<Card> getDummyCards() {

        Company comp = new Company("FUSSAL");
        Company comp1 = new Company("Pizza Hut");
        List<Card> cards = new ArrayList<Card>();
        cards.add(new Card(1,"Yogurt",comp, null ,new ArrayList<Stamp>(),10));
        cards.add(new Card(2,"Kebap",comp1, null ,new ArrayList<Stamp>(),5));
        cards.add(new Card(3,"FrozenYogurt",comp, null ,new ArrayList<Stamp>(),7));
        cards.add(new Card(4,"Pizza",comp1, null ,new ArrayList<Stamp>(),15));
        return cards;
    }
}
