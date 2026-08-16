package com.github.koros.sampleapp.model;

import java.util.Objects;

/**
 * Sample genre model used by the demo's one-column section.
 */
public class Genre {
    private String name;

    private String image;

    /**
     * Creates a genre with display metadata.
     *
     * @param name  Display name.
     * @param image Drawable resource name for the genre image.
     */
    public Genre(String name, String image) {
        this.name = name;
        this.image = image;
    }

    /**
     * Returns the genre display name.
     *
     * @return The genre name.
     */
    public String getName() {
        return name;
    }

    /**
     * Updates the genre display name.
     *
     * @param name The genre name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the drawable resource name used for the genre image.
     *
     * @return The genre image resource name.
     */
    public String getImage() {
        return image;
    }

    /**
     * Updates the drawable resource name used for the genre image.
     *
     * @param image The genre image resource name.
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * Compares genres by their display fields.
     *
     * @param o The object to compare with this genre.
     * @return True when the genre fields match.
     */
    @Override
    public boolean equals(Object o) {
        // Reference equality keeps repeated sample items cheap to compare.
        if (this == o) return true;
        if (!(o instanceof Genre)) return false;
        Genre genre = (Genre) o;
        return Objects.equals(name, genre.name) && Objects.equals(image, genre.image);
    }

    /**
     * Generates a hash code from the fields used by {@link #equals(Object)}.
     *
     * @return The genre hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, image);
    }
}
