package com.jku.stampit.dto;

import java.util.Date;

/**
 * Created by Andreas on 09.05.16.
 */
public class StoreDTO {

   // [{"id":"70259cfe7d3e43c195aa411ec89b7cb7","createdAt":"2016-06-16T00:55:01.1749925","updatedAt":null,"companyId":"ID123","address":"Coffeestreet","latitude":48.2,"longitude":12.4},{"id":"f9d5a436bef1422b9fa024f92ca5159b","createdAt":"2016-06-16T06:50:50.4039811","updatedAt":null,"companyId":"ID123","address":"Adersfsfd","latitude":48.2109472779491,"longitude":12.4177265167236}]

    private String id,companyId,address;
    private Date createdAt,updatedAt;
    private Double latitude,longitude;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
