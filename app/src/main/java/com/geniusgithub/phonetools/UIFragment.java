package com.geniusgithub.phonetools;

import java.sql.Date;
import java.text.SimpleDateFormat;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.geniusgithub.calllog.PhoneNumberUtilsEx;
import com.geniusgithub.phonetools.util.CommonLog;
import com.geniusgithub.phonetools.util.LogFactory;
import com.geniusgithub.phonetools.util.ViewUtil;
import com.litesuits.common.utils.AppUtil;

public class UIFragment extends Fragment implements View.OnClickListener{

	 private static final CommonLog log = LogFactory.createLog();
	 private Context mContext;
	 private View mRootView;
	 private Button mBtnShadow;
	    
	 private View mFloatButton;
	 
	 private EditText mErgenumberEditText;
	 private Button mBtnGetErgenumber;
	 private TextView mTVcontent;
	 
	 private Button mBtnTest;
	 private View shadowView;
	 private View circle1;
	 private View circle2;
	 
	 private ImageButton mImageButton;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView  =  LayoutInflater.from(mContext).inflate(R.layout.ui_layout, null);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mBtnShadow = (Button) mRootView.findViewById(R.id.btn_shadow1);
        Drawable drawable = mContext.getDrawable(R.drawable.call_bar_dial2_subordinate);
        int width = drawable.getMinimumWidth();
        int height = drawable.getMinimumHeight();
        drawable.setBounds(0, 0, width, height);
        mBtnShadow.setCompoundDrawables(drawable, null, null, null);


        
        mFloatButton = view.findViewById(R.id.floating_action_button);
        ViewUtil.setupFloatingActionButton(mFloatButton, getResources().getDimensionPixelSize(R.dimen.floating_action_button_translation_z));
        
        
        mErgenumberEditText = (EditText) mRootView.findViewById(R.id.et_getErinumber);
        mBtnGetErgenumber = (Button) mRootView.findViewById(R.id.btn_getErinumber);		
        mBtnGetErgenumber.setOnClickListener(this);
        mTVcontent = (TextView) mRootView.findViewById(R.id.tv_content);
        
        mBtnTest = (Button) mRootView.findViewById(R.id.test_action_button);
        mBtnTest.setOnClickListener(this);
        
        circle1 = mRootView.findViewById(R.id.v_circle2);
        circle2 = mRootView.findViewById(R.id.v_circle2);
  //      shadowView =  mRootView.findViewById(R.id.ll_shadows);
  //      shadowView.setOnClickListener(this);
        
        mImageButton = (ImageButton) mRootView.findViewById(R.id.iv_more);
        mImageButton.setOnClickListener(this);
    }

	@Override
	public void onClick(View v) {
		switch(v.getId()){
			case R.id.btn_getErinumber:
				getErinumber();
				break;
			case R.id.test_action_button:
				Toast.makeText(mContext, "onCLick!!!", Toast.LENGTH_SHORT).show();
				broadcastImsRegistrationState();
				break;
			case R.id.iv_more:
				Dialog dialog = createMulDialog();
				dialog.show();
				break;
		}
	}
	
	private void getErinumber(){
		
		String number = mErgenumberEditText.getText().toString();
		log.i("getErinumber = " + number);

		StringBuffer stringBuffer = new StringBuffer();
		boolean selfFlag = PhoneNumberUtilsEx.isEmergencyNumber(number, stringBuffer);
		boolean systemFlag = android.telephony.PhoneNumberUtils.isEmergencyNumber(number);
		
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append("selfFlag = "  + selfFlag + "\nsystemFlag = " + systemFlag);


		mTVcontent.setText(sBuffer.toString() + "\n" + stringBuffer.toString());
		
	    SimpleDateFormat simpleformat=new SimpleDateFormat("yy/MM/dd HH:mm:ss");
	    
//		Date date = new Date(1461748395386L);
//	    String dataString = simpleformat.format(date);
//	    
//	    
//		Date date2 = new Date(1461748542522L);
//		String data2String = simpleformat.format(date2);
//		
//		
//		
//		
//		Date date3 = new Date(1461748786373L);
//		String data3String = simpleformat.format(date3);
//		
//		
//		Date date4 = new Date(1461748799308L);
//		String data4String = simpleformat.format(date4);
//		
//		
//		mTVcontent.setText("data1 = " + dataString + "\ndata2String = " + data2String + "\ndata3String = " + data3String + "\nndata3String = " + data4String);
//	
		Date date0 = new Date(1461841091045L);
		String data0String = simpleformat.format(date0);
		mTVcontent.setText("data0String = " + data0String);
		
		
		try {
			Thread.sleep(10 * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


    
	
    public static final String ACTION_IMS_REGISTRATION = "com.verizon.net.IMS_REGISTRATION";
    public static final String EXTRA_STATE = "state";
	  public static final String EXTRA_STATE_LTE_REGISTERED = "lte_registered";
	  public static final String EXTRA_STATE_NOT_REGISTERED = "not_registered";
	private  void broadcastImsRegistrationState() {
		log.i("broadcastImsRegistrationState");
	          Intent intent = new Intent(ACTION_IMS_REGISTRATION);
	          intent.putExtra(EXTRA_STATE, EXTRA_STATE_LTE_REGISTERED); 
	         mContext.sendBroadcast(intent);
	  }
	
	
	
	public Dialog createMulDialog(){
		  AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setIcon(R.drawable.ic_launcher);
        
        String items[] = getResources().getStringArray(R.array.unknow_calllog__more_choices);

        builder.setItems(items, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                Toast.makeText(getActivity(), "which = " + which, Toast.LENGTH_SHORT).show();;
            }
        });
        return builder.create();
	}
}
