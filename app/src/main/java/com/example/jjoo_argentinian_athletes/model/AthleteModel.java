package com.example.jjoo_argentinian_athletes.model;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class AthleteModel implements Comparable {
    String fullName;
    String profilePhotoUrl;
    String profilePhotoLocalFilePath = null;
    Map<String, String> socialNetworks;
    String sport;
    List<String> sportEvents;
    List<String> olympicGamesAttend;

    public static AthleteModel EMPTY = new AthleteModel(
            "EMPTY", "EMPTY", Collections.<String, String>emptyMap(), "EMPTY",
            Collections.<String>emptyList(), Collections.<String>emptyList());

    public AthleteModel(String fullName, String profilePhotoUrl, Map<String, String> socialNetworks,
                        String sport, List<String> sportEvents, List<String> olympicGamesAttend) {
        this.fullName = fullName;
        this.profilePhotoUrl = profilePhotoUrl;
        this.socialNetworks = socialNetworks;
        this.sport = sport;
        this.sportEvents = sportEvents;
        this.olympicGamesAttend = olympicGamesAttend;
    }

    public AthleteModel(String fullName, String profilePhotoUrl, Map<String, String> socialNetworks,
                        String sport, List<String> sportEvents, List<String> olympicGamesAttend,
                        String profilePhotoLocalFilePath) {
        this.fullName = fullName;
        this.profilePhotoUrl = profilePhotoUrl;
        this.socialNetworks = socialNetworks;
        this.sport = sport;
        this.sportEvents = sportEvents;
        this.olympicGamesAttend = olympicGamesAttend;
        this.profilePhotoLocalFilePath = profilePhotoLocalFilePath;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getProfilePhotoUrl() {
        return profilePhotoUrl;
    }

    public void setProfilePhotoUrl(String profilePhotoUrl) {
        this.profilePhotoUrl = profilePhotoUrl;
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

    public String getSportEventsToString() {
        StringBuilder str = new StringBuilder();
        for (String s : sportEvents) {
            str.append(s).append(System.getProperty("line.separator"));
        }
        return str.toString();
    }

    public void setSportEvents(List<String> sportEvents) {
        this.sportEvents = sportEvents;
    }

    public List<String> getOlympicGamesAttend() {
        return olympicGamesAttend;
    }

    public String getOlympicGamesAttendToString() {
        StringBuilder str = new StringBuilder();
        for (String s : olympicGamesAttend) {
            str.append(s).append(System.getProperty("line.separator"));
        }
        return str.toString();
    }

    public void setOlympicGamesAttend(List<String> olympicGamesAttend) {
        this.olympicGamesAttend = olympicGamesAttend;
    }

    public boolean hasThisSocialNetwork(String socialNetworkName) {
        return socialNetworks.containsKey(socialNetworkName.toLowerCase());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AthleteModel athlete = (AthleteModel) o;
        return Objects.equals(fullName, athlete.fullName) &&
                Objects.equals(profilePhotoUrl, athlete.profilePhotoUrl) &&
                Objects.equals(socialNetworks, athlete.socialNetworks) &&
                Objects.equals(sport, athlete.sport) &&
                Objects.equals(sportEvents, athlete.sportEvents) &&
                Objects.equals(olympicGamesAttend, athlete.olympicGamesAttend);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullName, profilePhotoUrl, socialNetworks, sport, sportEvents, olympicGamesAttend);
    }

    @Override
    public int compareTo(Object o) {
        return this.getFullName().compareTo(((AthleteModel) o).getFullName());
    }
}
