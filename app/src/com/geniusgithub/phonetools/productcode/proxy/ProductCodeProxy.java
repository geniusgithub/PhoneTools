package com.geniusgithub.phonetools.productcode.proxy;

import org.json.JSONException;

import android.content.Context;

import com.geniusgithub.phonetools.productcode.model.ProductCodeType;
import com.geniusgithub.phonetools.productcode.model.ProductInfo;
import com.geniusgithub.phonetools.productcode.model.ProductInfoType;
import com.geniusgithub.phonetools.productcode.network.BaseRequestPacket;
import com.geniusgithub.phonetools.productcode.network.INetworkFrameEngine;
import com.geniusgithub.phonetools.productcode.network.IRequestContentCallback;
import com.geniusgithub.phonetools.productcode.network.NetworkFrameEngineFactory;
import com.geniusgithub.phonetools.util.CommonLog;
import com.geniusgithub.phonetools.util.LogFactory;

public class ProductCodeProxy{

    private static final CommonLog log = LogFactory.createLog();
    
    public static final String tmzs_productCodeUri = "http://webapi.chinatrace.org/api/getProductData";
    public static final String tmdr_productCodeUri = "http://203.91.45.184:8080/barcoder_server/server/pages/product/getmodel.json";
	
	
	public static  boolean getTMZSProductInfo(Context context, String productCode,final IProductInfoCallback callback) {

		INetworkFrameEngine engine = getNetworkFrameEngine(context);


		return getProductInfoFromTMZS(engine, productCode, callback);
	}
	
	
	public static  boolean getTMDRProductInfo(Context context, String productCode,final IProductInfoCallback callback) {

		INetworkFrameEngine engine = getNetworkFrameEngine(context);


		return getProductInfoFromTMDR(engine, productCode, callback);
	}

	
	private static INetworkFrameEngine getNetworkFrameEngine(Context context){
		return NetworkFrameEngineFactory.getNetworkFrameEngineInstance(context);
	}
	
	
	private static boolean getProductInfoFromTMZS(INetworkFrameEngine engine, String productCode, final IProductInfoCallback callback){
		ProductCodeType.TmzsProductCode object = new ProductCodeType.TmzsProductCode();
		object.mProductCode = productCode;

		
		BaseRequestPacket packet = new BaseRequestPacket(0, tmzs_productCodeUri, object);
		IRequestContentCallback contentCallback = new IRequestContentCallback() {
			
			@Override
			public void onSuccess(int action, String data) {
				if (callback != null){
					ProductInfoType.TMZSProductInfo info = new ProductInfoType.TMZSProductInfo();
					
					try {
						info.parseJson(data);
						ProductInfo object = ProductInfo.build(info);
						callback.onSuccess(object);
					} catch (JSONException e) {
						e.printStackTrace();
						callback.onFailure(e.getMessage());
					}
				
				}
			}
			
			@Override
			public void onFailure(int action, String result) {
				if (callback != null){
					callback.onFailure(result);
				}
			}
		};
		
		boolean ret = engine.httpGetRequest(packet, contentCallback);
		return ret;
	}
	
	private static boolean getProductInfoFromTMDR(INetworkFrameEngine engine, String productCode, final IProductInfoCallback callback){
		ProductCodeType.TmdrProductCode object = new ProductCodeType.TmdrProductCode();
		object.mGtin = productCode;

		
		BaseRequestPacket packet = new BaseRequestPacket(0, tmdr_productCodeUri, object);
		IRequestContentCallback contentCallback = new IRequestContentCallback() {
			
			@Override
			public void onSuccess(int action, String data) {
				if (callback != null){
					ProductInfoType.TMDRProductInfo info = new ProductInfoType.TMDRProductInfo();
					
					try {
						info.parseJson(data);
						ProductInfo object = ProductInfo.build(info);
						callback.onSuccess(object);
					} catch (JSONException e) {
						e.printStackTrace();
						callback.onFailure(e.getMessage());
					}
				
				}
			}
			
			@Override
			public void onFailure(int action, String result) {
				if (callback != null){
					callback.onFailure(result);
				}
			}
		};
		
		boolean ret = engine.httpGetRequest(packet, contentCallback);
		return ret;
	}
	
	
}
