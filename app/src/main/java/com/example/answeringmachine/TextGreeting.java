package com.example.answeringmachine;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class TextGreeting extends ListActivity {
    CharSequence[] items = {"Call you later", "Call me now", "Be right back", "Sorry!!", "Thank you!!"};
    int itemsChecked;

    String clas[] = {"DefaultGreetings", "CreateGreetings", "FavouriteBox", "Settings"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.textgreeting);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(TextGreeting.this, android.R.layout.simple_list_item_1, clas);
        setListAdapter(adapter);
    }

    @SuppressWarnings("deprecation")
    protected void onListItemClick(ListView l, View v, int position, long id) {
        String cheese = clas[position];
        super.onListItemClick(l, v, position, id);
        if (position == 0) {
            showDialog(0);
        }
        try {
            Class<?> ourClass = Class.forName("com.example.answeringmachine." + cheese);
            Intent ourIntent = new Intent(TextGreeting.this, ourClass);
            startActivity(ourIntent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case 0:
                return new AlertDialog.Builder(this)
                        .setTitle("Greeting Templates")
                        .setPositiveButton("OK", new
                                DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int whichButton) {
                                        Toast.makeText(getBaseContext(),
                                                "OK clicked!", Toast.LENGTH_SHORT).show();
                                    }
                                })
                        .setNegativeButton("Cancel", new
                                DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int whichButton) {
                                        Toast.makeText(getBaseContext(),
                                                "Cancel clicked!", Toast.LENGTH_SHORT).show();
                                    }
                                })
                        .setSingleChoiceItems(items, itemsChecked, new
                                DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(getBaseContext(),
                                                "Selected " + items[which],
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                        )
                        .create();
        }
        return null;
    }
}




