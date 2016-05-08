package com.jku.stampit.Services;

import android.os.Looper;

import org.apache.http.params.HttpConnectionParams;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by user on 07/05/16.
 */
public class WebService {
    private static WebService instance;
    public static WebService getInstance()
    {
        if (instance == null)
        {
            // Create the instance
            instance = new WebService();
        }
        return instance;
    }
    private WebService() {

    }

    /*
    Method which makes post request to serverurl and returns returned json object as string
     */
    public String PostJson(String server, String json) {
        String jsonResult = "";
        HttpURLConnection conn = null;
        URL url = null;

        try {
            url = new URL(server);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            OutputStream os = conn.getOutputStream();
            os.write(json.getBytes("UTF-8"));
            os.close();

            // read the response
            InputStream in = new BufferedInputStream(conn.getInputStream());
            String result = org.apache.commons.io.IOUtils.toString(in, "UTF-8");
            JSONObject jsonObject = new JSONObject(result);
            jsonResult = jsonObject.toString();

            in.close();
            conn.disconnect();
        } catch (ProtocolException exception) {

        } catch (IOException exception) {
            return "";
        }catch (JSONException exception) {
            return "";
        }
        return jsonResult;
    }
    public String readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        String returnString = "";
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));

            StringBuilder sb = new StringBuilder();
            int cp;
            while ((cp = rd.read()) != -1) {
                sb.append((char) cp);
            }
            returnString = sb.toString();

            //JSONObject json = new JSONObject(jsonText);
            //return json.toString();
        } catch (IOException exception) {

        }
        //catch (JSONException exception) {

        //}
        finally{
            is.close();
        }
        return returnString;
    }
}
