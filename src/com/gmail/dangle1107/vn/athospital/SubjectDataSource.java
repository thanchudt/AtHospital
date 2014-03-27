package com.gmail.dangle1107.vn.athospital;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class SubjectDataSource {
	private SQLiteDatabase database;
	private DatabaseHelper dbHelper;
	private String[] allColumns = { DatabaseHelper.COLUMN_ID, 
			DatabaseHelper.COLUMN_SUBJECT_NAME
	};
	private static final int COLUMN_ID_INDEX = 0;
	private static final int COLUMN_NAME_INDEX = 1;
	
	public SubjectDataSource(Context context){
		dbHelper = new DatabaseHelper(context);
	}
	
	public void open() throws SQLException {
		//database = dbHelper.getWritableDatabase();
		database = dbHelper.getDb();
	}
	
	public void close() {
		dbHelper.close();
	}
	
	public Subject createSubject(String name) {
		ContentValues values = new ContentValues();
		values.put(DatabaseHelper.COLUMN_SUBJECT_NAME, name);
		long insertId = database.insert(DatabaseHelper.TABLE_SUBJECT, null, values);
		Cursor cursor = database.query(DatabaseHelper.TABLE_SUBJECT, 
				allColumns, DatabaseHelper.COLUMN_ID + " = " + insertId, null, null, null, null);
		cursor.moveToFirst();
		Subject newSubject = cursorToSubject(cursor);
		cursor.close();
		return newSubject;
	}

	public void deleteSubject(Subject subject){
		long id = subject.getId();
		System.out.println("Subject deleted with id: " + id);
		database.delete(DatabaseHelper.TABLE_SUBJECT, DatabaseHelper.COLUMN_ID + " = " + id, null);
	}
	
	public List<Subject> getAllSubjects(){
		List<Subject> subjects = new ArrayList<Subject>();
		
		Cursor cursor = database.query(DatabaseHelper.TABLE_SUBJECT, allColumns, null, null, null, null, null);
		
		cursor.moveToFirst();
		while(!cursor.isAfterLast()){
			Subject subject = cursorToSubject(cursor);
			subjects.add(subject);
			cursor.moveToNext();
		}
		
		//make sure to close the cursor
		cursor.close();
		return subjects;
	}
	
	private Subject cursorToSubject(Cursor cursor) {
		Subject subject = new Subject();
		subject.setId(cursor.getLong(COLUMN_ID_INDEX));
		subject.setName(cursor.getString(COLUMN_NAME_INDEX));
		return subject;
	}
}

