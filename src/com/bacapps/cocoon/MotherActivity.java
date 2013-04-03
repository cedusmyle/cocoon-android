package com.bacapps.cocoon;

import com.bacapps.cocoon.config.Constant;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;

public class MotherActivity extends Activity{

	  protected static SharedPreferences sharePref;
	  protected SharedPreferences.Editor editor;
	  protected Activity motherActivity;
	  
	  @Override
	  protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        sharePref = getSharedPreferences(Constant.COCOON_PREFERENCES, MODE_PRIVATE);
	        motherActivity = this;
	        editor = sharePref.edit();
	  }

	  public void saveSharePref(String key, String value){
		editor.putString(key, value);
		editor.commit();
	  }
	  
	  public void saveShareBooleanPref(String key, Boolean value){
		editor.putBoolean(key, value);
		editor.commit();
	  }
	  
	  public Typeface font (){
		  return Typeface.createFromAsset(getAssets(), "font/Montserrat-Regular.ttf");
	  }
	  
	  
	  public static String getSharePref(String key){
		return sharePref.getString(key, "none");
	  }
	  
	  public Boolean getShareBooleanPref(String key){
		  return sharePref.getBoolean(key, false); 
	  }
	  public void clearSharePref(){
		editor.clear().commit();
	  }
	  
	  public void launchGiftListingsActivity(Activity currentActivity){
			Intent mainIntent = new Intent(currentActivity, GiftsListingActivity.class);
			currentActivity.finish();
			startActivity(mainIntent);
	  }
	  
}
