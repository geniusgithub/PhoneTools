package com.geniusgithub.phonetools.model;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

/** This class is never instantiated. */
public class LCTFactorySecretCodeHandler{
    private static final String TAG = "LCTFactorySecretCodeHandler";
    private static final String SECRET_CODE_ACTION = "android.provider.Telephony.SECRET_CODE";

    private static final String ENG_PCBA_TEST = "*#89#";
    private static final String ENG_ASSEM_TEST = "*#87#";
    private static final String ENG_ASSEM_TEST_AGAIN = "*#88#";
    private static final String ENG_TEST = "*#79837#";
    private static final String CUS_TEST = "#*2008#";
    private static final String ENG_LASER_CAMERA_TEST = "*#86#";

    private static Context mContext;    



    public static boolean handleChars(Context context, String input) {
        Log.v(TAG, "longcheer handleChars") ;
        if (handleEngineerEntry(context, input)) {
            return true ;
        }

        return false ;
    }

    public static boolean handleEngineerEntry(Context context, String input) {
        if (input.equals(ENG_PCBA_TEST)) {
            Intent intent = new Intent(SECRET_CODE_ACTION,
                    Uri.parse("android_secret_code://" + "89"));
            context.sendBroadcast(intent);
            return true;
        } else if (input.equals(ENG_ASSEM_TEST)) {
            Intent intent = new Intent(SECRET_CODE_ACTION,
                    Uri.parse("android_secret_code://" + "87"));
            context.sendBroadcast(intent);
            return true;
        } else if (input.equals(ENG_ASSEM_TEST_AGAIN)) {
            Intent intent = new Intent(SECRET_CODE_ACTION,
                    Uri.parse("android_secret_code://" + "88"));
            context.sendBroadcast(intent);
            Log.d(TAG, "88");
            return true;
        } else if (input.equals(ENG_TEST)) {
            Intent intent = new Intent(SECRET_CODE_ACTION,
                    Uri.parse("android_secret_code://" + "79837"));
            context.sendBroadcast(intent);
            return true;
        } else if (input.equals(CUS_TEST)) {
            Intent intent = new Intent(SECRET_CODE_ACTION,
                    Uri.parse("android_secret_code://" + "2008"));
            context.sendBroadcast(intent);
            return true;
        } else if (input.equals(ENG_LASER_CAMERA_TEST)) {
            Intent intent = new Intent(SECRET_CODE_ACTION,
                    Uri.parse("android_secret_code://" + "86"));
            context.sendBroadcast(intent);
            return true;
        }
        return false;
    }
    
   
}
