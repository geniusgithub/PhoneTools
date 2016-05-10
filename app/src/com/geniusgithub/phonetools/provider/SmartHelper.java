package com.geniusgithub.phonetools.provider;

import android.R.integer;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

public class SmartHelper {

    private static final String TAG = "SmartHelper";
    public static final int SIM_PREF_NOT_FOUND = -1;
    
    private static final String NUMBER = "number";
    
    private Context mContext;
    private String mNumber;
    
    public SmartHelper(Context context, String number) {
        mContext = context;
        mNumber = number;
    
    }
    
    public  int getRememberedSim(String number) {
        int subId = SIM_PREF_NOT_FOUND;
        Uri uri = Uri.withAppendedPath(UserPreferredSimProvider.CONTENT_URI_ENTRY, Uri.encode(number));
        String simSn = getQueryResult(uri,
                new String[] {UserPreferredSimProvider.CALL_SIM_SN}, null, null, null);
        if (simSn != null) {
            subId = getSubIdForSimSn(simSn);
        }
        Log.d(TAG, "getRememberedSim for number is "+subId);
        return subId;
    }
    
    public int getLearnModeSim(String number) {
        int subId = SIM_PREF_NOT_FOUND;
        Uri uri = UserPreferredSimProvider.CONTENT_URI_USER_SIM_PREFERENCE;
        uri = Uri.withAppendedPath(uri, Uri.encode(number));
        String simSn = getQueryResult(uri,
                new String[] {UserPreferredSimProvider.USER_SIM_SN}, null, null, null);
        if (simSn != null) {
            subId = getSubIdForSimSn(simSn);
        }
        Log.d(TAG, "getLearnModeSim for number is "+subId);
        return subId;
    }

    private void updateUsageData(String number, String simSerial) {

        ContentValues values = new ContentValues();

        Log.d(TAG, "updateUsageData number");
        if (TextUtils.isEmpty(number) || TextUtils.isEmpty(simSerial)) {
            return ;
        }

        //check if number already exist in the db
        Cursor cur = null;
        try {
            Uri uri = UserPreferredSimProvider.CONTENT_URI_USER_SIM_PREFERENCE;

            values.put(NUMBER, number);
            uri = Uri.withAppendedPath(uri, Uri.encode(number));
            String where = String.format(" sim_sn = '%s' ", simSerial);
            cur = mContext.getContentResolver().query(uri, null, where, null, null);
            Log.d(TAG,"queried for number");

            if (cur != null && cur.getCount() == 0){
                 Log.d(TAG," new Insert");
                values.put(UserPreferredSimProvider.USER_SIM_SN, simSerial);
                values.put(UserPreferredSimProvider.CALL_COUNT, 1);
                mContext.getContentResolver().insert(UserPreferredSimProvider.CONTENT_URI_USER_SIM_PREFERENCE, values);
            } else {
                 Log.d(TAG," already exist, will be a update");
                cur.moveToNext();
                int callCount = (int)(cur.getInt(cur.getColumnIndex(UserPreferredSimProvider.CALL_COUNT))) +1;

                values.put(UserPreferredSimProvider.CALL_COUNT, callCount);
                mContext.getContentResolver().update(uri, values, where, null);
            }

            values.clear();
            values.put(UserPreferredSimProvider.CALL_COUNT, 0);
            String updateWhere = String.format(" sim_sn != '%s' ", simSerial);
            mContext.getContentResolver().update(uri, values, updateWhere, null);

        } finally {
            if (cur != null) {
                cur.close();
            }
        }
    }
    
    
    private  String getQueryResult(Uri uri, String[] projection, String where, String[] whereArgs, String sortOrder) {
        Cursor cursor = null;
        String result = null;

        try {
            cursor = mContext.getContentResolver().query(uri, projection, where, whereArgs, sortOrder);
            if (cursor != null && cursor.moveToNext()) {
               result = cursor.getString(cursor.getColumnIndex(projection[0]));
            }
        } catch(SQLiteException e) {
            Log.v(TAG, "Caught SQLexception "+ e.getMessage() +" while retrieving uri " + uri);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return result;
    }
    
    private int getSubIdForSimSn(String simSn) {
        int subId = SIM_PREF_NOT_FOUND;
        
        try {
            subId = Integer.valueOf(simSn);
		} catch (Exception e) {
			e.printStackTrace();
		}

//        //BEGIN Motorola, a223056, IKVPREL1L-1271
//        for(int i = 0; i < MultiSimManager.getPhoneCount(mContext); i++) {
//            if (simSn != null && simSn.equals(MultiSimManager.getSimSerialNumber(mContext,i))) {
//        //END IKVPREL1L-1271
//                //If serial no. matches returns the same sim as the preferred one
//                subId = i;
//                break;
//            }
//        }
        return subId;
    }

    
}
