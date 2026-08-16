package com.github.koros.sampleapp.model;

import java.util.Objects;
import java.util.Set;

/**
 * Sample movie model used by the demo's movie grid section.
 */
public class Movie {
    private String name;
    private Studio studio;
    private String cover;
    private int awards;
    private Set<Actor> actors;
    private Director director;
    private Genre genre;

    /**
     * Creates a movie with core catalogue metadata.
     *
     * @param name   Display title.
     * @param studio Producing studio.
     * @param awards Award count shown by richer samples.
     * @param genre  Movie genre.
     * @param cover  Drawable resource name for the cover image.
     */
    public Movie(String name, Studio studio, int awards, Genre genre, String cover) {
        this.name = name;
        this.studio = studio;
        this.genre = genre;
        this.awards = awards;
        this.cover = cover;
    }

    /**
     * Creates a movie with the minimum data needed by the sample UI.
     *
     * @param name  Display title.
     * @param cover Drawable resource name for the cover image.
     */
    public Movie(String name, String cover) {
        this.name = name;
        this.cover = cover;
    }

    /**
     * Creates a movie with the full set of sample metadata.
     *
     * @param name     Display title.
     * @param studio   Producing studio.
     * @param cover    Drawable resource name for the cover image.
     * @param awards   Award count shown by richer samples.
     * @param actors   Cast members.
     * @param director Director metadata.
     * @param genre    Movie genre.
     */
    public Movie(String name, Studio studio, String cover, int awards, Set<Actor> actors, Director director, Genre genre) {
        this.name = name;
        this.studio = studio;
        this.cover = cover;
        this.awards = awards;
        this.actors = actors;
        this.director = director;
        this.genre = genre;
    }

    /**
     * Returns the movie display title.
     *
     * @return The movie title.
     */
    public String getName() {
        return name;
    }

    /**
     * Updates the movie display title.
     *
     * @param name The movie title.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the producing studio.
     *
     * @return The studio metadata.
     */
    public Studio getStudio() {
        return studio;
    }

    /**
     * Updates the producing studio.
     *
     * @param studio The studio metadata.
     */
    public void setStudio(Studio studio) {
        this.studio = studio;
    }

    /**
     * Returns the drawable resource name used for the cover.
     *
     * @return The cover resource name.
     */
    public String getCover() {
        return cover;
    }

    /**
     * Updates the drawable resource name used for the cover.
     *
     * @param cover The cover resource name.
     */
    public void setCover(String cover) {
        this.cover = cover;
    }

    /**
     * Returns the movie award count.
     *
     * @return The award count.
     */
    public int getAwards() {
        return awards;
    }

    /**
     * Updates the movie award count.
     *
     * @param awards The award count.
     */
    public void setAwards(int awards) {
        this.awards = awards;
    }

    /**
     * Returns the cast members.
     *
     * @return The actor set.
     */
    public Set<Actor> getActors() {
        return actors;
    }

    /**
     * Updates the cast members.
     *
     * @param actors The actor set.
     */
    public void setActors(Set<Actor> actors) {
        this.actors = actors;
    }

    /**
     * Returns the director metadata.
     *
     * @return The director.
     */
    public Director getDirector() {
        return director;
    }

    /**
     * Updates the director metadata.
     *
     * @param director The director.
     */
    public void setDirector(Director director) {
        this.director = director;
    }

    /**
     * Returns the movie genre.
     *
     * @return The genre metadata.
     */
    public Genre getGenre() {
        return genre;
    }

    /**
     * Updates the movie genre.
     *
     * @param genre The genre metadata.
     */
    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    /**
     * Compares movies by their sample data fields.
     *
     * @param o The object to compare with this movie.
     * @return True when all movie fields match.
     */
    @Override
    public boolean equals(Object o) {
        // Reference equality keeps comparisons cheap when lists reuse model instances.
        if (this == o) return true;
        if (!(o instanceof Movie)) return false;
        Movie movie = (Movie) o;
        return awards == movie.awards && Objects.equals(name, movie.name) && Objects.equals(studio, movie.studio) && Objects.equals(cover, movie.cover) && Objects.equals(actors, movie.actors) && Objects.equals(director, movie.director) && Objects.equals(genre, movie.genre);
    }

    /**
     * Generates a hash code from the fields used by {@link #equals(Object)}.
     *
     * @return The movie hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, studio, cover, awards, actors, director, genre);
    }
}
