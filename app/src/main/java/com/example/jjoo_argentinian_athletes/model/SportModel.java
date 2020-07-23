package com.example.jjoo_argentinian_athletes.model;

import java.util.Objects;

public class SportModel implements Comparable {
    private String name;
    private String logoUrl;
    private String logoLocalFilePath = null;

    public static SportModel EMPTY = new SportModel("EMPTY", "EMPTY", "EMPTY");


    public SportModel(String name, String logoUrl) {
        this.name = name;
        this.logoUrl = logoUrl;
    }

    public SportModel(String name, String logoUrl, String logoLocalFilePath) {
        this.name = name;
        this.logoUrl = logoUrl;
        this.logoLocalFilePath = logoLocalFilePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getLogoLocalFilePath() {
        return logoLocalFilePath;
    }

    public void setLogoLocalFilePath(String logoLocalFilePath) {
        this.logoLocalFilePath = logoLocalFilePath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SportModel sport = (SportModel) o;
        return Objects.equals(name, sport.name) &&
                Objects.equals(logoUrl, sport.logoUrl) &&
                Objects.equals(logoLocalFilePath, sport.logoLocalFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, logoUrl, logoLocalFilePath);
    }

    @Override
    public int compareTo(Object o) {
        return this.getName().compareTo(((SportModel) o).getName());
    }
}
