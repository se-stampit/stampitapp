package com.jku.stampit.utils;

/**
 * Created by user on 09/05/16.
 */
public class Constants {

    //Schnittstelle
    //public static final String ServerURL = "https://stampit.azurewebsites.net/api";
    public static final String ServerURL = "http://stampit.azurewebsites.net/api";
    public static final String ScanStampURL = ServerURL + "/me/scan";
    public static final String GetUserDetailURL = ServerURL + "/me";
    public static final String GetMyStampCardsURL = ServerURL + "/me/stampcard";
    public static final String GetMyStampCardsCountURL = ServerURL + "/me/stampcard/count";
    public static final String RegisterURL = ServerURL + "/register";
    public static final String LoginURL = ServerURL + "/login";
    public static final String GetStampItProvidersURL = ServerURL + "/stampitprovider";
    public static final String GetStampItProviderCountURL = ServerURL + "/stampitprovider/count";
    public static final String GetStoresForCompanyURL = ServerURL + "/stampitprovider/{companyid}/stores";
    public static final String GetStoresForCompanyCountURL = ServerURL + "/stampitprovider/{companyid}/stores/count";
    public static final String GetBlobForIDURL = ServerURL + "/blob/{blobid}/content";
    public static final String DeleteStampCardURL = ServerURL + "/me/stampcard/{cardid}";

    //HTTP Constants
    public static final int HTTP_RESULT_DELETE_OK = 204;
    public static final int HTTP_RESULT_OK = 200;
    public static final int HTTP_RESULT_BAD_REQUEST = 400;
    public static final int HTTP_RESULT_NOT_AUTHORIZED = 401;

    //Permissions
    public static final String ACCESS_COARSE_LOCATION = "android.permission.ACCESS_COARSE_LOCATION";
    public static final String ACCESS_FINE_LOCATION = "android.permission.ACCESS_FINE_LOCATION";
}
