package com.jku.stampit.data;

import android.media.Image;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by user on 03/05/16.
 * a Cards Information is stored here
 */
public class StampCard {
    private int id = 0;
    private int totalStampCount = 0;
    private List<Stamp> stamps = new ArrayList<Stamp>();
    private Image cardImage;
    private Date redeemDate;
    private String bonus;
    private String name;

    private Company company;

    public StampCard(int id, String name, Company company, Image image, List<Stamp> stamps, int totalstamps) {
        this.id = id;
        this.stamps = stamps;
        this.totalStampCount = totalstamps;
        this.cardImage = image;
        this.name = name;
        this.company = company;
    }

    public Boolean isFull() {
        return stamps.size() == totalStampCount;
    }
    public int getTotalStampCount() {
        return totalStampCount;
    }
    public int getId() {
        return id;
    }
    public List<Stamp> getStamps() {
        return stamps;
    }
    public Date getRedeemDate() {
        return redeemDate;
    }
    public void setRedeemDate(Date value) {
        redeemDate = value;
    }
    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean addStamp(Stamp stamp){
        if(isFull())
            return false;
        stamps.add(stamp);
        return true;
    }
}
