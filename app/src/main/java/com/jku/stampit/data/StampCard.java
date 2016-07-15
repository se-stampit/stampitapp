package com.jku.stampit.data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;

import com.jku.stampit.R;
import com.jku.stampit.StampItApplication;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by user on 03/05/16.
 * a Cards Information is stored here
 * implementes Parcelable to send Object through intents
 */
public class StampCard implements Parcelable {
    private String id;
    private Date createdAt = new Date(0),updatedAt = new Date(0);
    private String userId;

    public String getCompanyId() {
        return companyId;
    }

    private String companyId;
    private String productName;
    private String bonusDescription;
    private int requiredStampCount,maxDuration,currentStampCount;
    private boolean isUsed = false;
    private Company company;
    private Bitmap image;
    private byte[] imageBytes;
    private Date deleteDate = null;

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

    // Parcelling part
    public StampCard(Parcel in){
        String[] data = new String[3];

        in.readStringArray(data);
        this.id = data[0];
        this.productName = data[1];
        this.companyId = data[2];
        this.bonusDescription = data[3];
        this.requiredStampCount = Integer.getInteger(data[4]);
        this.currentStampCount = Integer.getInteger(data[5]);
        this.createdAt = new Date(Long.getLong(data[6]));
        this.updatedAt = new Date(Long.getLong(data[7]));
        this.maxDuration = Integer.getInteger(data[8]);
        this.isUsed = Boolean.getBoolean(data[9]);
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {

        long uTime = 0;
        long cTime = 0;
        if(updatedAt != null) {
            uTime = updatedAt.getTime();
        }
        if(createdAt != null) {
            cTime = createdAt.getTime();
        }

        dest.writeStringArray(new String[] {this.id,
                this.productName,
                this.companyId,
        this.bonusDescription,
        String.valueOf(this.requiredStampCount),
                String.valueOf(this.currentStampCount),
                String.valueOf(cTime),
                String.valueOf(uTime),
                String.valueOf(this.maxDuration),
                String.valueOf(this.isUsed)});
    }

    public int describeContents(){
        return 0;
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public StampCard createFromParcel(Parcel in) {
            return new StampCard(in);
        }

        public StampCard[] newArray(int size) {
            return new StampCard[size];
        }
    };

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
    public String getBonusDescription() {
        return bonusDescription;
    }
    public void setBonusDescription(String bonusDescription) {
        this.bonusDescription = bonusDescription;
    }

    public Date getDeleteDate() {
        return deleteDate;
    }
    public void setDeleteDate(Date date) {
        this.deleteDate = date;
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
    public String getCompanyName() {
        if(company == null) {
            return "";
        } else if(company.getCompanyName() == null){
            return "";
        }
        return company.getCompanyName();
    }
    public void setCompany(Company company) {
        this.company = company;
    }

    public String getProductName() {
        if(productName == null) {
            productName = "";
        }
        return productName;
    }

    public void setProductName(String name) {
        this.productName = name;
    }

}
