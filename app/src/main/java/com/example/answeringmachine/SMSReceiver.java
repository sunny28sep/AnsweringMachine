package com.example.answeringmachine;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SMSReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        SmsMessage[] msgs = null;
        String str = "";
        if (bundle != null) {
            Object[] pdus = (Object[]) bundle.get("pdus");
            msgs = new SmsMessage[pdus.length];
            for (int i = 0; i < msgs.length; i++) {
                msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                str = msgs[i].getOriginatingAddress() + "\t";
                str += msgs[i].getMessageBody().toString();

            }
            //Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
            /*Intent TTSIntent = new Intent(context, AndroidTextToSpeechActivity.class);
			TTSIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(TTSIntent);*/
            Intent broadcastIntent = new Intent();
            broadcastIntent.setAction("SMS_RECEIVED_ACTION");
            broadcastIntent.putExtra("sms", str);
            context.sendBroadcast(broadcastIntent);

        }


    }

}
