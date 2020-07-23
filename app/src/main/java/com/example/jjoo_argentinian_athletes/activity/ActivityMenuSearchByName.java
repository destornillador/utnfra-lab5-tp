package com.example.jjoo_argentinian_athletes.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jjoo_argentinian_athletes.R;
import com.example.jjoo_argentinian_athletes.adapter.AthletePreviewAdapter;
import com.example.jjoo_argentinian_athletes.model.AthleteModel;
import com.example.jjoo_argentinian_athletes.util.Const;
import com.example.jjoo_argentinian_athletes.util.EHttpManagerValidMethod;
import com.example.jjoo_argentinian_athletes.util.EHttpThreadReason;
import com.example.jjoo_argentinian_athletes.util.HttpThread;
import com.example.jjoo_argentinian_athletes.util.IRecycleViewClickItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ActivityMenuSearchByName extends AppCompatActivity implements
        IRecycleViewClickItem, Handler.Callback {

    private RecyclerView rvAthleteList;
    private AthletePreviewAdapter athletePreviewAdapter;
    private List<AthleteModel> athleteList;

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

        /*  This activity is also called from ActivityMenuSearchBySport.
            In such case, intent_extras  is populated
        */
        Intent intent_activity_menu_search_by_name = getIntent();
        Bundle intent_extras = intent_activity_menu_search_by_name.getExtras();

        if (intent_extras != null) {
            String sport = intent_extras.getString("SPORT");

            // Set Toolbar Name
            toolbar.setTitle(sport.toUpperCase().concat(" | ").concat(this.getResources().getString(R.string.title_activity_athlete)));

            // Populate a small set
            HttpThread threadGetAthletesBySport = new HttpThread(
                    Const.API_ATHLETES_URL,
                    EHttpManagerValidMethod.GET,
                    new Handler(this),
                    EHttpThreadReason.POPULATE_ATHLETE_MODEL_BY_SPORT,
                    sport);
            threadGetAthletesBySport.start();
        } else {
            // Set Toolbar name
            toolbar.setTitle(this.getResources().getString(R.string.title_activity_athlete));

            // Populate all athletes
            HttpThread threadGetAllAthletes = new HttpThread(
                    Const.API_ATHLETES_URL,
                    EHttpManagerValidMethod.GET,
                    new Handler(this),
                    EHttpThreadReason.POPULATE_MODEL_ATHLETE);
            threadGetAllAthletes.start();
        }

        athleteList = new ArrayList<>();
        athletePreviewAdapter = new AthletePreviewAdapter(this, this, athleteList);
        rvAthleteList.setAdapter(athletePreviewAdapter);
    }

    // Toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_menu_search_by_name, menu);

        // Search View
        MenuItem searchItem = menu.findItem(R.id.activity_menu_search_by_name_item_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                athletePreviewAdapter.getFilter().filter(newText);
                return false;
            }
        });

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
    public void onItemClick(int position) {
    }

    // Populate RecyclerView Thread
    @Override
    public boolean handleMessage(@NonNull Message msg) {
        athleteList.addAll((ArrayList<AthleteModel>) msg.obj);
        // It is not necessary to call the Adapter because the collection is empty
        if (athleteList.isEmpty()) {
            // Enable no data available message
            TextView tv = findViewById(R.id.rv_menu_search_by_name_empty_tv);
            tv.setVisibility(View.VISIBLE);
            return false;
        }
        Collections.sort(athleteList);
        athletePreviewAdapter.notifyDataSetChanged();
        return false;
    }
}
