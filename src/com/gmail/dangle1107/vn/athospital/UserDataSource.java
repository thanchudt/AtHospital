package com.gmail.dangle1107.vn.athospital;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class UserDataSource {
	private SQLiteDatabase database;
	private DatabaseHelper dbHelper;
	private String[] allColumns = { DatabaseHelper.COLUMN_ID, 
			DatabaseHelper.COLUMN_USER_NAME, DatabaseHelper.COLUMN_USER_PASSWORD
	};
	private static final int COLUMN_ID_INDEX = 0;
	private static final int COLUMN_NAME_INDEX = 1;
	private static final int COLUMN_PASSWORD_INDEX = 2;
	
	public UserDataSource(Context context){
		dbHelper = new DatabaseHelper(context);
	}
	
	public void open() throws SQLException {
		//database = dbHelper.getWritableDatabase();
		database = dbHelper.getDb();
	}
	
	public void close() {
		dbHelper.close();
	}
	
	public User createUser(String name, String password) {
		ContentValues values = new ContentValues();
		values.put(DatabaseHelper.COLUMN_USER_NAME, name);
		values.put(DatabaseHelper.COLUMN_USER_PASSWORD, password);
		long insertId = database.insert(DatabaseHelper.TABLE_USER, null, values);
		Cursor cursor = database.query(DatabaseHelper.TABLE_USER, 
				allColumns, DatabaseHelper.COLUMN_ID + " = " + insertId, null, null, null, null);
		cursor.moveToFirst();
		User newUser = cursorToUser(cursor);
		cursor.close();
		return newUser;
	}

	public void deleteUser(User user){
		long id = user.getId();
		System.out.println("User deleted with id: " + id);
		database.delete(DatabaseHelper.TABLE_USER, DatabaseHelper.COLUMN_ID + " = " + id, null);
	}
	
	public List<User> getAllUsers(){
		List<User> users = new ArrayList<User>();
		
		Cursor cursor = database.query(DatabaseHelper.TABLE_USER, allColumns, null, null, null, null, null);
		
		cursor.moveToFirst();
		while(!cursor.isAfterLast()){
			User user = cursorToUser(cursor);
			users.add(user);
			cursor.moveToNext();
		}
		
		//make sure to close the cursor
		cursor.close();
		return users;
	}
	
	private User cursorToUser(Cursor cursor) {
		User user = new User();
		user.setId(cursor.getLong(COLUMN_ID_INDEX));
		user.setName(cursor.getString(COLUMN_NAME_INDEX));
		user.setPassword(cursor.getString(COLUMN_PASSWORD_INDEX));
		return user;
	}
}
