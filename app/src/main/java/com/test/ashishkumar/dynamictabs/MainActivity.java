package com.test.ashishkumar.dynamictabs;

import android.os.AsyncTask;
import android.os.Bundle;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Context;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONArray;
import java.util.concurrent.ExecutionException;

/**
 * Created by ashishkumar on 26/03/16.
 */

public class MainActivity extends AppCompatActivity implements Tab.OnFragmentInteractionListener  {

    // Declaring Your View and Variables

    Toolbar toolbar;
    ViewPager pager;
    TextView  tab1TextView ;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence tabTitles[];
    Context context;
    public static  String[] descriptionArray ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.context = getApplicationContext();


        try {
            String data = new DownloadAsyncTask().execute("http://android.oliveboard.in/hiring/mocks.cgi", "").get();
            System.out.println("data received successfully");
            JSONObject json = new JSONObject(new JSONTokener(data));
            JSONArray array = json.getJSONArray("exams");
            System.out.println(array);
            System.out.println(array.length());
            System.out.println(array.getJSONArray(0));

            int numOfTabs = array.length();
            tabTitles = new CharSequence[array.length()];
            descriptionArray = new String[array.length()];

            for(int i=0;i<array.length();i++) {
                System.out.println("Title = " + array.getJSONArray(i).get(0));
                tabTitles[i] = (String) array.getJSONArray(i).get(0);
                descriptionArray[i] = (String)array.getJSONArray(i).get(1);
            }

            buildUI(tabTitles,numOfTabs);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }



    }

    public void buildUI(CharSequence[] titles, int numTabs) {
        // Creating The Toolbar and setting it as the Toolbar for the activity

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter =  new ViewPagerAdapter(getSupportFragmentManager(),titles,numTabs);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        // Assigning the Sliding Tab Layout View
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width
        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onFragmentInteraction(Uri uri){
        //you can leave it empty

    }

}
