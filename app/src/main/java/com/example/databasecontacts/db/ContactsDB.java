package com.example.databasecontacts.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ContactsDB extends SQLiteOpenHelper {
	private static final String LOGTAG = "DEBUG";

	private static final String DATABASE_NAME = "contacts.db";
	private static final int DATABASE_VERSION = 1;
	
	public static final String TABLE_CONTACTS = "contacts";
	public static final String BLOCK_LIST = "blocked";
	public static final String LIST2_TABLE = "block1";
	public static final String LIST1_TABLE = "block2";
	public static final String COLUMN_ID = "contactId";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_NUMBER = "cellnumber";
	
	
	private static final String TABLE_CREATE = 
			"CREATE TABLE " + TABLE_CONTACTS + " (" +
			COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
			COLUMN_NAME + " TEXT, " +
			COLUMN_NUMBER + " TEXT " +
			")";
	private static final String LIST1_TABLE_CREATE = 
			"CREATE TABLE " + LIST1_TABLE + " (" +
			COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
			COLUMN_NAME + " TEXT, " +
			COLUMN_NUMBER + " TEXT " +
			")";
	private static final String BLOCK_LIST_CREATE = 
			"CREATE TABLE " + BLOCK_LIST + " (" +
			COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
			COLUMN_NAME + " TEXT, " +
			COLUMN_NUMBER + " TEXT " +
			")";
	private static final String LIST2_TABLE_CREATE = 
			"CREATE TABLE " + LIST2_TABLE + " (" +
			COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
			COLUMN_NAME + " TEXT, " +
			COLUMN_NUMBER + " TEXT " +
			")";
	
			
	public ContactsDB(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_CREATE);
		db.execSQL(LIST2_TABLE_CREATE);
		db.execSQL(LIST1_TABLE_CREATE);
		db.execSQL(BLOCK_LIST_CREATE);
		Log.d(LOGTAG, "Table has been created");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.i("DEBUG", "ONUPGRADE");
		db.execSQL("DROP TABLE IF EXISTS" + TABLE_CONTACTS);
		db.execSQL("DROP TABLE IF EXISTS" + LIST1_TABLE);
		db.execSQL("DROP TABLE IF EXISTS" + LIST2_TABLE);
		db.execSQL("DROP TABLE IF EXISTS" + BLOCK_LIST);
		onCreate(db);
	}

}
