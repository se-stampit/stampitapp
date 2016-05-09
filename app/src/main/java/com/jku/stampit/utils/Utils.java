package com.jku.stampit.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.jku.stampit.StampItApplication;

/**
 * Created by user on 04/05/16.
 */
public class Utils {

    public static boolean HasInternetConnection( Context context) {

        ConnectivityManager cm = (ConnectivityManager) StampItApplication.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
