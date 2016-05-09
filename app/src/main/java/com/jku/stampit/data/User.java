package com.jku.stampit.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 03/05/16.
 * User Information is stored, stampCards,login,..
 */
public class User {
    private String id = "";
    //private final List<StampCard> stampCards = new ArrayList<StampCard>();
    private String firstName;
    private String lastName;
    private String mailAddress;
    //private String authService;
    //private String authId;

    public User(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }



    ///Method to add a stampCard to a user, only true if stampCard does not exist or is full
    /*
    public Boolean AddCard(StampCard stampCard){
        for (StampCard c : stampCards) {
            if(c.getId() == stampCard.getId() && !c.isFull()){
                return false;
            }
        }
        stampCards.add(stampCard);
        return true;
    }
    */
}
