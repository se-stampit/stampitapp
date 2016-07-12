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
 * Created by user on 11/05/16.
 */
public class HttpPutJsonRequest extends AsyncTask<String,Void,WebserviceReturnObject> {


    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected WebserviceReturnObject doInBackground( String... urls) {
        WebserviceReturnObject result = new WebserviceReturnObject();
        URL url = null;
        BufferedReader reader = null;
        OutputStreamWriter out = null;
        StringBuilder stringBuilder;
        String urlString = urls[0];
        String contentToSend = urls[1];

        try {
            // create the HttpURLConnection
            url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // just want to do an HTTP PUT here
            connection.setRequestMethod("PUT");

            // uncomment this if you want to write output to this url
            if(contentToSend != null && contentToSend.length() > 0) {
                connection.setDoOutput(true);
                out = new OutputStreamWriter(
                        connection.getOutputStream());
                out.write(contentToSend);
                out.close();
                out = null;
            }

            // give it 15 seconds to respond
            connection.setReadTimeout(15 * 1000);
            connection.connect();

            result.setStatusCode(connection.getResponseCode());
            BufferedReader br;
            if (200 <= connection.getResponseCode() && connection.getResponseCode() <= 299) {
                br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
            } else {
                br = new BufferedReader(new InputStreamReader((connection.getErrorStream())));
            }
            StringBuilder sb = new StringBuilder();
            String output;
            while ((output = br.readLine()) != null) {
                sb.append(output);
                result.setReturnString(sb.toString());
            }
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
            if(out != null)
            {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return result;
    }

    protected void onPostExecute(Boolean result) {
    }
}
