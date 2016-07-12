package com.jku.stampit.Services;

import android.os.AsyncTask;

import org.apache.commons.io.IOUtils;

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

            OutputStream os = connection.getOutputStream();
            os.write(contentToSend.getBytes("UTF-8"));
            os.close();

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
            //Connect
            //connection.connect();

            // read the response
            /*
            InputStream in = connection.getInputStream();
            String encoding = connection.getContentEncoding();
            encoding = encoding == null ? "UTF-8" : encoding;
            String body = IOUtils.toString(in, encoding);
            */
            //result.setReturnString(org.apache.commons.io.IOUtils.toString(in, "UTF-8"));

        } catch (Exception ex) {
            ex.printStackTrace();
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
