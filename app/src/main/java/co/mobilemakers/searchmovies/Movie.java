package co.mobilemakers.searchmovies;

import android.net.Uri;

import java.net.URL;

/**
 * Created by salvador on 12/02/15.
 */
public class Movie {

    private String name;
    private String description;
    private String actors;
    private String uriPicture;

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
