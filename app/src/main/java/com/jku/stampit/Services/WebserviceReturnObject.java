package com.jku.stampit.Services;

/**
 * Created by user on 11/05/16.
 */
public class WebserviceReturnObject {
    private int statusCode = -1;
    private String returnString = "";

    private byte[] bytes;

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
    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }
}