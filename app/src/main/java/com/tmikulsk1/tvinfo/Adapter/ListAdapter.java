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
import com.tmikulsk1.tvinfo.POJO.Show;
import com.tmikulsk1.tvinfo.R;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends ArrayAdapter<Show> {

    int id;
    int sharedPreferencesID;
    String name;
    List<String> genre;
    String genreString;
    String image;
    String des;

    TextView showName;
    TextView showGenre;
    ImageView showImage;
    ImageView showFavorite;


    public ListAdapter(Context context, ArrayList<Show> show){
        super(context, 0, show);
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



                final Show currentShows = getItem(position);

                id = currentShows.getShowId();
                name = currentShows.getShowName();
                genre = currentShows.getShowGenre();
                des = currentShows.getShowSummary();

                if (currentShows.getShowImage() != null) {
                    image =currentShows.getShowImage().getOriginal();
                }

                showName.setText(name);
                genreString = "";
                for (String s : genre){
                    genreString += s + " / ";
                }
                genreString = genreString.substring(0, genreString.length() - 2);
                showGenre.setText(genreString);

                final Favorite favorite = new Favorite(getContext());
                sharedPreferencesID = favorite.getShowFavorite(currentShows.getShowId());




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
