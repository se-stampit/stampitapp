package com.jku.stampit.utils;

/**
 * Created by user on 09/05/16.
 */
public class Constants {

    //Schnittstelle
    public static final String ServerURL = "";
    public static final String AddStampURL = ServerURL + "/me/scan/addstamps";
    public static final String GetUserDetailURL = ServerURL + "/me";
    public static final String GetMyStampCardsURL = ServerURL + "/me/stampcard";
    public static final String GetMyStampCardsCountURL = ServerURL + "/me/stampcard/count";
    public static final String UseStampCardURL = ServerURL + "/me/scan/usestamps";
    public static final String RegisterURL = ServerURL + "/register";
    public static final String LoginURL = ServerURL + "/login";
    public static final String GetStampItProvidersURL = ServerURL + "/stampitprovider";
    public static final String GetStampItProviderCountURL = ServerURL + "/stampitprovider/count";
    public static final String GetStoresForCompanyURL = ServerURL + "/stampitprovider/{companyid}/stores";
    public static final String GetStoresForCompanyCountURL = ServerURL + "/stampitprovider/{companyid}/stores/count";
    public static final String GetBlobForIDURL = ServerURL + "/blob/{blobid}/content";

    //HTTP Constants
    public static final int HTTP_RESULT_OK = 200;

    //Permissions
    public static final String ACCESS_COARSE_LOCATION = "android.permission.ACCESS_COARSE_LOCATION";
    public static final String ACCESS_FINE_LOCATION = "android.permission.ACCESS_FINE_LOCATION";
}
