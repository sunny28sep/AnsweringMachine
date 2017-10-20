package com.example.answeringmachine;

import java.util.List;

import com.example.contactfinder.model.Contact;
import com.example.databasecontacts.db.ContactsDataSource;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.ListView;
import android.widget.Toast;

public class BlockList extends Activity implements OnItemLongClickListener, OnClickListener {
    ListView lv;
    private static final String key = null;
    ContactsDataSource datasource;
    Button btnAll, btnBlock;
    Bundle args, arvs;
    CharSequence[] items = {"Add to Block list"};
    boolean[] itemsChecked = new boolean[items.length];

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.block_layout);
        datasource = new ContactsDataSource(this);
        datasource.open();
        lv = (ListView) findViewById(R.id.LView);
        List<String> contacts = datasource.findAll();
        if (contacts.size() == 0) {
            Toast.makeText(BlockList.this, "Fetching Contacts", Toast.LENGTH_LONG).show();
            Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
            while (phones.moveToNext()) {
                String ContactName = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String PhoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                Contact contact = new Contact();
                contact.setName(ContactName);
                contact.setCellNo(PhoneNumber);
                contact = datasource.create(contact);
            }
            phones.close();
        }
        List<String> contacts1 = datasource.findAll();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(BlockList.this, android.R.layout.simple_list_item_1, contacts1);

        lv.setAdapter(adapter);
        lv.setOnItemLongClickListener(this);
        btnAll = (Button) findViewById(R.id.BTAll);
        btnAll.setOnClickListener(this);
        btnBlock = (Button) findViewById(R.id.BlockList);
        btnBlock.setOnClickListener(this);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view,
                                   int position, long id) {
        args = new Bundle();
        String x = (String) parent.getItemAtPosition(position);
        args.putString(key, x);
        showDialog(0, args);
        return false;
    }

    @Override
    public void onClick(View v) {
        if (v == btnAll) {
            lv = (ListView) findViewById(R.id.LView);
            List<String> contacts1 = datasource.findAll();
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(BlockList.this, android.R.layout.simple_list_item_1, contacts1);
            lv.setAdapter(adapter);
            lv.setOnItemLongClickListener(this);
        }
        if (v == btnBlock) {
            Intent intent = new Intent(BlockList.this, Block.class);
            startActivity(intent);
        }

    }

    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case 0:
                return new AlertDialog.Builder(this).setTitle("Choose list").setSingleChoiceItems(items, id, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String y = args.getString(key);
                        String[] parts = y.split("\t");
                        String nme = parts[0];
                        String no = parts[1];
                        Contact contact = new Contact();
                        contact.setName(nme);
                        contact.setCellNo(no);
                        datasource.createBlock(contact);
                        datasource.delete(no);
                        List<String> contacts = datasource.findAll();
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(BlockList.this, android.R.layout.simple_list_item_1, contacts);
                        lv.setAdapter(adapter);
                        lv.setOnItemLongClickListener(BlockList.this);
                        Toast.makeText(BlockList.this, "Added to Block List", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                }).create();
        }
        return null;
    }

}
