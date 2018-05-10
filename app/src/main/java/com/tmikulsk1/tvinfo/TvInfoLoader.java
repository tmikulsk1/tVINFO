package com.tmikulsk1.tvinfo;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by tmikulsk1 on 10/05/2018.
 */

public class TvInfoLoader extends AsyncTaskLoader<List<TvInfo>> {

    private String mURL;

    public TvInfoLoader(Context context, String url) {
        super(context);
        mURL = url;
    }

    @Override
    protected void onStartLoading() {

        forceLoad();

    }

    @Override
    public List<TvInfo> loadInBackground() {
        if (mURL == null){
            return null;
        }

        List<TvInfo> tvInfoList = TvInfoJson.fetchEarthquakeData(mURL);

        return tvInfoList;
    }
}
