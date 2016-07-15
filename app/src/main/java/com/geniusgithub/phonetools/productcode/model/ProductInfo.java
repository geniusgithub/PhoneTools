package com.geniusgithub.phonetools.productcode.model;

public class ProductInfo implements IToPrintString{

	public String mProductCode = "";
	public String mItemName = "";
	public String mBrandName = "";
	public String mItemSpecification = "";
	public String mFirmName = "";
	public String mFirmAddress = "";
	
	public ProductInfo(){
		
	}


	public static ProductInfo build(ProductInfoType.TMZSProductInfo info){
		ProductInfo object = new ProductInfo();
		object.mProductCode = info.mProductCode;
		object.mItemName = info.mItemName;
		object.mBrandName = info.mBrandName;
		object.mItemSpecification = info.mItemSpecification;
		object.mFirmName = info.mFirmName;
		object.mFirmAddress = info.mFirmAddress;
		return object;
	}
	
	public static ProductInfo build(ProductInfoType.TMDRProductInfo info){
		ProductInfo object = new ProductInfo();
		object.mProductCode = info.mGtin;
		object.mItemName = info.mDescription;
		object.mBrandName = info.mBrand;
		object.mItemSpecification = info.mSpecification;
		object.mFirmName = info.mFirm_name;
		object.mFirmAddress = info.mRegister_address;
		return object;
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
