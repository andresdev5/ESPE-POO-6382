package ec.edu.espe.ajjacome2.moviecatalog.view;

import ec.edu.espe.ajjacome2.moviecatalog.common.BaseView;
import ec.edu.espe.ajjacome2.moviecatalog.model.Movie;
import java.text.SimpleDateFormat;

/**
 *
 * @author jon_m
 */
public class MovieView extends BaseView<Movie> {
    public MovieView(Movie context) {
        super(context);
    }
    
    @Override
    protected void init() {
        this.data.put("id", context.getId());
        this.data.put("title", context.getTitle());
        this.data.put("overview", context.getOverview());
        
        if (context.getReleaseDate() != null) {
            String formattedDate = new SimpleDateFormat("yyyy-MM-dd")
                    .format(context.getReleaseDate());
            this.data.put("releaseDate", formattedDate);
        } else {
            this.data.put("releaseDate", "unknown");
        }
    }

    @Override
    protected String getTemplate() {
        return
            "-------------------------------------------------\n" +
            "title: ${title}\n" +
            "overview: ${overview}\n" +
            "release date: ${releaseDate}\n" +
            "-------------------------------------------------";
    }
}
