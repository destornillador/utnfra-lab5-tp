package com.example.jjoo_argentinian_athletes.model;

import java.util.Arrays;
import java.util.Objects;

public class Sport {
    private String name;
    private String logoURL;
    private byte[] logoBinary;

    public Sport(String name, String logoURL) {
        this.name = name;
        this.logoURL = logoURL;
        this.logoBinary = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogoURL() {
        return logoURL;
    }

    public void setLogoURL(String logoURL) {
        this.logoURL = logoURL;
    }

    public byte[] getLogoBinary() {
        return logoBinary;
    }

    public void setLogoBinary(byte[] logoBinary) {
        this.logoBinary = logoBinary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sport sport = (Sport) o;
        return Objects.equals(name, sport.name) &&
                Objects.equals(logoURL, sport.logoURL) &&
                Arrays.equals(logoBinary, sport.logoBinary);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name, logoURL);
        result = 31 * result + Arrays.hashCode(logoBinary);
        return result;
    }
}
