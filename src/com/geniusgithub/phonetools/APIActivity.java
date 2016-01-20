package com.geniusgithub.phonetools;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.geniusgithub.phonetools.util.CommonLog;
import com.geniusgithub.phonetools.util.LogFactory;

public class APIActivity extends Activity {

    private static final CommonLog log = LogFactory.createLog();

    private FragmentTransaction mTransaction;
    private SubscriptionInfoFragment subscriptionInfoFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api_layout);

        initView();
    }


    private void initView(){
        subscriptionInfoFragment = new SubscriptionInfoFragment();

        FragmentManager fragmentManager = getFragmentManager();
        mTransaction = fragmentManager.beginTransaction();

        mTransaction.add(R.id.container, subscriptionInfoFragment);
        mTransaction.commit();
    }
}
