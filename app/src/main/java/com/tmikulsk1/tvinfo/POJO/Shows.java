package com.tmikulsk1.tvinfo.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Shows {

    @SerializedName("score")
    @Expose
    private Double mScore;
    @SerializedName("show")
    @Expose
    private Show mShow;

    public Shows(Double score,
                 Show show){

        mScore = score;
        mShow = show;

    }

    public Double getScore() {
        return mScore;
    }

    public Show getShow() {
        return mShow;
    }
}
