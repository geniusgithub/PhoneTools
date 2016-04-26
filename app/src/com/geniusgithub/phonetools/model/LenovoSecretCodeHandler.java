package com.geniusgithub.phonetools.model;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.SystemProperties;

public class LenovoSecretCodeHandler {
	private static final String SECRET_CODE_ACTION = "android.provider.Telephony.SECRET_CODE";
	
    private static final String MMI_EXTERNAL_VERSION = "####0000#";
    private static final String MMI_INTERNAL_VERSION = "####5993#";
    private static final String MMI_FACTORY_TEST= "####1111#"; //responses in midtest
    private static final String MMI_SN_DISPLAY = "####2222#";
    private static final String MMI_MTK_LOG="####3333#";

    private static final String MMI_RESTORE_FACTORY_SETTING = "####7777#";
    private static final String MMI_BASEBAND_VERSION = "####8375#" ;
    private static final String MMI_LCD_MEMORY_INFO = "####7599#"; //responses in midtest
    private static final String MMI_SPK_RCV_TEST = "####9327#"; //responses in midtest
    private static final String MMI_SWITCH_COUNTRY_CODE = "####682#"; //switch Country code
    private static final String MMI_ACM_SWITCH = "####33284#";
    private static final String MMI_STARTLOG = "####3334#";
    private static final String MMI_STOPLOG = "####3335#";
    
    
	public static boolean handleChars(Context context, String input) {		
		if (input.equals(MMI_EXTERNAL_VERSION)) {
            Intent intent = new Intent(SECRET_CODE_ACTION,
                    Uri.parse("android_secret_code://" + "0000"));
            context.sendBroadcast(intent);
            return true;
        } else if (input.equals(MMI_INTERNAL_VERSION)) {
            Intent intent = new Intent(SECRET_CODE_ACTION,
                    Uri.parse("android_secret_code://" + "5993"));
            context.sendBroadcast(intent);
            return true;
        } else if (input.equals(MMI_FACTORY_TEST)) {
            Intent intent = new Intent(SECRET_CODE_ACTION,
                    Uri.parse("android_secret_code://" + "1111"));
            context.sendBroadcast(intent);
            return true;
        }else if (input.equals(MMI_SN_DISPLAY)) {
            Intent intent = new Intent(SECRET_CODE_ACTION,
                    Uri.parse("android_secret_code://" + "2222"));
            context.sendBroadcast(intent);
            return true;
        } else if (input.equals(MMI_MTK_LOG)) {
            Intent intent = new Intent("android.intent.action.MAIN");
            intent.setClassName("com.mediatek.mtklogger","com.mediatek.mtklogger.MainActivity");
            context.startActivity(intent);
        } else if (input.equals(MMI_RESTORE_FACTORY_SETTING)) {
            Intent intent = new Intent(SECRET_CODE_ACTION,
                    Uri.parse("android_secret_code://" + "7777"));
            context.sendBroadcast(intent);
            return true;
        } else if (input.equals(MMI_BASEBAND_VERSION)){
            Intent intent = new Intent(SECRET_CODE_ACTION,
                    Uri.parse("android_secret_code://" + "8375"));
            context.sendBroadcast(intent);
            return true;
        } else if (input.equals(MMI_LCD_MEMORY_INFO)) {
            Intent intent = new Intent(SECRET_CODE_ACTION,
                    Uri.parse("android_secret_code://" + "7599"));
            context.sendBroadcast(intent);
            return true ;
        } else if (input.equals(MMI_SPK_RCV_TEST)) {
            Intent intent = new Intent(SECRET_CODE_ACTION,
                    Uri.parse("android_secret_code://" + "9327"));
            context.sendBroadcast(intent);
			return true;
        }else  if (input.equals(MMI_SWITCH_COUNTRY_CODE)) {
            if ("1".equals(SystemProperties.get("ro.lct_country_info_ctrl")))
            {
                Intent intent = new Intent(SECRET_CODE_ACTION,
                Uri.parse("android_secret_code://" + "682"));
                context.sendBroadcast(intent);
                return true;
            }
        }else if (input.equals((MMI_ACM_SWITCH))) {
            Intent intent = new Intent(SECRET_CODE_ACTION,
                    Uri.parse("android_secret_code://" + "33284"));
            context.sendBroadcast(intent);
            return true;
        } else if (input.equals(MMI_STARTLOG)) {
            Intent intent = new Intent(SECRET_CODE_ACTION,
                    Uri.parse("android_secret_code://" + "3334"));
            context.sendBroadcast(intent);
            return true;
        } else if (input.equals((MMI_STOPLOG))) {
            Intent intent = new Intent(SECRET_CODE_ACTION,
                    Uri.parse("android_secret_code://" + "3335"));
            context.sendBroadcast(intent);
            return true;
        }

        return false ;
    }
}
