package com.nilanc.herenow;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.view.inputmethod.EditorInfo.*;

public class Chat extends Activity {

    private ListView msgList;
    private MsgAdapter msgAdptr;
    private EditText msg;
    private Room chatRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        getActionBar().setDisplayHomeAsUpEnabled(true);
//
//        if (savedInstanceState == null) {
//            getFragmentManager().beginTransaction()
//                    .add(R.id.container, new PlaceholderFragment())
//                    .commit();
//        }

        init();
    }

    public void init() {
        msgList = (ListView) findViewById(R.id.msgList);

        msg = (EditText) findViewById(R.id.msgBox);


        msgAdptr = new MsgAdapter(this, new ArrayList<Message>());
        msgList.setAdapter(msgAdptr);

        msg.setOnEditorActionListener(new OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actId, KeyEvent e) {
                if(actId == IME_ACTION_SEND) {
                    //perform send message stuff
                    Message m = new Message(v.getText().toString(), new Date());
                    v.setText("");
                    return true;
                }
                return false;
            }
        });
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_chat, container, false);

            EditText msg = (EditText) rootView.findViewById(R.id.msgBox);
            msg.setOnEditorActionListener(new OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actId, KeyEvent e) {
                    if(actId == IME_ACTION_SEND) {
                        //perform send message stuff
                        Message m = new Message(v.getText().toString(), new Date());
                        v.setText("");
                        return true;
                    }
                    return false;
                }
            });
            return rootView;
        }
    }
}
