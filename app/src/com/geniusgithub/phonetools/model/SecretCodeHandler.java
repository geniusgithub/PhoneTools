package com.geniusgithub.phonetools.model;


import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.SystemProperties;
import android.util.Log;

/** This class is never instantiated. */
public class SecretCodeHandler{
    private static final String TAG = "SecretCodeHandler";
    private static final String SECRET_CODE_ACTION = "android.provider.Telephony.SECRET_CODE";
    
    private static Context mContext;    

	// for capsensor CE test
	private static final String MMI_CAPSENSOR_TEST = "*#227*#";
	//for SAR display
	private static final String MMI_SAR_DISPLAY = "*#07#";
    //for runintest
    private static final String ENG_RUNIN_TEST = "####78646#";


    public static boolean handleChars(Context context, String input) {
        Log.v(TAG, "longcheer handleChars") ;
        if (handleCapSensor(context , input) //for capsensor CE test 
        		|| handleSARDisplay(context , input) // for SAR display
                || handleRunintest(context,input) //for runintest
        		|| LenovoSecretCodeHandler.handleChars(context, input) // for custom secretcode e.g. lenovo
        		) {
            return true ;
        }

        return false ;
    }

    
    
    
	// for capsensor CE test
	static private boolean handleCapSensor(Context context, String input) {
		mContext = context;
		if (input.equals(MMI_CAPSENSOR_TEST)) {
			AlertDialog alert = new AlertDialog.Builder(context)
					.setTitle("Captouch sensor")
					.setMessage("Please select open or close")
					.setPositiveButton("Open",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(
										final DialogInterface dialog,
										final int which) {
									Intent intent = new Intent(
											SECRET_CODE_ACTION,
											Uri.parse("android_secret_code://"
													+ "2271"));
									mContext.sendBroadcast(intent);
									Log.i(TAG, "open---");
								}

							})
					.setNegativeButton("Close",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(
										final DialogInterface dialog,
										final int which) {
									Intent intent = new Intent(
											SECRET_CODE_ACTION,
											Uri.parse("android_secret_code://"
													+ "2270"));
									mContext.sendBroadcast(intent);
									Log.i(TAG, "close---");
								}

							}).setCancelable(false).show();

			return true;
		}
		return false;
	}


    //for SAR display
    public static boolean handleSARDisplay(Context context, String input) {
        if (input.equals(MMI_SAR_DISPLAY)) {
            //WebView is not allowed in system uid processes
            String cid = SystemProperties.get("persist.sys.lang_country");
            if (cid.equals("IN") || cid.equals("MY") ||cid.equals("PH")) {
                Intent mIntent = new Intent();
		ComponentName comp = new ComponentName("com.android.htmlviewer", "com.android.htmlviewer.SettingsWarranityActivity");
		mIntent.setComponent(comp);
		mIntent.setAction("android.intent.action.MAIN");
		context.startActivity(mIntent);
            }
            return true;
        } else {
            return false;
        }
    }

    public  static boolean handleRunintest(Context context , String input) {
        if(input.equals(ENG_RUNIN_TEST)){
            Intent intent = new Intent();
            intent.setComponent(new ComponentName("com.longcheertel.runintest", "com.longcheertel.runintest.RuninActivity"));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            try {
                context.startActivity(intent);
            } catch (Exception e) {

            }
            return true ;
        }
        return false ;
    }




    
}
