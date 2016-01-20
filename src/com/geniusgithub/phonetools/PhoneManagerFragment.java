package com.geniusgithub.phonetools;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.telecom.PhoneAccountHandle;
import android.telecom.TelecomManager;
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
            case R.id.btn_sub:
                getInfo();
                break;
        }
    }

    private void getInfo(){

        TelecomManager telecomManager = getTelecomManager();
        List<PhoneAccountHandle> mPhoneAccountHandle = telecomManager.getAllPhoneAccountHandles();

        if(mPhoneAccountHandle!=null) {
            int size = mPhoneAccountHandle.size();
            log.i("getAllPhoneAccountHandles size=" + size);
        }
    }



    private TelephonyManager getTelephonyManager() {
        return (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
    }

    private TelecomManager getTelecomManager() {
        return (TelecomManager) mContext.getSystemService(Context.TELECOM_SERVICE);
    }


}
