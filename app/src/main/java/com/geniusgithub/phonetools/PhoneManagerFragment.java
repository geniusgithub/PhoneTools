package com.geniusgithub.phonetools;

import android.Manifest.permission;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.CallLog.Calls;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.telecom.PhoneAccountHandle;
import android.telecom.TelecomManager;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.geniusgithub.phonetools.util.CommonLog;
import com.geniusgithub.phonetools.util.LogFactory;
import com.geniusgithub.phonetools.util.PermissionsUtil;

import java.util.List;

public class PhoneManagerFragment extends Fragment implements View.OnClickListener{

    private static final CommonLog log = LogFactory.createLog();

    private Context mContext;
    private View mRootView;
    private TextView mTvPhoneManager;
    private Button mBtnPhoneManager;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView  =  LayoutInflater.from(mContext).inflate(R.layout.phonemanager_layout, null);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTvPhoneManager = (TextView) view.findViewById(R.id.tv_phoneManager);
        mBtnPhoneManager = (Button) view.findViewById(R.id.btn_phoneManager);
        mBtnPhoneManager.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_phoneManager:
             //   getInfo();
             //   test();
            //	sendnotify();
            	getCallInfo();
                break;
        }
    }
    
    private void getCallInfo(){
    	  TelecomManager mTelecomManager = getTelecomManager();
          List<PhoneAccountHandle> mPhoneAccountHandle = mTelecomManager.getAllPhoneAccountHandles();
          if (mPhoneAccountHandle != null){
        	  mTvPhoneManager.setText("mPhoneAccountHandle.size = " + mPhoneAccountHandle.size());
          }else{
        		mTvPhoneManager.setText("mPhoneAccountHandle = null");
          }
    }

    @TargetApi(23)
	private void getInfo(){
//        TelecomManager telecomManager = getTelecomManager();
//        List<PhoneAccountHandle> mPhoneAccountHandle = telecomManager.getAllPhoneAccountHandles();
//
//        if(mPhoneAccountHandle!=null) {
//            int size = mPhoneAccountHandle.size();
//            log.i("getAllPhoneAccountHandles size=" + size);
//        }
        
//        TelephonyManager telephonyManager = getTelephonyManager();
//        int state1 = -1;
//        int state2 = -1;
//        int sub1 = -1;
//        int sub2 = -1;
//        int subid0[] = SubscriptionManager.getSubId(0);
//        int subid1[] = SubscriptionManager.getSubId(1);
//        String seri1 = "null";
//        String seri2 = "null";
//        if (subid0 != null && subid0.length > 0){
//        	sub1 = subid0[0];
//        	state1 =  telephonyManager.getCallState(sub1);
//        	seri1 =  telephonyManager.getSimSerialNumber(sub1);
//        }
//        if (subid1 != null && subid1.length > 0){
//        	sub2 = subid1[0];
//        	state2 =  telephonyManager.getCallState(sub2);
//        	seri2 =  telephonyManager.getSimSerialNumber(sub2);
//        }
//        
//        String value = "sub1 = " + sub1 + ", seri1 = "  + seri1 + "\nsub2 = " + sub2 + ", seri2 = " + seri2;
//        value += "\ntests1 = " + telephonyManager.getSimSerialNumber(1) + "\ntests2 = " + telephonyManager.getSimSerialNumber(2);
//        mTvPhoneManager.setText(value);
//        
        
//    		PackageInfo packageInfo1 = getPackageInfo(getActivity(), "com.motorola.android.providers.userpreferredsim");
//    		PackageInfo packageInfo2 = getPackageInfo(getActivity(), "com.android.dialer");
//    		String value = "userpreferredsim.packageInfo = " + packageInfo1;
//    		value +=  "\ndialer.packageInfo = " + packageInfo2;
//    		mTvPhoneManager.setText(value);
    	
    	String value = "null";
    	if (PermissionsUtil.sIsAtLeastM){
    		TelecomManager telecomManager = getTelecomManager();
        	PhoneAccountHandle phoneAccountHandle = telecomManager.getUserSelectedOutgoingPhoneAccount();
        	if (phoneAccountHandle != null){
        		value = "id = " + phoneAccountHandle.getId();	
        	}
    	}
    
    	
    	mTvPhoneManager.setText(value);
    }
    
    private static final String[] _PROJECTION_INTERNAL = new String[] {
        Calls._ID,                          // 0
        Calls.NUMBER,                       // 1
        Calls.IS_READ,                       // 2
        "conference_call_id"
    };
    
    public static final int ID = 0;
    public static final int NUMBER = 1;
    public static final int CONVER_NUMBER = 2;
    public static final int CONFERENCE = 3;
    
    public  void test(){
    	  Uri uri = CallLog.Calls.CONTENT_URI;
          Uri queryUri = uri.buildUpon().appendQueryParameter(CallLog.Calls.LIMIT_PARAM_KEY, Integer.toString(10)).build();
          String conString = null;
          Cursor cursor = getActivity().getContentResolver().query(queryUri, _PROJECTION_INTERNAL, null, null, null);
          if (cursor != null){
        	  cursor.moveToFirst();
        	  conString = cursor.getString(CONVER_NUMBER);
        	  log.i("conString = " + conString);
        	  
        	  long confer_call_id= cursor.getLong(CONFERENCE);
        	  log.i("confer_call_id = " + confer_call_id);
        	  cursor.close();
          }
          
         boolean isequal = TextUtils.equals(conString, null);
         log.i("isequal = " + isequal);
    }
    
    public void sendnotify() {
        Notification notification = new Notification(R.drawable.volte_close, "通知", System.currentTimeMillis());
        Intent intent = new Intent(mContext, APIActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(getActivity(), 100, intent, 0);
        notification.setLatestEventInfo(mContext, "通知标题", "通知正文", contentIntent);
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        NotificationManager manager = (NotificationManager)mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(100, notification);
    }

    public static boolean isConvertNumberExistInCalllog(Context context){
        Uri uri = CallLog.Calls.CONTENT_URI;
        Uri queryUri = uri.buildUpon().appendQueryParameter(CallLog.Calls.LIMIT_PARAM_KEY, Integer.toString(1)).build();
        Cursor cursor = context.getContentResolver().query(queryUri, null, null, null, null);
        if (cursor != null){
            try {
                cursor.getColumnIndexOrThrow("CACHED_CONVERT_NUMBER");
                return true;
            }catch (IllegalArgumentException e){
                return false;
            } finally {
                cursor.close();
            }
        }
        return false;

    }
	
    public PackageInfo getPackageInfo(Context context, String packageString) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = packageManager.getPackageInfo(packageString, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageInfo;
    }


    private TelephonyManager getTelephonyManager() {
        return (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
    }

    private TelecomManager getTelecomManager() {
        return (TelecomManager) mContext.getSystemService(Context.TELECOM_SERVICE);
    }


}
