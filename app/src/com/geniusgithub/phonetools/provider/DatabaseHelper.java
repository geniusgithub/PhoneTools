package com.geniusgithub.phonetools.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	 protected Context mContext;
	    private static final String TAG = "UserPreferredSimProvider";

	    protected static final String DATABASE_NAME = "userpreferredsim.db";
	    protected static final String DATABASE_TABLE = UserPreferredSimProvider.DATABASE_TABLE_NUMBER_PREFERENCE;
	    protected static final String DATABASE_SIM_CONFIG_TABLE = UserPreferredSimProvider.DATABASE_TABLE_SIM_CONFIG;
	    // BEGIN Motorola, a24159, IKVPREL1KK-2638 - SmartSIM-Learn from user preference
	    protected static final String DATABASE_USER_SIM_PREFERENCE_TABLE = UserPreferredSimProvider.DATABASE_TABLE_USER_SIM_PREFERENCE;
	    protected static final String DATABASE_PREFERENCE_SETTINGS_TABLE = UserPreferredSimProvider.DATABASE_TABLE_PREFERENCE_SETTINGS;
	    // END IKVPREL1KK-2638

	    public static final String AUTO_SIM_SELECT_PREF = "autoSimSelection";
	    
	    // If you update the database version, check to make sure the
	    // database gets upgraded properly. At a minimum, please confirm that 'upgradeVersion'
	    // is properly propagated through your change.  Not doing so will result in a loss of user
	    // userpreferredsim.
	    // version; 1 initial base
	    // protected static final int DATABASE_VERSION = 1; // initial version for sim sn/number/call/sms
	    // protected static final int DATABASE_VERSION = 2; // add sim config color, sim sn
	    // protected static final int DATABASE_VERSION = 3; // add user sim preference table
	    // protected static final int DATABASE_VERSION = 4;    // IKVPREL1KK-885 convert gray to purple in sim config table
	    //protected static final int DATABASE_VERSION = 5; // IKVPREL1KK-2638 update user sim preference table
	    //protected static final int DATABASE_VERSION = 6; // IKPIM-1851 - SmartSIM-BestCostCall
	    protected static final int DATABASE_VERSION = 7; // IKPIM-1867 - SmartSIM-BestCostCall-add RecentCalls

	    public DatabaseHelper(Context context) {
	        super(context, DATABASE_NAME, null, DATABASE_VERSION);
	        mContext = context;
	    }

	    /**
	     * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
	     *
	     * @param db SQLiteDatabase on which the sql statements are executed
	     */
	    @Override
	    public void onCreate(SQLiteDatabase db) {

	        String fmt = "CREATE TABLE %s ( " +
	                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
	                UserPreferredSimProvider.NUMBER + " TEXT NOT NULL, " +
	                UserPreferredSimProvider.CALL_SIM_SN + " TEXT," +
	                UserPreferredSimProvider.MSG_SIM_SN  + " TEXT);";


	        db.execSQL(String.format(fmt, DATABASE_TABLE));

	        db.execSQL("CREATE INDEX number_idex ON " + DATABASE_TABLE +
	                " (" + "number" + ");");

	        db.execSQL("CREATE TRIGGER " + DATABASE_TABLE + "_delete_empty AFTER UPDATE ON " + DATABASE_TABLE
	                + " BEGIN "
	                + "   DELETE FROM " + DATABASE_TABLE
	                + "     WHERE " + UserPreferredSimProvider.CALL_SIM_SN + " ISNULL AND " + UserPreferredSimProvider.MSG_SIM_SN + " ISNULL;"
	                + " END");

	        //sim config table(sn, label, color)
	        db.execSQL(String.format("CREATE TABLE %s ( " +
	                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
	                UserPreferredSimProvider.SIM_CONFIG_SN + " TEXT NOT NULL, " +
	                UserPreferredSimProvider.SIM_CONFIG_LABEL + " TEXT," +
	                UserPreferredSimProvider.SIM_CONFIG_COLOR + " INTEGER);", DATABASE_SIM_CONFIG_TABLE));

	        // BEGIN Motorola, a24159, IKVPREL1KK-2638 - SmartSIM-Learn from user preference
	        db.execSQL(String.format("CREATE TABLE %s ( " +
	                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
	                UserPreferredSimProvider.NUMBER + " TEXT NOT NULL, " +
	                UserPreferredSimProvider.USER_SIM_SN + " TEXT NOT NULL, " +
	                UserPreferredSimProvider.CALL_COUNT + " INTEGER);", DATABASE_USER_SIM_PREFERENCE_TABLE));

	        db.execSQL(String.format("CREATE TABLE %s ( " +
	                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
	                UserPreferredSimProvider.SETTING_NAME + " TEXT NOT NULL, "+
	                UserPreferredSimProvider.SETTING_VALUE + " INTEGER);", DATABASE_PREFERENCE_SETTINGS_TABLE));

	        db.execSQL(String.format("INSERT INTO %s (" +
	                UserPreferredSimProvider.SETTING_NAME + ", " +
	                UserPreferredSimProvider.SETTING_VALUE + ") VALUES ('" +
	                AUTO_SIM_SELECT_PREF + "', 0);", DATABASE_PREFERENCE_SETTINGS_TABLE));
	        // END IKVPREL1KK-2638

	        //create tables for SmartSIM-BestCostCall
	       // BestCostUtil.createTablesForBestCost(db, mContext);// MOTO MODE, xqc387, IKPIM-1851 - SmartSIM-BestCostCall
	    }

		@Override
		public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
			// TODO Auto-generated method stub
			
		}

	    /**
	     * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
	     *
	     * @param db SQLiteDatabase on which the sql statements are executed
	     * @param oldVersion The previous version of the database
	     * @param newVersion The newer version of the database
	     */
//
//	        if(oldVersion == 1){
//	            //sim config table(sn, label, color)
//	            db.execSQL(String.format("CREATE TABLE %s ( " +
//	                    "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
//	                    UserPreferredSimProvider.SIM_CONFIG_SN + " TEXT NOT NULL, " +
//	                    UserPreferredSimProvider.SIM_CONFIG_LABEL + " TEXT," +
//	                    UserPreferredSimProvider.SIM_CONFIG_COLOR + " INTEGER);", DATABASE_SIM_CONFIG_TABLE));
//	            oldVersion ++;
//	        }
//
//	        // BEGIN Motorola, a24159, IKVPREL1KK-2638 - SmartSIM-Learn from user preference
//	        if(oldVersion == 2){
//	            db.execSQL(String.format("CREATE TABLE %s ( " +
//	                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
//	                UserPreferredSimProvider.NUMBER + " TEXT NOT NULL, " +
//	                "sim_sn1" + " TEXT," +
//	                "sim_sn2" + " TEXT," +
//	                "call_count1" + " INTEGER," +
//	                "call_count2" + " INTEGER," +
//	                "pref_sim_sn" + " TEXT);", DATABASE_USER_SIM_PREFERENCE_TABLE));
//	            oldVersion ++;
//	        }
//	        // END IKVPREL1KK-2638
//
//	        //IKVPREL1KK-885 -- we shall display gray as purple
//	        if(oldVersion == 3){
//
//	            ContentValues val = new ContentValues();
//	            val.put(UserPreferredSimProvider.SIM_CONFIG_COLOR, 0xFF9933CC);
//
//	            db.update(DATABASE_SIM_CONFIG_TABLE,
//	                    val,
//	                    UserPreferredSimProvider.SIM_CONFIG_COLOR + "=?",
//	                    new String[]{String.valueOf(0xFF777777)});
//
//	            oldVersion ++;
//	        }
//
//	        // BEGIN Motorola, a24159, IKVPREL1KK-2638 - SmartSIM-Learn from user preference
//	        if(oldVersion == 4){
//	            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_USER_SIM_PREFERENCE_TABLE);
//	            db.execSQL(String.format("CREATE TABLE %s ( " +
//	                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
//	                UserPreferredSimProvider.NUMBER + " TEXT NOT NULL, " +
//	                UserPreferredSimProvider.USER_SIM_SN + " TEXT NOT NULL, " +
//	                UserPreferredSimProvider.CALL_COUNT + " INTEGER);", DATABASE_USER_SIM_PREFERENCE_TABLE));
//
//	            db.execSQL(String.format("CREATE TABLE %s ( " +
//	                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
//	                UserPreferredSimProvider.SETTING_NAME + " TEXT NOT NULL, "+
//	                UserPreferredSimProvider.SETTING_VALUE + " TEXT);", DATABASE_PREFERENCE_SETTINGS_TABLE));
//
//	            db.execSQL(String.format("INSERT INTO %s ( " +
//	                UserPreferredSimProvider.SETTING_NAME + ", " +
//	                UserPreferredSimProvider.SETTING_VALUE + ") VALUES ('" +
//	                PreferenceSettingsActivity.AUTO_SIM_SELECT_PREF + "', 0);", DATABASE_PREFERENCE_SETTINGS_TABLE));
//
//	            oldVersion ++;
//	        }
//	        // END IKVPREL1KK-2638
//
//	        // BEGIN Motorola, xqc387, IKPIM-1851 - SmartSIM-BestCostCall
//	        if (oldVersion == 5) {
//	            // create tables for SmartSIM-BestCostCall
//	            BestCostUtil.createPhoneLookupAndPhoneOperatorTables(db);
//
//	            oldVersion++;
//	        }
//	        // END Motorola, xqc387, IKPIM-1851 - SmartSIM-BestCostCall
//
//	        // BEGIN Motorola, xqc387, IKPIM-1867 - SmartSIM-BestCostCall-add RecentCalls
//	        if (oldVersion == 6) {
//	            // create tables for SmartSIM-BestCostCall-add RecentCalls
//	            BestCostUtil.modifyTablesToAddRCForBestCost(db);
//
//	            oldVersion++;
//
//	            // trigger the initialization sync with contacts db
//	            PhoneNumberSyncController.getInstance(mContext).startPhoneNumberSync();
//	        }
//	        // END Motorola, xqc387, IKPIM-1867 - SmartSIM-BestCostCall-add RecentCalls
//
//	        if(oldVersion != newVersion){
//	            throw new IllegalStateException("error upgrade to version:" + newVersion);
//	        }
//	    }


}
