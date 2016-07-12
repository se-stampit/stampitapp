package com.jku.stampit.Services;

import android.app.ProgressDialog;
import android.content.pm.PackageInstaller;
import android.util.Log;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jku.stampit.StampItApplication;
import com.jku.stampit.data.StampCard;
import com.jku.stampit.data.User;
import com.jku.stampit.dto.LoginDTO;
import com.jku.stampit.dto.RegisterDTO;
import com.jku.stampit.dto.ResponseMessageDTO;
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
    public interface LoginCompletionHandler {
        public void complete(SessionTokenDTO token, WebserviceReturnObject result);
    }
    private static UserManager instance;
    private User user;
    private String accessToken = "";
    private String sessionToken = ""; //Token for stampit

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
    public boolean login(final LoginDTO login ,final LoginCompletionHandler callback){

        Log.d("UserManager", "Login was called..");
        try {
            // create the HttpURLConnection
            String token = "";
            ObjectMapper mapper = new ObjectMapper();
            try {
                String userString = mapper.writeValueAsString(user);
                new HttpPutJsonRequest() {
                    @Override
                    protected void onPostExecute(WebserviceReturnObject result) {
                        ObjectMapper mapper = new ObjectMapper();
                        SessionTokenDTO token = new SessionTokenDTO();
                        try {
                            if (result.getStatusCode() == Constants.HTTP_RESULT_OK) {
                                token = mapper.readValue(result.getReturnString(), SessionTokenDTO.class);
                                sessionToken = token.getSessionToken();
                            } else {
                                ResponseMessageDTO resp = mapper.readValue(result.getReturnString(), ResponseMessageDTO.class);
                            }
                        } catch(Exception ex) {

                        }
                        callback.complete(token, result);
                    }
                }.execute(Constants.LoginURL,userString);
            } catch (Exception ex) {

            }

            //register(loginDTO, callback);

/*
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

*/
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void register(RegisterDTO regDTO, final LoginCompletionHandler completion) {
        // create the HttpURLConnection
        String token = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            String userString = mapper.writeValueAsString(regDTO);
            new HttpPostJsonRequest(null) {
                LoginCompletionHandler handler = completion;
                SessionTokenDTO token = new SessionTokenDTO();

                @Override
                protected void onPostExecute(WebserviceReturnObject result) {
                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        if (result.getStatusCode() == Constants.HTTP_RESULT_OK) {
                            SessionTokenDTO resp = mapper.readValue(result.getReturnString(), SessionTokenDTO.class);
                            sessionToken = resp.getSessionToken();
                        } else if(result.getStatusCode() == 400 || result.getStatusCode() == 401) {
                            ResponseMessageDTO resp = mapper.readValue(result.getReturnString(), ResponseMessageDTO.class);
                        } else {
                            //Unknown error
                        }
                    } catch(Exception ex) {
                        String err = ex.getLocalizedMessage();
                    }
                    handler.complete(token, result);
                }

            }.execute(Constants.RegisterURL,userString);
        } catch (Exception ex) {
            String err = ex.getLocalizedMessage();
        }
    }
    public boolean logout(){
        user = null;
        return true;
    }

    public String getSessionToken() {
        return sessionToken;
    }
    public String getAccessToken() {
        return accessToken;
    }
}
