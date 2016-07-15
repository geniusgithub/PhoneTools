package com.geniusgithub.phonetools;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.geniusgithub.phonetools.model.SIMInfo;
import com.geniusgithub.phonetools.util.CommonLog;
import com.geniusgithub.phonetools.util.LogFactory;
import com.geniusgithub.phonetools.util.SubscriptionManageReflect;

@SuppressLint("NewApi")
public class SubscriptionInfoFragment extends Fragment implements View.OnClickListener{

    private static final CommonLog log = LogFactory.createLog();

    private Context mContext;
    private View mRootView;
    private TextView mTvSub;
    private Button mBtnSub;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView  =  LayoutInflater.from(mContext).inflate(R.layout.subinfo_layout, null);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTvSub = (TextView) view.findViewById(R.id.tv_sub);
        mBtnSub = (Button) view.findViewById(R.id.btn_sub);
        mBtnSub.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_sub:
                getSub();
            //    getInfoByDatabase();
                break;
        }
    }

    private void getSub(){
        SubscriptionManager subscriptionManager = SubscriptionManager.from(mContext);

        int sub1 = SubscriptionManageReflect.static_getSubId(0);
        int sub2 = SubscriptionManageReflect.static_getSubId(1);

        SubscriptionInfo info1 = subscriptionManager.getActiveSubscriptionInfo(sub1);
        SubscriptionInfo info2 = subscriptionManager.getActiveSubscriptionInfo(sub2);

        String value = "slotid = 0\n" + getSubInfo(info1) + "\n======================\nslotid = 1\n" + getSubInfo(info2);
        mTvSub.setText(value);
    }

    public String getSubInfo(SubscriptionInfo info){
        if (info == null){
            return "null";
        }

        StringBuffer sBuffer = new StringBuffer();
        sBuffer.append("getCountryIso = " + info.getCountryIso() +
                        "\ngetCarrierName = " + info.getCarrierName() +
                        "\ngetDisplayName = " + info.getDisplayName() +
                        "\ngetIccId = " + info.getIccId() +
                        "\ngetIconTint() = " + info.getIconTint() +
                        "\ngetMcc = " + info.getMcc() +
                        "\ngetMnc = " + info.getMnc() +
                        "\ngetNumber = " + info.getNumber() +
                        "\ngetSimSlotIndex = " + info.getSimSlotIndex() +
                        "\ngetSubscriptionId = " + info.getSubscriptionId());

        return sBuffer.toString();

    }
    
    
//    public void getInfoByDatabase(){
//    	SIMInfo sim0 = SIMInfo.getSIMInfoBySlot(mContext, 0);
// 
//    	
//    	SIMInfo sim1 = SIMInfo.getSIMInfoBySlot(mContext, 1);
//    	
//    	String value = "slotid = 0\n" + (sim0 != null ? sim0.toString() : "null") + 
//    					"\n======================\nslotid = 1\n" + (sim1 != null ? sim1.toString() : "null");
//    	mTvSub.setText(value);
//    }

}
