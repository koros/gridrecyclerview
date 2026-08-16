package com.github.koros.sampleapp.model;

import com.github.koros.sampleapp.util.HeaderKey;

import java.util.Objects;

/**
 * Header model used as the key for each sample grid section.
 */
public class GridHeader {
    private String header;
    private String subHeader;
    private HeaderKey key;

    /**
     * Creates a header without supporting text.
     *
     * @param header Display title for the section.
     * @param key    Semantic key that identifies the section's item type.
     */
    public GridHeader(String header, HeaderKey key) {
        this.header = header;
        this.key = key;
    }

    /**
     * Creates a header with supporting text.
     *
     * @param header    Display title for the section.
     * @param subHeader Optional supporting text displayed under the title.
     * @param key       Semantic key that identifies the section's item type.
     */
    public GridHeader(String header, String subHeader, HeaderKey key) {
        this.header = header;
        this.subHeader = subHeader;
        this.key = key;
    }

    /**
     * Returns the section title.
     *
     * @return The header title.
     */
    public String getHeader() {
        return header;
    }

    /**
     * Updates the section title.
     *
     * @param header The header title.
     */
    public void setHeader(String header) {
        this.header = header;
    }

    /**
     * Returns optional supporting text for the section.
     *
     * @return The subheader text, or null when absent.
     */
    public String getSubHeader() {
        return subHeader;
    }

    /**
     * Updates optional supporting text for the section.
     *
     * @param subHeader The subheader text.
     */
    public void setSubHeader(String subHeader) {
        this.subHeader = subHeader;
    }

    /**
     * Returns the semantic key for the section.
     *
     * @return The header key.
     */
    public HeaderKey getKey() {
        return key;
    }

    /**
     * Updates the semantic key for the section.
     *
     * @param key The header key.
     */
    public void setKey(HeaderKey key) {
        this.key = key;
    }

    /**
     * Compares headers by visible title text.
     *
     * @param o The object to compare with this header.
     * @return True when the title and subheader match.
     */
    @Override
    public boolean equals(Object o) {
        // The key is intentionally excluded because the sample uses visible text as map identity.
        if (this == o) return true;
        if (!(o instanceof GridHeader)) return false;
        GridHeader that = (GridHeader) o;
        return Objects.equals(header, that.header) && Objects.equals(subHeader, that.subHeader);
    }

    /**
     * Generates a hash code from the visible title fields.
     *
     * @return The header hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(header, subHeader);
    }

    /**
     * Returns a concise debug string for logs and failed assertions.
     *
     * @return A string containing the visible section title.
     */
    @Override
    public String toString() {
        return "GridHeader{" +
                "header='" + header + '\'' +
                '}';
    }
}
