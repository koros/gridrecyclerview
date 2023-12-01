package com.korosmatick.sampleapp.model;

import java.util.Objects;

public class Studio {
    private String name;
    private String image;
    private int awards;

    public Studio(String name, String image, int awards) {
        this.name = name;
        this.image = image;
        this.awards = awards;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Studio)) return false;
        Studio studio = (Studio) o;
        return awards == studio.awards && Objects.equals(name, studio.name) && Objects.equals(image, studio.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, image, awards);
    }
}
