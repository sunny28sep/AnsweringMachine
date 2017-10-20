package com.example.answeringmachine;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class VoiceGreeting extends ListActivity{
		String clas[]={"Default VoiceGreeting","Create VoiceMessage","PlayGreeting"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.messagetext);
		ArrayAdapter<String> adapter= new ArrayAdapter<String>(VoiceGreeting.this,android.R.layout.simple_list_item_1,clas);
		setListAdapter(adapter);
	}
	protected void onListItemClick(ListView l,View v,int position,long id){
		String cheese= clas[position];
		super.onListItemClick(l, v, position, id);
		try{
		Class<?> ourClass = Class.forName("com.example.answeringmachine."+ cheese);
		Intent ourIntent= new Intent(VoiceGreeting.this,ourClass);
		startActivity(ourIntent);
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
	}
	}





