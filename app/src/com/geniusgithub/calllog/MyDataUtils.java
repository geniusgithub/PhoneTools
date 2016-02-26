package com.geniusgithub.calllog;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.hardware.hdmi.HdmiTimerRecordSources.Time;

public class MyDataUtils {

    /**
     * 
     * @return返回短时间格式 yyyy-MM-dd
     */
    public static String getDateShort(long time) {
	     Date currentTime = new Date(time);
	     SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	     String dateString = formatter.format(currentTime);
	     return dateString;
    }
    
    
}
