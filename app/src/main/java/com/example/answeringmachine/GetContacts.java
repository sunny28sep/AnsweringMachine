package com.example.answeringmachine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.example.contactfinder.model.Contact;
import com.example.databasecontacts.db.ContactsDataSource;


import android.R.xml;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class GetContacts extends ActionBarActivity implements OnItemClickListener, OnClickListener, OnItemLongClickListener {
	private static final String key = null;
	static int count,newcount;
	String PhoneNumber;
	String ContactName,y;
    ListView lv;
    public EditText mEdit,nEdit,dEdit;
	public static String message,number,delay;
    Button btnAll, btnBlock1, btnBlock2,save;
    ContactsDataSource datasource;
    private SharedPreferences prefs;
    private String prefName = "MyPref";
    Bundle args,arvs;

    CharSequence[] items  =  {"Add to list 1","Add to list 2"};
    boolean[]  itemsChecked  =  new  boolean [items.length];

   public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       
       setContentView(R.layout.layout_contacts);
       lv= (ListView) findViewById(R.id.LView);
       datasource= new ContactsDataSource(this);
       datasource.open();
       mEdit=(EditText)findViewById(R.id.editText1);
       prefs = getSharedPreferences(prefName, MODE_PRIVATE);
       mEdit.setText(prefs.getString("MESSAGE",""));
       count=prefs.getInt("NO_CONTACTS",0);
       Log.d("DEBUG", "count:"+count);
       List<String> contacts1 = datasource.findAll();
   		ArrayAdapter<String> adapter= new ArrayAdapter<String>(GetContacts.this,android.R.layout.simple_list_item_1,contacts1);
   		lv.setAdapter(adapter);
        lv.setOnItemLongClickListener(this);
       
       
       btnAll = (Button) findViewById(R.id.BTAll);
       btnAll.setOnClickListener(this);
       save = (Button) findViewById(R.id.save);
       save.setOnClickListener(this);

		btnBlock1 = (Button) findViewById(R.id.BlockList);
		btnBlock1.setOnClickListener(this);

		btnBlock2 = (Button) findViewById(R.id.btnblock2);
		btnBlock2.setOnClickListener(this);
        
        }

   @Override
protected void onResume() {
	
	super.onResume();
	datasource.open();
}
   @Override
protected void onPause() {
	
	super.onPause();
	datasource.close();
}
   
@Override
public void onClick(View v) {
	if (v == btnAll)
	{  
		List<String> contacts = datasource.findAll();
		if(contacts.size()==0)
		{
			Toast.makeText(GetContacts.this,"Fetching Contacts",Toast.LENGTH_LONG).show();
			Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
			while (phones.moveToNext())
	       {
	         ContactName=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
	         PhoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
	         	Contact contact = new Contact();
	        	contact.setName(ContactName);
	        	contact.setCellNo(PhoneNumber);
	        	contact = datasource.create(contact);
	        }
	           phones.close();
		}
		List<String> contacts1 = datasource.findAll();
	ArrayAdapter<String> adapter= new ArrayAdapter<String>(GetContacts.this,android.R.layout.simple_list_item_1,contacts1);
    
    lv.setAdapter(adapter);
     lv.setOnItemLongClickListener(this);
	}   
	

	if (v == btnBlock1) {
		
		Intent intent = new Intent(GetContacts.this,List1.class);
		startActivity(intent);
		
	}

	if (v == btnBlock2) {
		Intent intent = new Intent(GetContacts.this,List2.class);
		startActivity(intent);
		
	}
if (v == save) {
	prefs = getSharedPreferences(prefName, MODE_PRIVATE);
	SharedPreferences.Editor editor = prefs.edit();
	editor.putString("MESSAGE",mEdit.getText().toString());
	editor.commit();
	Toast.makeText(GetContacts.this,"DATA SAVED"+mEdit.getText().toString(), Toast.LENGTH_LONG).show();
		
	}

}
public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
}
public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == R.id.action_settings) {
Cursor phone = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
		newcount=phone.getCount();
	     phone.close();
	     prefs = getSharedPreferences(prefName, MODE_PRIVATE);
			SharedPreferences.Editor editor = prefs.edit();
			editor.putInt("NO_CONTACTS",newcount);
	                 editor.commit();
	                 if(newcount!=count)
	                 {
	                	 Log.d("DEBUG", "inside if newcount!=count");
	                	 Toast.makeText(GetContacts.this,"Fetching Contacts",Toast.LENGTH_LONG).show();
	                	 datasource.deleteAll();
			Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
			while (phones.moveToNext())
	       {
	         ContactName=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
	         PhoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
	         	Contact contact = new Contact();
	        	contact.setName(ContactName);
	        	contact.setCellNo(PhoneNumber);
	        	contact = datasource.create(contact);
	        		}
	                phones.close();
	                List<String> contacts = datasource.findAll();
	            	ArrayAdapter<String> adapter= new ArrayAdapter<String>(GetContacts.this,android.R.layout.simple_list_item_1,contacts);
	                
	                lv.setAdapter(adapter);
	                lv.setOnItemLongClickListener(this);
		}
	                 else{
	                	 Log.d("DEBUG", "inside else newcount!=count");
	                	 Toast.makeText(GetContacts.this,"Fetching Contacts",Toast.LENGTH_LONG).show();
	                	 List<String> contacts = datasource.findAll();
	                	 ArrayAdapter<String> adapter= new ArrayAdapter<String>(GetContacts.this,android.R.layout.simple_list_item_1,contacts);
	                	 lv.setAdapter(adapter);
	                	 lv.setOnItemLongClickListener(this);
	}  
    }
    return super.onOptionsItemSelected(item);
}
@Override
public boolean onItemLongClick(AdapterView<?> parent, View view, int position,
		long id) {
	
	args=new Bundle();
	String x=(String)parent.getItemAtPosition(position);
	args.putString(key, x);
	
					showDialog(0,args);
					
	return false;
}
@Override
protected  Dialog onCreateDialog(int id) {
	switch (id) {
	case 0:
		return new  AlertDialog.Builder(this).setTitle("Choose list").setSingleChoiceItems(items, id,  new DialogInterface.OnClickListener() {
			@Override
		public void onClick(DialogInterface dialog, int which)
		{
		if(which==0)
		{
		y=args.getString(key);
		String[] parts = y.split("\t");
		String nme = parts[0];
		String no = parts[1];
		Contact contact = new Contact();
		contact.setName(nme);
		contact.setCellNo(no);
		datasource.createList1(contact);
		datasource.delete(no);
		List<String> contacts = datasource.findAll();
		ArrayAdapter<String> adapter= new ArrayAdapter<String>(GetContacts.this,android.R.layout.simple_list_item_1,contacts);
		lv.setAdapter(adapter);
		lv.setOnItemLongClickListener(GetContacts.this);
		Toast.makeText(GetContacts.this,"Added to List 1",Toast.LENGTH_SHORT).show();
		dialog.cancel();
		}
		if(which==1)
		{
		y=args.getString(key);
		String[] parts = y.split("\t");
		String nme = parts[0];
		String no = parts[1];
		Contact contact = new Contact();
		contact.setName(nme);
		contact.setCellNo(no);
		datasource.createList2(contact);
		datasource.delete(no);
		List<String> contacts = datasource.findAll();
		ArrayAdapter<String> adapter= new ArrayAdapter<String>(GetContacts.this,android.R.layout.simple_list_item_1,contacts);
		lv.setAdapter(adapter);
		lv.setOnItemLongClickListener(GetContacts.this);
		Toast.makeText(getBaseContext(),"Added to List 2",Toast.LENGTH_SHORT).show();
		dialog.cancel();
		}}}).create();
		}
		return null;
		}
@Override
public void onItemClick(AdapterView<?> parent, View view, int position, long id) {	
}
}
