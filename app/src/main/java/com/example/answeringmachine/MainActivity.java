package com.example.answeringmachine;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends ListActivity {
    public static String delay="10";
    private SharedPreferences prefs2;
    public String classname;
    private String prefName2 = "MyPref2";
    String key = null;
    boolean TTS;
	String classes[]={"Block List","Service Mode","Delay Before Answer","Received Messages","Manage Contact Lists"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	  ArrayAdapter<String> adapter= new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,classes);
		setListAdapter(adapter);
		final ToggleButton tg=(ToggleButton) findViewById(R.id.toggleButton1);
		prefs2 = getSharedPreferences(prefName2, MODE_PRIVATE);
		tg.setChecked(prefs2.getBoolean("TOGGLE", false));
		tg.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
			if(tg.isChecked())
			{
				prefs2 = getSharedPreferences(prefName2, MODE_PRIVATE);
				SharedPreferences.Editor editor = prefs2.edit();
				
				editor.putBoolean("TOGGLE", true);
				editor.commit();
				showDialog(3);
				//registerReceiver(intentReceiver, intentFilter);
			}
			else
			{
				prefs2 = getSharedPreferences(prefName2, MODE_PRIVATE);
				SharedPreferences.Editor editor = prefs2.edit();
				
				editor.putBoolean("TOGGLE", false);
				editor.commit();
				//unregisterReceiver(intentReceiver);
			}
			}
		});
	}
	
	@SuppressWarnings("deprecation")
	protected void onListItemClick(ListView l,View v,int position,long id){
		if(position==4)
		{
			classname= "GetContacts";
			try{
				
				Class<?> ourClass = Class.forName("com.example.answeringmachine."+ classname);
				Intent ourIntent= new Intent(MainActivity.this,ourClass);
				startActivity(ourIntent);
				}
			catch(ClassNotFoundException e){
				e.printStackTrace();
			}
		}
		if(position==3)
		{
			classname= "ReceivedMessages";
			try{
				
				Class<?> ourClass = Class.forName("com.example.answeringmachine."+ classname);
				Intent ourIntent= new Intent(MainActivity.this,ourClass);
				startActivity(ourIntent);
				}
			catch(ClassNotFoundException e){
				e.printStackTrace();
			}
		}
		if(position==0)
		{
			classname= "BlockList";
			try{
				
				Class<?> ourClass = Class.forName("com.example.answeringmachine."+ classname);
				Intent ourIntent= new Intent(MainActivity.this,ourClass);
				startActivity(ourIntent);
				}
			catch(ClassNotFoundException e){
				e.printStackTrace();
			}
		}
		super.onListItemClick(l, v, position, id);
		if(position==1)
			showDialog(1);
		if(position==2)
			showDialog(0);
		
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
			getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	@Override
	protected Dialog onCreateDialog(int id)
	{
		final CharSequence[] items = { "2 seconds","4 seconds","8 seconds","16 seconds"};
		final int itemsChecked=0;
		final CharSequence[] items1 = { "ONLINE","OFFLINE"};
	    final int itemsChecked1 = 0;
	    final CharSequence[] items2 = {"General","Silent","Vibration","Outdoor"};
	    final int itemsChecked2 = 0;
	    final CharSequence[] items3 = {"With Text-to-speech service","Without TTS service"};
	    final int itemsChecked3 = 0;
	    
	    switch (id) 
	    {
	     case 0:
	     return new AlertDialog.Builder(this).setTitle("Delay Before Answer").setPositiveButton("OK", new
	      
	    		 DialogInterface.OnClickListener() {
	public void onClick(DialogInterface dialog,
	int whichButton)
	{
	}
	}).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	public void onClick(DialogInterface dialog,
	int whichButton)
	{
	}
	}).setSingleChoiceItems(items, itemsChecked, new DialogInterface.OnClickListener() {
	@Override
	public void onClick(DialogInterface dialog, int which) {
		String[] parts = items[which].toString().split(" ");
		delay = parts[0];
		prefs2 = getSharedPreferences(prefName2, MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs2.edit();
		editor.putString("DELAY", delay);
		editor.commit();
		Toast.makeText(MainActivity.this,"Delay:"+delay, Toast.LENGTH_LONG).show();
	}
	}
	).create();
	    case 1:
	    	return new AlertDialog.Builder(this).setTitle("Select Service").setPositiveButton("OK", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog,
		int whichButton)
		{
		}
		}).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog,
		int whichButton)
		{
		
		}
		}).setSingleChoiceItems(items1, itemsChecked1, new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			
		Toast.makeText(getBaseContext(),"Selected " +items1[which],Toast.LENGTH_SHORT).show();
		}
		}
		)
		.create();
	    case 3:
	    	return new AlertDialog.Builder(this).setTitle("Select Service").setPositiveButton("OK", new DialogInterface.OnClickListener() {
	    		public void onClick(DialogInterface dialog,
	    		int whichButton)
	    		{
	    		}
	    		}).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	    		public void onClick(DialogInterface dialog,
	    		int whichButton)
	    		{
	    		
	    		}
	    		}).setSingleChoiceItems(items3, itemsChecked3, new DialogInterface.OnClickListener() {
	    		@Override
	    		public void onClick(DialogInterface dialog, int which) {if(which==0){
	    			TTS=true;
	    			prefs2 = getSharedPreferences(prefName2, MODE_PRIVATE);
	    			SharedPreferences.Editor editor = prefs2.edit();
	    			editor.putBoolean("TTS", TTS);
	    			editor.commit();
	    			}
	    			if(which==1){
	    				TTS=false;
	    				prefs2 = getSharedPreferences(prefName2, MODE_PRIVATE);
	    				SharedPreferences.Editor editor = prefs2.edit();
	    				editor.putBoolean("TTS", TTS);
	    				editor.commit();
	    				}
	    		Toast.makeText(getBaseContext(),"Selected " +items3[which],Toast.LENGTH_SHORT).show();
	    		}
	    		}
	    		)
	    		.create();
	    case 2:
		     return new AlertDialog.Builder(this).setTitle("Select Profile").setPositiveButton("OK", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog,
		int whichButton)
		{
		
		}
		}).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog,
		int whichButton)
		{
		
		}
		}).setSingleChoiceItems(items2, itemsChecked2, new
		DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
		
		}
		}
		).create();
	    	
       }
		return null;
	}
	public String getDelay()
	{
		return delay;
	}
	}