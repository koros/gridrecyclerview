package com.korosmatick.sampleapp.model;

import java.util.Objects;
import java.util.Set;

public class Movie {
    private String name;
    private Studio studio;
    private String cover;
    private int awards;
    private Set<Actor> actors;
    private Director director;
    private Genre genre;

    public Movie(String name, Studio studio, int awards, Genre genre, String cover) {
        this.name = name;
        this.studio = studio;
        this.genre = genre;
        this.awards = awards;
        this.cover = cover;
    }

    public Movie(String name, String cover) {
        this.name = name;
        this.cover = cover;
    }

    public Movie(String name, Studio studio, String cover, int awards, Set<Actor> actors, Director director, Genre genre) {
        this.name = name;
        this.studio = studio;
        this.cover = cover;
        this.awards = awards;
        this.actors = actors;
        this.director = director;
        this.genre = genre;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Studio getStudio() {
        return studio;
    }

    public void setStudio(Studio studio) {
        this.studio = studio;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public int getAwards() {
        return awards;
    }

    public void setAwards(int awards) {
        this.awards = awards;
    }

    public Set<Actor> getActors() {
        return actors;
    }

    public void setActors(Set<Actor> actors) {
        this.actors = actors;
    }

    public Director getDirector() {
        return director;
    }

    public void setDirector(Director director) {
        this.director = director;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Movie)) return false;
        Movie movie = (Movie) o;
        return awards == movie.awards && Objects.equals(name, movie.name) && Objects.equals(studio, movie.studio) && Objects.equals(cover, movie.cover) && Objects.equals(actors, movie.actors) && Objects.equals(director, movie.director) && Objects.equals(genre, movie.genre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, studio, cover, awards, actors, director, genre);
    }
}
