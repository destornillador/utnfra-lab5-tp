package com.example.jjoo_argentinian_athletes.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jjoo_argentinian_athletes.R;
import com.example.jjoo_argentinian_athletes.activity.ActivityMenuSearchByName;
import com.example.jjoo_argentinian_athletes.model.SportModel;
import com.example.jjoo_argentinian_athletes.util.EHttpManagerValidMethod;
import com.example.jjoo_argentinian_athletes.util.EHttpThreadReason;
import com.example.jjoo_argentinian_athletes.util.HttpThread;
import com.example.jjoo_argentinian_athletes.util.IRecycleViewClickItem;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public class SportCategoryAdapter extends RecyclerView.Adapter<SportCategoryAdapter.SportCategoryViewHolder>
        implements Handler.Callback, Filterable {

    private List<SportModel> sportList;
    private List<SportModel> sportListFull;
    private Context context;
    private IRecycleViewClickItem listener;

    public SportCategoryAdapter(Context context, IRecycleViewClickItem listener, List<SportModel> sportList) {
        this.context = context;
        this.listener = listener;
        this.sportList = sportList;
    }

    @Override
    public SportCategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sport_category, parent, false);
        return new SportCategoryViewHolder(v, listener);
    }

    @Override
    public void onBindViewHolder(SportCategoryViewHolder holder, int position) {
        SportModel sport = sportList.get(position);

        holder.itemSportName.setText(sport.getName());
        holder.setPosition(position);
        holder.setContext(context);
        holder.setSport(sport);

        if (sport.getLogoLocalFilePath() != null) {
            holder.itemSportLogo.setImageBitmap(BitmapFactory.decodeFile(sport.getLogoLocalFilePath()));
        } else {
            HttpThread threadGetLogo = new HttpThread(sport.getLogoUrl(),
                    EHttpManagerValidMethod.GET ,
                    new Handler(this),
                    EHttpThreadReason.POPULATE_IMAGE,
                    position,
                    true,
                    context);
            threadGetLogo.start();
        }
    }

    @Override
    public int getItemCount() {
        return this.sportList.size();
    }

    @Override
    public boolean handleMessage(@NonNull Message msg) {
        String s = new String((byte[]) msg.obj, StandardCharsets.UTF_8);
        sportList.get(msg.arg1).setLogoLocalFilePath(s);
        this.notifyItemChanged(msg.arg1);

        return false;
    }

    // Regenerate recyclerview based on the SearchView value
    @Override
    public Filter getFilter() {
        return sportFilter;
    }

    private Filter sportFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<SportModel> sportListFiltered = new ArrayList<>();

            // Ugly workaround to have an updated copy of all Athletes
            if (sportListFull == null) {
                sportListFull = new ArrayList<>(sportList);
            }

            if (constraint == null || constraint.length() == 0) {
                sportListFiltered.addAll(sportListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (SportModel sport : sportListFull) {
                    if (sport.getName().toLowerCase().contains(filterPattern)) {
                        sportListFiltered.add(sport);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = sportListFiltered;

            return results;
        }


        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            sportList.clear();
            sportList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    static class SportCategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView itemSportName;
        private ImageView itemSportLogo;
        private IRecycleViewClickItem listener;
        private Context context;
        private int position;
        private SportModel sport;

        SportCategoryViewHolder(View itemView, IRecycleViewClickItem listener) {
            super(itemView);
            this.itemSportName =  itemView.findViewById(R.id.item_sport_category_name);
            this.itemSportLogo = itemView.findViewById(R.id.item_sport_category_logo);
            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        protected void setPosition(int position) {
            this.position = position;
        }

        protected void setContext(Context context) {
            this.context = context;
        }

        protected void setSport(SportModel sport) {
            this.sport = sport;
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(position);

            Intent intentActivitySearchByName = new Intent(context, ActivityMenuSearchByName.class);
            intentActivitySearchByName.putExtra("SPORT", sport.getName());
            context.startActivity(intentActivitySearchByName);
        }
    }
}
