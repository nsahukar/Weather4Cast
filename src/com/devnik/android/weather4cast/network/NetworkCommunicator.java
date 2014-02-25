package com.devnik.android.weather4cast.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

public class NetworkCommunicator {
	// base URL
	private static final String baseURL = "http://api.openweathermap.org/data/2.5/weather?q=%city%,%country%";

	/*
	 * function to create request URL from bae URL
	 */
	private static String createRequestURL(final String city,
			final String country) {
		String requestURL = baseURL.replace("%city%", city);
		requestURL = requestURL.replace("%country%", country);

		return requestURL;
	}

	/*
	 * function to request the openweathermap web service to get the weather
	 * forecast
	 */
	public static String getWeather4Cast(final String city, final String country) {
		final String requestURL = createRequestURL(city, country);
		
		InputStream inputStream = null;
		String jsonResponse = "";
		
		// Making http request
		try {
			// defaultHttpClient
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(requestURL);
			
			/*
			 *  The request I'm using does not require post parameters.
			 *  Following code snippet is commented because of the same reason.
			 *  It is just to show you how you can pass post parameters. Here goes...
			 */

			/*
			 
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("tag", _login_tag_));
			params.add(new BasicNameValuePair("email", _email_));
			params.add(new BasicNameValuePair("password", _password_));
			httpPost.setEntity(new UrlEncodedFormEntity(params));
 
			 */
			
			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			inputStream = httpEntity.getContent();
		} catch(UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch(ClientProtocolException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    inputStream, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "n");
            }
            inputStream.close();
            jsonResponse = sb.toString();
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }
 
        // return JSON String
        return jsonResponse;
	}
}
