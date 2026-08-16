package com.github.koros.sampleapp.model;

import java.util.Objects;

/**
 * Sample studio model used by richer movie data examples.
 */
public class Studio {
    private String name;
    private String image;
    private int awards;

    /**
     * Creates a studio with display metadata.
     *
     * @param name   Display name.
     * @param image  Drawable resource name for the studio image.
     * @param awards Award count shown by richer samples.
     */
    public Studio(String name, String image, int awards) {
        this.name = name;
        this.image = image;
        this.awards = awards;
    }

    /**
     * Returns the studio display name.
     *
     * @return The studio name.
     */
    public String getName() {
        return name;
    }

    /**
     * Updates the studio display name.
     *
     * @param name The studio name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the drawable resource name used for the studio image.
     *
     * @return The studio image resource name.
     */
    public String getImage() {
        return image;
    }

    /**
     * Updates the drawable resource name used for the studio image.
     *
     * @param image The studio image resource name.
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * Returns the studio award count.
     *
     * @return The award count.
     */
    public int getAwards() {
        return awards;
    }

    /**
     * Updates the studio award count.
     *
     * @param awards The award count.
     */
    public void setAwards(int awards) {
        this.awards = awards;
    }

    /**
     * Compares studios by their sample data fields.
     *
     * @param o The object to compare with this studio.
     * @return True when all studio fields match.
     */
    @Override
    public boolean equals(Object o) {
        // Reference equality avoids unnecessary field checks for the same object.
        if (this == o) return true;
        if (!(o instanceof Studio)) return false;
        Studio studio = (Studio) o;
        return awards == studio.awards && Objects.equals(name, studio.name) && Objects.equals(image, studio.image);
    }

    /**
     * Generates a hash code from the fields used by {@link #equals(Object)}.
     *
     * @return The studio hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, image, awards);
    }
}
