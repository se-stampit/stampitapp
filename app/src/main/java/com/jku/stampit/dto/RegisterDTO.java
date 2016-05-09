package com.jku.stampit.dto;

/**
 * Created by Andreas on 09.05.16.
 */
public class RegisterDTO {
    private String authprovider,token;
    private UserDTO user;

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

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }
}
