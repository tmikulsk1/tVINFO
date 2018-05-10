package com.tmikulsk1.tvinfo;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Main extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<TvInfo>> {

    private TvInfoAdapter mAdapter;
    private static final String JSON_MAIN = "http://api.tvmaze.com/shows";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView tvInfoListView = findViewById(R.id.list);

        mAdapter = new TvInfoAdapter(this, new ArrayList<TvInfo>());

        tvInfoListView.setAdapter(mAdapter);

        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(1, null, this);

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
        return new TvInfoLoader(this, JSON_MAIN);
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
