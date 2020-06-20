package com.example.jjoo_argentinian_athletes.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.jjoo_argentinian_athletes.R;

import com.example.jjoo_argentinian_athletes.adapter.AthleteAdapter;
import com.example.jjoo_argentinian_athletes.model.Athlete;

import java.util.List;

public class ActivityMenuHome extends AppCompatActivity {
    private RecyclerView rvAthleteList;
    private AthleteAdapter athleteAdapter;
    List<Athlete> athleteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_home);

        // Recycler view generation
        /*
        rvAthleteList = findViewById(R.id.rv_menu_search_by_athlete);
        rvAthleteList.setHasFixedSize(true);
        rvAthleteList.setLayoutManager(new LinearLayoutManager(this));
         */

        // Go to activity_search_by_athlete
        Button btnSearchByAthlete = (Button) findViewById(R.id.btn_menu_home_search_by_name);
        btnSearchByAthlete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_activity_search_by_athlete = new Intent(ActivityMenuHome.this, ActivityMenuSearchByName.class);
                ActivityMenuHome.this.startActivity(intent_activity_search_by_athlete);
            }
        });

        // Get data from the test API
        /*HttpThread h1 = new HttpThread(API_PEOPLE_URL, "GET", new Handler(this), EHttpThreadReason.POPULATE_MODEL);
        h1.start();
         */

        /*
        pmList = new ArrayList<>();
        pAdapter = new PersonAdapter(this, pmList);
        rvAthleteList.setAdapter(this.pAdapter);
        */

        /* PersonaModel personaModel = new PersonaModel();
        PersonaController personaController = new PersonaController(personaModel);
        PersonaView personaView = new PersonaView(this, personaController, personaModel);
        personaController.setView(personaView); */


    }
}
