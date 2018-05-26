package com.tmikulsk1.tvinfo.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tmikulsk1.tvinfo.SharedPreferences.Favorite;
import com.tmikulsk1.tvinfo.POJO.Shows;
import com.tmikulsk1.tvinfo.R;

import java.util.ArrayList;
import java.util.List;

public class ListAdapterSearch extends ArrayAdapter<Shows> {

    int id;
    int sharedPreferencesID;
    String name;
    List<String> genre;
    String genreString;
    String image;

    TextView showName;
    TextView showGenre;
    ImageView showImage;
    ImageView showFavorite;


    public ListAdapterSearch(Context context, ArrayList<Shows> shows){
        super(context, 0, shows);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listView = convertView;

        if (listView == null){
            listView = LayoutInflater.from(getContext()).inflate(R.layout.item_view, parent, false );
        }

        showName = listView.findViewById(R.id.show_name);
        showGenre = listView.findViewById(R.id.show_genre);
        showImage = listView.findViewById(R.id.show_image);
        showFavorite = listView.findViewById(R.id.show_favorite);



        final Shows currentShows = getItem(position);

        id = currentShows.getShow().getShowId();
        name = currentShows.getShow().getShowName();
        genre = currentShows.getShow().getShowGenre();

        if (currentShows.getShow().getShowImage() != null) {
            image =currentShows.getShow().getShowImage().getOriginal();
        }

        showName.setText(name);
        genreString = "";
        for (String s : genre){
            genreString += s + " / ";
            genreString = genreString.substring(0, genreString.length() - 1);
        }
        showGenre.setText(genreString);

        final Favorite favorite = new Favorite(getContext());
        sharedPreferencesID = favorite.getShowFavorite(currentShows.getShow().getShowId());




        if (sharedPreferencesID == id) {
            showFavorite.setImageResource(R.drawable.fav_1);
            //showFavorite.setBackgroundColor(Color.RED);
        } else {
            showFavorite.setImageResource(R.drawable.fav_0);
            //showFavorite.setBackgroundColor(Color.GRAY);
        }

        //PICASSO
        //ADD PLACEHOLDER AND ERROR FALLBACK
        if (image != null) {
            Picasso.with(getContext()).load(image).into(showImage);
        }
        return listView;
    }


}
