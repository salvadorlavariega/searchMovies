package co.mobilemakers.searchmovies;

import android.net.Uri;

import com.google.gson.annotations.SerializedName;

import java.net.URL;

/**
 * Created by salvador on 12/02/15.
 */
public class Movie {

    @SerializedName("Title")
    private String name;

    @SerializedName("Plot")
    private String description;

    @SerializedName("Actors")
    private String actors;

    @SerializedName("Poster")
    private String uriPicture;

    @SerializedName("Director")
    private String director;

    @SerializedName("Genre")
    private String genre;

    @SerializedName("Year")
    private String year;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getUriPicture() {
        return uriPicture;
    }

    public void setUriPicture(String uriPicture) {
        this.uriPicture = uriPicture;
    }
}
