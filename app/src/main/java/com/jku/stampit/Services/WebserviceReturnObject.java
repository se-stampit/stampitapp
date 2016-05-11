package com.jku.stampit.Services;

/**
 * Created by user on 11/05/16.
 */
public class WebserviceReturnObject {
    private int statusCode = -1;
    private String returnString = "";

    public String getReturnString() {
        return returnString;
    }

    public void setReturnString(String returnString) {
        this.returnString = returnString;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

}