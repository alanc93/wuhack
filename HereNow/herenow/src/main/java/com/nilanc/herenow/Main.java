package com.nilanc.herenow;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Main extends Activity implements AdapterView.OnItemClickListener {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
//    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private List<String> chatList;
    private CharSequence mTitle;
    private static ArrayAdapter<Place> menuAdapter;
    private ListView mainMenu;
    private LocationManager locMan;
    private static LocationSearch searcher;

    public static final String CHAT_NAME = "chat name";
    public static final String CHAT_ID = "chat id";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Chatrooms near you");

        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
//        QBAuth.createSession("user", "password", new QBCallback() {
//            @Override
//            public void onComplete(Result result) {
//
//            }
//
//            @Override
//            public void onComplete(Result result, Object o) {
//
//            }
//        });
        searcher = new LocationSearch();

        mainMenu = (ListView) findViewById(R.id.main_menu);

        final List<Place> chatList = new ArrayList<Place>();
        menuAdapter = new ArrayAdapter<Place>(this, android.R.layout.simple_list_item_2, android.R.id.text1, chatList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                text1.setText(chatList.get(position).events[0].summary);
                text2.setText(chatList.get(position).name);
                return view;
            }
        };

        mainMenu.setAdapter(menuAdapter);
        mainMenu.setOnItemClickListener(this);

        // Capture location
        ////////////////////
        LocationManager mlocManager = null;
        LocListener mlocListener;
        mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setSpeedRequired(false);
        mlocListener = new LocListener();
        String prov = mlocManager.getBestProvider(criteria, false);
        mlocManager.requestLocationUpdates(prov, 0, 0, mlocListener);
        Location temp = LocListener.getLocation(this);
        updateUI();

    }


    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_search:
                updateUI();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent launchChat = new Intent(this, Chat.class);

        TextView v1 = (TextView) view.findViewById(android.R.id.text1);
        TextView v2 = (TextView) view.findViewById(android.R.id.text2);
        launchChat.putExtra(CHAT_NAME, v1.getText());
        launchChat.putExtra(CHAT_ID, v1.getText() + " at " + v2.getText());

        startActivity(launchChat);
    }

    public static void updateUI()
    {
        try {
            PlacesList pl = searcher.performSearch();
            menuAdapter.clear();
            for (Place p : pl.results) {
                if (menuAdapter.getPosition(p) == -1 && p.events != null)
                    menuAdapter.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }

    }

}
