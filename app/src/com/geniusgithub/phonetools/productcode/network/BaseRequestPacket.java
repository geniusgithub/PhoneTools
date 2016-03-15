package com.geniusgithub.phonetools.productcode.network;

import com.geniusgithub.phonetools.productcode.model.IToStringMap;


public  class BaseRequestPacket {
	public int action;
	public String url;
	public IToStringMap object;
	
	public BaseRequestPacket(int action, String url, IToStringMap object){
		this.action = action;
		this.url = url;
		this.object = object;
	}
}
