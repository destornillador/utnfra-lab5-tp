package com.example.jjoo_argentinian_athletes.controller;

import com.example.jjoo_argentinian_athletes.model.AthleteModel;
import com.example.jjoo_argentinian_athletes.view.AthleteView;


public class AthleteController {
    AthleteModel athleteModel;
    AthleteView athleteView;

    public AthleteController(AthleteModel athlete) {
        this.athleteModel = athlete;
    }

    public void setView(AthleteView view) {
        athleteView = view;
    }

    public void populateView() {
        athleteView.setAthleteModel(athleteModel);
        athleteView.populateView();
    }

    public AthleteModel getAthlete() {
        return athleteModel;
    }
}
