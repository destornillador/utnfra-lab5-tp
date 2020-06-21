package com.example.jjoo_argentinian_athletes.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.jjoo_argentinian_athletes.R;
import com.example.jjoo_argentinian_athletes.adapter.AthleteAdapter;
import com.example.jjoo_argentinian_athletes.model.Athlete;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class ActivityMenuSearchByName extends AppCompatActivity {
    private RecyclerView rvAthleteList;
    private AthleteAdapter athleteAdapter;
    private List<Athlete> athleteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_search_by_name);

        ActionBar toolbar = getSupportActionBar();
        toolbar.setDisplayHomeAsUpEnabled(true);

        // Recycler view generation
        rvAthleteList = findViewById(R.id.rv_menu_search_by_name);
        rvAthleteList.setHasFixedSize(true);
        rvAthleteList.setLayoutManager(new LinearLayoutManager(this));

        // FIXME: This is only for testing purpose
        Map<String, String> testSN = new HashMap<String, String>();
        testSN.put("twitter", "twitter.com/pepe");
        List<String> testOG = new ArrayList<String>();
        testOG.add("tokio 2020");
        athleteList = new ArrayList<>();
        athleteList.add(new Athlete("Pepe Rodriguez", "https://google.com",
                testSN, "swimming", null, null));
        athleteList.add(new Athlete("Maria Rodriguez", "https://google.com",
                testSN, "swimming", null, null));

        athleteAdapter = new AthleteAdapter(this, athleteList);
        rvAthleteList.setAdapter(athleteAdapter);



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                super.finish();
                break;
        }

        return false;
    }
}
