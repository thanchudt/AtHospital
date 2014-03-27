package com.gmail.dangle1107.vn.athospital;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class CategoryDataSource{
	private SQLiteDatabase database;
	private DatabaseHelper dbHelper;
	private String[] allColumns = { DatabaseHelper.COLUMN_ID, 
			DatabaseHelper.COLUMN_CATEGORY_NAME, DatabaseHelper.COLUMN_CATEGORY_PARENT_ID
	};
	private static final int COLUMN_ID_INDEX = 0;
	private static final int COLUMN_NAME_INDEX = 1;
	private static final int COLUMN_PARENT_ID_INDEX = 2;
	
	public CategoryDataSource(Context context){
		dbHelper = new DatabaseHelper(context);
	}
	
	public void open() throws SQLException {
		//database = dbHelper.getWritableDatabase();
		database = dbHelper.getDb();
	}
	
	public void close() {
		dbHelper.close();
	}
	
	public Category createCategory(String name, long parent_id) {
		ContentValues values = new ContentValues();
		values.put(DatabaseHelper.COLUMN_CATEGORY_NAME, name);
		values.put(DatabaseHelper.COLUMN_CATEGORY_PARENT_ID, parent_id);
		long insertId = database.insert(DatabaseHelper.TABLE_CATEGORY, null, values);
		Cursor cursor = database.query(DatabaseHelper.TABLE_CATEGORY, 
				allColumns, DatabaseHelper.COLUMN_ID + " = " + insertId, null, null, null, null);
		cursor.moveToFirst();
		Category newCategory = cursorToCategory(cursor);
		cursor.close();
		return newCategory;
	}

	public Category createCategory(String name) {
		ContentValues values = new ContentValues();
		values.put(DatabaseHelper.COLUMN_CATEGORY_NAME, name);
		values.putNull(DatabaseHelper.COLUMN_CATEGORY_PARENT_ID);
		long insertId = database.insert(DatabaseHelper.TABLE_CATEGORY, null, values);
		Cursor cursor = database.query(DatabaseHelper.TABLE_CATEGORY, 
				allColumns, DatabaseHelper.COLUMN_ID + " = " + insertId, null, null, null, null);
		cursor.moveToFirst();
		Category newCategory = cursorToCategory(cursor);
		cursor.close();
		return newCategory;
	}
	
	public void deleteCategory(Category category){
		long id = category.getId();
		System.out.println("Category deleted with id: " + id);
		database.delete(DatabaseHelper.TABLE_CATEGORY, DatabaseHelper.COLUMN_ID + " = " + id, null);
	}
	
	public List<Category> getAllCategories(){
		List<Category> categories = new ArrayList<Category>();
		
		Cursor cursor = database.query(DatabaseHelper.TABLE_CATEGORY, allColumns, null, null, null, null, null);
		
		cursor.moveToFirst();
		while(!cursor.isAfterLast()){
			Category category = cursorToCategory(cursor);
			categories.add(category);
			cursor.moveToNext();
		}
		
		//make sure to close the cursor
		cursor.close();
		return categories;
	}
	
	private Category cursorToCategory(Cursor cursor) {
		Category category = new Category();
		category.setId(cursor.getLong(COLUMN_ID_INDEX));
		category.setName(cursor.getString(COLUMN_NAME_INDEX));
		category.setParentId(cursor.getLong(COLUMN_PARENT_ID_INDEX));
		return category;
	}
}
