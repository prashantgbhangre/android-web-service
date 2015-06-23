package com.prashantb.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

/**
 * Created by Prashant Bhangare
 *  
 * Manifest Permission 
 * <uses-permission android:name="android.permission.INTERNET" /> 
 * <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" /> 
 * 
 * Call From Java File
 * 
 * WebService.makeHttpRequest("URL","Parameter","Method")
 * 
 * URL : 
 * http://your ip:port/path
 * 
 * Parameter : 
 * List<NameValuePair> mList = new ArrayList<NameValuePair>();
 * mList.add(new BasicNameValuePair("name", "value"));
 * 
 * Method : 
 * POST/GET
 * 
 * mWebService is returns true ( Data ) or false ( Null )
 *  
 */

public class WebService {

	public static String makeHttpRequest(String url,
			List<NameValuePair> params, String method) {
		InputStream is = null;
		String return_data = null;
		
		// Making HTTP request
		try {
			// check for request method
			if (method == "POST") {
				// request method is POST
				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(url);
				httpPost.setEntity(new UrlEncodedFormEntity(params));
				HttpResponse httpResponse = httpClient.execute(httpPost);
				HttpEntity httpEntity = httpResponse.getEntity();
				is = httpEntity.getContent();
				Log.i("OP", "input stream is " + is);
			} else if (method == "GET") {
				// request method is GET
				DefaultHttpClient httpClient = new DefaultHttpClient();
				String paramString = URLEncodedUtils.format(params, "utf-8");
				url += "?" + paramString;
				HttpGet httpGet = new HttpGet(url);
				httpGet.addHeader("accept", "application/json");
				HttpResponse httpResponse = httpClient.execute(httpGet);
				HttpEntity httpEntity = httpResponse.getEntity();
				is = httpEntity.getContent();
				Log.i("OP", "input stream is " + is);
			}
			return_data = inputStreamToString(is).toString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return return_data;
	}

	public static StringBuilder inputStreamToString(InputStream is) {
		try {
			String rLine = "";
			StringBuilder answer = new StringBuilder();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));

			try {
				while ((rLine = rd.readLine()) != null) {
					answer.append(rLine);
				}
			} catch (IOException e) {
			}
			return answer;
		} catch (Exception ex) {
		}
		return null;
	}
}
