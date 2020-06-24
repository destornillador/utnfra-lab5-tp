package com.example.jjoo_argentinian_athletes.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.jjoo_argentinian_athletes.R;
import com.example.jjoo_argentinian_athletes.adapter.AthletePreviewAdapter;
import com.example.jjoo_argentinian_athletes.model.Athlete;
import com.example.jjoo_argentinian_athletes.util.Const;
import com.example.jjoo_argentinian_athletes.util.EHttpManagerValidMethod;
import com.example.jjoo_argentinian_athletes.util.EHttpThreadReason;
import com.example.jjoo_argentinian_athletes.util.HttpThread;
import com.example.jjoo_argentinian_athletes.util.IRecycleViewClickItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ActivityMenuSearchByName extends AppCompatActivity implements
        SearchView.OnQueryTextListener, IRecycleViewClickItem, Handler.Callback {

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
            // Populate a small set
            Log.d("extras", "No hay extras");
        } else {
            // Populate all athletes
            HttpThread threadGetAllAthletes = new HttpThread(Const.API_ATHLETES_URL, EHttpManagerValidMethod.GET,
                    new Handler(this), EHttpThreadReason.POPULATE_MODEL);
            threadGetAllAthletes.start();
        }

        athleteList = new ArrayList<>();
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

    // Populate RecyclerView Thread

    @Override
    public boolean handleMessage(@NonNull Message msg) {
        athleteList.addAll((ArrayList<Athlete>) msg.obj);
        // TODO: Probably the sort stuff  should be in another file
        Collections.sort(athleteList);
        athletePreviewAdapter.notifyDataSetChanged();
        return false;
    }
}
