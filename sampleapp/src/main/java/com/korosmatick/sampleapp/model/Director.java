package com.korosmatick.sampleapp.model;

import java.util.Objects;

public class Director {
    private String name;
    String image;
    private int rating;

    public Director(String name, String image, int rating) {
        this.name = name;
        this.image = image;
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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Director)) return false;
        Director director = (Director) o;
        return rating == director.rating && Objects.equals(name, director.name) && Objects.equals(image, director.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, image, rating);
    }
}
