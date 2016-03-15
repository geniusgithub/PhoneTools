package com.geniusgithub.phonetools.productcode.network;


public interface IRequestContentCallback {

	public void onSuccess(int action, String data);
	public void onFailure(int action, String result);
	
}
