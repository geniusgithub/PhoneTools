package com.geniusgithub.phonetools.productcode;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.telecom.PhoneAccountHandle;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.geniusgithub.phonetools.R;
import com.geniusgithub.phonetools.model.IToStringMap;
import com.geniusgithub.phonetools.model.ProductCodeType;
import com.geniusgithub.phonetools.util.CommonLog;
import com.geniusgithub.phonetools.util.LogFactory;
import com.litesuits.http.LiteHttp;
import com.litesuits.http.data.NameValuePair;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.listener.HttpListener;
import com.litesuits.http.request.StringRequest;
import com.litesuits.http.request.param.HttpMethods;
import com.litesuits.http.response.Response;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ProductCodeFragment extends Fragment implements View.OnClickListener{

    private static final CommonLog log = LogFactory.createLog();
    
    public static final int TMZS_TYPE = 0x0001;
    public static final int TMDR_TYPE = 0x0002;
    public static final int LT_TYPE = 0x0003;
    public static final int WPBM_TYPE = 0x0004;
    public int mType = TMZS_TYPE;
	
    private Context mContext;
    private View mRootView;
	private Button mBtnGet;
	private TextView mTextView;
	
	protected  LiteHttp liteHttp;

	
	public void setType(int type){
	   mType = type;
	}

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
    	log.i("onAttach mType = " + mType);
        liteHttp = ProductCodeActivity.getLiteHttp(mContext);
    }
    
    

    @Override
	public void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		
		log.i("onCreate mType = " + mType);
	}



	@Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	log.i("onCreateView mType = " + mType);
        mRootView  =  LayoutInflater.from(mContext).inflate(R.layout.productcode_fragment, null);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mBtnGet = (Button) view.findViewById(R.id.btn_get);
        mBtnGet.setOnClickListener(this);

        mTextView = (TextView) view.findViewById(R.id.tv_content);
 
  
    }
    
    
    
    
    @Override
	public void onDestroyView() {
    	log.i("onCreateView mType = " + mType);
		super.onDestroyView();
	}

	@Override
	public void onDestroy() {
		log.i("onDestroy mType = " + mType);
		super.onDestroy();
	}
    
	@Override
	public void onDetach() {
		log.i("onDetach mType = " + mType);
		super.onDetach();
	}

	private String getCodeFromEditText(){
    	return ProductCodeActivity.getCodeFromEditText();
    }
    
    private void updateContext(String data){
    	StringBuffer stringBuffer = new StringBuffer(data);
    	
    	mTextView.setText(stringBuffer.toString());
    }
    
    private void updateContext(HttpException e){
    	StringBuffer stringBuffer = new StringBuffer(e.toString());
    	
    	mTextView.setText(stringBuffer.toString());
    }
    
    
    
  


	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btn_get:
			requestProduct(getCodeFromEditText());
			break;

		default:
			break;
		}
	}
	

	private void requestProduct(String code){
		switch (mType) {
		case TMZS_TYPE:
			requestTMZSProductCode(code);
			break;
		case TMDR_TYPE:
			requestTMDRProductCode(code);
			break;
		case LT_TYPE:
			requestLTProductCode(code);
			break;
		case WPBM_TYPE:
			requestWPBMProductCode(code);
			break;
		default:
			break;
		}
	}

	
	
    public static final String tmzs_productCodeUri = "http://webapi.chinatrace.org/api/getProductData";
	private void requestTMZSProductCode(String code){
		log.i("requestTMZSProductCode code = " + code);
		if (TextUtils.isEmpty(code)){
			Toast.makeText(mContext, "input is empty!!!", Toast.LENGTH_SHORT).show();
			return ;
		}
		
		ProductCodeType.TmzsProductCode productCode = new ProductCodeType.TmzsProductCode ();
		productCode.mProductCode = code;
		
		requestGet(tmzs_productCodeUri, productCode);
		
	}
	
	public static final String tmdr_productCodeUri = "http://203.91.45.184:8080/barcoder_server/server/pages/product/getmodel.json";
	private void requestTMDRProductCode(String code){
		log.i("requestTMDRProductCode code = " + code);
		if (TextUtils.isEmpty(code)){
			Toast.makeText(mContext, "input is empty!!!", Toast.LENGTH_SHORT).show();
			return ;
		}
		
		ProductCodeType.TmdrProductCode productCode = new ProductCodeType.TmdrProductCode ();
		productCode.mGtin = code;
		
		requestGet(tmdr_productCodeUri, productCode);
		
	}
	
	public static final String lt_productCodeUri = "http://www.topscan.com/tiaoma/query.php";
	private void requestLTProductCode(String code){
		log.i("requestLTProductCode code = " + code);
		if (TextUtils.isEmpty(code)){
			Toast.makeText(mContext, "input is empty!!!", Toast.LENGTH_SHORT).show();
			return ;
		}
		
		ProductCodeType.LTProductCode productCode = new ProductCodeType.LTProductCode ();
		productCode.mEan = code;
		
		requestPost(lt_productCodeUri, productCode);
		
	}
	
	public static final String wpbm_productCodeUri = "http://search.anccnet.com/searchResult2.aspx";
	private void requestWPBMProductCode(String code){
		log.i("requestWPBMProductCode code = " + code);
		if (TextUtils.isEmpty(code)){
			Toast.makeText(mContext, "input is empty!!!", Toast.LENGTH_SHORT).show();
			return ;
		}
		
		ProductCodeType.WPBMProductCode productCode = new ProductCodeType.WPBMProductCode ();
		productCode.mKeyword = code;
		
		requestGet(wpbm_productCodeUri, productCode);
		
	}
	
	private void requestGet(String uri, IToStringMap iStringMap){
		final StringRequest request = new StringRequest(uri);
		request.setMethod(HttpMethods.Get);
		List<NameValuePair> list = convertToNameValuePairs(iStringMap.toStringMap());
		request.addUrlParam(list);
		request.setHttpListener(new HttpListener<String>() {

			@Override
			public void onSuccess(String data, Response<String> response) {
				log.i("onSuccess response = \n" + response.toString());
				updateContext(data);
			}

			@Override
			public void onFailure(HttpException e, Response<String> response) {
				log.e("onFailure response = \n" + response.toString());
				updateContext(e);
			}
			
			
		});
		
		liteHttp.executeAsync(request);
		log.i("liteHttp.executeAsync request = \n"  + request.toString());
		
	}
	
	private void requestPost(String uri, IToStringMap iStringMap){
		final StringRequest request = new StringRequest(uri);
		request.setMethod(HttpMethods.Post);
		List<NameValuePair> list = convertToNameValuePairs(iStringMap.toStringMap());
		request.addUrlParam(list);
		request.setHttpListener(new HttpListener<String>() {

			@Override
			public void onSuccess(String data, Response<String> response) {
				log.i("onSuccess response = \n" + response.toString());
				
				
				String sb = new String("\u4e2d\u56fd");
				sb = new String("\u91d1\u7ea2\u53f6\u7eb8\u4e1a\u96c6\u56e2\u6709\u9650\u516c\u53f8");
			      
				log.i("sb = " + sb);
				updateContext(data);
			}

			@Override
			public void onFailure(HttpException e, Response<String> response) {
				log.e("onFailure response = \n" + response.toString());
				updateContext(e);
			}
			
			
		});
		
		liteHttp.executeAsync(request);
		log.i("liteHttp.executeAsync request = \n"  + request.toString());
		
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
