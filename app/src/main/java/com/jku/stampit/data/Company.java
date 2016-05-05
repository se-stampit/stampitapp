package com.jku.stampit.data;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 03/05/16.
 * A Companies Information is stored
 */
public class Company {

    private String name = "";

    public Company(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
