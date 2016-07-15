package com.geniusgithub.phonetools.model;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

import com.geniusgithub.phonetools.util.CommonLog;
import com.geniusgithub.phonetools.util.LogFactory;

public class SIMInfo {
	

    private static final CommonLog log = LogFactory.createLog();
    
    
    private static final String TAG = "SIMInfo";
    private static final boolean DEBUG = true;
    
    public static final class Sim_Info implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.parse("content://telephony/siminfo");
        
        public static final String DEFAULT_SORT_ORDER = "name ASC";
        
        /**
         * <P>Type: TEXT</P>
         */
        
        public static final String ICC_ID = "icc_id";
        
        /**
         * <P>Type: TEXT</P>
         */
        public static final String DISPLAY_NAME = "display_name";
        
        /**
         * <P>Type: TEXT</P>
         */
        public static final String NUMBER = "number";
        
        /**
         * <P>Type: TEXT</P>
         */
        public static final String TYPE = "type";
        
        /**
         * Eight kinds of colors. 0-3 will represent the eight colors.
         * Default value: any color that is not in-use.
         * <P>Type: INTEGER</P>
         */
        public static final String COLOR = "color";
        public static final int COLOR_DEFAULT = 0x00112233;
        
        /**
         * <P>Type: INTEGER</P>
         */
        public static final String SLOT = "sim_id";
        public static final int SLOT_NONE = -1;
        
        public static final int DISPLAY_NUMBER_FIRST    = 1;
        public static final int DISPLAY_NUMBER_LAST     = 2;        
    }
    
    public int mSubId;
    public String mICCId;
    public String mDisplayName = "";
    public String mNumber = "";

    public int mColor;                                      //ARGB value, warning: not finished yet
    public int mSlot = Sim_Info.SLOT_NONE;
    
    public static class ErrorCode {
        public static final int ERROR_GENERAL = -1;
        public static final int ERROR_NAME_EXIST = -2;
    }

    private static SIMInfo fromCursor(Cursor cursor) {
        SIMInfo info = new SIMInfo();
        info.mSubId = cursor.getInt(cursor.getColumnIndexOrThrow(Sim_Info._ID));
        info.mICCId = cursor.getString(cursor.getColumnIndexOrThrow(Sim_Info.ICC_ID));
        info.mDisplayName = cursor.getString(cursor.getColumnIndexOrThrow(Sim_Info.DISPLAY_NAME));
        info.mNumber = cursor.getString(cursor.getColumnIndexOrThrow(Sim_Info.NUMBER));
        info.mColor = cursor.getInt(cursor.getColumnIndexOrThrow(Sim_Info.COLOR));
        info.mSlot = cursor.getInt(cursor.getColumnIndexOrThrow(Sim_Info.SLOT));        
        log.i("mSubId = " + info.mSubId + ", mDisplayName = " +  info.mDisplayName + ", mNumber = " +  info.mNumber);
        return info;
    }
    



    /**
     * 
     * @param ctx
     * @return the array list of Current SIM Info
     */
    public static List<SIMInfo> getInsertedSIMList(Context ctx) {
        ArrayList<SIMInfo> simList = new ArrayList<SIMInfo>();
        Cursor cursor = ctx.getContentResolver().query(Sim_Info.CONTENT_URI, 
                null, Sim_Info.SLOT + "!=" + Sim_Info.SLOT_NONE, null, null);
        try {
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    simList.add(SIMInfo.fromCursor(cursor));
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        if(simList.size() > 1){
            SIMInfo info = simList.get(0);
            if(info.mSlot != 0){
                simList.remove(info);
                simList.add(info);
            }
        }

        return simList;
    }
    
    /**
     * 
     * @param ctx
     * @return array list of all the SIM Info include what were used before
     */
    public static List<SIMInfo> getAllSIMList(Context ctx) {
        ArrayList<SIMInfo> simList = new ArrayList<SIMInfo>();
        
        Cursor cursor = ctx.getContentResolver().query(Sim_Info.CONTENT_URI, 
                null, null, null, null);
        try {
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    simList.add(SIMInfo.fromCursor(cursor));
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        
        return simList;
    }
    
    /**
     * 
     * @param ctx
     * @param SIMId the unique SIM id
     * @return SIM-Info, maybe null
     */
    public static SIMInfo getSIMInfoById(Context ctx, long SIMId) {
        if (SIMId <= 0 ) {
            return null;
        }
        Cursor cursor = ctx.getContentResolver().query(ContentUris.withAppendedId(Sim_Info.CONTENT_URI, SIMId), 
                null, null, null, null);
        try {
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    return SIMInfo.fromCursor(cursor);
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        
        return null;        
    }
    
    /**
     * 
     * @param ctx
     * @param SIMName the Name of the SIM Card
     * @return SIM-Info, maybe null
     */
    public static SIMInfo getSIMInfoByName(Context ctx, String SIMName) {
        if (SIMName == null) {
            return null;
        }
        Cursor cursor = ctx.getContentResolver().query(Sim_Info.CONTENT_URI, 
                null, Sim_Info.DISPLAY_NAME + "=?", new String[]{SIMName}, null);
        try {
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    return SIMInfo.fromCursor(cursor);
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        
        return null;
    }
    
    /**
     * @param ctx
     * @param cardSlot
     * @return The SIM-Info, maybe null
     */
    public static SIMInfo getSIMInfoBySlot(Context context, int slotId) {
        Cursor cursor = context.getContentResolver().query(Sim_Info.CONTENT_URI, 
                null, Sim_Info.SLOT + "=?", new String[]{"" + slotId}, null);
        
        if(cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    return SIMInfo.fromCursor(cursor);
                }
            } finally {
                cursor.close();
            }
        }
        
        return null;
    }
    
    /**
     * @param ctx
     * @param iccid 
     * @return The SIM-Info, maybe null
     */
    public static SIMInfo getSIMInfoByICCId(Context context, String iccid) {
        Cursor cursor = context.getContentResolver().query(Sim_Info.CONTENT_URI, 
                null, Sim_Info.ICC_ID + "=?", new String[]{iccid}, null);
        
        if(cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    return SIMInfo.fromCursor(cursor);
                }
            } finally {
                cursor.close();
            }
        }
        
        return null;
    }
    
    /**
     * @param ctx
     * @param SIMId
     * @return the slot of the SIM Card, -1 indicate that the SIM card is missing
     */
    public static int getSlotById(Context ctx, long SIMId) {
        if (SIMId <= 0 ) {
            return Sim_Info.SLOT_NONE;
        }
        Cursor cursor = ctx.getContentResolver().query(ContentUris.withAppendedId(Sim_Info.CONTENT_URI, SIMId), 
                new String[]{Sim_Info.SLOT}, null, null, null);
        try {
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    return cursor.getInt(0);
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return  Sim_Info.SLOT_NONE;
    }
    
    /**
     * @param ctx
     * @param SIMName
     * @return the slot of the SIM Card, -1 indicate that the SIM card is missing
     */
    public static int getSlotByName(Context ctx, String SIMName) {
        if (SIMName == null) {
            return Sim_Info.SLOT_NONE;
        }
        Cursor cursor = ctx.getContentResolver().query(Sim_Info.CONTENT_URI, 
                new String[]{Sim_Info.SLOT}, Sim_Info.DISPLAY_NAME + "=?", new String[]{SIMName}, null);
        try {
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    return cursor.getInt(0);
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        
        return Sim_Info.SLOT_NONE;
    }
    
    /**
     * @param ctx
     * @return current SIM Count
     */
    public static int getInsertedSIMCount(Context ctx) {
        Cursor cursor = ctx.getContentResolver().query(Sim_Info.CONTENT_URI, 
                null, Sim_Info.SLOT + "!=" + Sim_Info.SLOT_NONE, null, null);
        try {
            if (cursor != null) {
                return cursor.getCount();
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return 0;
    }
    
    /**
     * @param ctx
     * @return the count of all the SIM Card include what was used before
     */
    public static int getAllSIMCount(Context ctx) {
        Cursor cursor = ctx.getContentResolver().query(Sim_Info.CONTENT_URI, 
                null, null, null, null);
        try {
            if (cursor != null) {
                return cursor.getCount();
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
            
        return 0;
    }

    public static long getSimIdBySlot(Context context, int slotId) {
        Cursor cursor = context.getContentResolver().query(Sim_Info.CONTENT_URI, 
                new String[]{"_id"}, Sim_Info.SLOT + "=?", new String[]{"" + slotId}, null);
        
        if(cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    return cursor.getLong(0);
                }
            } finally {
                cursor.close();
            }
        }
        
        return -2;//Lenovo-sw maods 20131114 modify:"-1"->"-2",to avoid on phone contact indicate_phone_sim=-1
    }
    
    public static int getDisplayNumberFormat() {
        return Sim_Info.DISPLAY_NUMBER_FIRST;
    }
    
    public String getDisplayName(Context context) {     
        return mDisplayName;
    }
    
    public String getDisplayNumber(Context context) {
        return mNumber;
    }       

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "sim id: " + mSubId + "\n slot id: " + mSlot
                + "\n display name: " + mDisplayName
                + "\n number: " + mNumber
                + "\n color: " + mColor;
    }
}
