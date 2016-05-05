package com.jku.stampit.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 03/05/16.
 * User Information is stored, cards,login,..
 */
public class User {
    private String id = "";
    private final List<Card> cards = new ArrayList<Card>();

    public User(String id) {
        this.id = id;
    }

    ///Method to add a card to a user, only true if card does not exist or is full
    public Boolean AddCard(Card card){
        for (Card c : cards) {
            if(c.getId() == card.getId() && !c.isFull()){
                return false;
            }
        }
        cards.add(card);
        return true;
    }
}
