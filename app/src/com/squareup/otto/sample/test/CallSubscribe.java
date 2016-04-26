package com.squareup.otto.sample.test;

import com.geniusgithub.phonetools.util.CommonLog;
import com.geniusgithub.phonetools.util.LogFactory;
import com.squareup.otto.Produce;
import com.squareup.otto.Subscribe;

public class CallSubscribe {

    private static final String TAG = CallSubscribe.class.getSimpleName();
    private  final CommonLog log = LogFactory.createNewLog(TAG);

    
	@Subscribe
	public void OnCallReceive1(CallEvent event){
		log.i("OnCallReceive1 this -> "  + this);
	}
	
	@Subscribe
	public void OnCallReceive2(CallEvent event){
		log.i("OnCallReceive2 this -> "  + this);
	}
}
