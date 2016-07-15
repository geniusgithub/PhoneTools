package com.geniusgithub.phonetools;

import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.geniusgithub.phonetools.provider.SmartHelper;
import com.geniusgithub.phonetools.provider.UserPreferenceProviderHelper;
//import com.geniusgithub.phonetools.provider.UserSimPreferenceObject;
import com.geniusgithub.phonetools.util.CommonLog;
import com.geniusgithub.phonetools.util.LogFactory;

public class ProviderFragment extends Fragment implements View.OnClickListener{

	  private static final CommonLog log = LogFactory.createLog();

	    private Context mContext;
	    private View mRootView;

	    private Button mBtnClean;
	    private Button mBtnSet;
	    private EditText mEditTextNumber;
	    private EditText mEditTextSernum;
	    
	    private Button mBtnGet;
	    private EditText mEditTextNumberEx;
	    private TextView mTextViewContent;
	    
	    
	    private SmartHelper mSmartHelper;

	    
	    @Override
	    public void onAttach(Activity activity) {
	        super.onAttach(activity);
	        mContext = activity;
	        mSmartHelper = new SmartHelper(mContext);
	    }

	    @Nullable
	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	        mRootView  =  LayoutInflater.from(mContext).inflate(R.layout.provider_layout, null);
	        return mRootView;
	    }

	    @Override
	    public void onViewCreated(View view, Bundle savedInstanceState) {
	        super.onViewCreated(view, savedInstanceState);
	        mBtnClean =  (Button) mRootView.findViewById(R.id.btn_clean);
	        mBtnClean.setOnClickListener(this);
	        mBtnSet = (Button) mRootView.findViewById(R.id.btn_set);
	        mEditTextNumber = (EditText) mRootView.findViewById(R.id.et_number);
	        mEditTextSernum = (EditText) mRootView.findViewById(R.id.et_sernum);
	        mBtnSet.setOnClickListener(this);
	        
	        mBtnGet = (Button) mRootView.findViewById(R.id.btn_get);
	        mEditTextNumberEx = (EditText) mRootView.findViewById(R.id.et_numberex);
	        mBtnGet.setOnClickListener(this);
	        
	        mTextViewContent = (TextView) mRootView.findViewById(R.id.tv_content);
	    }

	    @Override
	    public void onClick(View v) {
	        switch (v.getId()){
		        case R.id.btn_get:
		        	get();
		        	break;
		        case R.id.btn_set:
		        	set();
		        	break;
		        case R.id.btn_clean:
		        	clean();
		        	break;
	        }
	    }
	    
	    private void get(){
	    	String number = mEditTextNumberEx.getText().toString();

//	    	Cursor cursor = UserPreferenceProviderHelper.queryUserSimPreference(mContext, number);
//	    	if (cursor != null){
//	    		int count = cursor.getCount();
//	    		int index = 0;
//	    		log.i("queryUserSimPreference count = " + count);
//		    	StringBuffer stringBuffer = new StringBuffer();
//		    	stringBuffer.append("count = " + count + "\n");
//	    		while (cursor.moveToNext()) {
//					UserSimPreferenceObject object = UserPreferenceProviderHelper.newInstancefromCursor(cursor);
//					stringBuffer.append("index = " + index + "-->\n" + object.toString() + "\n");
//					index++;
//				}
//	    		
//	    		int subID = mSmartHelper.getLearnModeSim(number);
//	    		stringBuffer.append("subID = " + subID);
//	    		updateContetn(stringBuffer.toString());
//	    	}else{
//	    		updateContetn("queryUserSimPreference cursor = null...");
//	    	}
	    	
	    }
	    
	    
	    private void set(){
	    	String number = mEditTextNumber.getText().toString();
	    	if (TextUtils.isEmpty(number)){
	    		Toast.makeText(mContext, "number is empty!!!", Toast.LENGTH_SHORT).show();
	    		return ;
	    	}
	    	
	    	String seriNum = mEditTextSernum.getText().toString();
	    	if (TextUtils.isEmpty(seriNum)){
	    		Toast.makeText(mContext, "seriNum is empty!!!", Toast.LENGTH_SHORT).show();
	    		return ;
	    	}

	    	mSmartHelper.updateUsageData(number, seriNum);
	    	
	    	log.d("updateUsageData complete...");
	    	
	    }
	    
	    private void clean(){
	    	int count  = UserPreferenceProviderHelper.cleanUserSimPreference(mContext);
	    	updateContetn("clean count = " + count);
	    }
	    
	    
	    private void updateContetn(String value){
	    	mTextViewContent.setText(value);
	    }

}
