package com.tmikulsk1.tvinfo;

import android.content.pm.InstrumentationInfo;

/**
 * Created by tmikulsk1 on 10/05/2018.
 */

public class TvInfo {

    private int mShowId;
    private String mShowName;
    private String mShowGenre;
    private String mShowImage;
    private String mShowSummary;
    private String mShowPremiered;

    public TvInfo(int showId,
                  String showName,
                  String showGenre,
                  String showImage,
                  String showSummary,
                  String showPremiered){

        mShowId = showId;
        mShowName = showName;
        mShowGenre = showGenre;
        mShowImage = showImage;
        mShowSummary = showSummary;
        mShowPremiered = showPremiered;
    }

    public int getShowId() { return mShowId; }

    public String getShowName(){
        return mShowName;
    }

    public String getShowGenre(){
        return mShowGenre;
    }

    public String getShowImage(){
        return mShowImage;
    }

    public String getShowSummary(){
        return mShowSummary;
    }

    public String getShowPremiered(){
        return mShowPremiered;
    }

}
