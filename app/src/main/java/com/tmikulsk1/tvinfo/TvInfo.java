package com.tmikulsk1.tvinfo;

import android.content.pm.InstrumentationInfo;

/**
 * Created by tmikulsk1 on 10/05/2018.
 */

public class TvInfo {

    private String mShowName;
    private String mShowGenre;
    private String mShowImage;
    private String mShowSummary;
    private String mShowPremiered;

    public TvInfo(String showName,
                  String showGenre,
                  String showImage,
                  String showSummary,
                  String showPremiered){

        mShowName = showName;
        mShowGenre = showGenre;
        mShowImage = showImage;
        mShowSummary = showSummary;
        mShowPremiered = showPremiered;
    }

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
