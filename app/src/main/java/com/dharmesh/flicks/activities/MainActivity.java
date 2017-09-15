package com.dharmesh.flicks.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.dharmesh.flicks.R;
import com.dharmesh.flicks.adapters.MovieAdapter;
import com.dharmesh.flicks.models.Movie;
import com.dharmesh.flicks.networks.MovieClient;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView rvMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvMovies = findViewById(R.id.rvMovies);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvMovies.setLayoutManager(linearLayoutManager);

        MovieClient.loadMovies(responseHandler);
    }

    private JsonHttpResponseHandler responseHandler = new JsonHttpResponseHandler() {

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
            Log.i(TAG, "onFailure: " + errorResponse);
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            try {
                GsonBuilder builder = new GsonBuilder()
                        .setDateFormat("yyyy-MM-dd")
                        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);

                JSONArray jsonArray = response.getJSONArray("results");
                Movie[] movies = builder.create().fromJson(jsonArray.toString(), Movie[].class);
                for(Movie movie: movies){
                    Log.i(TAG, "onSuccess: "+movie);
                }
                MovieAdapter movieAdapter = new MovieAdapter(movies, getBaseContext());
                rvMovies.setAdapter(movieAdapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };
}
