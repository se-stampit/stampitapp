package com.jku.stampit.Services;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * Created by user on 29/06/16.
 */
public class HttpDeleteRequest extends AsyncTask<String,Void,WebserviceReturnObject> {
    private Map<String,String> headerParams;
    public HttpDeleteRequest(Map<String,String> headerParams) {
        this.headerParams = headerParams;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected WebserviceReturnObject doInBackground( String... urls) {
        WebserviceReturnObject result = new WebserviceReturnObject();
        URL url = null;
        String urlString = urls[0];

        try {
            // create the HttpURLConnection
            url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // just want to do an HTTP PUT here
            connection.setRequestMethod("DELETE");
            if(headerParams != null){
                for(String key : headerParams.keySet()){
                    connection.setRequestProperty(key,headerParams.get(key));
                }
            }
            // give it 15 seconds to respond
            connection.setReadTimeout(15 * 1000);
            connection.connect();

            result.setStatusCode(connection.getResponseCode());
        } catch (Exception e) {
            e.printStackTrace();
            // throw e;
        } finally {
            // close the reader; this can throw an exception too, so
            // wrap it in another try/catch block.
        }
        return result;
    }

    protected void onPostExecute(Boolean result) {
    }
}
