package com.jku.stampit.Services;

import android.content.pm.PackageInstaller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jku.stampit.data.User;
import com.jku.stampit.dto.LoginDTO;
import com.jku.stampit.dto.SessionTokenDTO;
import com.jku.stampit.utils.Constants;

/**
 * Created by Andreas on 09.05.16.
 */
public class UserManager {
    private static UserManager instance;
    private User user;

    private String sessionToken;

    public static UserManager getInstance() {
        if(instance == null)
            instance = new UserManager();
        return instance;
    }

    private UserManager() {
    }
    private boolean login(String authprovider, String token){
        ObjectMapper jsonMapper = new ObjectMapper();
        LoginDTO login = new LoginDTO();
        login.setAuthprovider(authprovider);
        login.setToken(token);
        try {
            WebService.WebServiceReturnObject result = WebService.getInstance().PutJson(Constants.LoginURL,WebService.NO_HEADER_PARAM,jsonMapper.writeValueAsString(login));
            if(result.getStatusCode() == Constants.HTTP_RESULT_BAD_REQUEST)
                return false;
            if(result.getStatusCode() == Constants.HTTP_RESULT_NOT_AUTHORIZED){
                //TODO try to register User
                return true;
            }
            if(result.getStatusCode() != Constants.HTTP_RESULT_OK)
                return false;

            this.sessionToken = jsonMapper.readValue(result.getReturnDataString(), SessionTokenDTO.class).getSessionToken();
            //TODO get user information

        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private boolean register(User newUser){
        return false;
    }
    public boolean logout(){
        user = null;
        return true;
    }

    public String getSessionToken() {
        return sessionToken;
    }
}
