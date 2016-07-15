package com.geniusgithub.phonetools.productcode.network;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.content.Context;
import android.text.TextUtils;

import com.geniusgithub.phonetools.productcode.model.IToStringMap;
import com.geniusgithub.phonetools.util.CommonLog;
import com.geniusgithub.phonetools.util.LogFactory;
import com.litesuits.http.HttpConfig;
import com.litesuits.http.LiteHttp;
import com.litesuits.http.data.NameValuePair;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.listener.HttpListener;
import com.litesuits.http.request.StringRequest;
import com.litesuits.http.request.param.HttpMethods;
import com.litesuits.http.response.Response;

public class LiteNetworkFrameEngineImpl implements INetworkFrameEngine{

	private static LiteNetworkFrameEngineImpl mInstance;
	
	private Context mContext;
	private LiteHttp mLiteHttp;

	
	public static synchronized LiteNetworkFrameEngineImpl getInstance(Context context) {
		if (mInstance == null){
			mInstance  = new LiteNetworkFrameEngineImpl(context);
		}
		return mInstance;
	}

	private LiteNetworkFrameEngineImpl(Context context)
	{	
		mContext = context.getApplicationContext();

		initEngine();
	}

	

	@Override
	public void initEngine() {
		if (mLiteHttp == null){
			  HttpConfig config = new HttpConfig(mContext.getApplicationContext()) // configuration quickly
            .setDebugged(true)                   // log output when debugged
            .setDetectNetwork(true)              // detect network before connect
            .setDoStatistics(true)               // statistics of time and traffic
            .setUserAgent("Mozilla/5.0 (...)")   // set custom User-Agent
            .setTimeOut(10000, 10000);             // connect and socket timeout: 10s
			  mLiteHttp = LiteHttp.newApacheHttpClient(config);
		}
	}

	@Override
	public void uninitEngine() {
		// TODO Auto-generated method stub
		
	}
	
	
	@Override
	public boolean httpGetRequest(BaseRequestPacket packet,  IRequestContentCallback callback)
	{
		String url = packet.url;
		if (TextUtils.isEmpty(url)){
			return false;
		}
		
		final StringRequest request = new StringRequest(url);
		request.setMethod(HttpMethods.Get);
		
		if (packet.object != null){
			List<NameValuePair> list = convertToNameValuePairs(packet.object.toStringMap());
			request.addUrlParam(list);
		}
		
		ResponseStringHttpLisener lisener = new ResponseStringHttpLisener(packet.action, callback);
		request.setHttpListener(lisener);
		
		mLiteHttp.executeAsync(request);
		
		return true;
		
	}

	@Override
	public boolean httpPostRequest(BaseRequestPacket packet,  IRequestContentCallback callback)
	{
		String url = packet.url;
		if (TextUtils.isEmpty(url)){
			return false;
		}
		
		final StringRequest request = new StringRequest(url);
		request.setMethod(HttpMethods.Post);
		
		if (packet.object != null){
			List<NameValuePair> list = convertToNameValuePairs(packet.object.toStringMap());
			request.addUrlParam(list);
		}
		
		ResponseStringHttpLisener lisener = new ResponseStringHttpLisener(packet.action, callback);
		request.setHttpListener(lisener);
		
		mLiteHttp.executeAsync(request);
		
		return true;
	}
	
	
	private List<NameValuePair> convertToNameValuePairs(Map<String, String> mapString){
		List<NameValuePair> list = new ArrayList<NameValuePair>();

		String key = "";
		Set<String> keySet = mapString.keySet();
		Iterator<String> it = keySet.iterator();
		while (it.hasNext())
		{
			key = it.next();
			NameValuePair nameValuePair = new NameValuePair(key, mapString.get(key));
			list.add(nameValuePair);
		}
		
		return list;
	}



}
