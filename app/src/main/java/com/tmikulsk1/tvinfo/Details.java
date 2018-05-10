package com.tmikulsk1.tvinfo;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Details extends AppCompatActivity {

    public TextView name;
    public ImageView image;
    public TextView genre;
    public TextView summary;
    public TextView premiered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        name = findViewById(R.id.name_details);
        image = findViewById(R.id.image_details);
        genre = findViewById(R.id.genre_details);
        summary = findViewById(R.id.summary_details);
        premiered = findViewById(R.id.premiered_details);

        Bundle extras = getIntent().getExtras();

        if (extras != null){

            name.setText(extras.getString("mName"));
            genre.setText(extras.getString("mGenre"));

            if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N){
                summary.setText(Html.fromHtml(extras.getString("mSummary"), Html.FROM_HTML_MODE_LEGACY));
            } else{
                summary.setText(Html.fromHtml(extras.getString("mSummary")));
            }

            premiered.setText(extras.getString("mPremiered"));

            //PICASSO
            //ADD PLACEHOLDER AND ERROR FALLBACK
            Picasso.with(this).load(extras.getString("mImage")).into(image);

        }
    }
}
