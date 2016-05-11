package com.jku.stampit.Services;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * Created by user on 11/05/16.
 */
public class HttpPostJsonRequest extends AsyncTask<String,Void,WebserviceReturnObject> {

    public String contentToSend = "";
    private Map<String,String> headerParams;
    public HttpPostJsonRequest(Map<String,String> headerParams) {
        this.headerParams = headerParams;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected WebserviceReturnObject doInBackground(String... urls) {
        WebserviceReturnObject result = new WebserviceReturnObject();
        URL url = null;
        BufferedReader reader = null;
        StringBuilder stringBuilder;
        String urlString = urls[0];
        contentToSend = urls[1];
        try {
            // create the HttpURLConnection
            url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // just want to do an HTTP POST here
            connection.setRequestMethod("POST");
            if(headerParams != null){
                for(String key : headerParams.keySet()){
                    connection.setRequestProperty(key,headerParams.get(key));
                }
            }

            // uncomment this if you want to write output to this url
            //connection.setDoOutput(true);

            // give it 15 seconds to respond
            connection.setReadTimeout(15 * 1000);

            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setDoOutput(true);
            connection.setDoInput(true);

            //Connect
            connection.connect();

           OutputStream os = connection.getOutputStream();
            os.write(contentToSend.getBytes("UTF-8"));
            os.close();

            // read the response
            /*InputStream in = new BufferedInputStream(connection.getInputStream());*/
            //result.setReturnString(org.apache.commons.io.IOUtils.toString(in, "UTF-8"));
            result.setStatusCode(connection.getResponseCode());
        } catch (Exception e) {
            e.printStackTrace();
            // throw e;
        } finally {
            // close the reader; this can throw an exception too, so
            // wrap it in another try/catch block.
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
        return result;
    }

    protected void onPostExecute(Boolean result) {
    }
}
