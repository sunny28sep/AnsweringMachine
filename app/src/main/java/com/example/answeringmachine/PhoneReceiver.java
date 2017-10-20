package com.example.answeringmachine;

import com.example.answeringmachine.MainActivity;

import android.view.ContextMenu;

import com.example.contactfinder.*;
import com.example.databasecontacts.*;
import com.android.internal.telephony.*;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.example.databasecontacts.db.*;

public class PhoneReceiver extends BroadcastReceiver {
    private ITelephony telephonyService;
    public static boolean Mode;
    String number, message, message1, message2, delay1;
    ArrayList<String> block;
    private String incommingNumber;
    ContactsDataSource datasource;
    private SharedPreferences prefs3;
    private String prefName3 = "MyPref3";
    boolean TTS;

    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        MainActivity obj = new MainActivity();
        datasource = new ContactsDataSource(context);
        datasource.open();
        boolean isEnabled = context.getSharedPreferences("MyPref2", Context.MODE_PRIVATE).getBoolean("TOGGLE", false);
        message = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE).getString("MESSAGE", "");
        message1 = context.getSharedPreferences("MyPref1", Context.MODE_PRIVATE).getString("MESSAGE1", "");
        message2 = context.getSharedPreferences("MyPref1", Context.MODE_PRIVATE).getString("MESSAGE2", "");
        delay1 = context.getSharedPreferences("MyPref2", Context.MODE_PRIVATE).getString("DELAY", "10");
        prefs3 = context.getSharedPreferences(prefName3, Context.MODE_PRIVATE);
        TTS = context.getSharedPreferences("MyPref2", Context.MODE_PRIVATE).getBoolean("TTS", true);
        //Mode=context.getSharedPreferences("MyPref3", Context.MODE_PRIVATE).getBoolean("MODE", true);
        if (isEnabled) {

            if (extras != null) {
                if (extras.getString(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                    incommingNumber = extras.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
                    List<String> contacts = datasource.findAll();
                    List<String> contacts1 = datasource.findList1();
                    List<String> contacts2 = datasource.findList2();
                    List<String> block_list = datasource.findBlock();
                    for (Iterator iterator = block_list.iterator(); iterator.hasNext(); ) {
                        String x = (String) iterator.next();
                        String[] parts = x.split("\t");
                        String nme = parts[0];
                        String no = parts[1];
                        Log.d("DEBUG", "Number:" + no);
                        if (incommingNumber.equals("+91" + no) || incommingNumber.equals("0" + no) || incommingNumber.equals(no))
                            try {

                                TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                                Class c = Class.forName(tm.getClass().getName());
                                Method m = c.getDeclaredMethod("getITelephony");
                                m.setAccessible(true);
                                com.android.internal.telephony.ITelephony telephonyService = (ITelephony) m.invoke(tm);
                                //long l = Long.parseLong(delay1);
                                //long d=l*1000;
                                //Thread.sleep(d);
                                telephonyService.endCall();
                                //SmsManager sms=SmsManager.getDefault();
                                //sms.sendTextMessage(incommingNumber ,null,message , null, null);

                                Toast.makeText(context, "Call Terminated:" + incommingNumber, Toast.LENGTH_SHORT).show();
                                //Mode=false;
                                prefs3 = context.getSharedPreferences(prefName3, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = prefs3.edit();
                                editor.putBoolean("MODE", false);
                                editor.commit();
                            } catch (Exception e) {
                                Log.d("DEBUG", e.toString());
                            }

                    }
                    for (Iterator iterator = contacts.iterator(); iterator.hasNext(); ) {
                        String x = (String) iterator.next();
                        String[] parts = x.split("\t");
                        String nme = parts[0];
                        String no = parts[1];
                        if (incommingNumber.equals("+91" + no) || incommingNumber.equals("0" + no) || incommingNumber.equals(no))
                            try {

                                TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                                Class c = Class.forName(tm.getClass().getName());
                                Method m = c.getDeclaredMethod("getITelephony");
                                m.setAccessible(true);
                                com.android.internal.telephony.ITelephony telephonyService = (ITelephony) m.invoke(tm);
                                long l = Long.parseLong(delay1);
                                long d = l * 1000;
                                Thread.sleep(d);
                                telephonyService.endCall();
                                SmsManager sms = SmsManager.getDefault();
                                sms.sendTextMessage(incommingNumber, null, message, null, null);
                                Log.d("DEBUG", "Message sent message:" + message + "Number" + incommingNumber);
                                Toast.makeText(context, "Call Terminated:" + incommingNumber, Toast.LENGTH_SHORT).show();
                                //Mode=false;
                                prefs3 = context.getSharedPreferences(prefName3, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = prefs3.edit();
                                editor.putBoolean("MODE", false);
                                editor.commit();
                            } catch (Exception e) {
                                Log.d("DEBUG", e.toString());
                            }

                    }
                    for (Iterator iterator = contacts1.iterator(); iterator.hasNext(); ) {
                        String x = (String) iterator.next();
                        String[] parts = x.split("\t");
                        String nme = parts[0];
                        String no = parts[1];
                        if (incommingNumber.equals("+91" + no) || incommingNumber.equals("0" + no) || incommingNumber.equals(no))
                            try {

                                TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                                Class c = Class.forName(tm.getClass().getName());
                                Method m = c.getDeclaredMethod("getITelephony");
                                m.setAccessible(true);
                                com.android.internal.telephony.ITelephony telephonyService = (ITelephony) m.invoke(tm);
                                long l = Long.parseLong(delay1);
                                long d = l * 1000;
                                Thread.sleep(d);
                                telephonyService.endCall();
                                SmsManager sms = SmsManager.getDefault();
                                sms.sendTextMessage(incommingNumber, null, message1, null, null);
                                Log.d("DEBUG", "Message sent message:" + message1 + "Number" + incommingNumber);
                                Toast.makeText(context, "Call Terminated:" + incommingNumber, Toast.LENGTH_SHORT).show();
                                //Mode=false;
                                prefs3 = context.getSharedPreferences(prefName3, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = prefs3.edit();
                                editor.putBoolean("MODE", false);
                                editor.commit();
                            } catch (Exception e) {
                                Log.d("DEBUG", e.toString());
                            }

                    }
                    for (Iterator iterator = contacts2.iterator(); iterator.hasNext(); ) {
                        String x = (String) iterator.next();
                        String[] parts = x.split("\t");
                        String nme = parts[0];
                        String no = parts[1];
                        if (incommingNumber.equals("+91" + no) || incommingNumber.equals("0" + no) || incommingNumber.equals(no))
                            try {

                                TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                                Class c = Class.forName(tm.getClass().getName());
                                Method m = c.getDeclaredMethod("getITelephony");
                                m.setAccessible(true);
                                com.android.internal.telephony.ITelephony telephonyService = (ITelephony) m.invoke(tm);
                                long l = Long.parseLong(delay1);
                                long d = l * 1000;
                                Thread.sleep(d);
                                telephonyService.endCall();
                                SmsManager sms = SmsManager.getDefault();
                                sms.sendTextMessage(incommingNumber, null, message2, null, null);
                                Log.d("DEBUG", "Message sent message:" + message2 + "Number" + incommingNumber);
                                Toast.makeText(context, "Call Terminated:" + incommingNumber, Toast.LENGTH_SHORT).show();
                                //Mode=false;
                                prefs3 = context.getSharedPreferences(prefName3, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = prefs3.edit();
                                editor.putBoolean("MODE", false);
                                editor.commit();
                            } catch (Exception e) {
                                Log.d("DEBUG", e.toString());
                            }

                    }


                } else if (extras.getString(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_IDLE)
                    //&&Mode==true
                        ) {
                    if (TTS) {
                        Intent broadcastIntent = new Intent();
                        broadcastIntent.setAction("SMS_RECEIVED_ACTION");
                        broadcastIntent.putExtra("sms", "Call Terminated");
                        context.sendBroadcast(broadcastIntent);
                        Intent TTSIntent = new Intent(context, AndroidTextToSpeechActivity.class);
                        TTSIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(TTSIntent);
                    }
                }
            }
        }
    }
}