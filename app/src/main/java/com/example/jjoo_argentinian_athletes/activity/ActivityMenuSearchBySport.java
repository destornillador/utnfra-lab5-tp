package com.example.jjoo_argentinian_athletes.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jjoo_argentinian_athletes.R;
import com.example.jjoo_argentinian_athletes.adapter.SportCategoryAdapter;
import com.example.jjoo_argentinian_athletes.model.SportModel;
import com.example.jjoo_argentinian_athletes.util.Const;
import com.example.jjoo_argentinian_athletes.util.EHttpManagerValidMethod;
import com.example.jjoo_argentinian_athletes.util.EHttpThreadReason;
import com.example.jjoo_argentinian_athletes.util.HttpThread;
import com.example.jjoo_argentinian_athletes.util.IRecycleViewClickItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ActivityMenuSearchBySport extends AppCompatActivity implements
        IRecycleViewClickItem, Handler.Callback {

    private RecyclerView rvSportList;
    private SportCategoryAdapter sportCategoryAdapter;
    private List<SportModel> sportList;

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

        // Set Toolbar name
        toolbar.setTitle(this.getResources().getString(R.string.title_activity_menu_by_sport));
        // Get sports from API
        HttpThread threadGetAllSports = new HttpThread(
                Const.API_SPORTS_URL,
                EHttpManagerValidMethod.GET,
                new Handler(this),
                EHttpThreadReason.POPULATE_MODEL_SPORT);
        threadGetAllSports.start();

        sportList = new ArrayList<>();
        sportCategoryAdapter = new SportCategoryAdapter(this, this, sportList);
        rvSportList.setAdapter(sportCategoryAdapter);
    }

    // Toolbar
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_menu_search_by_sport, menu);

        // Search View
        MenuItem searchItem = menu.findItem(R.id.activity_menu_search_by_sport_item_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                sportCategoryAdapter.getFilter().filter(newText);
                return false;
            }
        });

        return true;
    }

    @Override
    public void onItemClick(int position) {
    }

    // Populate RecyclerView Thread
    @Override
    public boolean handleMessage(@NonNull Message msg) {
        sportList.addAll((ArrayList<SportModel>) msg.obj);
        Collections.sort(sportList);
        sportCategoryAdapter.notifyDataSetChanged();
        return false;
    }
}
