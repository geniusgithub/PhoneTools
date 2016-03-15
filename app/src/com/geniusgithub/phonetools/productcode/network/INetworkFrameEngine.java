package com.geniusgithub.phonetools.productcode.network;


public interface INetworkFrameEngine {
	public void initEngine();
	public void uninitEngine();
	public boolean httpGetRequest(BaseRequestPacket packet,  IRequestContentCallback callback);
	public boolean httpPostRequest(BaseRequestPacket packet,  IRequestContentCallback callback);
}
