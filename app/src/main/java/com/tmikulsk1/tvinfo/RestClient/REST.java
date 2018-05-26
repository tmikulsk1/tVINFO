package com.tmikulsk1.tvinfo.RestClient;

import com.tmikulsk1.tvinfo.POJO.Show;
import com.tmikulsk1.tvinfo.POJO.Shows;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class REST {

    public interface api_tvMaze{

        @GET ("search/shows")
        Call<ArrayList<Shows>> getShowBySearch(
                @Query("q") String search
        );

        @GET ("/shows")
        Call<ArrayList<Show>> getAllShows();

        @GET ("/shows/{id}")
        Call<ArrayList<Show>> getShowById(
                @Path("id") int id
        );
    }

}
