package com.example.answeringmachine;

import java.util.Locale;

import com.example.answeringmachine.R;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AndroidTextToSpeechActivity extends Activity implements
        TextToSpeech.OnInitListener {
    /**
     * Called when the activity is first created.
     */

    public TextToSpeech tts;
    public Button btnSpeak;
    public EditText txtText;
    IntentFilter intentFilter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        BroadcastReceiver intentReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                Handler handler = new Handler();
                Runnable x = new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                };
                handler.postDelayed(x, 8000);

                TextView SMSes = (TextView) findViewById(R.id.receivedmsg);
                TextView Name = (TextView) findViewById(R.id.name);
                String sms = intent.getExtras().getString("sms");
                String[] parts = sms.split("\t");
                String msg = parts[1];
                String nme = parts[0];
                SMSes.setText("Message:" + msg);
                Name.setText("Number:" + nme);
                SpeakString(msg);
            }
        };
        intentFilter = new IntentFilter();
        intentFilter.addAction("SMS_RECEIVED_ACTION");
        registerReceiver(intentReceiver, intentFilter);
        tts = new TextToSpeech(this, this);

		/*btnSpeak = (Button) findViewById(R.id.btnSpeak);

		txtText = (EditText) findViewById(R.id.txtText);
		

		// button on click event
		btnSpeak.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				speakOut();
			}

		});*/
    }

    @Override
    public void onDestroy() {
        // Don't forget to shutdown!
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    @Override
    public void onInit(int status) {
        // TODO Auto-generated method stub

        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.US);

            // tts.setPitch(5); // set pitch level

            // tts.setSpeechRate(2); // set speech speed rate

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "Language is not supported");
            }
            /*else {
				btnSpeak.setEnabled(true);
				speakOut();
			}
			*/

        } else {
            Log.e("TTS", "Initilization Failed");
        }

    }

    public void speakOut() {

        String text = txtText.getText().toString();

        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    public void SpeakString(String s) {
        tts.speak(s, TextToSpeech.QUEUE_FLUSH, null);

    }
}