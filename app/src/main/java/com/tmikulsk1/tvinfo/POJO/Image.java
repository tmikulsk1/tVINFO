package com.tmikulsk1.tvinfo.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Image {

    @SerializedName("medium")
    @Expose
    private String mMedium;
    @SerializedName("original")
    @Expose
    private String mOriginal;

    public Image(String medium,
                 String original){

        mMedium = medium;
        mOriginal = original;

    }

    public String getMedium() {
        return mMedium;
    }

    public String getOriginal() {
        return mOriginal;
    }
}
