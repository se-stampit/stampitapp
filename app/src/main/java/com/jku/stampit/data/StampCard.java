package com.jku.stampit.data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;

import com.jku.stampit.R;
import com.jku.stampit.StampItApplication;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by user on 03/05/16.
 * a Cards Information is stored here
 */
public class StampCard {
    private String id;
    private Date createdAt,updatedAt;
    private String userId,companyId,productName,bonusDescription;
    private int requiredStampCount,maxDuration,currentStampCount;
    private boolean isUsed = false;
    private Company company;
    private Bitmap image;
    private byte[] imageBytes;
    //TODO should be private and only be Created with newInstance
    public StampCard(String id, String productName, String companyId,String bonusDescription, int requiredStampCount,
                     int currentStampCount, Date createdAt, Date updatedAt,int maxDuration, Boolean isUsed) {
        this.id = id;
        this.companyId = companyId;
        this.productName = productName;
        this.image = image;
        this.requiredStampCount = requiredStampCount;
        this.currentStampCount = currentStampCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isUsed = isUsed;
        this.maxDuration = maxDuration;
        this.bonusDescription = bonusDescription;
    }
    public StampCard(String id, String productName, Company company,String bonusDescription, int requiredStampCount,
                     int currentStampCount, Date createdAt, Date updatedAt,int maxDuration, Boolean isUsed) {
        this.id = id;
        this.company = company;
        this.productName = productName;
        this.image = image;
        this.requiredStampCount = requiredStampCount;
        this.currentStampCount = currentStampCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isUsed = isUsed;
        this.maxDuration = maxDuration;
        this.bonusDescription = bonusDescription;
    }

    public Boolean isFull() {
        return requiredStampCount == currentStampCount;
    }
    public Bitmap getImage() {
        if(company != null) {
            image = company.getImage();
        } else {
            image = BitmapFactory.decodeResource(StampItApplication.getContext().getResources(), R.drawable.missing_icon);
        }
        return image;
    }
    public int getCurrentStampCount() {
        return currentStampCount;
    }
    public void setCurrentStampCount(int stamps) {
        this.currentStampCount = stamps;
    }

    public int getRequiredStampCount() {
        return requiredStampCount;
    }
    public void setRequiredStampCount(int stamps) {
        this.requiredStampCount = stamps;
    }

    public String getId() {
        return id;
    }
    public Company getCompany() {
        return company;
    }
    public void setCompany(Company company) {
        this.company = company;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String name) {
        this.productName = name;
    }

}
