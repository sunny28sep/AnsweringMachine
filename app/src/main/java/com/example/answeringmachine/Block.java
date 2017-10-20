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
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class Block extends Activity implements OnClickListener{
	ContactsDataSource datasource;
	ListView lv;
	Button all,list,clear;
	String PhoneNumber;
	String ContactName,y;
    public EditText mEdit,nEdit,dEdit;
    Bundle args,arvs;
    private static final String key = null;
    CharSequence[] items  =  {"Remove from Block list"};
    boolean[]  itemsChecked  =  new  boolean [items.length];
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.block);
		datasource= new ContactsDataSource(this);
        datasource.open();
		lv= (ListView) findViewById(R.id.LView);
		List<String> contacts = datasource.findBlock();
		ArrayAdapter<String> adapter= new ArrayAdapter<String>(Block.this,android.R.layout.simple_list_item_1,contacts);
		lv.setAdapter(adapter);
        all = (Button) findViewById(R.id.BTAll);
        all.setOnClickListener(this);
        clear = (Button) findViewById(R.id.clear);
        clear.setOnClickListener(this);
        list = (Button) findViewById(R.id.BlockList);
        list.setOnClickListener(this);
        
	}
	@Override
	public void onClick(View v) {
		
		if (v == all)
		{  
			Intent intent = new Intent(Block.this,BlockList.class);
			startActivity(intent);
		}   
		if (v == list) {
			List<String> contacts = datasource.findBlock();
			ArrayAdapter<String> adapter= new ArrayAdapter<String>(Block.this,android.R.layout.simple_list_item_1,contacts);
			lv.setAdapter(adapter);
		}
		if (v == clear) {
			datasource.deleteBlist();
			List<String> contacts = datasource.findBlock();
			ArrayAdapter<String> adapter= new ArrayAdapter<String>(Block.this,android.R.layout.simple_list_item_1,contacts);
			lv.setAdapter(adapter);
		}
	}
}
