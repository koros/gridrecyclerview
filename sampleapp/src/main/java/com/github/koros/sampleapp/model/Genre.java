package com.github.koros.sampleapp.model;

import java.util.Objects;

public class Genre {
    private String name;

    private String image;

    public Genre(String name, String image) {
        this.name = name;
        this.image = image;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Genre)) return false;
        Genre genre = (Genre) o;
        return Objects.equals(name, genre.name) && Objects.equals(image, genre.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, image);
    }
}
