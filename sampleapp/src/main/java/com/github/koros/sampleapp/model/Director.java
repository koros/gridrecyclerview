package com.github.koros.sampleapp.model;

import java.util.Objects;

/**
 * Sample director model used by richer movie data examples.
 */
public class Director {
    private String name;
    String image;
    private int rating;

    /**
     * Creates a director with display metadata.
     *
     * @param name   Display name.
     * @param image  Drawable resource name for the director image.
     * @param rating Rating score shown by richer samples.
     */
    public Director(String name, String image, int rating) {
        this.name = name;
        this.image = image;
        this.rating = rating;
    }

    /**
     * Returns the director display name.
     *
     * @return The director name.
     */
    public String getName() {
        return name;
    }

    /**
     * Updates the director display name.
     *
     * @param name The director name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the drawable resource name used for the director image.
     *
     * @return The director image resource name.
     */
    public String getImage() {
        return image;
    }

    /**
     * Updates the drawable resource name used for the director image.
     *
     * @param image The director image resource name.
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * Returns the director rating.
     *
     * @return The rating score.
     */
    public int getRating() {
        return rating;
    }

    /**
     * Updates the director rating.
     *
     * @param rating The rating score.
     */
    public void setRating(int rating) {
        this.rating = rating;
    }

    /**
     * Compares directors by their sample data fields.
     *
     * @param o The object to compare with this director.
     * @return True when all director fields match.
     */
    @Override
    public boolean equals(Object o) {
        // Reference equality avoids field comparisons for the same model instance.
        if (this == o) return true;
        if (!(o instanceof Director)) return false;
        Director director = (Director) o;
        return rating == director.rating && Objects.equals(name, director.name) && Objects.equals(image, director.image);
    }

    /**
     * Generates a hash code from the fields used by {@link #equals(Object)}.
     *
     * @return The director hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, image, rating);
    }
}
