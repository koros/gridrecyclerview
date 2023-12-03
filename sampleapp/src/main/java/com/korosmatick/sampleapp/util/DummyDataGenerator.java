package com.korosmatick.sampleapp.util;

import com.korosmatick.sampleapp.model.Actor;
import com.korosmatick.sampleapp.model.Genre;
import com.korosmatick.sampleapp.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class DummyDataGenerator {

    public static List<Actor> getSampleActors() {
        List<Actor> actors = new ArrayList<>();
        actors.add(new Actor("Denzel Washington", "denzel2", 179, 5));
        actors.add(new Actor("Leonardo DiCaprio", "leonardo", 983, 5));
        actors.add(new Actor("Keanu Reeves", "keanu", 349, 5));
        actors.add(new Actor("Jet Li", "jet", 546, 5));
        actors.add(new Actor("Harrison Ford", "harrison_ford", 1793, 5));
        actors.add(new Actor("Christoph Waltz", "christoph", 1793, 5));
        actors.add(new Actor("Carrie-Anne Moss", "carrie", 1793, 5));
        return actors;
    }

    public static List<Movie> getSampleMovies() {
        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie("Full Metal Jacket", "full_metal_jacket_1"));
        movies.add(new Movie("Kick Boxer", "kick_boxer"));
        movies.add(new Movie("Matrix", "matrix"));
        movies.add(new Movie("Hobbit", "hobbit"));
        movies.add(new Movie("Bait", "bait"));
        return movies;
    }

    public static List<Genre> getSampleGenres() {
        List<Genre> genres = new ArrayList<>();
        genres.add(new Genre("Action", "action_genre"));
        genres.add(new Genre("Comedy", "comedy_genre"));
        genres.add(new Genre("Documentaries", "documentary_genre"));
        genres.add(new Genre("SciFi", "sci_fi_genre"));
        return genres;
    }
}
