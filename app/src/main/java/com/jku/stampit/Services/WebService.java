package com.jku.stampit.Services;

import android.os.Looper;
import android.util.Log;

import org.apache.http.params.HttpConnectionParams;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * Created by user on 07/05/16.
 */
public class WebService {
    private static WebService instance;
    public static final Map<String,String> NO_HEADER_PARAM = null;
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
    public WebServiceReturnObject PostString(String server, Map<String,String> headParams, String json) {
        WebServiceReturnObject result = new WebServiceReturnObject();
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
            //conn.addRequestProperty("auth", UserManager.getInstance().getSessionToken());
            if(headParams != WebService.NO_HEADER_PARAM) {
                for (String key : headParams.keySet()) {
                    conn.addRequestProperty(key, headParams.get(key));
                }
            }
            OutputStream os = conn.getOutputStream();
            os.write(json.getBytes("UTF-8"));
            os.close();

            // read the response
            InputStream in = new BufferedInputStream(conn.getInputStream());
            result.setStatusCode(conn.getResponseCode());
            if(result.getStatusCode() == 200) {
                String res = org.apache.commons.io.IOUtils.toString(in, "UTF-8");
                JSONObject jsonObject = new JSONObject(res);
                result.setReturnData(jsonObject.toString().getBytes());
            }
            in.close();
            conn.disconnect();
        } catch (ProtocolException exception) {

        } catch (IOException exception) {

        }catch (JSONException exception) {

        }
        return result;
    }
    public WebServiceReturnObject PutJson(String server, Map<String,String> headParams, String json)
    {
        WebServiceReturnObject result = new WebServiceReturnObject();
        URL url = null;
        try {
            url = new URL(server);
            HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
            httpCon.setDoOutput(true);
            httpCon.setRequestMethod("PUT");
            if(headParams != WebService.NO_HEADER_PARAM) {
                for (String key : headParams.keySet()) {
                    httpCon.addRequestProperty(key, headParams.get(key));
                }
            }
            OutputStreamWriter out = new OutputStreamWriter(
                    httpCon.getOutputStream());
            out.write(json);
            out.close();
            InputStream in = new BufferedInputStream(httpCon.getInputStream());
            result.setStatusCode(httpCon.getResponseCode());
            if(result.getStatusCode() == 200) {
                String res = org.apache.commons.io.IOUtils.toString(in, "UTF-8");
                JSONObject jsonObject = new JSONObject(res);
                result.setReturnData(jsonObject.toString().getBytes());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public WebServiceReturnObject GetJson(String url) throws IOException, JSONException {
        WebServiceReturnObject result = new WebServiceReturnObject();
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            StringBuilder sb = new StringBuilder();
            int cp;
            while ((cp = rd.read()) != -1) {
                sb.append((char) cp);
            }
            result.setReturnData(sb.toString().getBytes());
            result.setStatusCode(200);
            //JSONObject json = new JSONObject(jsonText);
            //return json.toString();
        } catch (IOException exception) {

        }
        //catch (JSONException exception) {

        //}
        finally{
            is.close();
        }
        return result;
    }
    public class WebServiceReturnObject {
        private int statusCode = -1;
        private byte[] returnData;

        public byte[] getReturnData() {
            return returnData;
        }

        public void setReturnData(byte[] returnData) {
            this.returnData = returnData;
        }

        public int getStatusCode() {
            return statusCode;
        }

        public void setStatusCode(int statusCode) {
            this.statusCode = statusCode;
        }

        public String getReturnDataString() {
            if( returnData != null) {
                return new String(returnData);
            }
            return "";
        }
    }
}
