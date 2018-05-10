package com.tmikulsk1.tvinfo;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by tmikulsk1 on 10/05/2018.
 */

public class TvInfoAdapter extends ArrayAdapter<TvInfo> {

    String name;
    String genre;
    String image;

    TextView showName;
    TextView showGenre;
    ImageView showImage;


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

        TvInfo currentShow = getItem(position);

        showName = listView.findViewById(R.id.show_name);
        showGenre = listView.findViewById(R.id.show_genre);
        showImage = listView.findViewById(R.id.show_image);

        name = currentShow.getShowName();
        genre = currentShow.getShowGenre();
        image = currentShow.getShowImage();

        showName.setText(name);
        showGenre.setText(genre);

        //PICASSO
        //ADD PLACEHOLDER AND ERROR FALLBACK
        Picasso.with(getContext()).load(image).into(showImage);

        return listView;
    }
}
