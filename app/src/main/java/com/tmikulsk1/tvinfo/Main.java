package com.tmikulsk1.tvinfo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;


import com.tmikulsk1.tvinfo.Adapter.ListAdapter;
import com.tmikulsk1.tvinfo.Adapter.ListAdapterSearch;
import com.tmikulsk1.tvinfo.POJO.Show;
import com.tmikulsk1.tvinfo.POJO.Shows;
import com.tmikulsk1.tvinfo.RestClient.REST;
import com.tmikulsk1.tvinfo.SharedPreferences.Favorite;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Main extends AppCompatActivity {

    private SearchView searchView;
    private ImageView favoriteImage;
    private LinearLayout loading;

    private REST.api_tvMaze service;
    private Retrofit retrofit;
    private ListAdapter listAdapter;
    private ListAdapterSearch listAdapterSearch;
    private ListView tvInfoListView;

    private static final String JSON_MAIN = "http://api.tvmaze.com";

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.remove_favorite:

                Favorite favorite = new Favorite(getApplicationContext());

                favorite.clearAll();
                getLoaderManager().restartLoader(0, null, Main.this);


            break;
        }
        return super.onOptionsItemSelected(item);
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        loading = findViewById(R.id.loading);
        tvInfoListView = findViewById(R.id.list);
        searchView = findViewById(R.id.search_json);

        loading.setVisibility(View.VISIBLE);

        retrofitBuild();
        retrieveAllShows();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                retrieveSearchShow(s);
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

                String detailsName = "";
                String detailsGenre = "";
                String detailsImage = "";
                String detailsSummary = "";
                String detailsPremiered = "";

                String adapterType = adapterView
                        .getAdapter()
                        .getClass()
                        .getSimpleName();

                switch (adapterType){
                    case "ListAdapter":

                        Show la = listAdapter.getItem(i);

                        detailsName = la.getShowName();
                        for (String s : la.getShowGenre()){
                            detailsGenre += s + " / ";
                        }
                        detailsGenre = detailsGenre.substring(0, detailsGenre.length() - 2);
                        if (la.getShowImage() != null) {
                            detailsImage = la.getShowImage().getOriginal();
                        }
                        detailsSummary = la.getShowSummary();
                        detailsPremiered = la.getShowPremiered();

                        break;

                    case "ListAdapterSearch":

                        Shows las = listAdapterSearch.getItem(i);

                        detailsName = las.getShow().getShowName();
                        for (String s : las.getShow().getShowGenre()){
                            detailsGenre += s + " / ";
                        }
                        detailsGenre = detailsGenre.substring(0, detailsGenre.length() - 2);
                        if (las.getShow().getShowImage() != null) {
                            detailsImage = las.getShow().getShowImage().getOriginal();
                        }
                        detailsSummary = las.getShow().getShowSummary();
                        detailsPremiered = las.getShow().getShowPremiered();

                        break;

                }

                Intent intent = new Intent(getApplicationContext(), Details.class);

                intent.putExtra("mName", detailsName);
                intent.putExtra("mImage", detailsImage);
                intent.putExtra("mGenre", detailsGenre);
                intent.putExtra("mSummary", detailsSummary);
                intent.putExtra("mPremiered", detailsPremiered);

                startActivity(intent);

            }
        });

        tvInfoListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                String adapterType = adapterView
                        .getAdapter()
                        .getClass()
                        .getSimpleName();

                int favoriteId = 0;

                final Favorite favorite = new Favorite(getApplicationContext());
                favoriteImage = view.findViewById(R.id.show_favorite);

                switch (adapterType) {
                    case "ListAdapter":

                        Show la = listAdapter.getItem(i);

                        favoriteId = la.getShowId();

                        break;

                    case "ListAdapterSearch":

                        Shows las = listAdapterSearch.getItem(i);

                        favoriteId = las.getShow().getShowId();

                        break;
                }

                int idInSharedPreferences = favorite.getShowFavorite(favoriteId);

                if (idInSharedPreferences == favoriteId) {
                    favoriteImage.setImageResource(R.drawable.fav_0);
                    //view.findViewById(R.id.show_favorite).setBackgroundColor(Color.GRAY);
                    favorite.delShowFavorite(favoriteId);
                } else {
                    favoriteImage.setImageResource(R.drawable.fav_1);
                    //view.findViewById(R.id.show_favorite).setBackgroundResource(Color.RED);
                    favorite.addShowFavorite(favoriteId);
                }

                return true;

            }

        });

    }

    public void retrofitBuild(){

        retrofit = new Retrofit
                .Builder()
                .baseUrl(JSON_MAIN)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(REST.api_tvMaze.class);

    }

    public void retrieveAllShows(){

        service.getAllShows().enqueue(new Callback<ArrayList<Show>>() {
            @Override
            public void onResponse(Call<ArrayList<Show>> call, Response<ArrayList<Show>> response) {

                loading.setVisibility(View.GONE);
                listAdapter = new ListAdapter(getApplicationContext(), response.body());
                tvInfoListView.setAdapter(listAdapter);

            }

            @Override
            public void onFailure(Call<ArrayList<Show>> call, Throwable t) {

            }
        });

    }

    public void retrieveSearchShow(String show){
        service.getShowBySearch(show).enqueue(new Callback<ArrayList<Shows>>() {
            @Override
            public void onResponse(Call<ArrayList<Shows>> call, Response<ArrayList<Shows>> response) {

                loading.setVisibility(View.GONE);
                listAdapterSearch = new ListAdapterSearch(getApplicationContext(), response.body());
                tvInfoListView.setAdapter(listAdapterSearch);

            }

            @Override
            public void onFailure(Call<ArrayList<Shows>> call, Throwable t) {

            }
        });
    }


}
