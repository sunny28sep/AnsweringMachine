package com.example.databasecontacts.db;

import java.sql.SQLRecoverableException;
import java.util.ArrayList;
import java.util.List;

import com.example.contactfinder.model.Contact;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ContactsDataSource {
	private static final String LOGTAG = "DEBUG";
	SQLiteOpenHelper dbhelper;
    SQLiteDatabase database;
   //SELECT PhoneNumber WHERE PhoneNumbr = contact.getCellNo();
    public static final String[] allcolumns={ 
    	ContactsDB.COLUMN_ID,
    	ContactsDB.COLUMN_NAME,
    	ContactsDB.COLUMN_NUMBER};
	public ContactsDataSource(Context context){
		dbhelper= new ContactsDB(context);
	}
	public void open()
	{
		Log.i(LOGTAG,"Database opened");
		
		database = dbhelper.getWritableDatabase();
		Log.i(LOGTAG,"Database opened after get writable");
	}
	public void close()
	{
		Log.i(LOGTAG,"Database closed");
		dbhelper.close();
	}
	public Contact create(Contact contact){
		ContentValues values = new ContentValues();
		values.put(ContactsDB.COLUMN_NAME, contact.getName());
		values.put(ContactsDB.COLUMN_NUMBER, contact.getCellNo());
		long insertid = database.insert(ContactsDB.TABLE_CONTACTS, null,values);
		contact.setId(insertid);
		return contact;
		
	}
	public Contact createBlock(Contact contact){
		ContentValues values = new ContentValues();
		values.put(ContactsDB.COLUMN_NAME, contact.getName());
		values.put(ContactsDB.COLUMN_NUMBER, contact.getCellNo());
		long insertid = database.insert(ContactsDB.BLOCK_LIST, null,values);
		contact.setId(insertid);
		return contact;
		
	}
	public void deleteAll()
	{
		database.delete(ContactsDB.TABLE_CONTACTS, null, null);
	}
	public void deleteBlist()
	{
		database.delete(ContactsDB.BLOCK_LIST, null, null);
	}
	public void deleteAllList1()
	{
		database.delete(ContactsDB.LIST1_TABLE, null, null);
	}
	public void deleteAllList2()
	{
		database.delete(ContactsDB.LIST2_TABLE, null, null);
	}
	
	public void delete(String no)
	{
		try{
			Log.i(LOGTAG,"Number:"+no);
		Cursor c= database.query(ContactsDB.TABLE_CONTACTS, allcolumns, ContactsDB.COLUMN_NUMBER+"="+no,null,null,null,null);
	
		c.moveToFirst();
		long ID=c.getLong(0);
		database.delete(ContactsDB.TABLE_CONTACTS, ContactsDB.COLUMN_ID+"="+ID, null);
		}
		catch(Exception e)
		{
			Log.i(LOGTAG,e.toString());
		}
		
	}
	public void deletelist1(String no)
	{
		try{
			Log.i(LOGTAG,"Number1:"+no);
		Cursor c1= database.query(ContactsDB.LIST1_TABLE, allcolumns, ContactsDB.COLUMN_NUMBER+"="+no,null,null,null,null);
	
		c1.moveToFirst();
		long ID=c1.getLong(0);
		database.delete(ContactsDB.LIST1_TABLE, ContactsDB.COLUMN_ID+"="+ID, null);
		}
		catch(Exception e)
		{
			Log.i(LOGTAG,e.toString());
		}
		
	}
	public void deletelist2(String no)
	{
		try{
			Log.i(LOGTAG,"Number2:"+no);
		Cursor c2= database.query(ContactsDB.LIST2_TABLE, allcolumns, ContactsDB.COLUMN_NUMBER+"="+no,null,null,null,null);
		
		c2.moveToFirst();
		long ID=c2.getLong(0);
		
		database.delete(ContactsDB.LIST2_TABLE, ContactsDB.COLUMN_ID+"="+ID, null);
		}
		catch(Exception e)
		{
			Log.i(LOGTAG,e.toString());
		}
		
	}
	public void deleteblock(String no)
	{
		try{
			Log.i(LOGTAG,"Number3:"+no);
		Cursor c3= database.query(ContactsDB.BLOCK_LIST, allcolumns, ContactsDB.COLUMN_NUMBER+"="+no,null,null,null,null);
		c3.moveToFirst();
		long ID=c3.getLong(0);
		database.delete(ContactsDB.BLOCK_LIST, ContactsDB.COLUMN_ID+"="+ID, null);
		}
		catch(Exception e)
		{
			Log.i(LOGTAG,e.toString());
		}
		
	}
	public Contact createList1(Contact contact){
			ContentValues values = new ContentValues();
			values.put(ContactsDB.COLUMN_NAME, contact.getName());
			values.put(ContactsDB.COLUMN_NUMBER, contact.getCellNo());
			long insertid = database.insert(ContactsDB.LIST1_TABLE, null,values);
			contact.setId(insertid);
			return contact;
			
		}
	public Contact createList2(Contact contact){
			ContentValues values = new ContentValues();
			values.put(ContactsDB.COLUMN_NAME, contact.getName());
			values.put(ContactsDB.COLUMN_NUMBER, contact.getCellNo());
			long insertid = database.insert(ContactsDB.LIST2_TABLE, null,values);
			contact.setId(insertid);
			return contact;
			
		}
	public List<String> findAll() {
		List<String> cont= new ArrayList<String>();
		List<Contact> contacts= new ArrayList<Contact>();
		Cursor cursor= database.query(ContactsDB.TABLE_CONTACTS, allcolumns, null, null, null,null , "name ASC");
		if(cursor.getCount()>0){
			while(cursor.moveToNext()){
				Contact contact = new Contact();
				contact.setId(cursor.getLong(cursor.getColumnIndex(ContactsDB.COLUMN_ID)));
				contact.setName(cursor.getString(cursor.getColumnIndex(ContactsDB.COLUMN_NAME)));
				contact.setCellNo(cursor.getString(cursor.getColumnIndex(ContactsDB.COLUMN_NUMBER)));
				
				long ID=cursor.getLong(cursor.getColumnIndex(ContactsDB.COLUMN_ID));
				String name= cursor.getString(cursor.getColumnIndex(ContactsDB.COLUMN_NAME));
				String no=cursor.getString(cursor.getColumnIndex(ContactsDB.COLUMN_NUMBER));
				String c= name+"\t"+no;
				cont.add(c);
			}
		}
		return cont;
	}
	public List<String> findBlock() {
		List<String> cont= new ArrayList<String>();
		List<Contact> contacts= new ArrayList<Contact>();
		Cursor cursor= database.query(ContactsDB.BLOCK_LIST, allcolumns, null, null, null,null , "name ASC");
		if(cursor.getCount()>0){
			while(cursor.moveToNext()){
				Contact contact = new Contact();
				contact.setId(cursor.getLong(cursor.getColumnIndex(ContactsDB.COLUMN_ID)));
				contact.setName(cursor.getString(cursor.getColumnIndex(ContactsDB.COLUMN_NAME)));
				contact.setCellNo(cursor.getString(cursor.getColumnIndex(ContactsDB.COLUMN_NUMBER)));
				
				long ID=cursor.getLong(cursor.getColumnIndex(ContactsDB.COLUMN_ID));
				String name= cursor.getString(cursor.getColumnIndex(ContactsDB.COLUMN_NAME));
				String no=cursor.getString(cursor.getColumnIndex(ContactsDB.COLUMN_NUMBER));
				String c= name+"\t"+no;
				cont.add(c);
			}
		}
		return cont;
	}
	public List<String> findAllID() {
		List<String> cont= new ArrayList<String>();
		List<Contact> contacts= new ArrayList<Contact>();
		Cursor cursor= database.query(ContactsDB.TABLE_CONTACTS, allcolumns, null, null, null,null , "name ASC");
		if(cursor.getCount()>0){
			while(cursor.moveToNext()){
				Contact contact = new Contact();
				contact.setId(cursor.getLong(cursor.getColumnIndex(ContactsDB.COLUMN_ID)));
				contact.setName(cursor.getString(cursor.getColumnIndex(ContactsDB.COLUMN_NAME)));
				contact.setCellNo(cursor.getString(cursor.getColumnIndex(ContactsDB.COLUMN_NUMBER)));
				
				long ID=cursor.getLong(cursor.getColumnIndex(ContactsDB.COLUMN_ID));
				String name= cursor.getString(cursor.getColumnIndex(ContactsDB.COLUMN_NAME));
				String no=cursor.getString(cursor.getColumnIndex(ContactsDB.COLUMN_NUMBER));
				String c= ID+"\t"+name+"\t"+no;
				cont.add(c);
			}
		}
		return cont;
	}
	public List<String> findList1() {
		List<String> cont= new ArrayList<String>();
		List<Contact> contacts= new ArrayList<Contact>();
		Cursor cursor= database.query(ContactsDB.LIST1_TABLE, allcolumns, null, null, null,null , "name ASC");
		if(cursor.getCount()>0){
			while(cursor.moveToNext()){
				Contact contact = new Contact();
				contact.setId(cursor.getLong(cursor.getColumnIndex(ContactsDB.COLUMN_ID)));
				contact.setName(cursor.getString(cursor.getColumnIndex(ContactsDB.COLUMN_NAME)));
				contact.setCellNo(cursor.getString(cursor.getColumnIndex(ContactsDB.COLUMN_NUMBER)));
				
				long ID=cursor.getLong(cursor.getColumnIndex(ContactsDB.COLUMN_ID));
				String name= cursor.getString(cursor.getColumnIndex(ContactsDB.COLUMN_NAME));
				String no=cursor.getString(cursor.getColumnIndex(ContactsDB.COLUMN_NUMBER));
				String c= name+"\t"+no;
				cont.add(c);
			}
		}
		return cont;
	}
	public List<String> findList2() {
		Log.i(LOGTAG,"inside Findall");
		List<String> cont= new ArrayList<String>();
		List<Contact> contacts= new ArrayList<Contact>();
		Cursor cursor= database.query(ContactsDB.LIST2_TABLE, allcolumns, null, null, null,null , "name ASC");
		if(cursor.getCount()>0){
			while(cursor.moveToNext()){
				Contact contact = new Contact();
				contact.setId(cursor.getLong(cursor.getColumnIndex(ContactsDB.COLUMN_ID)));
				contact.setName(cursor.getString(cursor.getColumnIndex(ContactsDB.COLUMN_NAME)));
				contact.setCellNo(cursor.getString(cursor.getColumnIndex(ContactsDB.COLUMN_NUMBER)));
				
				long ID=cursor.getLong(cursor.getColumnIndex(ContactsDB.COLUMN_ID));
				String name= cursor.getString(cursor.getColumnIndex(ContactsDB.COLUMN_NAME));
				String no=cursor.getString(cursor.getColumnIndex(ContactsDB.COLUMN_NUMBER));
				String c= name+"\t"+no;
				cont.add(c);
			}
		}
		return cont;
	}
}
