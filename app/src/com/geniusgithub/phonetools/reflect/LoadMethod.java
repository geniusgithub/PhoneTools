package com.geniusgithub.phonetools.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.geniusgithub.phonetools.util.CommonLog;
import com.geniusgithub.phonetools.util.LogFactory;

import android.telephony.TelephonyManager;
import android.util.Log;

public class LoadMethod {
    private static final CommonLog log = LogFactory.createLog();
	  public static boolean TelephonyManager_isVolteAvailable(TelephonyManager telephonyManager) {
	    	Class callClass = null;
			Method isVolteAvailable = null;
			Object result = null;

			try {
				try {
					callClass = Class.forName("android.telephony.TelephonyManager");
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				
				if(callClass != null)
				{
					isVolteAvailable = callClass.getMethod("isVolteAvailable", new Class[] {});
				}
			} catch (NoSuchMethodException e) {
				 e.printStackTrace();
			}
			
			if ((isVolteAvailable != null)) {
				try {
					isVolteAvailable.setAccessible(true);
					result = isVolteAvailable.invoke(telephonyManager, new Object[] {});
					log.i("invoke isVolteAvailable = " + isVolteAvailable);
				} catch (IllegalArgumentException e1) {
					 e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					 e1.printStackTrace();
				} catch (InvocationTargetException e1) {
					 e1.printStackTrace();
				}
			}
			
			if(result != null)
			{
				return (Boolean)result;
			}
			else {
				return false;
			}
	    }
}
