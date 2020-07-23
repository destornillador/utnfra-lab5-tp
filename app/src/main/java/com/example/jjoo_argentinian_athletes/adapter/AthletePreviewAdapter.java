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
import com.example.jjoo_argentinian_athletes.activity.ActivityAthlete;
import com.example.jjoo_argentinian_athletes.model.AthleteModel;
import com.example.jjoo_argentinian_athletes.util.EHttpThreadReason;
import com.example.jjoo_argentinian_athletes.util.EHttpManagerValidMethod;
import com.example.jjoo_argentinian_athletes.util.HttpThread;
import com.example.jjoo_argentinian_athletes.util.IRecycleViewClickItem;
import com.google.gson.Gson;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public class AthletePreviewAdapter extends RecyclerView.Adapter<AthletePreviewAdapter.AthletePreviewViewHolder>
        implements Handler.Callback, Filterable {

    private List<AthleteModel> athleteList;
    private List<AthleteModel> athleteListFull;
    private Context context;
    private IRecycleViewClickItem listener;

    public AthletePreviewAdapter(Context context, IRecycleViewClickItem listener, List<AthleteModel> athleteList) {
        this.context = context;
        this.listener = listener;
        this.athleteList = athleteList;
    }

    @Override
    public AthletePreviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_athlete_preview, parent, false);
        return new AthletePreviewViewHolder(v, listener);
    }

    @Override
    public void onBindViewHolder(AthletePreviewViewHolder holder, int position) {
        AthleteModel athlete = athleteList.get(position);

        holder.itemAthleteFullname.setText(athlete.getFullName());
        holder.setPosition(position);
        holder.setContext(context);
        holder.setAthlete(athlete);

        if (athlete.getProfilePhotoLocalFilePath() != null) {
            holder.itemAthleteProfilePhoto.setImageBitmap(BitmapFactory.decodeFile(athlete.getProfilePhotoLocalFilePath()));
        } else {
            HttpThread threadGetProfilePhoto = new HttpThread(athlete.getProfilePhotoUrl(),
                    EHttpManagerValidMethod.GET ,
                    new Handler(this),
                    EHttpThreadReason.POPULATE_IMAGE,
                    position,
                    true,
                    context);
            threadGetProfilePhoto.start();
        }
    }

    @Override
    public int getItemCount() {
        return athleteList.size();
    }

    @Override
    public boolean handleMessage(@NonNull Message msg) {
        String s = new String((byte[]) msg.obj, StandardCharsets.UTF_8);
        athleteList.get(msg.arg1).setProfilePhotoLocalFilePath(s);
        this.notifyItemChanged(msg.arg1);

        return false;
    }

    // Regenerate recyclerview based on the SearchView value
    @Override
    public Filter getFilter() {
        return athleteFilter;
    }

    private Filter athleteFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<AthleteModel> athleteListFiltered = new ArrayList<>();

            // Ugly workaround to have an updated copy of all Athletes
            if (athleteListFull == null) {
                athleteListFull = new ArrayList<>(athleteList);
            }

            if (constraint == null || constraint.length() == 0) {
                athleteListFiltered.addAll(athleteListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (AthleteModel athlete : athleteListFull) {
                    if (athlete.getFullName().toLowerCase().contains(filterPattern)) {
                        athleteListFiltered.add(athlete);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = athleteListFiltered;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            athleteList.clear();
            athleteList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };



    static class AthletePreviewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView itemAthleteFullname;
        private ImageView itemAthleteProfilePhoto;
        private IRecycleViewClickItem listener;
        private Context context;
        private int position;
        private AthleteModel athlete;

        AthletePreviewViewHolder(View itemView, IRecycleViewClickItem listener) {
            super(itemView);
            this.itemAthleteFullname =  itemView.findViewById(R.id.item_athlete_preview_fullname);
            this.itemAthleteProfilePhoto = itemView.findViewById(R.id.item_athlete_preview_profile_photo);
            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        protected void setPosition(int position) {
            this.position = position;
        }

        protected void setContext(Context context) {
            this.context = context;
        }

        protected void setAthlete(AthleteModel athlete) {
            this.athlete = athlete;
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(position);

            Gson gson = new Gson();
            Intent intentActivityAthlete = new Intent(context, ActivityAthlete.class);
            intentActivityAthlete.putExtra("ITEM_ATHLETE", gson.toJson(athlete));
            context.startActivity(intentActivityAthlete);
        }
    }
    }
