package com.capam.rest;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Iterator;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

/**
 * Created by acer on 7/10/13.
 */
public class RestHelpers {

    public static HttpClient getNewHttpClient() {
        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            ctx.init(null, new TrustManager[] { new CustomX509TrustManager() },
                    new SecureRandom());

            HttpClient client = new DefaultHttpClient();

            SSLSocketFactory ssf = new CustomSSLSocketFactory(ctx);
            ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            ClientConnectionManager ccm = client.getConnectionManager();
            SchemeRegistry sr = ccm.getSchemeRegistry();
            sr.register(new Scheme("https", ssf, 443));
            DefaultHttpClient sslClient = new DefaultHttpClient(ccm,
                    client.getParams());

            return sslClient;
        } catch (Exception e) {
            return null;
        }
    }

    public static JSONObject getJsonObjectFromMap(Map params) throws JSONException {

        //all the passed parameters from the post request
        //iterator used to loop through all the parameters
        //passed in the post request
        Iterator iter = params.entrySet().iterator();

        //Stores JSON
        JSONObject holder = new JSONObject();

        //using the earlier example your first entry would get email
        //and the inner while would get the value which would be 'foo@bar.com'
        //{ fan: { email : 'foo@bar.com' } }

        //While there is another entry
        while (iter.hasNext())
        {
            //gets an entry in the params
            Map.Entry pairs = (Map.Entry)iter.next();

            //creates a key for Map
            String key = (String)pairs.getKey();

            //Create a new map
            Map m = (Map)pairs.getValue();

            //object for storing Json
            JSONObject data = new JSONObject();

            //gets the value
            Iterator iter2 = m.entrySet().iterator();
            while (iter2.hasNext())
            {
                Map.Entry pairs2 = (Map.Entry)iter2.next();
                data.put((String)pairs2.getKey(), (String)pairs2.getValue());
            }

            //puts email and 'foo@bar.com'  together in map
            holder.put(key, data);
        }
        return holder;
    }

    public static HttpResponse makePOST(String uri, Map params){
        // Create a new HttpClient and Post Header
        HttpClient httpclient = RestHelpers.getNewHttpClient();
        HttpPost httppost = new HttpPost(uri);
        try {

            //convert parameters into JSON object
            JSONObject holder = RestHelpers.getJsonObjectFromMap(params);

            //passes the results to a string builder/entity
            StringEntity se = new StringEntity(holder.toString());

            //sets the post request as the resulting string
            httppost.setEntity(se);
            //sets a request header so the page receving the request will know what to do with it
            httppost.setHeader("Accept", "application/json");
            httppost.setHeader("Content-type", "application/json");

            return httpclient.execute(httppost);

        } catch (ClientProtocolException e) {
            return null;
        } catch (IOException e) {
            return null;
        } catch (JSONException e){
            return null;
        }
    }
}
