package com.jku.stampit.Services;

import android.content.pm.PackageInstaller;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jku.stampit.data.StampCard;
import com.jku.stampit.data.User;
import com.jku.stampit.dto.LoginDTO;
import com.jku.stampit.dto.RegisterDTO;
import com.jku.stampit.dto.SessionTokenDTO;
import com.jku.stampit.dto.UserDTO;
import com.jku.stampit.dto.UserInfo;
import com.jku.stampit.utils.Constants;

import java.io.IOException;
import java.util.List;

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

    public interface UserManagerLoginCallback
    {
        public void LoginSuccessfull();
        public void LoginFailed();
    }

    private UserManager() {
    }
    public boolean login(final String authprovider, final String token, final UserInfo user ,final UserManagerLoginCallback callback){
        final ObjectMapper jsonMapper = new ObjectMapper();
        LoginDTO login = new LoginDTO();
        login.setAuthprovider(authprovider);
        login.setToken(token);
        Log.d("UserManager", "Login was called..");
        try {
            new HttpPutJsonRequest(){

                @Override
                protected void onPostExecute(WebserviceReturnObject result) {
                    try {
                        Log.d("UserManager", "Login, Statuscode: " + result.getStatusCode());
                        if (result.getStatusCode() == Constants.HTTP_RESULT_OK) {
                            SessionTokenDTO sessiontoken = jsonMapper.readValue(result.getReturnString(), SessionTokenDTO.class);
                            sessionToken = sessiontoken.getSessionToken();

                            if (callback != null)
                                callback.LoginSuccessfull();

                        } else if(result.getStatusCode() == Constants.HTTP_RESULT_NOT_AUTHORIZED){
                            RegisterDTO register = new RegisterDTO();
                            register.setAuthprovider(authprovider);
                            register.setToken(token);
                            register.setUser(user);
                            String json = jsonMapper.writeValueAsString(register);
                            Log.d("Register", "JSON: " + json);

                            new HttpPostJsonRequest(null)
                            {

                                @Override
                                protected  void onPostExecute(WebserviceReturnObject result)
                                {
                                    try {
                                        if (result.getStatusCode() == Constants.HTTP_RESULT_OK) {
                                            sessionToken = jsonMapper.readValue(result.getReturnString(), SessionTokenDTO.class).getSessionToken();
                                            if (callback != null)
                                                callback.LoginSuccessfull();
                                        }
                                    }
                                    catch (IOException ioe)
                                    {
                                        if(callback != null)
                                            callback.LoginFailed();
                                    }
                                }

                            }.execute(Constants.RegisterURL,json);

                        }
                        if (callback != null) {
                            callback.LoginSuccessfull();
                        }
                    }
                    catch (IOException ioe)
                    {
                        callback.LoginFailed();
                    }
                }
            }.execute(Constants.LoginURL, jsonMapper.writeValueAsString(login));


        }
        catch(Exception e){
            e.printStackTrace();
            callback.LoginFailed();
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
