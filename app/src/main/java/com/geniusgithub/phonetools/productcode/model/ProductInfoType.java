package com.geniusgithub.phonetools.productcode.model;

import org.json.JSONException;
import org.json.JSONObject;

public class ProductInfoType {
	

	/*{"c":"200",
	 * "d":{"productEx":null,
	 * "ItemHeight":"74MM",
	 * "honestCount":"0",
	 * "ItemNetContent":"0",
	 * "ItemPackagingMaterialCode":"",
	 * "keepOnRecord":"",
	 * "FirmContactMan":"宋林飞",
	 * "QS":[],"ItemGrossWeight":"0",
	 * "Image":[],
	 * "ItemName":"清风软抽面纸",
	 * "qualificationCount":"0",
	 * "productFangWei":null,
	 * "ItemClassCode":"14111701",
	 * "ItemPackagingTypeCode":"",
	 * "ItemDescription":"http://www.gds.org.cn/goods.aspx?base_id=F25F56A9F703ED74D9E0B3753355C626FD335E7CFA6A6F336216A90B7855D5A8D3FA492A34211798",
	 * "FirmValidDate":"2/5/2018 12:00:00 AM",
	 * "FirmContactPhone":"62832209",
	 * "productCode":"06922266445057",
	 * "ItemNetWeight":"0",
	 * "ItemShortDescription":"",
	 * "FirmName":"金红叶纸业集团有限公司",
	 * "FirmAddress":"江苏省苏州市苏州工业园区金胜路1号",
	 * "alermCount":"0",
	 * "recallCount":"0",
	 * "batch":"",
	 * "ItemSpecification":"单包装",
	 * "FirmStatus":"有效",
	 * "FirmLogoutDate":"",
	 * "ItemWidth":"138MM",
	 * "ItemDepth":"105MM",
	 * "BrandName":"清风"}}
	 */
	public static class TMZSProductInfo implements IParseJson, IToPrintString{

		public final static String KEY_C = "c";		
		public final static String KEY_D = "d";	
		
		public final static String KEY_PRODUCTCODE = "productCode";
		public final static String KEY_ITEMNAME = "ItemName";
		public final static String KEY_BRANDNAME = "BrandName";
		public final static String KEY_ITEMSPECIFICATION = "ItemSpecification";
		public final static String KEY_FIRMNAME = "FirmName";
		public final static String KEY_FIRMADDRESS = "FirmAddress";



		public int c = 0;
		
		public String mProductCode = "";
		public String mItemName = "";
		public String mBrandName = "";
		public String mItemSpecification = "";
		public String mFirmName = "";
		public String mFirmAddress = "";
		
		@Override
		public boolean parseJson(String content) throws JSONException {
			
			JSONObject jsonObject = new JSONObject(content);	
			c = jsonObject.getInt(KEY_C);
			
			JSONObject data = jsonObject.getJSONObject(KEY_D);
			
			mProductCode = data.getString(KEY_PRODUCTCODE);
			mItemName = data.getString(KEY_ITEMNAME);
			mBrandName = data.getString(KEY_BRANDNAME);
			mItemSpecification = data.getString(KEY_ITEMSPECIFICATION);
			mFirmName = data.getString(KEY_FIRMNAME);
			mFirmAddress = data.getString(KEY_FIRMADDRESS);
			
			return true;
		}

		@Override
		public String getShowString() {
			
			StringBuffer stringBuffer = new StringBuffer();
			stringBuffer.append("---------------------------\n");
			stringBuffer.append("mProductCode = " + mProductCode + "\n");
			stringBuffer.append("mItemName" + mItemName + "\n");
			stringBuffer.append("mBrandName = " +mBrandName + "\n");
			stringBuffer.append("mItemSpecification = " + mItemSpecification + "\n");
			stringBuffer.append("mFirmName = " + mFirmName + "\n");
			stringBuffer.append("mFirmAddress = " + mFirmAddress + "\n");
			stringBuffer.append("---------------------------");
			
			return stringBuffer.toString();
		}
	}
	
	
	

	/*{"data":
	 * {"avgscore":5,
	 * "base_id":20682734,
	 * "brand":"清风",
	 * "description":"清风软抽面纸",
	 * "descriptionshort":",
	 * ","f_id":42663,
	 * "firm_name":"金红叶纸业集团有限公司",
	 * "follow":"0",
	 * "gtin":"6922266445057",
	 * "logoutflag":0,
	 * "register_address":"江苏省苏州市苏州工业园区金胜路1号",
	 * "specification":"单包装",
	 * "totalnum":0,
	 * "totalscore":0,
	 * "valid_date":"2016-02-05 00:00:00.0",
	 * "vtaccuracy":0,
	 * "vtcheap":0,
	 * "vtexpensive":0}}
	 */
	public static class TMDRProductInfo implements IParseJson, IToPrintString{

	
		public final static String KEY_DATA = "data";	
		
		public final static String KEY_GTIN = "gtin";
		public final static String KEY_DESCRIPTION = "description";
		public final static String KEY_BRAND = "brand";
		public final static String KEY_SPECIFICATION = "specification";
		public final static String KEY_FIRMNAME = "firm_name";
		public final static String KEY_REGISTERADDRESS = "register_address";

	
		public String mGtin = "";
		public String mDescription = "";
		public String mBrand = "";
		public String mSpecification = "";
		public String mFirm_name = "";
		public String mRegister_address = "";
		
		@Override
		public boolean parseJson(String content) throws JSONException {
			
			JSONObject jsonObject = new JSONObject(content);	
			JSONObject data = jsonObject.getJSONObject(KEY_DATA);
			
			mGtin = data.getString(KEY_GTIN);
			mDescription = data.getString(KEY_DESCRIPTION);
			mBrand = data.getString(KEY_BRAND);
			mSpecification = data.getString(KEY_SPECIFICATION);
			mFirm_name = data.getString(KEY_FIRMNAME);
			mRegister_address = data.getString(KEY_REGISTERADDRESS);
			
			return true;
		}

		@Override
		public String getShowString() {
			
			StringBuffer stringBuffer = new StringBuffer();
			stringBuffer.append("---------------------------\n");
			stringBuffer.append("mGtin = " + mGtin + "\n");
			stringBuffer.append("mDescription" + mDescription + "\n");
			stringBuffer.append("mBrand = " +mBrand + "\n");
			stringBuffer.append("mSpecification = " + mSpecification + "\n");
			stringBuffer.append("mFirm_name = " + mFirm_name + "\n");
			stringBuffer.append("mRegister_address = " + mRegister_address + "\n");
			stringBuffer.append("---------------------------");
			
			return stringBuffer.toString();
		}
	}
	
}
