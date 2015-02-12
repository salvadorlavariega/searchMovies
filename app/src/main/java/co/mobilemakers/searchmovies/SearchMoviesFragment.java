package co.mobilemakers.searchmovies;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class SearchMoviesFragment extends ListFragment {

    private static final String LOG_TAG = SearchMoviesFragment.class.getSimpleName();

    private static final Boolean NO_CLEAR_LIST=false;
    private static final Boolean CLEAR_LIST=true;
    EditText editTextSearchMovie;
    SearchMoviesAdapter adapter;

    public SearchMoviesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search_movies, container, false);
        wireUpView(rootView);
        prepareButton(rootView);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        prepareListview();
        fetchMoviesInQueue("Pulp Fiction",NO_CLEAR_LIST);
        fetchMoviesInQueue("el Padrino",NO_CLEAR_LIST);
        fetchMoviesInQueue("scarface",NO_CLEAR_LIST);
        fetchMoviesInQueue("titanic",NO_CLEAR_LIST);
        fetchMoviesInQueue("shreck",NO_CLEAR_LIST);
    }

    private void wireUpView(View rootView) {
        editTextSearchMovie = (EditText) rootView.findViewById(R.id.edit_text_name_movie);
    }

    private void prepareButton(View rootView) {
        Button buttonSearchMovie = (Button)rootView.findViewById(R.id.button_search_movies);
        buttonSearchMovie.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                   String movieTitle = editTextSearchMovie.getText().toString();
                fetchMoviesInQueue(movieTitle,CLEAR_LIST);
            }
        });
    }

    private void prepareListview(){
        List<Movie> listMovies = new ArrayList<>();
        adapter = new SearchMoviesAdapter(getActivity(),listMovies);
        setListAdapter(adapter);
    }

    private void  fetchMoviesInQueue(String nameMovie, final Boolean cleanList){
        try {
            URL url = constructQuery(nameMovie);
            Request request  = new Request.Builder().url(url.toString()).build();
            OkHttpClient client = new OkHttpClient();
            client.newCall(request).enqueue(new com.squareup.okhttp.Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    String responseString = response.body().string();

                    final List<Movie> listOfLocations = parseResponse(responseString);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(cleanList)adapter.clear();
                            adapter.addAll(listOfLocations);
                            adapter.notifyDataSetChanged();

                        }
                    });


                }
            });
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private URL constructQuery(String nameMovie) throws MalformedURLException {
        final String API_MOVIES = "www.omdbapi.com";
        final String KEY_PARAMETER_TITLE = "t";
        final String FORMAT_PARAMETER  = "r";
        final String JSON_FORMAT = "json";

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http").authority(API_MOVIES).
                appendQueryParameter(KEY_PARAMETER_TITLE, nameMovie).appendQueryParameter(FORMAT_PARAMETER, JSON_FORMAT);

        Uri uri = builder.build();
        Log.d(LOG_TAG, "Built URI: " + uri.toString());

        return new URL(uri.toString());
    }


    private List<Movie> parseResponse(String response) throws MalformedURLException {
        final String TITLE="Title";
        final String PLOT="Plot";
        final String POSTER_URL="Poster";
        List<Movie> movieList = new ArrayList<>();
        Movie movie;
        try {
            JSONObject jsonObject = new JSONObject(response);
            String title = jsonObject.getString(TITLE);
            String plot= jsonObject.getString(PLOT);
           String posterurl = jsonObject.getString(POSTER_URL);


            movie = new Movie();
            movie.setName(title);
            movie.setDescription(plot);

            movie.setUriPicture(posterurl);
            movieList.add(movie);



        } catch (JSONException e) {
            e.printStackTrace();
        }

        return movieList;
    }
}
