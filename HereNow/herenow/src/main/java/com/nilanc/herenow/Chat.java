package com.nilanc.herenow;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;


import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class Chat extends Activity {
    public static final String RECEIVE_MESSAGE = "com.nilanc.herenow.RECEIVE_MESSAGE";
    private String CHAT_ROOM;

    String PROJECT_NUMBER = "56902053111";

    private GoogleCloudMessaging gcm;
    private String regid;
    private MsgAdapter adptr;
    private TextView messageView;
    private ListView msgList;
    private EditText t;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        messageView = (TextView) findViewById(R.id.msgText);
        msgList = (ListView) findViewById(R.id.msgList);

        Intent i = getIntent();
        getActionBar().setTitle(i.getStringExtra(Main.CHAT_NAME));
        CHAT_ROOM = i.getStringExtra(Main.CHAT_ID);

        // Tell GCM we want to receive messages.
        registerWithGCM();

        // Tell our local broadcast manager we want to receive messages and handle
        // them with our 'bReceiver'.
        adptr = new MsgAdapter(this, new ArrayList<Message>());
        msgList.setAdapter(adptr);

        LocalBroadcastManager bManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(RECEIVE_MESSAGE);
        bManager.registerReceiver(bReceiver, intentFilter);

        t = (EditText) findViewById(R.id.msgBox);
        t.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actId, KeyEvent event) {
                if(actId == EditorInfo.IME_ACTION_SEND) {
                    Message m = new Message(v.getText().toString(), true);
                    adptr.add(m);
                    System.out.println("Added self thing");
                    //send message to GCM
                    try {
                        gcm.send(RECEIVE_MESSAGE, m.getMsg(), null);
                        Log.e("sent", "sent");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    v.setText("");
                    t.onEditorAction(EditorInfo.IME_ACTION_DONE);
                }
                return false;
            }
        });
    }


    // Set up a broadcast receiver
    private BroadcastReceiver bReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(RECEIVE_MESSAGE)) {
                String message = intent.getStringExtra("message");
                adptr.add(new Message(message, false));
                System.out.println("Added other thing");
//                messageView.setText(message);
            }
        }
    };


    // registerWithGCM contacts the GCM server and logs the ID it receives.
    public void registerWithGCM(){
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
                    }
                    String regid = gcm.register(PROJECT_NUMBER);
                    // In reality, we would want to send this to the server so it can reach us.
                    // For the sake of simplicity for this demo, we'll just copy and paste it from
                    // the logs.
                    msg = "Device registered, registration ID=" + regid;
                    Log.i("GCM", msg);
                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                }
                return msg;
            }
        }.execute(null, null, null);
    }
}