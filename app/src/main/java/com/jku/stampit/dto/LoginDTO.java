package com.jku.stampit.dto;

/**
 * Created by Andreas on 09.05.16.
 */
public class LoginDTO {

    public LoginDTO(String authProvider, String token) {
        this.authprovider = authProvider;
        this.token = token;
    }
    private String authprovider,token;

    public String getAuthprovider() {
        return authprovider;
    }

    public void setAuthprovider(String authprovider) {
        this.authprovider = authprovider;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
