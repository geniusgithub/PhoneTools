package com.geniusgithub.calllog;

import com.geniusgithub.phonetools.R;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class CallLogListItemViewHolder extends RecyclerView.ViewHolder{

	public TextView mDate;
	public TextView mNumber;
	
	public CallLogSearchListItemView mItemView;
	
	public CallLogListItemViewHolder(View itemView) {
		super(itemView);
		mItemView = (CallLogSearchListItemView) itemView;
		mNumber = (TextView) itemView.findViewById(R.id.number);
		mDate = (TextView) itemView.findViewById(R.id.date);
	}

	
	public void setDate(String date){
		mDate.setText(date);
	}
	
	public void setNumber(String number){
		mNumber.setText(number);
	}
}
