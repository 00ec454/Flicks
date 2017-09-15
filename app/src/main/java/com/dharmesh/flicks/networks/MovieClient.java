package com.dharmesh.flicks.networks;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by dgohil on 9/12/17.
 */

public class MovieClient {

    private static final String API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed";
    private static final String MOVIES_ENDPOINT_URL = "https://api.themoviedb.org/3/movie/now_playing";
    private static final String TAG = MovieClient.class.getSimpleName();

    public static void loadMovies(JsonHttpResponseHandler responseHandler) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("api_key", API_KEY);
        client.get(MOVIES_ENDPOINT_URL, params, responseHandler);
    }
}
