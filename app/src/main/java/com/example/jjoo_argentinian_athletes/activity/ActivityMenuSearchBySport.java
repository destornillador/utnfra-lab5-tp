package com.example.jjoo_argentinian_athletes.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.jjoo_argentinian_athletes.R;
import com.example.jjoo_argentinian_athletes.adapter.SportCategoryAdapter;
import com.example.jjoo_argentinian_athletes.model.Sport;
import com.example.jjoo_argentinian_athletes.util.IRecycleViewClickItem;

import java.util.ArrayList;
import java.util.List;

public class ActivityMenuSearchBySport extends AppCompatActivity implements
        SearchView.OnQueryTextListener, IRecycleViewClickItem {

    private RecyclerView rvSportList;
    private SportCategoryAdapter sportCategoryAdapter;
    private List<Sport> sportList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_search_by_sport);

        // Toolbar
        ActionBar toolbar = getSupportActionBar();
        if (toolbar != null) {
            toolbar.setDisplayHomeAsUpEnabled(true);
        }

        // Recycler view generation
        rvSportList = findViewById(R.id.rv_menu_search_by_sport);
        rvSportList.setHasFixedSize(true);
        rvSportList.setLayoutManager(new LinearLayoutManager(this));

        // FIXME: This is only for testing purpose

        sportList = new ArrayList<>();
        sportList.add(new Sport("Swimming", "https://google.com"));
        sportList.add(new Sport("Hockey", "https://google.com"));
        sportList.add(new Sport("Canoe", "https://google.com"));

        sportCategoryAdapter = new SportCategoryAdapter(this,this, sportList);
        rvSportList.setAdapter(sportCategoryAdapter);



    }

    // Toolbar

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_menu_search_by_sport, menu);

        // Search View
        MenuItem searchItem = menu.findItem(R.id.activity_menu_search_by_sport_item_search);
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
