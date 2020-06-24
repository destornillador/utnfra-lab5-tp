package com.example.jjoo_argentinian_athletes.model;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Athlete implements Comparable{
    String fullName;
    String profilePhotoURL;
    String profilePhotoLocalFilePath;
    Map<String, String> socialNetworks;
    String sport;
    List<String> sportEvents;
    List<String> olympicGamesAttend;

    public Athlete(String fullName, String profilePhotoURL, Map<String, String> socialNetworks,
                   String sport, List<String> sportEvents, List<String> olympicGamesAttend) {
        this.fullName = fullName;
        this.profilePhotoURL = profilePhotoURL;
        this.socialNetworks = socialNetworks;
        this.sport = sport;
        this.sportEvents = sportEvents;
        this.olympicGamesAttend = olympicGamesAttend;
        this.profilePhotoLocalFilePath = null;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getProfilePhotoURL() {
        return profilePhotoURL;
    }

    public void setProfilePhotoURL(String profilePhotoURL) {
        this.profilePhotoURL = profilePhotoURL;
    }

    public String getProfilePhotoLocalFilePath() {
        return profilePhotoLocalFilePath;
    }

    public void setProfilePhotoLocalFilePath(String profilePhotoLocalFilePath) {
        this.profilePhotoLocalFilePath = profilePhotoLocalFilePath;
    }

    public Map<String, String> getSocialNetworks() {
        return socialNetworks;
    }

    public void setSocialNetworks(Map<String, String> socialNetworks) {
        this.socialNetworks = socialNetworks;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public List<String> getSportEvents() {
        return sportEvents;
    }

    public void setSportEvents(List<String> sportEvents) {
        this.sportEvents = sportEvents;
    }

    public List<String> getOlympicGamesAttend() {
        return olympicGamesAttend;
    }

    public void setOlympicGamesAttend(List<String> olympicGamesAttend) {
        this.olympicGamesAttend = olympicGamesAttend;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Athlete athlete = (Athlete) o;
        return Objects.equals(fullName, athlete.fullName) &&
                Objects.equals(profilePhotoURL, athlete.profilePhotoURL) &&
                Objects.equals(socialNetworks, athlete.socialNetworks) &&
                Objects.equals(sport, athlete.sport) &&
                Objects.equals(sportEvents, athlete.sportEvents) &&
                Objects.equals(olympicGamesAttend, athlete.olympicGamesAttend);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullName, profilePhotoURL, socialNetworks, sport, sportEvents, olympicGamesAttend);
    }

    @Override
    public int compareTo(Object o) {
        return this.getFullName().compareTo(((Athlete) o).getFullName());
    }
}
