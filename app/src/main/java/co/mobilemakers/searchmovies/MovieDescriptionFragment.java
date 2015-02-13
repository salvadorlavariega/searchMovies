package co.mobilemakers.searchmovies;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import retrofit.RetrofitError;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieDescriptionFragment extends Fragment {

    private static final String LOG_TAG = MovieDescriptionFragment.class.getSimpleName();

    TextView textViewTitle ;
    TextView textViewyYear;
    TextView textViewGenre;
    TextView textViewDirector;
    TextView textViewActors;
    TextView textViewPlot;
    WebView imageViewPoster;
    String titleMovie;

    private MovieService.ApiInterface mMovieService;

    public MovieDescriptionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        MovieService movieService = new MovieService();
        mMovieService = movieService.generateServiceInterface();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View rootView = inflater.inflate(R.layout.item_movie_description,container,false);
        wireUp(rootView);

        initMovieDescription();
        return rootView;
    }

    private void initMovieDescription() {
        if(getArguments()!=null && getArguments().containsKey(SearchMoviesFragment.KEY_TITLE_MOVIE)){
            titleMovie = getArguments().getString(SearchMoviesFragment.KEY_TITLE_MOVIE);
            getMoviesByTitle(titleMovie);
        }
    }

    private void wireUp(View rootView) {
        textViewTitle = (TextView)rootView.findViewById(R.id.text_view_title_desc);
        textViewyYear = (TextView)rootView.findViewById(R.id.text_view_year_desc);
        textViewGenre = (TextView)rootView.findViewById(R.id.text_view_genre_desc);
        textViewDirector = (TextView)rootView.findViewById(R.id.text_view_director_desc);
        textViewActors = (TextView)rootView.findViewById(R.id.text_view_actors_desc);
        textViewPlot = (TextView)rootView.findViewById(R.id.text_view_plot_desc);
        imageViewPoster = (WebView)rootView.findViewById(R.id.web_view_poster);
    }


    private void getMoviesByTitle(String titleMovie ) {
        mMovieService.getMovieForTitle(titleMovie, new retrofit.Callback<Movie>() {
            @Override
            public void success(Movie movie, retrofit.client.Response response) {
                if (response.getStatus() == 200) {
                    textViewTitle.setText(movie.getName());
                    textViewyYear.setText(movie.getYear());
                    textViewGenre.setText(movie.getGenre());
                    textViewDirector.setText(movie.getDirector());
                    textViewActors.setText(movie.getActors());
                    textViewPlot.setText(movie.getDescription());
                    imageViewPoster.loadDataWithBaseURL("","<img src='"+movie.getUriPicture()+"'/>","text/html", "UTF-8", "");
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

    public Bitmap getBitmapFromURL(String src) {
        try {
            java.net.URL url = new java.net.URL(src);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);

            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;

        }
    }


}
