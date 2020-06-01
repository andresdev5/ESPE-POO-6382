package ec.edu.espe.ajjacome2.moviecatalog.service;

import ec.edu.espe.ajjacome2.moviecatalog.model.Movie;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * Simple service to retrieve movie data
 * 
 * @author jon_m
 */
public class MovieService {
    private final String TMDB_API_KEY = "1712a91425f337218d2d328bd4e2b8ee";
    
    /**
     * Search and retrieve movies online using TMDB api
     * 
     * @see https://www.themoviedb.org/
     * @param query
     * @return 
     */
    public List<Movie> searchOnline(String query) {
        ArrayList<Movie> results = new ArrayList<>();
        String jsonText = new String();
        
        try {
            String encodedQuery = URLEncoder
                    .encode(query, StandardCharsets.UTF_8.toString());
            jsonText = getJsonFromUrl(String.format(
                "https://api.themoviedb.org/3/search/movie?api_key=%s"
                + "&language=es-ES&query=%s&page=1&include_adult=false",
                TMDB_API_KEY, encodedQuery
            ));
        } catch (Exception exception) {}
        
        if (jsonText.isEmpty()) {
            return results;
        }
        
        JsonObject jsonObject = JsonParser
                .parseString(jsonText).getAsJsonObject();
        
        // "results" key not found
        if (!jsonObject.has("results")) {
            return results;
        }
        
        JsonArray resultsObject = jsonObject.get("results").getAsJsonArray();
        
        for(JsonElement element : resultsObject) {
            try {
                JsonObject object = element.getAsJsonObject();
                
                int id = object.get("id").getAsInt();                
                String title = object.get("title").getAsString();                
                String overview = object.get("overview").getAsString();
                Date releaseDate = null;
                
                try {
                    String dateString = object.get("release_date").getAsString();
                    releaseDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
                } catch (Exception exception) {}
                
                
                Movie movie = new Movie(title, overview, releaseDate);
                movie.setTmdbId(id);
                results.add(movie);
            } catch (Exception exception) {
                System.err.println(exception.getMessage());
            }
        }
        
        return results;
    }
    
    private String getJsonFromUrl(String url) {
        String jsonText = new String();
        
        try {
            URL urlObject = new URL(url);
            
            InputStream stream = urlObject.openStream();
            Scanner s = new Scanner(stream).useDelimiter("\\A");
            jsonText = s.hasNext() ? s.next() : "";
        } catch (Exception exception) {
            return "";
        }
        
        return jsonText;
    }
}
