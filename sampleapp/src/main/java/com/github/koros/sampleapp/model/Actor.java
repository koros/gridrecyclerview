package com.github.koros.sampleapp.model;

import java.util.Objects;

public class Actor {
    private String name;
    private String image;
    private int awards;
    private int rating;

    public Actor(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public Actor(String name, String image, int awards, int rating) {
        this.name = name;
        this.image = image;
        this.awards = awards;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getAwards() {
        return awards;
    }

    public void setAwards(int awards) {
        this.awards = awards;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Actor)) return false;
        Actor actor = (Actor) o;
        return awards == actor.awards && rating == actor.rating && Objects.equals(name, actor.name) && Objects.equals(image, actor.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, image, awards, rating);
    }
}
