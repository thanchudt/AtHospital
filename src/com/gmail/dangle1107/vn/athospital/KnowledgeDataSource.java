package com.gmail.dangle1107.vn.athospital;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class KnowledgeDataSource {
	private SQLiteDatabase database;
	private DatabaseHelper dbHelper;
	private String[] allColumns = { DatabaseHelper.COLUMN_ID,  DatabaseHelper.COLUMN_KNOWLEDGE_SUBJECT_ID, 
			DatabaseHelper.COLUMN_KNOWLEDGE_CONTENT, DatabaseHelper.COLUMN_KNOWLEDGE_CATEGORY_ID 
	};
	private static final int COLUMN_ID_INDEX = 0;
	private static final int COLUMN_KNOWLEDGE_SUBJECT_ID_INDEX = 1;
	private static final int COLUMN_KNOWLEDGE_CONTENT_INDEX = 2;
	private static final int COLUMN_KNOWLEDGE_CATEGORY_ID_INDEX = 3;
	
	public KnowledgeDataSource(Context context){
		dbHelper = new DatabaseHelper(context);
	}
	
	public void open() throws SQLException {
		//database = dbHelper.getWritableDatabase();
		database = dbHelper.getDb();
	}
	
	public void close() {
		dbHelper.close();
	}
	
	public Knowledge createKnowledge(long subject_id, String content, long category_id) {
		ContentValues values = new ContentValues();
		values.put(DatabaseHelper.COLUMN_KNOWLEDGE_SUBJECT_ID, subject_id);
		values.put(DatabaseHelper.COLUMN_KNOWLEDGE_CONTENT, content);
		values.put(DatabaseHelper.COLUMN_KNOWLEDGE_CATEGORY_ID, category_id);
		long insertId = database.insert(DatabaseHelper.TABLE_KNOWLEDGE, null, values);
		Cursor cursor = database.query(DatabaseHelper.TABLE_KNOWLEDGE, 
				allColumns, DatabaseHelper.COLUMN_ID + " = " + insertId, null, null, null, null);
		cursor.moveToFirst();
		Knowledge newKnowledge = cursorToKnowledge(cursor);
		cursor.close();
		return newKnowledge;
	}

	public void deleteKnowledge(Knowledge knowledge){
		long id = knowledge.getId();
		System.out.println("Knowledge deleted with id: " + id);
		database.delete(DatabaseHelper.TABLE_KNOWLEDGE, DatabaseHelper.COLUMN_ID + " = " + id, null);
	}
	
	public List<Knowledge> getAllKnowledges(){
		List<Knowledge> knowledges = new ArrayList<Knowledge>();
		
		Cursor cursor = database.query(DatabaseHelper.TABLE_KNOWLEDGE, allColumns, null, null, null, null, null);
		
		cursor.moveToFirst();
		while(!cursor.isAfterLast()){
			Knowledge knowledge = cursorToKnowledge(cursor);
			knowledges.add(knowledge);
			cursor.moveToNext();
		}
		
		//make sure to close the cursor
		cursor.close();
		return knowledges;
	}
	
	public Knowledge getKnowledge(long id){
		Knowledge knowledge = null;
		
		Cursor cursor = database.query(DatabaseHelper.TABLE_KNOWLEDGE, allColumns, DatabaseHelper.COLUMN_ID + " = ?", new String[] { Long.toString(id) }, null, null, null);
		
		cursor.moveToFirst();
		while(!cursor.isAfterLast()){
			knowledge = cursorToKnowledge(cursor);
			cursor.moveToNext();
		}
		
		//make sure to close the cursor
		cursor.close();
		return knowledge;
	}
	
	private Knowledge cursorToKnowledge(Cursor cursor) {
		Knowledge knowledge = new Knowledge();
		knowledge.setId(cursor.getLong(COLUMN_ID_INDEX));
		knowledge.setSubjectId(cursor.getLong(COLUMN_KNOWLEDGE_SUBJECT_ID_INDEX));
		knowledge.setContent(cursor.getString(COLUMN_KNOWLEDGE_CONTENT_INDEX));
		knowledge.setCategoryId(cursor.getLong(COLUMN_KNOWLEDGE_CATEGORY_ID_INDEX));
		return knowledge;
	}
}
