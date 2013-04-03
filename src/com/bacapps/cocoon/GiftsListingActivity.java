package com.bacapps.cocoon;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bacapps.coccon.api.ApiClient;
import com.bacapps.cocoon.adapters.GiftAdapter;
import com.bacapps.cocoon.models.Gift;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;

public class GiftsListingActivity extends MotherActivity {
	
	private ListView giftListView;
	private ArrayList<Gift> giftsData = new ArrayList<Gift>();
	private GiftAdapter giftAdapter;
	private Activity giftListingActivity;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gifts_listing);
		giftListingActivity = this;
		giftListView = (ListView)findViewById(R.id.giftListViewId);
		
		getGiftsViewViaAPI();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_gifts_listing, menu);
		return true;
	}
		
	public void getGiftsViewViaAPI(){

		ApiClient.get("gifts.json", null , new JsonHttpResponseHandler(){
			
			@Override
			public void onSuccess(int code, JSONArray response) {
				// TODO Auto-generated method stub
				super.onSuccess(code, response);
				parseJsonGiftsArray(response);
				if (!giftsData.isEmpty()) {
				  giftAdapter = new GiftAdapter(giftListingActivity, R.layout.gift_listview_row, R.id.senderTextViewId, giftsData);
				}
				System.out.println("getGiftsViewAPI() SUCESS");
				System.out.println("getGiftsViewAPI() SUCESS"+response.toString());
			}
			
		});
	}

	public void parseJsonGiftsArray(JSONArray giftsJsonArray){
		JSONObject currenJsonGift;
		for (int i = 0; i < giftsJsonArray.length(); i++) {	
			try {
				currenJsonGift =  (JSONObject) giftsJsonArray.get(i);
				giftsData.add(new Gift(currenJsonGift.get("sender_id").toString(), currenJsonGift.get("asset_id").toString(), currenJsonGift.get("send_at").toString(), currenJsonGift.get("tap_count").toString(), currenJsonGift.get("wrap").toString()));
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
				
		}
	}
}
