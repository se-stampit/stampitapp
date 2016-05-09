package com.jku.stampit.dto;

import java.util.Date;

/**
 * Created by user on 03/05/16.
 * DTO to transfer Object between server and application
 */
public class CardDTO {
    private String id;
    private Date createdAt,updatedAt;
    private String userId,companyId,productName,bonusDescription;
    private int requiredStampCount,maxDuration,currentStampCount;
    private boolean isUsed;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getBonusDescription() {
        return bonusDescription;
    }

    public void setBonusDescription(String bonusDescription) {
        this.bonusDescription = bonusDescription;
    }

    public int getRequiredStampCount() {
        return requiredStampCount;
    }

    public void setRequiredStampCount(int requiredStampCount) {
        this.requiredStampCount = requiredStampCount;
    }

    public int getMaxDuration() {
        return maxDuration;
    }

    public void setMaxDuration(int maxDuration) {
        this.maxDuration = maxDuration;
    }

    public int getCurrentStampCount() {
        return currentStampCount;
    }

    public void setCurrentStampCount(int currentStampCount) {
        this.currentStampCount = currentStampCount;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setIsUsed(boolean isUsed) {
        this.isUsed = isUsed;
    }
}
