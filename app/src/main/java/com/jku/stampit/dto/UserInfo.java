package com.jku.stampit.dto;

/**
 * Created by Andreas on 15.06.16.
 */
public class UserInfo {
    private String firstName,lastName,mailAddress;

    public UserInfo(String firstName,String lastName,String mailAddress)
    {
        this.firstName=firstName;
        this.lastName=lastName;
        this.mailAddress=mailAddress;
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
}
