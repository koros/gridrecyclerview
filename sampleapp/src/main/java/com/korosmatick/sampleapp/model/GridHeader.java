package com.korosmatick.sampleapp.model;

import com.korosmatick.sampleapp.util.HeaderKey;

import java.util.Objects;

public class GridHeader {
    private String header;
    private String subHeader;
    private HeaderKey key;

    public GridHeader(String header, HeaderKey key) {
        this.header = header;
        this.key = key;
    }

    public GridHeader(String header, String subHeader, HeaderKey key) {
        this.header = header;
        this.subHeader = subHeader;
        this.key = key;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getSubHeader() {
        return subHeader;
    }

    public void setSubHeader(String subHeader) {
        this.subHeader = subHeader;
    }

    public HeaderKey getKey() {
        return key;
    }

    public void setKey(HeaderKey key) {
        this.key = key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GridHeader)) return false;
        GridHeader that = (GridHeader) o;
        return Objects.equals(header, that.header) && Objects.equals(subHeader, that.subHeader);
    }

    @Override
    public int hashCode() {
        return Objects.hash(header, subHeader);
    }

    @Override
    public String toString() {
        return "GridHeader{" +
                "header='" + header + '\'' +
                '}';
    }
}
