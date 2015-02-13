package co.mobilemakers.searchmovies;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by salvador on 13/02/15.
 */
public class MovieDescriptionActivity extends ActionBarActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_description);
        if (savedInstanceState == null) {
            MovieDescriptionFragment movieDescriptionFragment = new MovieDescriptionFragment();

            String titleMovie = getIntent().getStringExtra(SearchMoviesFragment.KEY_TITLE_MOVIE);
            Bundle bundle = new Bundle();
            bundle.putString(SearchMoviesFragment.KEY_TITLE_MOVIE, titleMovie);
            movieDescriptionFragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container_description, movieDescriptionFragment)
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_movies, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
