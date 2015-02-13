package co.mobilemakers.searchmovies;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

import javax.security.auth.callback.Callback;

import retrofit.RetrofitError;

/**
 * A placeholder fragment containing a simple view.
 */
public class SearchMoviesFragment extends ListFragment {

    private static final String LOG_TAG = SearchMoviesFragment.class.getSimpleName();

    private static final Boolean NO_CLEAR_LIST=false;
    private static final Boolean CLEAR_LIST=true;
    public static String KEY_TITLE_MOVIE ="key_title_movie";
    String titleMovie;

    EditText editTextSearchMovie;
    SearchMoviesAdapter adapter;
    private MovieService.ApiInterface mMovieService;


    public SearchMoviesFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        MovieService movieService = new MovieService();
        mMovieService = movieService.generateServiceInterface();
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.clear();
        getMoviesByTitle("pulp fiction",NO_CLEAR_LIST);
        getMoviesByTitle("scarface",NO_CLEAR_LIST);
        getMoviesByTitle("shrek",NO_CLEAR_LIST);
        getMoviesByTitle("the Godfather",NO_CLEAR_LIST);
        getMoviesByTitle("great",NO_CLEAR_LIST);
        getMoviesByTitle("wolf",NO_CLEAR_LIST);


    }

    private void getMoviesByTitle(String titleMovie, final Boolean clear) {
        mMovieService.getMovieForTitle(titleMovie, new retrofit.Callback<Movie>() {
            @Override
            public void success(Movie movie, retrofit.client.Response response) {
                if (response.getStatus() == 200) {

                if(clear)adapter.clear();
                adapter.add(movie);
                adapter.notifyDataSetChanged();
                } else {
                    Log.e(LOG_TAG, "Project retrieval status problem: " + response.getReason());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.w(LOG_TAG, "ERROR: downloading " + error.getBody()+",URL= "+error.getUrl());
                error.printStackTrace();

            }
        });
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
       /* fetchMoviesInQueue("Pulp Fiction",NO_CLEAR_LIST);
        fetchMoviesInQueue("el Padrino",NO_CLEAR_LIST);
        fetchMoviesInQueue("scarface",NO_CLEAR_LIST);
        fetchMoviesInQueue("titanic",NO_CLEAR_LIST);
        fetchMoviesInQueue("shrek",NO_CLEAR_LIST);*/

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Movie movie = (Movie)adapter.getItem(position);
                String movietitle = movie.getName();
                Intent intent = new Intent(getActivity(), MovieDescriptionActivity.class);
                intent.putExtra(KEY_TITLE_MOVIE, movietitle);
                startActivity(intent);



            }
        });
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
                getMoviesByTitle(movieTitle,CLEAR_LIST);
            }
        });
    }

    private void prepareListview(){
        List<Movie> listMovies = new ArrayList<>();
        adapter = new SearchMoviesAdapter(getActivity(),listMovies);
        setListAdapter(adapter);
    }

    /*private void  fetchMoviesInQueue(String nameMovie, final Boolean cleanList){
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
    }*/

  /*  private URL constructQuery(String nameMovie) throws MalformedURLException {
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
    }*/


  /* private List<Movie> parseResponse(String response) throws MalformedURLException {
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
    }*/
}
