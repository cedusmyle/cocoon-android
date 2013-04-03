package com.bacapps.coccon.api;

import com.bacapps.cocoon.MotherActivity;
import com.bacapps.cocoon.config.Constant;
import com.loopj.android.http.*;

public class ApiClient {
  //private static final String BASE_URL = "http://www.policolors.com/";
 
  
  private static AsyncHttpClient client = new AsyncHttpClient();
  
  public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
	  client.addHeader("Accept", "application/json");
	  addAuthToken();
	  client.get(getAbsoluteUrl(url), params, responseHandler);
  }
  
  public static void post(String url, 	RequestParams params, AsyncHttpResponseHandler responseHandler) {
	  client.addHeader("Accept", "application/json");
	  addAuthToken();
      client.post(getAbsoluteUrl(url), params, responseHandler);
  }
  
  public static void put(String url, 	RequestParams params, AsyncHttpResponseHandler responseHandler) {
	  client.addHeader("Accept", "application/json");
	  addAuthToken();
      client.put(getAbsoluteUrl(url), params, responseHandler);
  }
  
  private static String getAbsoluteUrl(String relativeUrl) {
      return Constant.BASE_URL + relativeUrl;
  }
  
  public static void addAuthToken(){
	   if (MotherActivity.getSharePref("auth_token") != "none") {
		   client.addHeader("auth_token", MotherActivity.getSharePref("auth_token"));
	   }
  }
}
