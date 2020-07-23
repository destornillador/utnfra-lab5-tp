package com.example.jjoo_argentinian_athletes.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.jjoo_argentinian_athletes.R;

public class ActivityMenuHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_menu_home);

        // Go to activity_search_by_athlete
        Button btnSearchByAthlete = (Button) findViewById(R.id.btn_menu_home_search_by_name);
        btnSearchByAthlete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_activity_search_by_athlete = new Intent(ActivityMenuHome.this, ActivityMenuSearchByName.class);
                ActivityMenuHome.this.startActivity(intent_activity_search_by_athlete);
            }
        });

        // Go to activity_search_by_sport
        Button btnSearchBySport = (Button) findViewById(R.id.btn_menu_home_search_by_sport);
        btnSearchBySport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_activity_search_by_sport = new Intent(ActivityMenuHome.this, ActivityMenuSearchBySport.class);
                ActivityMenuHome.this.startActivity(intent_activity_search_by_sport);
            }
        });

        /* PersonaModel personaModel = new PersonaModel();
        PersonaController personaController = new PersonaController(personaModel);
        PersonaView personaView = new PersonaView(this, personaController, personaModel);
        personaController.setView(personaView); */


    }
}
