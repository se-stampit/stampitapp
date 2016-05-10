package com.jku.stampit.data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.jku.stampit.R;
import com.jku.stampit.StampItApplication;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.jku.stampit.R;

/**
 * Created by user on 03/05/16.
 * A Companies Information is stored
 */
public class Company {
    private String id,blobId,companyName,companyAddress,contactName,contactMail;
    private String description;
    private Date createdAt, updatedAt;

    private Bitmap image;
    private byte[] imageBytes;
    private final List<Store> stores = new ArrayList<Store>();
    public Company(String id, String name, byte[] imageBytes) {
        this.id = id;
        this.companyName = name;
    }

    public Bitmap getImage() {
        if(image == null) {
            if(imageBytes != null) {
                image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            } else {
                image = BitmapFactory.decodeResource(StampItApplication.getContext().getResources(),R.drawable.missing_icon);
            }
        }
        return image;
    }

    public List<Store> getStores() {
        return stores;
    }

    public void AddStore(Store store) {

        this.stores.add(store);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBlobId() {
        return blobId;
    }

    public void setBlobId(String blobId) {
        this.blobId = blobId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactMail() {
        return contactMail;
    }

    public void setContactMail(String contactMail) {
        this.contactMail = contactMail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
