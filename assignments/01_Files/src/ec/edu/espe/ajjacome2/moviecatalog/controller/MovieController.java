package ec.edu.espe.ajjacome2.moviecatalog.controller;

import ec.edu.espe.ajjacome2.moviecatalog.menu.ConsoleMenu;
import ec.edu.espe.ajjacome2.moviecatalog.model.Movie;
import ec.edu.espe.ajjacome2.moviecatalog.service.MovieService;
import ec.edu.espe.ajjacome2.moviecatalog.view.AddMovieForm;
import ec.edu.espe.ajjacome2.moviecatalog.view.MovieView;
import java.awt.Dialog;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 *
 * @author jon_m
 */
public class MovieController {
    private MovieService movieService = new MovieService();
    private AtomicBoolean awaitingForm = new AtomicBoolean(true);
    
    /**
     * Add a custom movie using a visual form
     */
    public void addCustomMovie() {
        AddMovieForm form = new AddMovieForm();
        
        form.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        
        form.addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent event) {
                awaitingForm.set(false);
            }
        });
        
        form.onSubmit((Movie movie) -> {
            saveMovie(movie);
            form.dispose();
            awaitingForm.set(false);
            System.out.println("movie saved!");
        });
        
        System.out.println("waiting for form submittion...");        
        form.setVisible(true);
        awaitingForm.set(true);
        
        while (awaitingForm.get());
    }
    
    /**
     * Listamos todas las peliculas guardadas
     */
    public void listAllMovies(boolean fromBinary) {
        List<Movie> movies = fromBinary
            ? readMoviesFromBinary()
            : readMoviesFromPlain();
        
        if (movies.isEmpty()) {
            System.out.println("records not found...");
        } else {
            System.out.printf("\nTotal records: %d\n\n", movies.size());
        }
        
        for (Movie movie : movies) {
            MovieView view = new MovieView(movie);
            view.display();
            System.out.println();
        }
    }
    
    public void listAllMovies() {
        listAllMovies(false);
    }
    
    /**
     * Buscamos una pelicula dado un termino ingresado por el usuario utilizando
     * el API REST de TMDB
     */
    public void searchAndAddMovies() {
        Scanner scanner = ConsoleMenu.getScanner();
        
        System.out.print("add a search term or put ':q' to cancel: ");
        String query = scanner.nextLine();
        
        if (query.isEmpty() || query.trim().equalsIgnoreCase(":q")) {
            return;
        }
        
        System.out.printf("Searching '%s'...\n", query);
        
        List<Movie> movies = movieService.searchOnline(query);
        
        if (movies.size() > 10) {
            movies = movies.subList(0, 11);
        }
        
        if (movies.size() == 0) {
            System.out.println("No records found...");
            return;
        }
        
        int position = 1;
        boolean invalid = false;
        int choosed;
        
        for (Movie movie : movies) {
            MovieView view = new MovieView(movie);
            view.display(position + ": ${title}\n");
            position++;
        }
        
        System.out.println();
        System.out.println();
        
        do {
            System.out.print((invalid 
                ? "Please enter a valid option: " 
                : "Choose the number of movie list or put '-1' to cancel: "));
            choosed = scanner.nextInt();
            scanner.nextLine();
            invalid = choosed != -1 && (choosed < 1 || choosed > movies.size());
        } while (invalid);
        
        if (choosed != -1) {
            try {
                Movie movie = movies.get(choosed - 1);
                saveMovie(movie);
                System.out.println("'" + movie.getTitle() + "' saved!");
            } catch (Exception exception) {
                System.err.println("An error ocurred while attempt to save a record");
                System.err.println(exception.getMessage());
            }
        }
    }
    
    private List<Movie> readMoviesFromPlain() {
        ArrayList<Movie> movies = new ArrayList<>();
        File txt = new File("database.txt");
        
        if (!txt.exists() || !txt.canRead()) {
            return movies;
        }
        
        try {
            Scanner scanner = new Scanner(txt);
            
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String tokens[] = line.split(";");
                
                if (tokens.length < 4) {
                    continue;
                }
                
                Date formattedDate = new Date();
                
                try {
                    formattedDate = 
                            new SimpleDateFormat("yyyy-MM-dd").parse(tokens[3]);
                } catch (Exception exception) {}
                
                Movie movie = new Movie(
                    Integer.parseInt(tokens[0]),
                    tokens[1],
                    tokens[2],
                    formattedDate
                );
                
                movies.add(movie);
            }
        } catch (FileNotFoundException exception) {}
        
        return movies;
    }
    
    private List<Movie> readMoviesFromBinary() {
        ArrayList<Movie> movies = new ArrayList<>();
        
        try {
            FileInputStream fileEntry = new FileInputStream("database.bin");
            ObjectInputStream tuberiaEntry = new ObjectInputStream(fileEntry);
            movies = (ArrayList<Movie>) tuberiaEntry.readObject();
        } catch (Exception exception) {}
        
        return movies;
    }
    
    /**
     * Guardamos una instancia de la clase Movie en ficheros
     * 
     * @param movie objeto a ser guardado
     */
    private void saveMovie(Movie movie) {
        File txt = new File("database.txt");
        File binary = new File("database.bin");
        int lastInsertId = readMoviesFromPlain().size() + 1;
        
        movie.setId(lastInsertId);
        
        try {
            if (!txt.exists()) {
                txt.createNewFile();
            }

            if (!binary.exists()) {
                binary.createNewFile();
            }

            // write to txt format file
            PrintWriter printer = new PrintWriter(new FileWriter(txt, true));
            printer.println(movie);
            printer.close();

            // write to binary format file
            List<Movie> movies = readMoviesFromPlain();
            movies.add(movie);

            FileOutputStream fileStream = new FileOutputStream(binary, false);
            ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);
            objectStream.writeObject(movies);
            fileStream.close();
            objectStream.close();
        } catch (Exception exception) {
            // log error
        }
    }
}
