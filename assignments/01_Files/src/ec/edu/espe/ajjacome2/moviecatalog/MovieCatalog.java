package ec.edu.espe.ajjacome2.moviecatalog;

import ec.edu.espe.ajjacome2.moviecatalog.controller.MovieController;
import ec.edu.espe.ajjacome2.moviecatalog.menu.ConsoleMenu;
import ec.edu.espe.ajjacome2.moviecatalog.menu.ConsoleMenuOption;

/**
 * 
 * @author jon_m
 */
public class MovieCatalog {
    /**
     * @param args command line arguments
     */
    public static void main(String[] args) {        
        ConsoleMenu menu = new ConsoleMenu("Movie catalog");
        MovieController movieController = new MovieController();
        
        // add list movies option
        menu.addOption(new ConsoleMenuOption(
            "list all registered movies",
            movieController::listAllMovies
        ));
        
        // add search option
        menu.addOption(new ConsoleMenuOption(
            "Search/add a movie online",
            movieController::searchAndAddMovies
        ));
        
        // add search option
        menu.addOption(new ConsoleMenuOption(
            "Add a custom movie",
            movieController::addCustomMovie
        ));
        
        // add exit option
        menu.addOption(new ConsoleMenuOption("Exit", menu::exit, false));
        
        // display menu showing all options
        menu.display();
    }
}
