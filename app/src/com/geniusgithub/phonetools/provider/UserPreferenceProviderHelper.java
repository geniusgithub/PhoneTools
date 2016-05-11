package com.geniusgithub.phonetools.provider;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;

public class UserPreferenceProviderHelper {
	
	public final static String[] _PROJECTION_USER_SIM_PREFERENCE = new String[] {
		"_id", // 0
		UserPreferredSimProvider.NUMBER, // 1
		UserPreferredSimProvider.USER_SIM_SN, // 2
		UserPreferredSimProvider.CALL_COUNT, // 3
	};

	public static final int USER_SIM_PREFERENCE_ID = 0;
	public static final int USER_SIM_PREFERENCE_NUMBER = 1;
	public static final int USER_SIM_PREFERENCE_USER_SIM_SN = 2;
	public static final int USER_SIM_PREFERENCE_CALL_COUNT = 3;
	
	
    public static Cursor queryUserSimPreference(Context context, String number){
 	   Cursor cur = null;
            Uri uri = UserPreferredSimProvider.CONTENT_URI_USER_SIM_PREFERENCE;
            if (!TextUtils.isEmpty(number)){
                uri = Uri.withAppendedPath(uri, Uri.encode(number));
            }
            String _PROJECTION[] = UserPreferenceProviderHelper._PROJECTION_USER_SIM_PREFERENCE;
            cur = context.getContentResolver().query(uri, _PROJECTION, null, null, null);

        return cur;
    }

    public static int cleanUserSimPreference(Context context){
    	  Uri uri = UserPreferredSimProvider.CONTENT_URI_USER_SIM_PREFERENCE;
    	  int count = context.getContentResolver().delete(uri, null, null);
    	  return count;
    }
    
    public static UserSimPreferenceObject newInstancefromCursor(Cursor cursor){
    	UserSimPreferenceObject object = new UserSimPreferenceObject();
    	object.number = cursor.getString(UserPreferenceProviderHelper.USER_SIM_PREFERENCE_NUMBER);
    	object.sim_sn = cursor.getString(UserPreferenceProviderHelper.USER_SIM_PREFERENCE_USER_SIM_SN);
    	object.call_count = cursor.getInt(UserPreferenceProviderHelper.USER_SIM_PREFERENCE_CALL_COUNT);
    	return object;
    }
}
