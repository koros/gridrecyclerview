package com.github.koros.sampleapp.util;

import com.github.koros.sampleapp.model.Actor;
import com.github.koros.sampleapp.model.Genre;
import com.github.koros.sampleapp.model.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Factory for sample data displayed by the demo app.
 */
public class DummyDataGenerator {

    /**
     * Builds actor items for the three-column sample section.
     *
     * @return A mutable list of sample actors.
     */
    public static List<Actor> getSampleActors() {
        List<Actor> actors = new ArrayList<>();

        // The image names are drawable resource entry names resolved by the Compose sample.
        actors.add(new Actor("Denzel Washington", "denzel2", 179, 5));
        actors.add(new Actor("Leonardo DiCaprio", "leonardo", 983, 5));
        actors.add(new Actor("Keanu Reeves", "keanu", 349, 5));
        actors.add(new Actor("Jet Li", "jet", 546, 5));
        actors.add(new Actor("Harrison Ford", "harrison_ford", 1793, 5));
        actors.add(new Actor("Christoph Waltz", "christoph", 1793, 5));
        actors.add(new Actor("Carrie-Anne Moss", "carrie", 1793, 5));
        return actors;
    }

    /**
     * Builds movie items for the two-column sample section.
     *
     * @return A mutable list of sample movies.
     */
    public static List<Movie> getSampleMovies() {
        List<Movie> movies = new ArrayList<>();

        // Keep this list short enough to show both complete and incomplete grid rows.
        movies.add(new Movie("Full Metal Jacket", "full_metal_jacket_1"));
        movies.add(new Movie("Kick Boxer", "kick_boxer"));
        movies.add(new Movie("Matrix", "matrix"));
        movies.add(new Movie("Hobbit", "hobbit"));
        movies.add(new Movie("Bait", "bait"));
        return movies;
    }

    /**
     * Builds genre items for the one-column sample section.
     *
     * @return A mutable list of sample genres.
     */
    public static List<Genre> getSampleGenres() {
        List<Genre> genres = new ArrayList<>();

        // One-column rows demonstrate that the same grid renderer handles list-like sections.
        genres.add(new Genre("Action", "action_genre"));
        genres.add(new Genre("Comedy", "comedy_genre"));
        genres.add(new Genre("Documentaries", "documentary_genre"));
        genres.add(new Genre("SciFi", "sci_fi_genre"));
        return genres;
    }
}
