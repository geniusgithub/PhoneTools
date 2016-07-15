package com.squareup.otto.sample.test;

import java.util.ArrayList;
import java.util.List;

import com.squareup.otto.Bus;

public class TestBus extends Bus{

	public List<Object> mCallEventList = new ArrayList<Object>();
	public void addObjectForCallEvent(Object object){
		mCallEventList.add(object);
		register(object);
	}
	
	public void removeAllSubscribe(){
		for(Object object : mCallEventList){
			unregister(object);
		}
		mCallEventList.clear();
	}
}
