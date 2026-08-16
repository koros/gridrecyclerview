package com.github.koros.sampleapp.model;

import java.util.Objects;

/**
 * Sample actor model used by the demo's actor grid section.
 */
public class Actor {
    private String name;
    private String image;
    private int awards;
    private int rating;

    /**
     * Creates an actor with the minimum data needed by the sample UI.
     *
     * @param name  Display name.
     * @param image Drawable resource name for the actor image.
     */
    public Actor(String name, String image) {
        this.name = name;
        this.image = image;
    }

    /**
     * Creates an actor with display and ranking metadata.
     *
     * @param name   Display name.
     * @param image  Drawable resource name for the actor image.
     * @param awards Award count shown by richer samples.
     * @param rating Rating score shown by richer samples.
     */
    public Actor(String name, String image, int awards, int rating) {
        this.name = name;
        this.image = image;
        this.awards = awards;
        this.rating = rating;
    }

    /**
     * Returns the actor display name.
     *
     * @return The actor name.
     */
    public String getName() {
        return name;
    }

    /**
     * Updates the actor display name.
     *
     * @param name The actor name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the drawable resource name used for the actor image.
     *
     * @return The actor image resource name.
     */
    public String getImage() {
        return image;
    }

    /**
     * Updates the drawable resource name used for the actor image.
     *
     * @param image The actor image resource name.
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * Returns the actor award count.
     *
     * @return The award count.
     */
    public int getAwards() {
        return awards;
    }

    /**
     * Updates the actor award count.
     *
     * @param awards The award count.
     */
    public void setAwards(int awards) {
        this.awards = awards;
    }

    /**
     * Returns the actor rating.
     *
     * @return The rating score.
     */
    public int getRating() {
        return rating;
    }

    /**
     * Updates the actor rating.
     *
     * @param rating The rating score.
     */
    public void setRating(int rating) {
        this.rating = rating;
    }

    /**
     * Compares actors by their sample data fields.
     *
     * @param o The object to compare with this actor.
     * @return True when all actor fields match.
     */
    @Override
    public boolean equals(Object o) {
        // Reference equality is a common fast path for sample list comparisons.
        if (this == o) return true;
        if (!(o instanceof Actor)) return false;
        Actor actor = (Actor) o;
        return awards == actor.awards && rating == actor.rating && Objects.equals(name, actor.name) && Objects.equals(image, actor.image);
    }

    /**
     * Generates a hash code from the fields used by {@link #equals(Object)}.
     *
     * @return The actor hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, image, awards, rating);
    }
}
