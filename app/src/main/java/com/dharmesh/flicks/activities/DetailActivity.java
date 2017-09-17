package com.dharmesh.flicks.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dharmesh.flicks.R;
import com.dharmesh.flicks.models.Movie;
import com.google.gson.Gson;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = DetailActivity.class.getSimpleName();
    private static final String IMAGE_URL = "https://image.tmdb.org/t/p/w1280/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        RelativeLayout rlContainer = findViewById(R.id.rlContainer);
        ImageView ivPoster = findViewById(R.id.ivPoster);
        ImageView ivPlay = findViewById(R.id.ivPlay);
        TextView tvTitle = findViewById(R.id.tvTitle);
        TextView tvDescription = findViewById(R.id.tvDescription);
        RatingBar ratingBar = findViewById(R.id.ratingBar);

        String movieJson = getIntent().getStringExtra("movie");
        Movie movie = new Gson().fromJson(movieJson, Movie.class);


        tvTitle.setText(movie.getTitle());
        tvDescription.setText(movie.getOverview());
        ratingBar.setRating(movie.getVoteAverage());

        ivPlay.setOnClickListener(view -> {
            Intent intent = new Intent(this, TrailerActivity.class);
            intent.putExtra("MOVIE_ID", movie.getId());
            startActivity(intent);
        });

        String imageUrl = IMAGE_URL + movie.getBackdropPath();
        Picasso.with(this).load(imageUrl).into(ivPoster, new Callback.EmptyCallback() {
            @Override
            public void onSuccess() {
                Bitmap bitmap = ((BitmapDrawable) ivPoster.getDrawable()).getBitmap();
                Palette.from(bitmap).generate(p -> {
                    Palette.Swatch vibrant = p.getLightVibrantSwatch();
                    if (vibrant != null) {
                        tvTitle.setTextColor(vibrant.getTitleTextColor());
                        tvDescription.setTextColor(vibrant.getBodyTextColor());
                        rlContainer.setBackgroundColor(vibrant.getRgb());
                    }
                });
            }
        });
    }
}
