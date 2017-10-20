package com.example.answeringmachine;

import java.util.List;

import com.example.contactfinder.model.Contact;
import com.example.databasecontacts.db.ContactsDataSource;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class List1 extends Activity implements OnClickListener {
	ContactsDataSource datasource;
	ListView lv;
	Button all,list1,list2,save,clear;
	String PhoneNumber;
	String ContactName,y;
	private SharedPreferences prefs1;
    private String prefName1 = "MyPref1";
    public EditText mEdit,nEdit,dEdit;
    Bundle args,arvs;
    private static final String key = null;
    CharSequence[] items  =  {"Remove from list 1"};
    boolean[]  itemsChecked  =  new  boolean [items.length];
		@Override
		protected void onCreate(Bundle savedInstanceState) {
		
			super.onCreate(savedInstanceState);
			
			setContentView(R.layout.layout_list1);
			
			mEdit=(EditText)findViewById(R.id.message);
			datasource= new ContactsDataSource(this);
	        datasource.open();
	       
	        all = (Button) findViewById(R.id.cont);
	        all.setOnClickListener(this);
	        clear = (Button) findViewById(R.id.clear1);
	        clear.setOnClickListener(this);
	        save = (Button) findViewById(R.id.savedata);
	        save.setOnClickListener(this);
	        list1 = (Button) findViewById(R.id.list1);
	        list1.setOnClickListener(this);
	        list2 = (Button) findViewById(R.id.list2);
	        list2.setOnClickListener(this);
	        lv= (ListView) findViewById(R.id.listview);
	        prefs1 = getSharedPreferences(prefName1, MODE_PRIVATE);
	        mEdit.setText(prefs1.getString("MESSAGE1",""));
			List<String> contacts = datasource.findList1();
			ArrayAdapter<String> adapter= new ArrayAdapter<String>(List1.this,android.R.layout.simple_list_item_1,contacts);
			lv.setAdapter(adapter);
		}
		@Override
		public void onClick(View v) {
			if (v == all)
			{  
				Intent intent = new Intent(List1.this,GetContacts.class);
				startActivity(intent);
			}
			if (v == clear) {
				datasource.deleteAllList1();
				List<String> contacts = datasource.findList1();
				ArrayAdapter<String> adapter= new ArrayAdapter<String>(List1.this,android.R.layout.simple_list_item_1,contacts);
				lv.setAdapter(adapter);
			}
			

			if (v == list1) {
				
			}

			if (v == list2) {
				
				Intent intent = new Intent(List1.this,List2.class);
				startActivity(intent);
			}
			if (v == save) {
				prefs1 = getSharedPreferences(prefName1, MODE_PRIVATE);
				SharedPreferences.Editor editor = prefs1.edit();
				editor.putString("MESSAGE1",mEdit.getText().toString());
				editor.commit();
				Toast.makeText(List1.this,"Message Saved", Toast.LENGTH_LONG).show();
			}
			
		}
			
		}
