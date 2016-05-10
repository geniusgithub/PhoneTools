package com.geniusgithub.phonetools;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.telecom.PhoneAccountHandle;
import android.telecom.TelecomManager;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.geniusgithub.phonetools.util.CommonLog;
import com.geniusgithub.phonetools.util.LogFactory;

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
                getInfo();
                break;
        }
    }

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
        
    		PackageInfo packageInfo1 = getPackageInfo(getActivity(), "com.motorola.android.providers.userpreferredsim");
    		PackageInfo packageInfo2 = getPackageInfo(getActivity(), "com.android.dialer");
    		String value = "userpreferredsim.packageInfo = " + packageInfo1;
    		value +=  "\ndialer.packageInfo = " + packageInfo2;
    		mTvPhoneManager.setText(value);
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
