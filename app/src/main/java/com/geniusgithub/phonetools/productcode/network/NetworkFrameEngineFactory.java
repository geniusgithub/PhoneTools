package com.geniusgithub.phonetools.productcode.network;

import android.content.Context;

public class NetworkFrameEngineFactory {
	
	public static INetworkFrameEngine getNetworkFrameEngineInstance(Context context){
		return getLiteNetworkFrameEngineInstance(context);
	}

	private static INetworkFrameEngine getLiteNetworkFrameEngineInstance(Context context){
		return LiteNetworkFrameEngineImpl.getInstance(context);
	}
}
