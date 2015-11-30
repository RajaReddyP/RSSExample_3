package com.polamr.rssexample;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.polamr.rssexample.utils.RssItem;
import com.polamr.rssexample.utils.RssReader;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String mRssUrl = "";
    private ArrayList<RssItem> itemsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mRecyclerView = (RecyclerView) findViewById(R.id.feedInfoRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        loadData("https://www.picturebox.tv/xml/feeds/FindAnyFilm/FindAnyFilm.xml");

        FeedInfoAdapter.setOnItemClickListener(new FeedInfoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                RssItem feedinfo = itemsList.get(position);
//                Intent intent = new Intent(MainActivity.this, FeedDisplayActivity.class);
//                intent.putExtra(Global.RSS_FEED_URL, feedinfo.getDataUrl());
//                startActivity(intent);
            }
        });

    }

    private void loadData(String feedUrl) {
        new getData().execute(feedUrl);
    }

    private class getData extends AsyncTask<String, ArrayList<RssItem>, ArrayList<RssItem>> {

        ProgressDialog mProgressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            itemsList = new ArrayList<>();
            mProgressDialog = new ProgressDialog(MainActivity.this);
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.show();
        }

        @Override
        protected ArrayList<RssItem> doInBackground(String... params) {
            try {
                RssReader reader = new RssReader(params[0]);
                ArrayList<RssItem> items = reader.getItems();
                return items;
            }catch (Exception e) {
                Global.showLog("Exception : "+e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<RssItem> rssItems) {
            super.onPostExecute(rssItems);
            itemsList = rssItems;
            if(mProgressDialog.isShowing())
                mProgressDialog.dismiss();

            if(rssItems != null) {
                //Global.showLog("items : " + rssItems.size());
                //Global.showLog("item desc : " + rssItems.get(0).getImageUrl());

                ArrayList<RssItem> newItemsList = new ArrayList<>();
                for(RssItem item : rssItems)
                {
                    if(item != null) {
                        String title = item.getTitle();
                        //Global.showLog("title : "+title);
                        if(title != null) {
                            if (title.length() > 0 && !title.equals(""))
                                newItemsList.add(item);
                        }
                    }
                }
                mAdapter = new FeedInfoAdapter(MainActivity.this, newItemsList);
                mRecyclerView.setAdapter(mAdapter);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }


}