package com.jku.stampit.data;

import java.util.Date;

/**
 * Created by user on 03/05/16.
 */
public class Stamp {
    private int id; //maybe we use a id for each stamp to identify
    private Date scanDate;
    private Date verifyDate;

    Stamp(int id, Date scanDate, Date verifyDate ) {
        this.id = id;
        this.scanDate = scanDate;
        this.verifyDate = verifyDate;
    }

    public Boolean IsVerified() {
        return verifyDate != null;
    }
    public void setVerifyDate(Date value){
        verifyDate = value;
    }
    public int getId() {
        return id;
    }
}
