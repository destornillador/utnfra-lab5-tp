package com.example.jjoo_argentinian_athletes.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.jjoo_argentinian_athletes.R;
import com.example.jjoo_argentinian_athletes.adapter.AthletePreviewAdapter;
import com.example.jjoo_argentinian_athletes.model.Athlete;
import com.example.jjoo_argentinian_athletes.util.IRecycleViewClickItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityMenuSearchByName extends AppCompatActivity implements
        SearchView.OnQueryTextListener, IRecycleViewClickItem {

    private RecyclerView rvAthleteList;
    private AthletePreviewAdapter athletePreviewAdapter;
    private List<Athlete> athleteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_search_by_name);

        // Toolbar
        ActionBar toolbar = getSupportActionBar();
        if (toolbar != null) {
            toolbar.setDisplayHomeAsUpEnabled(true);
        }

        // Recycler view generation
        rvAthleteList = findViewById(R.id.rv_menu_search_by_name);
        rvAthleteList.setHasFixedSize(true);
        rvAthleteList.setLayoutManager(new LinearLayoutManager(this));

        /* TODO: This activity is also used from ActivityMenuSearchBySport.
                 I have to find out the way to populate it from getExtra (in case of being
                 invoked from ActivityMenuSearchBySport) too
                 Perhaps something like "if (extra > 0) then populate from here, else populate from all"
         */
        Intent intent_activity_menu_search_by_name = getIntent();
        Bundle intent_extras = intent_activity_menu_search_by_name.getExtras();

        // Populate with all athletes or from a small set, given from Intent Extra
        if (intent_extras != null) {
            // Populate all athletes
            Log.d("extras","Hay extras");
        } else {
            // Populate a small set
            Log.d("extras", "No hay extras");
        }

        // FIXME: This is only for testing purpose
        Map<String, String> testSN = new HashMap<String, String>();
        testSN.put("twitter", "twitter.com/pepe");
        List<String> testOG = new ArrayList<String>();
        testOG.add("tokio 2020");
        List<String> testSE = new ArrayList<String>();
        testSE.add("100m freestyle");

        athleteList = new ArrayList<>();
        athleteList.add(new Athlete("Pepe Rodriguez", "https://google.com",
                testSN, "swimming", testSE, testOG));
        athleteList.add(new Athlete("Maria Rodriguez", "https://google.com",
                testSN, "swimming", null, null));

        athletePreviewAdapter = new AthletePreviewAdapter(this,this, athleteList);
        rvAthleteList.setAdapter(athletePreviewAdapter);



    }

    // Toolbar

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_menu_search_by_name, menu);

        // Search View
        MenuItem searchItem = menu.findItem(R.id.activity_menu_search_by_name_item_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);

        return true;
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

    @Override
    public boolean onQueryTextSubmit(String s) {
        // TODO: Update the RecyclerView every time that a letter is entered
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        // TODO: Update the RecyclerView every time that a letter is entered
        return false;
    }

    @Override
    public void onAthleteClick(int position) {
    }
}
