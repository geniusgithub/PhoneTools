package com.geniusgithub.phonetools.productcode.proxy;

import android.content.Context;

public interface IRequestProductInfo{

	public boolean getProductInfo(Context context, String productCode, IProductInfoCallback callback);
}
