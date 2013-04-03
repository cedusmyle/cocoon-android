package com.bacapps.cocoon.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import com.bacapps.cocoon.R;
import com.bacapps.cocoon.models.Gift;


public class GiftAdapter extends ArrayAdapter<Gift> {

	Context context; 
	int layoutResourceId; 
	int gitTitleId; 
	ArrayList<Gift> data = null;
	
	public GiftAdapter(Context context, int layoutResourceId, int gitTitleId, ArrayList<Gift> data) {
		super(context, gitTitleId, gitTitleId, data);
		this.context = context;
		this.layoutResourceId = layoutResourceId;
		this.gitTitleId = gitTitleId;
		this.data = data;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		 View row = convertView;
		 GiftHolder holder = null;
		 
		if ( row == null ){
			LayoutInflater inflater =  ((Activity)context).getLayoutInflater();
			holder = new GiftHolder();
			holder.senderTextView = (TextView) row.findViewById(R.id.senderTextViewId);
			holder.tapCountTextView = (TextView) row.findViewById(R.id.TapCountTextViewId);
			holder.giftImageView = (ImageView) row.findViewById(R.id.giftImageViewId);
			row.setTag(holder);
			
			
		} else {
			holder = (GiftHolder)row.getTag();
		}
		
		if (data.get(position) != null) {
		  Gift gift =  data.get(position);
		  holder.senderTextView.setText("By "+gift.getSenderId());
		  holder.tapCountTextView.setText("taps remaining: "+gift.getTapCount());
		}
		
		return row;
	}
	
	static class GiftHolder {
		TextView senderTextView;
		TextView tapCountTextView;
		ImageView giftImageView;
	}

}
