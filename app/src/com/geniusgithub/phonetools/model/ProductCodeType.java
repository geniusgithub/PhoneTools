package com.geniusgithub.phonetools.model;

import java.util.Map;

public class ProductCodeType {

	
	public static class TmzsProductCode extends BaseType{
		
		public final static String KEY_PRODUCTCODE = "productCode";
		
		public String mProductCode = "";
		@Override
		public Map<String, String> toStringMap() {
			mapValue.put(KEY_PRODUCTCODE, mProductCode);
			return mapValue;
		}
	}
	
/*	{"c":"200",
	"d":
	{"productEx":null,
		"ItemHeight":"120CM",
		"honestCount":"0",
		"ItemNetContent":"0",
		"ItemPackagingMaterialCode":"",
		"keepOnRecord":"",
		"FirmContactMan":"徐莉莉",
		"QS":[],"ItemGrossWeight":"0",
		"Image":[{"Imageurl":"http://www.anccnet.com/userfile/20101115/m641555076.jpg",
		"ImageDescription":""}],
		"ItemName":"绿亮秀宝2代48V17Ah",
		"qualificationCount":"0",
		"productFangWei":null,
		"ItemClassCode":"49221502",
		"ItemPackagingTypeCode":"",
		"ItemDescription":"http://www.gds.org.cn/goods.aspx?base_id=F25F56A9F703ED7479655C310FA189E5A9069FC4F1CB74F812904567F8DDC332",
		"FirmValidDate":"5/4/2016 12:00:00 AM",
		"FirmContactPhone":"37626262-6201",
		"productCode":"06945627000276",
		"ItemNetWeight":"0",
		"ItemShortDescription":"",
		"FirmName":"上海绿亮电动车有限公司",
		"FirmAddress":"上海市闵行区虹梅南路2468号",
		"alermCount":"0",
		"recallCount":"0",
		"batch":"",
		"ItemSpecification":"TDR158Z-2",
		"FirmStatus":"有效",
		"FirmLogoutDate":"",
		"ItemWidth":"180CM",
		"ItemDepth":"50CM",
		"BrandName":"绿亮"}
	   } */

	
	
	

	public static class TmdrProductCode extends BaseType{
		
//		public final static String KEY_METHOD = "method";
//		public final static String KEY_TYPE = "type";
//		public final static String KEY_CLIENT = "client";
		public final static String KEY_GTIN = "gtin";
//		public String mMethod = "getmodel";
//		public String mType = "1";
//		public String mClient = "";
		public String mGtin = "";
		
		@Override
		public Map<String, String> toStringMap() {
		//	mapValue.put(KEY_METHOD, mMethod);
		//	mapValue.put(KEY_TYPE, mType);
		//	mapValue.put(KEY_CLIENT, mClient);
			mapValue.put(KEY_GTIN, mGtin);
			return mapValue;
		}
	}
	
	/*	{"data":
	{"avgscore":5,
	"base_id":9682659,
	"brand":"绿亮",
	"description":"绿亮秀宝2代48V17Ah",
	"f_id":267287,
	"firm_name":"上海绿亮电动车有限公司",
	"follow":"0",
	"gtin":"6945627000276",
	"logoutflag":0,
	"register_address":"上海市闵行区虹梅南路2468号",
	"specification":"TDR158Z-2",
	"totalnum":0,"totalscore":0,
	"valid_date":"2016-05-04 00:00:00.0",
	"vtaccuracy":0,
	"vtcheap":0,
	"vtexpensive":0}
	} */
	
	
	
	
	
	
	public static class LTProductCode extends BaseType{
		
		public final static String KEY_EAN = "ean";
		
		public String mEan = "";
		@Override
		public Map<String, String> toStringMap() {
			mapValue.put(KEY_EAN, mEan);
			return mapValue;
		}
	}
	
	/* {"ean":"6922266445057",
		 "name":"",
		 "titleSrc":"http:\/\/www.topscan.com\/tiaoma\/eantitle.php?title=NW54dUI5dXd2akowN3VaM1NiT1FoZz09",
		 "guobie":"\u4e2d\u56fd",
		 "price":"6.00",
		 "supplier":"\u91d1\u7ea2\u53f6\u7eb8\u4e1a\u96c6\u56e2\u6709\u9650\u516c\u53f8",
		 "sort_id":"7"} */


	public static class WPBMProductCode extends BaseType{
		
		public final static String KEY_KEYWORD= "keyword";
		
		public String mKeyword = "";
		@Override
		public Map<String, String> toStringMap() {
			mapValue.put(KEY_KEYWORD, mKeyword);
			return mapValue;
		}
	}
	
}
