package com.jku.stampit.Services;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by user on 11/05/16.
 */
public class HttpGetJsonRequest extends AsyncTask<String,Void,WebserviceReturnObject> {
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

        try {
            // create the HttpURLConnection
            url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // just want to do an HTTP GET here
            connection.setRequestMethod("GET");

            // uncomment this if you want to write output to this url
            //connection.setDoOutput(true);

            // give it 15 seconds to respond
            connection.setReadTimeout(15 * 1000);
            connection.connect();

            // read the output from the server
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            stringBuilder = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }
            result.setReturnString(stringBuilder.toString());
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
