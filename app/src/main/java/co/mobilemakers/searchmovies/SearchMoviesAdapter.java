package co.mobilemakers.searchmovies;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * Created by salvador on 12/02/15.
 */
public class SearchMoviesAdapter extends ArrayAdapter<Movie> {

    List<Movie> listMovies;

    public SearchMoviesAdapter(Context context, List<Movie> listMovies){
        super(context,R.layout.list_item_movies,listMovies);
        this.listMovies = listMovies;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = reuseOrgenerateView(convertView,parent);
        displayMovieInRow(position,rowView);
        return rowView;
    }

    private View reuseOrgenerateView(View convertView, ViewGroup parent){
        View rowView;
        if(convertView != null){
            rowView = convertView;
        }
        else{
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.list_item_movies,parent,false);
            ViewHolder viewHolder = new ViewHolder(rowView);
            rowView.setTag(viewHolder);

        }

        return  rowView;

    }

    private void displayMovieInRow(int position, View rowView){
        ViewHolder viewHolder = (ViewHolder) rowView.getTag();
        viewHolder.textViewNameMovie.setText(listMovies.get(position).getName());
        viewHolder.textViewMovieDescription.setText(listMovies.get(position).getDescription());

    }

    public class ViewHolder {
        public final TextView textViewNameMovie;
        public final TextView textViewMovieDescription;


        public ViewHolder (View view) {
            textViewNameMovie = (TextView) view.findViewById(R.id.text_view_name_movie);
            textViewMovieDescription = (TextView) view.findViewById(R.id.text_view_desc_movie);

        }
    }

}


