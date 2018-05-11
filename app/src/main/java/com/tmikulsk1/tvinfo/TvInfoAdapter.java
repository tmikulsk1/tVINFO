package com.tmikulsk1.tvinfo;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by tmikulsk1 on 10/05/2018.
 */

public class TvInfoAdapter extends ArrayAdapter<TvInfo> {

    int id;
    String name;
    String genre;
    String image;

    TextView showName;
    TextView showGenre;
    ImageView showImage;
    ImageView showFavorite;


    public TvInfoAdapter(Activity context, ArrayList<TvInfo> tvInfos){
        super(context, 0, tvInfos);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listView = convertView;

        if (listView == null){
            listView = LayoutInflater.from(getContext()).inflate(R.layout.item_view, parent, false );
        }

        final TvInfo currentShow = getItem(position);

        showName = listView.findViewById(R.id.show_name);
        showGenre = listView.findViewById(R.id.show_genre);
        showImage = listView.findViewById(R.id.show_image);
        showFavorite = listView.findViewById(R.id.show_favorite);

        id = currentShow.getShowId();
        name = currentShow.getShowName();
        genre = currentShow.getShowGenre();
        image = currentShow.getShowImage();

        showName.setText(name);
        showGenre.setText(genre);

        final Favorite favorite = new Favorite(getContext());
        int rec = favorite.getShowFavorite(currentShow.getShowId());

        if (rec == id) {
            showFavorite.setBackgroundColor(Color.RED);
        } else {
            showFavorite.setBackgroundColor(Color.GRAY);
        }

        //PICASSO
        //ADD PLACEHOLDER AND ERROR FALLBACK
        Picasso.with(getContext()).load(image).into(showImage);

        return listView;
    }

}
