package com.jku.stampit.Services;

import android.content.pm.PackageInstaller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jku.stampit.data.User;
import com.jku.stampit.dto.LoginDTO;
import com.jku.stampit.dto.SessionTokenDTO;

/**
 * Created by Andreas on 09.05.16.
 */
public class UserManager {
    private static UserManager instance;
    private User user;
    private ObjectMapper jsonMapper = new ObjectMapper();
    private String sessionToken;

    public static UserManager getInstance() {
        if(instance == null)
            instance = new UserManager();
        return instance;
    }

    private UserManager() {
    }
    private boolean login(String authprovider, String token){
        LoginDTO login = new LoginDTO();
        login.setAuthprovider(authprovider);
        login.setToken(token);


        try {
            String resultJson = "";
            resultJson = WebService.getInstance().PutJson("", jsonMapper.writeValueAsString(login));
            if(resultJson == "")
                return false;

            this.sessionToken = jsonMapper.readValue(resultJson, SessionTokenDTO.class).getSessionToken();


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
