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
            WebService.WebServiceReturnObject result = WebService.getInstance().PutJson("", jsonMapper.writeValueAsString(login));
            if(result.getStatusCode() != Constants.HTTP_RESULT_OK)
                return false;

            this.sessionToken = jsonMapper.readValue(result.getReturnDataString(), SessionTokenDTO.class).getSessionToken();

        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
    }
    public boolean logout(){
        user = null;
        return true;
    }
}
