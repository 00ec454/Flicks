package com.dharmesh.flicks.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dharmesh.flicks.R;
import com.dharmesh.flicks.activities.DetailActivity;
import com.dharmesh.flicks.activities.TrailerActivity;
import com.dharmesh.flicks.models.Movie;
import com.google.gson.Gson;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;


public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Movie[] movies;
    private Context context;
    private boolean potraitMode = true;
    //AIzaSyAMsT7EpgH_y8Vyg-n3xkkGSyfrdSMFuYU

    public MovieAdapter(Movie[] movies, Context context) {
        this.movies = movies;
        this.context = context;
        potraitMode = context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == 0) {
            View view = inflater.inflate(R.layout.movie_element, parent, false);
            viewHolder = new MovieViewHolder(view);
        } else {
            View view = inflater.inflate(R.layout.fivestar_movie_element, parent, false);
            viewHolder = new FiveStarMovieViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Movie movie = movies[position];
        if (holder.getItemViewType() == 0) {
            final MovieViewHolder holder1 = (MovieViewHolder) holder;
            String imageUrl = "https://image.tmdb.org/t/p/w154/" + movie.getPosterPath();
            if (!potraitMode) {
                imageUrl = "https://image.tmdb.org/t/p/w300/" + movie.getBackdropPath();
            }
            Picasso.with(context).load(imageUrl).into(holder1.ivPoster);
            holder1.tvDescription.setText(movie.getOverview());
            holder1.tvTitle.setText(movie.getTitle());
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("movie", new Gson().toJson(movie));
            holder1.itemView.setOnClickListener(view1 -> context.startActivity(intent));

        } else {
            final FiveStarMovieViewHolder holder1 = (FiveStarMovieViewHolder) holder;
            String imageUrl = "https://image.tmdb.org/t/p/w1280/" + movie.getBackdropPath();
            Picasso.with(context).load(imageUrl).into(holder1.ivPoster, new Callback.EmptyCallback() {
                @Override
                public void onSuccess() {
                    Bitmap bitmap = ((BitmapDrawable) holder1.ivPoster.getDrawable()).getBitmap(); // Ew!
                    Palette.from(bitmap).generate(p -> {
                        Palette.Swatch vibrant = p.getVibrantSwatch();
                        if (vibrant != null) {
                            int color = Color.argb(150, Color.red(vibrant.getRgb()), Color.green(vibrant.getRgb()), Color.blue(vibrant.getRgb()));
                            holder1.tvTitle.setBackgroundColor(color);
                            holder1.tvTitle.setTextColor(vibrant.getTitleTextColor());
                        }
                    });
                }
            });
            holder1.tvDescription.setText(movie.getOverview());
            holder1.tvTitle.setText(movie.getTitle());
            holder1.ivPlay.setOnClickListener(view -> {
                Intent intent = new Intent(context, TrailerActivity.class);
                intent.putExtra("MOVIE_ID", movie.getId());
                context.startActivity(intent);
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        return movies[position].getVoteAverage() > 5 ? 1 : 0;
    }

    @Override
    public int getItemCount() {
        return movies.length;
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPoster;
        TextView tvTitle;
        TextView tvDescription;

        MovieViewHolder(View view) {
            super(view);
            ivPoster = view.findViewById(R.id.ivPoster);
            tvTitle = view.findViewById(R.id.tvTitle);
            tvDescription = view.findViewById(R.id.tvDescription);
        }
    }

    class FiveStarMovieViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPoster;
        ImageView ivPlay;
        TextView tvTitle;
        TextView tvDescription;

        FiveStarMovieViewHolder(View view) {
            super(view);
            ivPoster = view.findViewById(R.id.ivPoster);
            ivPlay = view.findViewById(R.id.ivPlay);
            tvTitle = view.findViewById(R.id.tvTitle);
            tvDescription = view.findViewById(R.id.tvDescription);
        }
    }
}
