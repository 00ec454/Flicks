package com.dharmesh.flicks.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dharmesh.flicks.R;
import com.dharmesh.flicks.models.Movie;
import com.squareup.picasso.Picasso;


public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Movie[] movies;
    private Context context;

    public MovieAdapter(Movie[] movies, Context context) {
        this.movies = movies;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == 0) { // HEADER
            View view = inflater.inflate(R.layout.movie_element, parent, false);
            viewHolder = new FiveStarMovieViewHolder(view);
        } else { // DETAIL
            View view = inflater.inflate(R.layout.movie_element, parent, false);
            viewHolder = new FiveStarMovieViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Movie movie = movies[position];
        if (holder.getItemViewType() == 0) {
            final FiveStarMovieViewHolder holder1 = (FiveStarMovieViewHolder) holder;
            Picasso.with(context).load("https://image.tmdb.org/t/p/w154/"+movie.getPosterPath()).into(holder1.ivPoster);
            holder1.tvDescription.setText(movie.getOverview());
            holder1.tvTitle.setText(movie.getTitle());
        }
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return movies.length;
    }

    class FiveStarMovieViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPoster;
        TextView tvTitle;
        TextView tvDescription;

        FiveStarMovieViewHolder(View view) {
            super(view);
            ivPoster = view.findViewById(R.id.ivPoster);
            tvTitle = view.findViewById(R.id.tvTitle);
            tvDescription = view.findViewById(R.id.tvDescription);
        }
    }
}
