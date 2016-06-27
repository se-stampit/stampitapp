package com.jku.stampit.Services;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by user on 27/06/16.
 */
public class HttpGetByteRequest extends AsyncTask<String,Void,WebserviceReturnObject> {
    private static final int BUFFER_SIZE = 4096;

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
            int responseCode = connection.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK) {
                String fileName = "";
                String disposition = connection.getHeaderField("Content-Disposition");
                String contentType = connection.getContentType();
                int contentLength = connection.getContentLength();

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                InputStream is = null;
                try {
                    is = url.openStream ();
                    byte[] byteChunk = new byte[4096]; // Or whatever size you want to read in at a time.
                    int n;

                    while ( (n = is.read(byteChunk)) > 0 ) {
                        baos.write(byteChunk, 0, n);
                    }
                }
                catch (IOException e) {
                    System.err.printf ("Failed while reading bytes from %s: %s", url.toExternalForm(), e.getMessage());
                    e.printStackTrace ();
                    // Perform any other exception handling that's appropriate.
                }
                finally {
                    if (is != null) { is.close(); }
                }
                result.setBytes(baos.toByteArray());
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
        }
        return result;
    }
    protected void onPostExecute(Boolean result) {
    }
}
