package com.nilanc.herenow;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.format.Time;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.quickblox.module.chat.QBChatRoom;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.view.inputmethod.EditorInfo.*;

public class Chat extends Activity {

    private ListView msgList;
    private MsgAdapter msgAdptr;
    private EditText msg;
    private Room chatRoom;

    public static void start(Context context, Bundle bundle) {
        Intent intent = new Intent(context, Chat.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();


        msgList = (ListView) findViewById(R.id.msgList);

        msg = (EditText) findViewById(R.id.msgBox);


        msgAdptr = new MsgAdapter(this, new ArrayList<Message>());
        msgList.setAdapter(msgAdptr);
        msgAdptr.add(new Message("Hi there!", new Date()));
        msgAdptr.add(new Message("Isn't <event> so cool!", new Date()));

        msg.setOnEditorActionListener(new OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actId, KeyEvent e) {
                if (actId == IME_ACTION_SEND) {
                    //perform send message stuff
                    Message m = new Message(v.getText().toString(), new Date());
                    msgAdptr.add(m);
                    v.setText("");
                    msg.onEditorAction(IME_ACTION_DONE);
                    return true;
                }
                return false;
            }
        });

//        QBChatRoom cr = ;
    }

    public void showMessage(Message message) {
//        saveMessageToHistory(message);
        msgAdptr.add(message);
        msgAdptr.notifyDataSetChanged();
        scrollDown();
    }

    public void showMessage(List<Message> messages) {
        msgAdptr.add(messages);
        msgAdptr.notifyDataSetChanged();
        scrollDown();
    }

    private void scrollDown() {
        msgList.setSelection(msgList.getCount() - 1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.chat, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch(item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
