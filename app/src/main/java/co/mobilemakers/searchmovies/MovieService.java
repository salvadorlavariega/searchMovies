package co.mobilemakers.searchmovies;

import android.util.Base64;

import com.squareup.okhttp.OkHttpClient;

import java.util.List;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by salvador on 13/02/15.
 */
public class MovieService {
    final static String MOVIE_API_URL = "http://www.omdbapi.com";
    final static String ACCEPTED_DATA = "application/json";
    final static String MOVIES_FOR_TITLE = "";

    final static String EMPTY_ENPOIT="/";



    public interface ApiInterface {

        @GET(EMPTY_ENPOIT)
        void getMovieForTitle(@Query("t")String title, Callback<Movie> callback);

    }

    public MovieService() {

    }

    public ApiInterface generateServiceInterface() {
        RestAdapter.Builder builder = new RestAdapter.Builder();
        builder.setEndpoint(MOVIE_API_URL)
                .setClient(new OkClient(new OkHttpClient()))
              .setRequestInterceptor(new RequestInterceptor() {
                  @Override
                  public void intercept(RequestFacade request) {

                      request.addHeader("Accept", ACCEPTED_DATA);
                  }
              });
        RestAdapter restAdapter = builder.build();

        return restAdapter.create(ApiInterface.class);
    }

}
