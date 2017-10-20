package com.example.answeringmachine;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class TextMessages extends ListActivity {
    String clas[] = {"Inbox", "Drafts", "Outbox", "SentMessages", "MemoryStatus"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messagetext);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(TextMessages.this, android.R.layout.simple_list_item_1, clas);
        setListAdapter(adapter);
    }

    protected void onListItemClick(ListView l, View v, int position, long id) {
        String cheese = clas[position];
        super.onListItemClick(l, v, position, id);
        try {
            Class<?> ourClass = Class.forName("com.example.answeringmachine." + cheese);
            Intent ourIntent = new Intent(TextMessages.this, ourClass);
            startActivity(ourIntent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}




