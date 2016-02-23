package com.geniusgithub.phonetools;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog.Calls;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.geniusgithub.phonetools.util.CommonLog;
import com.geniusgithub.phonetools.util.LogFactory;

public class CalllogFragment extends Fragment implements View.OnClickListener{

	 private static final CommonLog log = LogFactory.createLog();
	 private Context mContext;
	 private View mRootView;
	 private Button mBtnCalllogButton;
	 private TextView mTextView;
	    
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView  =  LayoutInflater.from(mContext).inflate(R.layout.calllog_layout, null);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTextView = (TextView) mRootView.findViewById(R.id.tv_calllog);
        mBtnCalllogButton = (Button) mRootView.findViewById(R.id.btn_calllog);
        mBtnCalllogButton.setOnClickListener(this);
    }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_calllog:
			getUnreadMissCalllogInfo();
			break;

		default:
			break;
		}
		
		
	}
	
	private void getUnreadMissCalllogInfo(){
		long time1 = System.currentTimeMillis();
		 Uri uri = Calls.CONTENT_URI;
		 uri = uri.buildUpon().appendQueryParameter(Calls.LIMIT_PARAM_KEY, Integer.toString(2)).build();
		 
		Cursor mCursor = mContext.getContentResolver().query(uri, new String[] {Calls.NUMBER,
		Calls.TYPE, Calls.NEW, Calls.IS_READ}, getSelection(), null, Calls.DEFAULT_SORT_ORDER);     

		StringBuffer sBuffer = new StringBuffer();
		if (mCursor != null){
			
			int count = mCursor.getCount();
		
			sBuffer.append("cursor.getCount = " + count);
			if (mCursor.moveToFirst()){
				String value = CallLogInfoToString(mCursor);
				int index = 0;
				sBuffer.append("\nCallLogInfoToString index = " + index + "  " + value);
				while(mCursor.moveToNext()){
				    value = CallLogInfoToString(mCursor);
				    index++;
				    sBuffer.append("\nCallLogInfoToString index = " + index + "  " + value);
				}
			}

			mCursor.close();
		}
		mTextView.setText(sBuffer.toString());
		long time2 = System.currentTimeMillis();
		log.i(sBuffer.toString());
		log.i("getCalllogInfo cost time:"  +(time2 - time1));
	}
	
	
    public static String getSelection() {
        StringBuilder builder = new StringBuilder();
        builder.append(Calls.TYPE + "=" + Calls.MISSED_TYPE);
        builder.append(" and ");
        builder.append(Calls.NEW + "=" + 1);

        return builder.toString();
    }
	
    private String CallLogInfoToString(Cursor cursor){
    	 	if (cursor != null){  
        		String number = cursor.getString(0);
          	    int callType = cursor.getInt(1);
          	    int isNew = cursor.getInt(2);
        		int isRead = cursor.getInt(3);

                String valueString =  "number = " + number + ", callType = " + callType + ", isNew = " + isNew + ", isRead = " + isRead;
                return valueString;
        	}
    	 	return "null";
    }
    
	

}
