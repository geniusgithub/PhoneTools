package com.geniusgithub.phonetools.provider;

import android.R.anim;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.util.Log;

public class UserPreferredSimProvider extends ContentProvider{

	 private static final String TAG = "UserPreferredSimProvider";

	    private static final boolean DEBUG = true;

	    public static final String PROVIDER_NAME = "com.geniusgithub.phonetools.providers.UserPreferredSimProvider";
	    public static final Uri CONTENT_URI_CALL = Uri.parse("content://" + PROVIDER_NAME + "/call");
	    public static final Uri CONTENT_URI_MESSAGE = Uri.parse("content://" + PROVIDER_NAME + "/message");
	    public static final Uri CONTENT_URI_ENTRY = Uri.parse("content://" + PROVIDER_NAME + "/entry");

	    public static final Uri CONTENT_URI_SIM_CONFIG = Uri.parse("content://" + PROVIDER_NAME + "/sim_config");

	    //BEGIN Motorola, a24159, IKVPREL1KK-2638 - SmartSIM-Learn from user preference
	    public static final Uri CONTENT_URI_USER_SIM_PREFERENCE = Uri.parse("content://" + PROVIDER_NAME + "/user_sim_preference");
	    public static final Uri CONTENT_URI_PREFERENCE_SETTINGS = Uri.parse("content://" + PROVIDER_NAME + "/preference_settings");
	    //END IKVPREL1KK-2638

	    //BEGIN Motorola, a24159, IKVPREL2KK-1900 - SmartSIM-BestCostCall
	    public static final Uri CONTENT_URI_PHONE_OPERATOR = Uri.parse("content://" + PROVIDER_NAME + "/phone_operator");
	    //END IKVPREL2KK-1900

	    // BEGIN IKSWSRVC-899
	    public static final Uri CONTENT_URI_DEFAULT_DDD = Uri.parse("content://" + PROVIDER_NAME + "/default_ddd");
	    // END IKSWSRVC-899
	    // MOTO CODE BEGIN - IKSWM-23299: Read/write DDD and automatic sim properties
	    public static final Uri CONTENT_URI_AUTO_SIM_SELECT = Uri.parse("content://" + PROVIDER_NAME
	                                                                    + "/auto_sim_select");
	    // MOTO CODE END - IKSWM-23299

	    // BEGIN Motorola, a23056 , IKPIM-2084 - SmartSIM
	    public static final String METHOD_BESTSIM = "bestsim";
	    private static final String PARAM_DIALED_NUMBER ="dialednumber";
	    private static final String PARAM_SIM_PREFERENCE ="simpreference";
	    private static final String PARAM_TEXT_TOSHOW ="subtext";
	    private static final String PARAM_SUGGESTION_TYPE = "suggestiontype";
	    private static final String PARAM_DIALOG_SUBTEXT = "dialogSubText";
	    // END Motorola, a23056 ,IKPIM-2084 - SmartSIM
	    // MOTO CODE BEGIN - IKSWM-23299: Read/write DDD and automatic sim properties
	    private static final String PARAM_DEFAULT_DDD = "default_ddd";
	    private static final String PARAM_AUTO_SIM_SELECT = "auto_sim_select";
	    // MOTO CODE END - IKSWM-23299

	    public static final String DATABASE_TABLE_NUMBER_PREFERENCE = "number_preference";
	    public static final String DATABASE_TABLE_SIM_CONFIG = "sim_config";
	    public static final String FROM_BACKUPAGENT = "from_backup_agent";
	    //BEGIN Motorola, a24159, IKVPREL1KK-2638 - SmartSIM-Learn from user preference
	    public static final String DATABASE_TABLE_USER_SIM_PREFERENCE = "user_sim_preference";
	    public static final String DATABASE_TABLE_PREFERENCE_SETTINGS = "preference_settings";
	    //END IKVPREL1KK-2638
	    
	    
	    public static final String DEFAULT_AREA_CODE_PREF = "defaultAreaCode";
	    public static final String AUTO_SIM_SELECT_PREF = "autoSimSelection";

	    /* fields in the data set */
	    public static final String ID = "_id";           //unique id
	    public static final String NUMBER = "number";    //phone number
	    public static final String CALL_SIM_SN = "call_sim_sn"; // preferred sim serial for calling
	    public static final String MSG_SIM_SN = "msg_sim_sn";   //preferred sim serial for messaging
	    //BEGIN Motorola, a24159, IKVPREL1KK-2638 - SmartSIM-Learn from user preference
	    public static final String USER_SIM_SN = "sim_sn"; // sim serial to store call history for
	    public static final String CALL_COUNT = "call_count"; // call count corresponding to sim_sn2
	    public static final String SETTING_NAME ="name"; // name of the setting
	    public static final String SETTING_VALUE = "value"; // value of the setting
	    //END IKVPREL1KK-2638

	    protected static final int CALL_DIR = 1;
	    protected static final int CALL_ITEM = 2;
	    protected static final int MESSAGE_DIR = 3;
	    protected static final int MESSAGE_ITEM = 4;
	    protected static final int NUMBER_ENTRY = 5;
	    protected static final int SIM_CONFIG = 6;
	    //BEGIN Motorola, a24159, IKVPREL1KK-2638 - SmartSIM-Learn from user preference
	    protected static final int USER_SIM_PREF_ENTRY = 7;
	    protected static final int USER_SIM_PREF = 8;
	    protected static final int PREFERENCE_SETTING = 9;
	    //END IKVPREL1KK-2638
	    // BEGIN Motorola, xqc387, IKPIM-1851 - SmartSIM-BestCostCall
	   // protected static final int BEST_CALL_NUM_SYNC = 10;
	    // END Motorola, xqc387, IKPIM-1851 - SmartSIM-BestCostCall
	    //BEGIN Motorola, a24159, IKVPREL2KK-1900 - SmartSIM-BestCostCall
	    protected static final int BEST_CALL_PHONE_OPERATOR = 11;
	    protected static final int BEST_CALL_PHONE_OPERATOR_ENTRY = 12;
	    //END IKVPREL2KK-1900

	    // BEGIN IKSWSRVC-899
	    protected static final int DEFAULT_DDD = 13;
	    // END IKSWSRVC-899
	    // MOTO CODE BEGIN - IKSWM-23299: Read/write DDD and automatic sim properties
	    protected static final int AUTO_SIM_SELECT = 14;
	    // MOTO CODE END - IKSWM-23299

	    //sim  config column
	    public static final String SIM_CONFIG_ID = "_id";    //unique id
	    public static final String SIM_CONFIG_SN = "sim_sn"; // sim serial number
	    public static final String SIM_CONFIG_LABEL = "label"; // label of the sim card
	    public static final String SIM_CONFIG_COLOR = "color"; // color of the sim card


	    private static final String CURSOR_DIR = "vnd.android.cursor.dir";
	    private static final String CURSOR_ITEM = "vnd.android.cursor.item";

	    private static final UriMatcher uriMatcher;
	    static {
	        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	        uriMatcher.addURI(PROVIDER_NAME, "call", CALL_DIR);
	        uriMatcher.addURI(PROVIDER_NAME, "call/#", CALL_ITEM);
	        uriMatcher.addURI(PROVIDER_NAME, "message", MESSAGE_DIR);
	        uriMatcher.addURI(PROVIDER_NAME, "message/#", MESSAGE_ITEM);
	        uriMatcher.addURI(PROVIDER_NAME, "entry/*", NUMBER_ENTRY);

	        uriMatcher.addURI(PROVIDER_NAME, "sim_config", SIM_CONFIG);

	        //BEGIN Motorola, a24159, IKVPREL1KK-2638 - SmartSIM-Learn from user preference
	        uriMatcher.addURI(PROVIDER_NAME, "user_sim_preference", USER_SIM_PREF);
	        uriMatcher.addURI(PROVIDER_NAME, "user_sim_preference/*", USER_SIM_PREF_ENTRY);
	        uriMatcher.addURI(PROVIDER_NAME, "preference_settings", PREFERENCE_SETTING);
	        //END IKVPREL1KK-2638

	        // BEGIN IKSWSRVC-899
	        uriMatcher.addURI(PROVIDER_NAME, "default_ddd", DEFAULT_DDD);
	        // END IKSWSRVC-899
	        // MOTO CODE BEGIN - IKSWM-23299: Read/write DDD and automatic sim properties
	        uriMatcher.addURI(PROVIDER_NAME, "auto_sim_select", AUTO_SIM_SELECT);
	        // MOTO CODE END - IKSWM-23299

	        // BEGIN Motorola, xqc387, IKPIM-1851 - SmartSIM-BestCostCall
	       // uriMatcher.addURI(PROVIDER_NAME, BestCostConstants.BEST_COST_NUM_SYNC_WITH_CONTACTS, BEST_CALL_NUM_SYNC);
	        // END Motorola, xqc387, IKPIM-1851 - SmartSIM-BestCostCall
	        //BEGIN Motorola, a24159, IKVPREL2KK-1900 - SmartSIM-BestCostCall
	        uriMatcher.addURI(PROVIDER_NAME, "phone_operator", BEST_CALL_PHONE_OPERATOR);
	        uriMatcher.addURI(PROVIDER_NAME, "phone_operator/*", BEST_CALL_PHONE_OPERATOR_ENTRY);
	        //END IKVPREL2KK-1900
	    }

	    protected SQLiteOpenHelper mDatabaseHelper = null;

	    //private BackupManager mBackupManager = null;

	    /**
	     * @see android.content.ContentProvider#onCreate()
	     */
	    @Override
	    public boolean onCreate() {
	        Context context = this.getContext();

	       // mBackupManager = new BackupManager(context);

	        if( mDatabaseHelper == null ) {
	            mDatabaseHelper = new DatabaseHelper(context);
	        }

	        // BEGIN Motorola, xqc387, IKPIM-1851 - SmartSIM-BestCostCall
	        // Init the PhoneNumberSyncController to start observer and background threads
	        // Schedule one sync between contacts db and UPSP for exsiting phoen numbers
	       // PhoneNumberSyncController.getInstance(context).schedulePhoneNumberSync();
	        // END Motorola, xqc387, IKPIM-1851 - SmartSIM-BestCostCall
	        return true;
	    }

	    /**
	     * @see android.content.ContentProvider#getType(android.net.Uri)
	     *
	     * @param uri The URI, for the content to get
	     */
	    @Override
	    public String getType(Uri uri) {

	        switch (uriMatcher.match(uri)) {

	            case CALL_DIR:
	                return CURSOR_DIR + "/call";
	            case CALL_ITEM:
	                return CURSOR_ITEM + "/call";

	            case MESSAGE_DIR:
	                return CURSOR_DIR + "/message";

	            case MESSAGE_ITEM:
	                return CURSOR_ITEM + "/message";

	            case SIM_CONFIG:
	                return CURSOR_DIR + "/sim_config";

	            // BEGIN Motorola, a24159, IKVPREL1KK-2638 - SmartSIM-Learn from user preference
	            case USER_SIM_PREF:
	                return CURSOR_DIR + "/user_sim_preference";
	            case USER_SIM_PREF_ENTRY:
	                return CURSOR_ITEM + "/user_sim_preference";

	            case PREFERENCE_SETTING:
	                return CURSOR_DIR + "/preference_settings";
	            // END IKVPREL1KK-2638

	            // BEGIN Motorola, xqc387, IKPIM-1851 - SmartSIM-BestCostCall
//	            case BEST_CALL_NUM_SYNC:
//	                return BestCostConstants.BEST_COST_NUM_SYNC_WITH_CONTACTS;
	            // END Motorola, xqc387, IKPIM-1851 - SmartSIM-BestCostCall
	            //BEGIN Motorola, a24159, IKVPREL2KK-1900 - SmartSIM-BestCostCall
	            case BEST_CALL_PHONE_OPERATOR_ENTRY:
	                return CURSOR_ITEM + "/phone_operator";
	            case BEST_CALL_PHONE_OPERATOR:
	                return CURSOR_DIR + "/phone_operator";
	            //END IKVPREL2KK-1900
	            // BEGIN IKSWSRVC-899
	            case DEFAULT_DDD:
	                return CURSOR_DIR + "/default_ddd";
	            // END IKSWSRVC-899
	            // MOTO CODE BEGIN - IKSWM-23299: Read/write DDD and automatic sim properties
	            case AUTO_SIM_SELECT:
	                return CURSOR_DIR + "/auto_sim_select";
	            // MOTO CODE END - IKSWM-23299
	            default:
	                throw new IllegalArgumentException(String.format("Unsupported URI %s", uri.toString()));
	        }
	    }

	    // BEGIN Motorola, a23056 , IKPIM-2084 - SmartSIM
	    /**
	     * @param method provider-defined method name to call.Opaque to framework,but must be non-null
	     * @param arg provider-defined String argument. May be null.
	     * @param extras provider-defined Bundle argument. May be null.
	     * @return Bundle, possibly null.Will be null if the ContentProvider does not implement call.
	     * This method is being called from InCallUI to get the necessary information to show the
	     * CallVia dialog with suggestion ,or autodial in case we have learnt,remembered or best carrier
	     * CallVia dialog calls with provider defined method and pass the number as extras and query UPSP
	     * to find the best subscription,label to be displayed in CallVia dialog and InCallUI.
	     * All the necessary parameters are being passed as extras incase we find the bestsubscription
	     * for the number passed to this method.
	     */
//	    @Override
//	    public Bundle call(String method, String arg, Bundle extras) {
//	        if (DEBUG) Log.d(TAG, "Entered call method of UPSP");
//	        if (METHOD_BESTSIM.equals(method)) {
//	            if(extras != null){
//	                SmartSimSuggestion smartSimObj;
//	                String number = null;
//	                String autoSimSelectLabel = null;
//	                String dialogSubText = null;
//	                int subscription = SmartSimUtilities.SIM_PREF_NOT_FOUND;
//	                number = extras.getString(PARAM_DIALED_NUMBER,null);
//	                if(number != null){
//	                    smartSimObj = new SmartSimSuggestion(this.getContext(), number);
//	                    subscription = smartSimObj.getBestSim();
//	                    if (DEBUG) Log.d(TAG,"best subscription found is " + subscription);
//	                    int suggestionType = smartSimObj.getSimSuggestionType();
//	                    if (DEBUG) Log.d(TAG,"suggestiontype found is " + suggestionType);
//	                    if(subscription != SmartSimUtilities.SIM_PREF_NOT_FOUND){
//	                        autoSimSelectLabel = smartSimObj.getAutoSimSelectLabel(subscription);
//	                        if (DEBUG) Log.d(TAG,"autoSimSelectLabel is " + autoSimSelectLabel);
//	                        dialogSubText = smartSimObj.getDialogSubText(suggestionType);
//	                        if (DEBUG) Log.d(TAG,"dialogSubText is " + dialogSubText);
//	                    }
//	                    Bundle result = new Bundle();
//	                    result.putInt(PARAM_SIM_PREFERENCE, subscription);
//	                    result.putString(PARAM_TEXT_TOSHOW, autoSimSelectLabel);
//	                    result.putString(PARAM_DIALOG_SUBTEXT, dialogSubText);
//	                    result.putInt(PARAM_SUGGESTION_TYPE, suggestionType);
//	                    return result;
//	                }else {
//	                    if (DEBUG) Log.d(TAG,"number received is null");
//	                    Bundle result = new Bundle();
//	                    result.putInt(PARAM_SIM_PREFERENCE, SmartSimUtilities.SIM_PREF_NOT_FOUND);
//	                    return result;
//	                }
//	            }else{
//	               if (DEBUG) Log.d(TAG,"extras being passed are null");
//	            }
//	        }
//	        return null;
//	    }
	    // END Motorola, a23056 ,IKPIM-2084 - SmartSIM
	    /**
	     * @see android.content.ContentProvider#query(android.net.Uri, java.lang.String[], java.lang.String, java.lang.String[], java.lang.String)
	     *
	     * @param uri The URI, for the content to update
	     * @param select The list of which columns to return
	     * @param where Where clause to filter the rows
	     * @param whereArgs the selection arguments which will replace the '?' in the selection
	     * @param order ordering of the rows, formatted as an SQL ORDER BY clause
	     */
	    @Override
	    public Cursor query(Uri uri, String[] select, String where, String[] whereArgs, String order) {


	        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

	        Cursor rv = null;


	        SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();

	        switch (uriMatcher.match(uri)){
	            case CALL_DIR:
	            case MESSAGE_DIR:
	                qb.setTables(DATABASE_TABLE_NUMBER_PREFERENCE);

	                break;

	            case CALL_ITEM:
	            case MESSAGE_ITEM:
	                qb.setTables(DATABASE_TABLE_NUMBER_PREFERENCE);
	                qb.appendWhere(String.format("_id = %s", uri.getPathSegments().get(1)));

	                break;

	            case NUMBER_ENTRY:
	                qb.setTables(DATABASE_TABLE_NUMBER_PREFERENCE);
	                String number = uri.getPathSegments().size() > 1
	                        ? uri.getLastPathSegment() : "";

	                String min_number = PhoneNumberUtils.toCallerIDMinMatch(number);
	                qb.appendWhere(String.format("number = '%s'", min_number));
	                break;

	            case SIM_CONFIG:
	                qb.setTables(DATABASE_TABLE_SIM_CONFIG);
	                break;

	            // BEGIN Motorola, a24159, IKVPREL1KK-2638 - SmartSIM-Learn from user preference
	            case USER_SIM_PREF:
	                qb.setTables(DATABASE_TABLE_USER_SIM_PREFERENCE);
	                break;

	            case USER_SIM_PREF_ENTRY:
	                qb.setTables(DATABASE_TABLE_USER_SIM_PREFERENCE);
	                String phone_number = uri.getPathSegments().size() > 1
	                        ? uri.getLastPathSegment() : "";

	                String query_number = PhoneNumberUtils.toCallerIDMinMatch(phone_number);
	                qb.appendWhere(String.format("number = '%s'", query_number));
	                if (where == null) {
	                    qb.appendWhere(" AND call_count >= 3");
	                }

	                break;

	            case PREFERENCE_SETTING :
	                qb.setTables(DATABASE_TABLE_PREFERENCE_SETTINGS);
	                break;
	            // END IKVPREL1KK-2638

	            //BEGIN Motorola, a24159, IKVPREL2KK-1900 - SmartSIM-BestCostCall
//	            case BEST_CALL_PHONE_OPERATOR_ENTRY:
//	                qb.setTables(BestCostConstants.Tables.DATABASE_TABLE_PHONE_OPERATOR);
//	                String phone = uri.getPathSegments().size() > 1
//	                        ? uri.getLastPathSegment() : "";
//
//	                qb.appendWhere(String.format("phone_number = '%s'", phone));
//	                break;
//	            case BEST_CALL_PHONE_OPERATOR:
//	                qb.setTables(BestCostConstants.Tables.DATABASE_TABLE_PHONE_OPERATOR);
//	                break;
	            //END IKVPREL2KK-1900
	            // BEGIN IKSWSRVC-899
	            case DEFAULT_DDD:
	                // MOTO CODE BEGIN - IKSWM-23299: Read/write DDD and automatic sim properties
	                MatrixCursor mc = new MatrixCursor(new String[] {
	                        PARAM_DEFAULT_DDD
	                });
	                // MOTO CODE END - IKSWM-23299
	                String areaCode = PreferenceManager.getDefaultSharedPreferences(getContext())
	                        .getString(DEFAULT_AREA_CODE_PREF , "");
	                mc.addRow(new Object[]{areaCode});
	                return mc;
	            // END IKSWSRVC-899

	            // MOTO CODE BEGIN - IKSWM-23299: Read/write DDD and automatic sim properties
	            case AUTO_SIM_SELECT: {
	                MatrixCursor autoSimCursor = new MatrixCursor(new String[] {
	                        PARAM_AUTO_SIM_SELECT
	                });

	                autoSimCursor.addRow(new Object[]{getAutoSimSelectPref(getContext())
	                });

	                return autoSimCursor;
	            }
	            // MOTO CODE END - IKSWM-23299
	            default:
	                throw new IllegalArgumentException(String.format("Unsupported URI %s", uri.toString()));
	        }

	        try {
	            rv = qb.query(db, select, where, whereArgs, null, null, null);
	        } catch (IllegalArgumentException e) {
	            android.util.Log.e(TAG, "Unable to query " +  e.getMessage());
	        }

	        return rv;
	    }

	    /**
	     * @see android.content.ContentProvider#delete(android.net.Uri, java.lang.String, java.lang.String[])
	     *
	     * @param uri The URI, for the content to delete
	     * @param where Where clause to filter the rows
	     * @param whereArgs the selection arguments which will replace the '?' in the selection
	     *
	     */
	    @Override
	    public int delete(Uri uri, String where, String[] whereArgs) {
	        int count = 0;
	        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();

	        Log("In delete()");

	        try {
	            switch (uriMatcher.match(uri)){
	                case CALL_DIR:
	                case MESSAGE_DIR:
	                    count = db.delete(DATABASE_TABLE_NUMBER_PREFERENCE, where, whereArgs);

	                    break;

	                case CALL_ITEM:
	                case MESSAGE_ITEM:
	                    count = db.delete(DATABASE_TABLE_NUMBER_PREFERENCE, extendWhere(uri, where), whereArgs);

	                    break;

	                case SIM_CONFIG:
	                    count = db.delete(DATABASE_TABLE_SIM_CONFIG, where, whereArgs);

	                // BEGIN Motorola, a24159, IKVPREL1KK-2638 - SmartSIM-Learn from user preference
	                    break;

	                case USER_SIM_PREF:
	                    count = db.delete(DATABASE_TABLE_USER_SIM_PREFERENCE, where, whereArgs);
	                    break;

	                case PREFERENCE_SETTING:
	                    count = db.delete(DATABASE_TABLE_PREFERENCE_SETTINGS, where, whereArgs);
	                    break;
	                // END IKVPREL1KK-2638

	                default:
	                    throw new IllegalArgumentException();
	            }

	        } catch (IllegalArgumentException e) {
	            android.util.Log.e(TAG, "Unable to delete " +  e.getMessage());
	        }

	        if(count > 0){
	            if(uriMatcher.match(uri) == SIM_CONFIG){
	                getContext().getContentResolver().notifyChange(CONTENT_URI_SIM_CONFIG, null);
	            // BEGIN Motorola, a24159, IKVPREL1KK-2638 - SmartSIM-Learn from user preference
	            } else if (uriMatcher.match(uri) == USER_SIM_PREF) {
	                getContext().getContentResolver().notifyChange(CONTENT_URI_USER_SIM_PREFERENCE, null);
	            } else if (uriMatcher.match(uri) == PREFERENCE_SETTING) {
	                getContext().getContentResolver().notifyChange(CONTENT_URI_PREFERENCE_SETTINGS, null);
	            // END IKVPREL1KK-2638
	            } else {
	                getContext().getContentResolver().notifyChange(CONTENT_URI_ENTRY, null);
	            //    mBackupManager.dataChanged();
	                Log("BackupManager.dataChanged");
	            }
	        }

	        return count;
	    }

	    /**
	     * @see android.content.ContentProvider#insert(android.net.Uri, android.content.ContentValues)
	     *
	     * @param uri The URI, for the content to insert
	     * @param values The new Content values to be inserted
	     */
	    @Override
	    public Uri insert(Uri uri, ContentValues values) {
	        Uri     rv         = null;
	        long    id         = 0;
	        SQLiteDatabase db  = mDatabaseHelper.getWritableDatabase();

	        Log("In insert()");

	        switch (uriMatcher.match(uri)){
	            case SIM_CONFIG:
	                id = db.insert(DATABASE_TABLE_SIM_CONFIG, null, values);
	                if(id > 0){
	                    rv = ContentUris.withAppendedId(uri, id);
	                    getContext().getContentResolver().notifyChange(CONTENT_URI_SIM_CONFIG, null);
	                }
	                break;

	            case CALL_DIR:
	            case MESSAGE_DIR:{
	                String  min_number = null;
	                String number = values.getAsString(NUMBER);

	                if(!TextUtils.isEmpty(number)){

	                    if(uri.getBooleanQueryParameter(FROM_BACKUPAGENT, false)){
	                        min_number = number;
	                    }else{
	                        min_number = PhoneNumberUtils.toCallerIDMinMatch(number);
	                    }

	                    values.put(NUMBER, min_number);

	                }else{
	                    return null;
	                }

	                //check if number already exist in the db
	                Cursor cur = null;
	                try{
	                    String where="number=?";
	                    String[] arges=new String[]{min_number};

	                    cur = db.query(DATABASE_TABLE_NUMBER_PREFERENCE, new String[]{ID}, where, arges, null,null,null);

	                    if(cur.getCount() == 0){
	                        Log("in insert(): new Insert");
	                        id = db.insert(DATABASE_TABLE_NUMBER_PREFERENCE, null, values);
	                    }else{
	                        Log("in insert(): already exist, will be a update");
	                        cur.moveToNext();
	                        id = cur.getLong(0);
	                        db.update(DATABASE_TABLE_NUMBER_PREFERENCE, values, where, arges);
	                    }

	                    if (id > 0) {
	                        rv = ContentUris.withAppendedId(uri, id);
	                        getContext().getContentResolver().notifyChange(CONTENT_URI_ENTRY, null);
	                    }

	                }finally{
	                    if(cur != null){
	                        cur.close();
	                    }
	                }

	               // mBackupManager.dataChanged();
	                Log("mBackupManager.dataChanged");
	                break;
	            }

	            // BEGIN Motorola, a24159, IKVPREL1KK-2638 - SmartSIM-Learn from user preference
	            case USER_SIM_PREF:
	               android.util.Log.d(TAG, "insert USER_SIM_PREF ");
	               String minNumber = null;
	               String number = values.getAsString(NUMBER);

	               if (!TextUtils.isEmpty(number)){
	                   minNumber = PhoneNumberUtils.toCallerIDMinMatch(number);
	                   values.put(NUMBER, minNumber);
	                } else {
	                    return null;
	                }

	               id = db.insert(DATABASE_TABLE_USER_SIM_PREFERENCE, null, values);

	               if (id > 0) {
	                   rv = ContentUris.withAppendedId(uri, id);
	                   getContext().getContentResolver().notifyChange(CONTENT_URI_USER_SIM_PREFERENCE, null);
	               }
	               break;

	            case PREFERENCE_SETTING:
	                id = db.insert(DATABASE_TABLE_PREFERENCE_SETTINGS, null, values);
	                if (id > 0){
	                    rv = ContentUris.withAppendedId(uri, id);
	                    getContext().getContentResolver().notifyChange(CONTENT_URI_PREFERENCE_SETTINGS, null);
	                }
	                break;

	            // END IKVPREL1KK-2638

	            default:
	                throw new IllegalArgumentException("Unknown URI " + uri);
	        }

	        return rv;
	    }


	    /**
	     * @see android.content.ContentProvider#update(android.net.Uri, android.content.ContentValues, java.lang.String, java.lang.String[])
	     *
	     * @param uri The URI, for the content to update
	     * @param values The Content values to be updated to the uri
	     * @param where Where clause to filter the rows
	     * @param whereArgs the selection arguments which will replace the '?' in the selection
	     */
	    @Override
	    public int update(Uri uri, ContentValues values, String where, String[] whereArgs) {

	        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
	        int count = 0;

	        Log("In update()");
	        try{
	            switch (uriMatcher.match(uri)){
	                case SIM_CONFIG:{
	                    count = db.update(DATABASE_TABLE_SIM_CONFIG, values, where, whereArgs);

	                    break;
	                }
	                case NUMBER_ENTRY:
	                case CALL_ITEM:
	                case MESSAGE_ITEM:{
	                    if(values.containsKey(NUMBER)){
	                        values.remove(NUMBER);
	                    }
	                    count = db.update(DATABASE_TABLE_NUMBER_PREFERENCE, values, extendWhere(uri, where), whereArgs);
	                    break;
	                }

	                // BEGIN Motorola, a24159, IKVPREL1KK-2638 - SmartSIM-Learn from user preference
	                case USER_SIM_PREF:
	                    if(values.containsKey(NUMBER)){
	                        values.remove(NUMBER);
	                    }
	                    count = db.update(DATABASE_TABLE_USER_SIM_PREFERENCE, values, where, whereArgs);

	                    break;
	                case USER_SIM_PREF_ENTRY:
	                    if(values.containsKey(NUMBER)){
	                        values.remove(NUMBER);
	                    }
	                    count = db.update(DATABASE_TABLE_USER_SIM_PREFERENCE, values, extendWhere(uri,where), whereArgs);

	                    break;

	                case PREFERENCE_SETTING:
	                    if(values.containsKey(SETTING_NAME)){
	                        values.remove(SETTING_NAME);
	                    }
	                    count = db.update(DATABASE_TABLE_PREFERENCE_SETTINGS, values, where, whereArgs);

	                    break;
	                // END IKVPREL1KK-2638

	                // BEGIN Motorola, xqc387, IKPIM-1851 - SmartSIM-BestCostCall
//	                case BEST_CALL_NUM_SYNC:
//	                    if (BestCost.DEBUG) {
//	                        Log.v(TAG, "UserPreferredSimProvider update BEST_CALL_NUM_SYNC");
//	                    }
//	                    BestCostUtil.syncPhoneNumberWithContacts(db, getContext());
//	                    //TO DO:
//	                    //notify specific uri only after sync completed
//	                    break;
	                 // END Motorola, xqc387, IKPIM-1851 - SmartSIM-BestCostCall

	                //BEGIN Motorola, a24159, IKVPREL2KK-1900 - SmartSIM-BestCostCall
//	                case BEST_CALL_PHONE_OPERATOR:
//	                     count = db.update(BestCostConstants.Tables.DATABASE_TABLE_PHONE_OPERATOR, values, where, whereArgs);
//	                     if (DEBUG) Log.d(TAG, "UPSP update PHONE_OPERATOR count="+count);
//	                     break;
//
//	                case BEST_CALL_PHONE_OPERATOR_ENTRY:
//	                    count = db.update(BestCostConstants.Tables.DATABASE_TABLE_PHONE_OPERATOR, values, extendWhere(uri, where), whereArgs);
//	                    if (DEBUG) Log.d(TAG, "UPSP update PHONE_OPERATOR ENTRY count="+count);
//	                break;
	                //END IKVPREL2KK-1900

	                // MOTO CODE BEGIN - IKSWM-23299: Read/write DDD and automatic sim properties
//	                case DEFAULT_DDD: {
//	                    //TODO remove debug logs
//	                    String value = (String) values.get(PARAM_DEFAULT_DDD);
//	                    if (!TextUtils.isEmpty(value)) {setDefaultAreaCodePref(getContext(), value);
//	                        return 1;
//	                    }
//	                    Log.w(TAG, "Update DDD failed.");
//	                    return 0;
//	                }

	                case  AUTO_SIM_SELECT: {
	                    Boolean value = (Boolean) values.get(PARAM_AUTO_SIM_SELECT);
	                    if (value != null) {setAutoSimSelectPref(getContext(), value);
	                        return 1;
	                    }
	                    Log.d(TAG, "Update automatic SIM failed.");
	                    return 0;
	                }
	                // MOTO CODE END - IKSWM-23299
	                default:
	                    throw new IllegalArgumentException("Unknown URI " + uri);
	            }
	        }catch (IllegalArgumentException e) {
	            Log.e(TAG, "Unable to update " + e.getMessage());
	        }

	        if(count > 0){
	            if(SIM_CONFIG == uriMatcher.match(uri)){
	                getContext().getContentResolver().notifyChange(CONTENT_URI_SIM_CONFIG, null);
	            // BEGIN Motorola, a24159, IKVPREL1KK-2638 - SmartSIM-Learn from user preference
	            } else if (USER_SIM_PREF_ENTRY == uriMatcher.match(uri)) {
	                getContext().getContentResolver().notifyChange(CONTENT_URI_USER_SIM_PREFERENCE, null);
	            } else if (PREFERENCE_SETTING == uriMatcher.match(uri)) {
	                getContext().getContentResolver().notifyChange(CONTENT_URI_PREFERENCE_SETTINGS, null);
	            // END IKVPREL1KK-2638
	            //BEGIN Motorola, a24159, IKVPREL2KK-1900 - SmartSIM-BestCostCall
	            } else if (BEST_CALL_PHONE_OPERATOR == uriMatcher.match(uri) ||
	                    BEST_CALL_PHONE_OPERATOR_ENTRY == uriMatcher.match(uri)) {
	                getContext().getContentResolver().notifyChange(CONTENT_URI_PHONE_OPERATOR, null);
	            //END IKVPREL2KK-1900
	            } else {
	                getContext().getContentResolver().notifyChange(CONTENT_URI_ENTRY, null);
	             //   mBackupManager.dataChanged();
	                Log("mBackupManager.dataChanged()");
	            }
	        }

	        return count;
	    }

	    /**
	     * extend the where string based on the URI
	     *
	     * @param uri uri to extract the path/ID
	     * @param whereStr String consisting of where clause
	     * @return
	     * @throws IllegalArgumentException
	     */
	    private String extendWhere(Uri uri, String whereStr) throws IllegalArgumentException {
	        switch (uriMatcher.match(uri)) {
	        case CALL_ITEM:
	        case MESSAGE_ITEM:
	            try {
	                String id = uri.getPathSegments().get(1);

	                String wherenumber = ID + " = " + id;
	                if (whereStr!= null && !TextUtils.isEmpty(whereStr)) {
	                    wherenumber = wherenumber + " AND ( " + whereStr + " )";
	                }
	                whereStr = wherenumber;
	            }catch(Exception e) {
	                Log.e(TAG, "Where Clause creation exception " + e.getMessage());
	            }
	            break;

	        case NUMBER_ENTRY:

	            try {
	                String number = uri.getPathSegments().get(1);
	                String min_number = PhoneNumberUtils.toCallerIDMinMatch(number);

	                String wherenumber = NUMBER + " = '" + min_number + "'";
	                if (whereStr!= null && !TextUtils.isEmpty(whereStr)) {
	                    wherenumber = wherenumber + " AND ( " + whereStr + " )";
	                }
	                whereStr = wherenumber;

	            }catch(Exception e) {
	                Log.e(TAG, "Where Clause creation exception " + e.getMessage());
	            }
	            break;

	        // BEGIN Motorola, a24159, IKVPREL1KK-2638 - SmartSIM-Learn from user preference
	        case USER_SIM_PREF_ENTRY:
	            try {
	                String number = uri.getPathSegments().get(1);
	                String min_number = PhoneNumberUtils.toCallerIDMinMatch(number);

	                String wherenumber = NUMBER + " = '" + min_number + "'";
	                if (whereStr!= null && !TextUtils.isEmpty(whereStr)) {
	                    wherenumber = wherenumber + " AND ( " + whereStr + " )";
	                }
	                whereStr = wherenumber;

	            }catch(Exception e) {
	                Log.e(TAG, "Where Clause creation exception " + e.getMessage());
	            }
	            break;
	            // END IKVPREL1KK-2638

	        //BEGIN Motorola, a24159, IKVPREL2KK-1900 - SmartSIM-BestCostCall
//	        case BEST_CALL_PHONE_OPERATOR_ENTRY:
//	            try {
//	                String number = uri.getPathSegments().get(1);
//
//	                String wherenumber = BestCostConstants.PhoneOperatorColumns.PHONE_NUMBER + " = '" + number + "'";
//	                if (whereStr!= null && !TextUtils.isEmpty(whereStr)) {
//	                    wherenumber = wherenumber + " AND ( " + whereStr + " )";
//	                }
//	                whereStr = wherenumber;
//
//	            }catch(Exception e) {
//	                Log.e(TAG, "Where Clause creation exception " + e.getMessage());
//	            }
//	            break;
	        //END IKVPREL2KK-1900
	        default:
	            throw new IllegalArgumentException("Unknown URI " + uri);
	        }

	        return whereStr;
	    }

	    static public void Log(String str){
	        if(DEBUG && Log.isLoggable(TAG, Log.DEBUG)){
	            Log.d(TAG, str);
	        }
	    }

	    
	    
	    
	    
	    
	    public static boolean getAutoSimSelectPref(Context context) {
	        SharedPreferences sp =
	                PreferenceManager.getDefaultSharedPreferences(context);
	        return sp.getBoolean(AUTO_SIM_SELECT_PREF, false);
	    }

	    
	    public static void setAutoSimSelectPref(Context context, boolean status) {
	        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
	        SharedPreferences.Editor editor = sp.edit();
	        editor.putBoolean(AUTO_SIM_SELECT_PREF, status);
	        editor.apply();
	    }
	    
//	    public static void setDefaultAreaCodePref(Context context, String value) {
//	        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
//	        SharedPreferences.Editor editor = sp.edit();
//	        editor.putString(DEFAULT_AREA_CODE_PREF, value);
//
//	        // According to default area code value, update related preferences values
//	        if (TextUtils.isEmpty(value)) {
//	            editor.putBoolean(PreferenceSettingsActivity.SMART_SIM_WIZARD_SETUP_DONE, false);
//	            editor.putBoolean(PreferenceSettingsActivity.SMART_WIZARD_NOTIFICATION_SHOWN, false);
//	            editor.putInt(PreferenceSettingsActivity.NOTIFICATION_COUNT, 0);
//	        } else {
//	            editor.putBoolean(PreferenceSettingsActivity.SMART_SIM_WIZARD_SETUP_DONE, true);
//	            editor.putBoolean(PreferenceSettingsActivity.SMART_WIZARD_NOTIFICATION_SHOWN, true);
//	            editor.putBoolean(PreferenceSettingsActivity.AUTO_SIM_SELECT_PREF, true);
//	        }
//	        editor.apply();
//	    }

}
