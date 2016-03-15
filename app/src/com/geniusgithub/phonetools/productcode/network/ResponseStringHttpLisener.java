package com.geniusgithub.phonetools.productcode.network;

import com.litesuits.http.exception.HttpException;
import com.litesuits.http.listener.HttpListener;
import com.litesuits.http.response.Response;

public class ResponseStringHttpLisener extends HttpListener<String> {

	
	private int mAction = 0;
	private IRequestContentCallback mContentCallback;
	
	
	public ResponseStringHttpLisener(int action, IRequestContentCallback callback){
		mAction = action;
		mContentCallback = callback;
	}
	
	@Override
	public void onSuccess(String data, Response<String> response) {
		if (mContentCallback != null){
			mContentCallback.onSuccess(mAction, data);
		}
	}

	@Override
	public void onFailure(HttpException e, Response<String> response) {

		if (mContentCallback != null){
			mContentCallback.onFailure(mAction, response.getResult());
		}
	}

}
