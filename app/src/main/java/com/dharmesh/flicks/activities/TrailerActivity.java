/*
 * Copyright 2012 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dharmesh.flicks.activities;

import android.os.Bundle;
import android.util.Log;

import com.dharmesh.flicks.R;
import com.dharmesh.flicks.models.Trailer;
import com.dharmesh.flicks.networks.MovieClient;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class TrailerActivity extends YouTubeBaseActivity {

    private static final String TAG = TrailerActivity.class.getSimpleName();
    private static final String KEY = "AIzaSyAMsT7EpgH_y8Vyg-n3xkkGSyfrdSMFuYU";
    private YouTubePlayerView youTubePlayerView;
    private String youTubeKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailer);

        youTubePlayerView =
                findViewById(R.id.player);
        final long movieId = getIntent().getLongExtra("MOVIE_ID", -1);
        MovieClient.getTrailers(movieId, responseHandler);


    }

    YouTubePlayer.OnInitializedListener listener = new YouTubePlayer.OnInitializedListener() {
        @Override
        public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                            YouTubePlayer youTubePlayer, boolean b) {

            youTubePlayer.cueVideo(youTubeKey);
        }

        @Override
        public void onInitializationFailure(YouTubePlayer.Provider provider,
                                            YouTubeInitializationResult youTubeInitializationResult) {
            Log.e(TAG, youTubeInitializationResult.name());
        }
    };

    private JsonHttpResponseHandler responseHandler = new JsonHttpResponseHandler() {

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
            Log.i(TAG, "onFailure: " + errorResponse);
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            try {
                GsonBuilder builder = new GsonBuilder()
                        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);

                JSONArray jsonArray = response.getJSONArray("results");
                Trailer[] trailers = builder.create().fromJson(jsonArray.toString(), Trailer[].class);
                for (Trailer trailer : trailers) {
                    if ("YouTube".equals(trailer.getSite())) {
                        youTubeKey = trailer.getKey();
                        break;
                    }
                }
                youTubePlayerView.initialize(KEY,
                        listener);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    };
}
