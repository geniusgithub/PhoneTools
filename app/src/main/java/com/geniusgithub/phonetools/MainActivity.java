package com.geniusgithub.phonetools;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SubscriptionManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }


    private void test(){
        SubscriptionManager subscriptionManager = SubscriptionManager.from(this);
        int result[] = subscriptionManager.getActiveSubscriptionIdList();

    }
}
