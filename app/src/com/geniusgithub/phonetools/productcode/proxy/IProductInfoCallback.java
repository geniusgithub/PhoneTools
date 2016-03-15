package com.geniusgithub.phonetools.productcode.proxy;

import com.geniusgithub.phonetools.productcode.model.ProductInfo;

public interface IProductInfoCallback {
	public void onSuccess(ProductInfo info);
	public void onFailure(String result);
}
