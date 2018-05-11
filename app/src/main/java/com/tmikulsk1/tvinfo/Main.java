package com.tmikulsk1.tvinfo;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;


import java.util.ArrayList;
import java.util.List;

public class Main extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<TvInfo>> {

    private TvInfoAdapter mAdapter;
    private SearchView searchView;

    private static String JSON = "";
    //0 for index shows | 1 for search shows
    public static int TYPE_JSON = 0;
    private static final String JSON_MAIN = "http://api.tvmaze.com/shows";
    private static final String JSON_SEARCH = "http://api.tvmaze.com/search/shows?q=";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView tvInfoListView = findViewById(R.id.list);

        mAdapter = new TvInfoAdapter(this, new ArrayList<TvInfo>());

        tvInfoListView.setAdapter(mAdapter);

        JSON = JSON_MAIN;
        TYPE_JSON = 0;

        final LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(0, null, this);

        searchView = findViewById(R.id.search_json);

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {

                JSON = JSON_MAIN;
                TYPE_JSON = 0;

                getLoaderManager().restartLoader(0, null, Main.this);

                return false;
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                if (!s.isEmpty()) {

                    JSON = JSON_SEARCH + s;
                    TYPE_JSON = 1;

                    getLoaderManager().restartLoader(0, null, Main.this);

                }

                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                return false;
            }

        });

        tvInfoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                TvInfo currentShowInfo = mAdapter.getItem(i);

                Intent intent = new Intent(getApplicationContext(), Details.class);

                intent.putExtra("mName", currentShowInfo.getShowName());
                intent.putExtra("mImage", currentShowInfo.getShowImage());
                intent.putExtra("mGenre", currentShowInfo.getShowGenre());
                intent.putExtra("mSummary", currentShowInfo.getShowSummary());
                intent.putExtra("mPremiered", currentShowInfo.getShowPremiered());

                startActivity(intent);

            }

        });
    }

    @Override
    public Loader<List<TvInfo>> onCreateLoader(int i, Bundle bundle) {
        mAdapter.clear();
        return new TvInfoLoader(this, JSON);
    }

    @Override
    public void onLoadFinished(Loader<List<TvInfo>> loader, List<TvInfo> tvInfos) {

        if (tvInfos != null && !tvInfos.isEmpty()) {
            mAdapter.addAll(tvInfos);
        } else {
            Log.e("main", "no tv show");
        }

    }

    @Override
    public void onLoaderReset(Loader<List<TvInfo>> loader) {
        mAdapter.clear();
    }


}
