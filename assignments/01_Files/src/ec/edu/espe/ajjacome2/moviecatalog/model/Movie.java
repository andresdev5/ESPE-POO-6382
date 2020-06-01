package ec.edu.espe.ajjacome2.moviecatalog.model;

import ec.edu.espe.ajjacome2.moviecatalog.common.Model;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author jon_m
 */
public class Movie implements Model {
    private int id;
    private int tmdbId;
    private String title;
    private String overview;
    private Date releaseDate;
    
    public Movie(int id, String title, String overview, Date releaseDate) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.releaseDate = releaseDate;
    }
    
    public Movie(String title, String overview, Date releaseDate) {
        this.title = title;
        this.overview = overview;
        this.releaseDate = releaseDate;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getTmdbId() {
        return tmdbId;
    }

    public void setTmdbId(int tmdbId) {
        this.tmdbId = tmdbId;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }
    
    @Override
    public String toString() {
        return String.format(
            "%d;%s;%s;%s",
            id,
            title.replace(";", ","),
            overview.replace(";", ","),
            new SimpleDateFormat("dd-MM-yyyy").format(releaseDate)
        );
    }
}
