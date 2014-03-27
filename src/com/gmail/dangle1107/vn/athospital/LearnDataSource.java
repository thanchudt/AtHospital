package com.gmail.dangle1107.vn.athospital;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class LearnDataSource {
	private SQLiteDatabase database;
	private DatabaseHelper dbHelper;
	private String[] allColumns = { DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_LEARN_USER_ID, DatabaseHelper.COLUMN_LEARN_KNOWLEDGE_ID, 
			DatabaseHelper.COLUMN_LEARN_LAST_LEARN_DATE, DatabaseHelper.COLUMN_LEARN_MARK, DatabaseHelper.COLUMN_LEARN_TIMES, DatabaseHelper.COLUMN_LEARN_TOTAL_MARK
	};
	private static final int COLUMN_ID_INDEX = 0;
	private static final int COLUMN_LEARN_USER_ID_INDEX = 1;
	private static final int COLUMN_LEARN_KNOWLEDGE_ID_INDEX = 2;
	private static final int COLUMN_LEARN_LAST_LEARN_DATE_INDEX = 3;
	private static final int COLUMN_LEARN_MARK_INDEX = 4;
	private static final int CCOLUMN_LEARN_TIMES_INDEX = 5;
	private static final int COLUMN_LEARN_TOTAL_MARK_INDEX = 6;
	
	public LearnDataSource(Context context){
		dbHelper = new DatabaseHelper(context);		
	}
	
	public void open() throws SQLException {
		//database = dbHelper.getWritableDatabase();
		database = dbHelper.getDb();
	}
	
	public void close() {
		dbHelper.close();
	}
	
	@SuppressLint("SimpleDateFormat")
	public Learn createLearn(long user_id, long knowledge_id, Date last_learn_date, long mark, long times, long total_mark) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ContentValues values = new ContentValues();
		values.put(DatabaseHelper.COLUMN_LEARN_USER_ID, user_id);
		values.put(DatabaseHelper.COLUMN_LEARN_KNOWLEDGE_ID, knowledge_id);
		values.put(DatabaseHelper.COLUMN_LEARN_LAST_LEARN_DATE, dateFormat.format(last_learn_date));
		values.put(DatabaseHelper.COLUMN_LEARN_MARK, mark);
		values.put(DatabaseHelper.COLUMN_LEARN_TIMES, times);
		values.put(DatabaseHelper.COLUMN_LEARN_TOTAL_MARK, total_mark);
		
		long insertId = database.insert(DatabaseHelper.TABLE_LEARN, null, values);
		Cursor cursor = database.query(DatabaseHelper.TABLE_LEARN, 
				allColumns, DatabaseHelper.COLUMN_ID + " = " + insertId, null, null, null, null);
		cursor.moveToFirst();
		Learn newLearn = cursorToLearn(cursor);
		cursor.close();
		return newLearn;
	}

	@SuppressLint("SimpleDateFormat")
	public Learn createLearn(long user_id, long knowledge_id, Date last_learn_date, long mark) {
		Learn learn = getLearn(user_id, knowledge_id);
		if(learn == null)
			return createLearn(user_id, knowledge_id, last_learn_date, mark, 1, mark);
		else
			return updateLearn(learn.getId(), last_learn_date, mark, learn.getTimes() + 1, learn.getTotalMark() + mark);					
	}
	
	public Learn getLearn(long id){
		Learn learn = null;
		
		Cursor cursor = database.query(DatabaseHelper.TABLE_LEARN, allColumns, DatabaseHelper.COLUMN_ID + " = ?", new String[] { Long.toString(id) }, null, null, null);
		
		cursor.moveToFirst();
		while(!cursor.isAfterLast()){
			learn = cursorToLearn(cursor);
			cursor.moveToNext();
		}
		
		//make sure to close the cursor
		cursor.close();
		return learn;
	}
	
	public Learn getLearn(long user_id, long knowledge_id){
		Learn learn = null;
		
		Cursor cursor = database.query(DatabaseHelper.TABLE_LEARN, allColumns, 
				DatabaseHelper.COLUMN_LEARN_USER_ID + " = ? AND " + DatabaseHelper.COLUMN_LEARN_KNOWLEDGE_ID + " = ?", new String[] { Long.toString(user_id), Long.toString(knowledge_id) }, null, null, null);
		
		cursor.moveToFirst();
		while(!cursor.isAfterLast()){
			learn = cursorToLearn(cursor);
			cursor.moveToNext();
		}
		
		//make sure to close the cursor
		cursor.close();
		return learn;
	}
	
	public void deleteLearn(Learn learn){
		long id = learn.getId();
		System.out.println("Learn deleted with id: " + id);
		database.delete(DatabaseHelper.TABLE_LEARN, DatabaseHelper.COLUMN_ID + " = " + id, null);
	}
	
	@SuppressLint("SimpleDateFormat")
	public Learn updateLearn(long id, Date last_learn_date, long mark, long times, long total_mark){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ContentValues values = new ContentValues();
		values.put(DatabaseHelper.COLUMN_LEARN_LAST_LEARN_DATE, dateFormat.format(last_learn_date));
		values.put(DatabaseHelper.COLUMN_LEARN_MARK, mark);
		values.put(DatabaseHelper.COLUMN_LEARN_TIMES, times);
		values.put(DatabaseHelper.COLUMN_LEARN_TOTAL_MARK, total_mark);		
		long updateId = database.update(DatabaseHelper.TABLE_LEARN, values, DatabaseHelper.COLUMN_ID + " = ?", new String[] { Long.toString(id) });		
		Cursor cursor = database.query(DatabaseHelper.TABLE_LEARN, 
				allColumns, DatabaseHelper.COLUMN_ID + " = " + updateId, null, null, null, null);
		cursor.moveToFirst();
		Learn newLearn = cursorToLearn(cursor);
		cursor.close();
		return newLearn;
	}
	
	public List<Learn> getAllLearns(){
		List<Learn> learns = new ArrayList<Learn>();
		
		Cursor cursor = database.query(DatabaseHelper.TABLE_LEARN, allColumns, null, null, null, null, null);
		
		cursor.moveToFirst();
		while(!cursor.isAfterLast()){
			Learn learn = cursorToLearn(cursor);
			learns.add(learn);
			cursor.moveToNext();
		}
		
		//make sure to close the cursor
		cursor.close();
		return learns;
	}
	
	@SuppressLint("SimpleDateFormat")
	private Learn cursorToLearn(Cursor cursor) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Learn learn = new Learn();
		learn.setId(cursor.getLong(COLUMN_ID_INDEX));		
		learn.setUserId(cursor.getLong(COLUMN_LEARN_USER_ID_INDEX));
		learn.setKnowledgeId(cursor.getLong(COLUMN_LEARN_KNOWLEDGE_ID_INDEX));	
		try {
			learn.setLastLearnDate(dateFormat.parse(cursor.getString(COLUMN_LEARN_LAST_LEARN_DATE_INDEX)));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		learn.setMark(cursor.getLong(COLUMN_LEARN_MARK_INDEX));
		learn.setTimes(cursor.getLong(CCOLUMN_LEARN_TIMES_INDEX));
		learn.setTotalMark(cursor.getLong(COLUMN_LEARN_TOTAL_MARK_INDEX));		
		
		return learn;
	}
}
