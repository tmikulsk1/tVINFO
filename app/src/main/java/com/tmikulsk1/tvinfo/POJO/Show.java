package com.tmikulsk1.tvinfo.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Show {

    @SerializedName("id")
    @Expose
    private int mShowId;
    @SerializedName("name")
    @Expose
    private String mShowName;
    @SerializedName("genres")
    @Expose
    private List<String> mShowGenre;
    @SerializedName("image")
    @Expose
    private Image mShowImage;
    @SerializedName("summary")
    @Expose
    private String mShowSummary;
    @SerializedName("premiered")
    @Expose
    private String mShowPremiered;

    public Show(int showId,
                  String showName,
                  List<String> showGenre,
                  Image showImage,
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

    public List<String> getShowGenre(){
        return mShowGenre;
    }

    public Image getShowImage(){
        return mShowImage;
    }

    public String getShowSummary(){
        return mShowSummary;
    }

    public String getShowPremiered(){
        return mShowPremiered;
    }

}
