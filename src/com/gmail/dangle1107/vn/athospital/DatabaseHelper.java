package com.gmail.dangle1107.vn.athospital;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper{
	
	// Log tag
	private static final String LOG = DatabaseHelper.class.getName();
	// If you change the database schema, you must increment the database version.
	// Database Version
	//private static final int DATABASE_VERSION = 1;
	// Database Name
	//private static final String DATABASE_NAME = "Learner20140226.db";
	// Table Names	
	public static final String TABLE_USER = "user";
	public static final String TABLE_CATEGORY = "category";
	public static final String TABLE_SUBJECT = "subject";
	public static final String TABLE_LIBRARY = "library";	
	public static final String TABLE_TEST = "test";
	public static final String TABLE_KNOWLEDGE = "knowledge";
	public static final String TABLE_LEARN = "learn";
	
	// Common column names
	public static final String COLUMN_ID = "_id";
	
	// USER Table - column names
	public static final String COLUMN_USER_NAME = "name";
	public static final String COLUMN_USER_PASSWORD = "password";
    
	// CATEGORY Table - column names
	public static final String COLUMN_CATEGORY_NAME = "name";
	public static final String COLUMN_CATEGORY_PARENT_ID = "parent_id";
		
	// SUBJECT Table - column names
	public static final String COLUMN_SUBJECT_NAME = "name";
		
	// LIBRARY Table - column names
	public static final String COLUMN_LIBRARY_SUBJECT_ID = "subject_id";
	public static final String COLUMN_LIBRARY_QUESTION = "question";
	public static final String COLUMN_LIBRARY_ANSWER = "answer";
	public static final String COLUMN_LIBRARY_CATEGORY_ID = "category_id";
		
	// Test Table - column names
	public static final String COLUMN_TEST_USER_ID = "user_id";
	public static final String COLUMN_TEST_LIBRARY_ID = "library_id";
	public static final String COLUMN_TEST_LAST_TEST_DATE = "last_test_date";
	public static final String COLUMN_TEST_MARK = "mark";
	public static final String COLUMN_TEST_TIMES = "times";
	public static final String COLUMN_TEST_TOTAL_MARK = "total_mark";
	
	// KNOWLEDGE Table - column names
	public static final String COLUMN_KNOWLEDGE_SUBJECT_ID = "subject_id";
	public static final String COLUMN_KNOWLEDGE_CONTENT = "content";
	public static final String COLUMN_KNOWLEDGE_CATEGORY_ID = "category_id";
		
	// LEARN Table - column names
	public static final String COLUMN_LEARN_USER_ID = "user_id";
	public static final String COLUMN_LEARN_KNOWLEDGE_ID = "knowledge_id";
	public static final String COLUMN_LEARN_LAST_LEARN_DATE = "last_learn_date";
	public static final String COLUMN_LEARN_MARK = "mark";
	public static final String COLUMN_LEARN_TIMES = "times";
	public static final String COLUMN_LEARN_TOTAL_MARK = "total_mark";
		
    /*// Table Create Statements
    // Users table create statement    
    private static final String CREATE_TABLE_USER = "CREATE TABLE "
  	      + TABLE_USER + "(" 
  		  + COLUMN_ID + " INTEGER PRIMARY KEY autoincrement, "
  	      + COLUMN_USER_NAME + " text not null unique, "
  	      + COLUMN_USER_PASSWORD + " text not null"
  	      + ");";
    
    // Category table create statement    
    private static final String CREATE_TABLE_CATEGORY = "CREATE TABLE "
  	      + TABLE_CATEGORY + "(" 
  		  + COLUMN_ID + " INTEGER PRIMARY KEY autoincrement, "
  	      + COLUMN_CATEGORY_NAME + " text not null, "
  	      + COLUMN_CATEGORY_PARENT_ID + " INTEGER null, "
  	      + "FOREIGN KEY ("+ COLUMN_CATEGORY_PARENT_ID +") REFERENCES " + TABLE_CATEGORY + " ("+ COLUMN_ID +")"
  	      + ");";
    
    // SUBJECT table create statement    
    private static final String CREATE_TABLE_SUBJECT = "CREATE TABLE "
  	      + TABLE_SUBJECT + "(" 
  		  + COLUMN_ID + " INTEGER PRIMARY KEY autoincrement, "
  	      + COLUMN_SUBJECT_NAME + " text not null "
  	      + ");";
    
    // LIBRARY table create statement    
    private static final String CREATE_TABLE_LIBRARY = "CREATE TABLE "
  	      + TABLE_LIBRARY + "(" 
  		  + COLUMN_ID + " INTEGER PRIMARY KEY autoincrement, "
  		  + COLUMN_LIBRARY_SUBJECT_ID + " INTEGER not null, "
  	      + COLUMN_LIBRARY_QUESTION + " text not null, "
  	      + COLUMN_LIBRARY_ANSWER + " text not null, "
  	      + COLUMN_LIBRARY_CATEGORY_ID + " INTEGER not null, "
  	      + "FOREIGN KEY ("+ COLUMN_LIBRARY_SUBJECT_ID +") REFERENCES " + TABLE_SUBJECT + " ("+ COLUMN_ID +"),"
  	      + "FOREIGN KEY ("+ COLUMN_LIBRARY_CATEGORY_ID +") REFERENCES " + TABLE_CATEGORY + " ("+ COLUMN_ID +")"
  	      + ");";
    
    
    // Test table create statement    
    private static final String CREATE_TABLE_TEST = "CREATE TABLE "
  	      + TABLE_TEST + "(" 
  		  + COLUMN_ID + " INTEGER PRIMARY KEY autoincrement, "
  	      + COLUMN_TEST_USER_ID + " INTEGER not null, "
  	      + COLUMN_TEST_LIBRARY_ID + " INTEGER not null, "
  	      + COLUMN_TEST_LAST_TEST_DATE + " DATETIME not null, "
  	      + COLUMN_TEST_MARK + " INTEGER not null, "
  	      + COLUMN_TEST_TIMES + " INTEGER not null, "
  	      + COLUMN_TEST_TOTAL_MARK + " INTEGER not null, "
  	      + "FOREIGN KEY ("+ COLUMN_TEST_USER_ID +") REFERENCES " + TABLE_USER + " ("+ COLUMN_ID +")," 
  	      + "FOREIGN KEY ("+ COLUMN_TEST_LIBRARY_ID +") REFERENCES " + TABLE_LIBRARY + " ("+ COLUMN_ID +")"  	      
  	      + ");";
  	*/
    /*
    public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);		
	}
    */
	/*
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_USER);
		db.execSQL(CREATE_TABLE_CATEGORY);
		db.execSQL(CREATE_TABLE_SUBJECT);
		db.execSQL(CREATE_TABLE_LIBRARY);
		db.execSQL(CREATE_TABLE_TEST);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(LOG, "Upgrading database from version " + oldVersion + " to "
				+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUBJECT);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_LIBRARY);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEST);
		onCreate(db);
	}
	*/
    private static final int DATABASE_VERSION = 1;
    public static final String DB_NAME = "Learner.sqlite3";
	//Path to the device folder with databases
    public static String DB_PATH;
    //Database file name
    public SQLiteDatabase database;
    public final Context context;
    private static final String DB_FORMAT_PATH = "//data//data//%s//databases//";
    public SQLiteDatabase getDb() {
        return database;
    }
    
    public DatabaseHelper(Context context, String databaseName) {
		super(context, databaseName, null, DATABASE_VERSION);
		this.context = context;
		 //Write a full path to the databases of your application
		 String packageName = context.getPackageName();
		 DB_PATH = String.format(DB_FORMAT_PATH, packageName);
		 openDataBase();
    }
    
    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
        this.context = context;
	     //Write a full path to the databases of your application
	     String packageName = context.getPackageName();
	     DB_PATH = String.format(DB_FORMAT_PATH, packageName);
	     openDataBase();
    }
    
    //This piece of code will create a database if it’s not yet created
    public void createDataBase() {
        boolean dbExist = checkDataBase();
        if (!dbExist) {
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                Log.e(this.getClass().toString(), "Copying error");
                throw new Error("Error copying database!");
            }
        } else {
            Log.i(this.getClass().toString(), "Database already exists");
        }
    }
    
    //Performing a database existence check
    private boolean checkDataBase() {
        SQLiteDatabase checkDb = null;
        try {
            String path = DB_PATH + DB_NAME;
            checkDb = SQLiteDatabase.openDatabase(path, null,
                          SQLiteDatabase.OPEN_READONLY);
        } catch (SQLException e) {
            Log.e(this.getClass().toString(), "Error while checking db");
        }
        //Android doesn’t like resource leaks, everything should 
        // be closed
        if (checkDb != null) {
            checkDb.close();
        }
        return checkDb != null;
    }
    
    //Method for copying the database
    private void copyDataBase() throws IOException {
        //Open a stream for reading from our ready-made database
        //The stream source is located in the assets
        InputStream externalDbStream = context.getAssets().open(DB_NAME);

         //Path to the created empty database on your Android device
        String outFileName = DB_PATH + DB_NAME;

         //Now create a stream for writing the database byte by byte
        OutputStream localDbStream = new FileOutputStream(outFileName);

         //Copying the database
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = externalDbStream.read(buffer)) > 0) {
            localDbStream.write(buffer, 0, bytesRead);
        }
        //Don’t forget to close the streams
        localDbStream.close();
        externalDbStream.close();
    }
    
    public SQLiteDatabase openDataBase() throws SQLException {
        String path = DB_PATH + DB_NAME;
        if (database == null) {
            createDataBase();
            database = SQLiteDatabase.openDatabase(path, null,
                SQLiteDatabase.OPEN_READWRITE);
        }
        return database;
    }
    
    @Override
    public synchronized void close() {
        if (database != null) {
            database.close();
        }
        super.close();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {}
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}
