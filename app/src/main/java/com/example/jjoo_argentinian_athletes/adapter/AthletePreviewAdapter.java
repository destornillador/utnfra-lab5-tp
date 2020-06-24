package com.example.jjoo_argentinian_athletes.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jjoo_argentinian_athletes.R;
import com.example.jjoo_argentinian_athletes.activity.ActivityAthlete;
import com.example.jjoo_argentinian_athletes.model.Athlete;
import com.example.jjoo_argentinian_athletes.util.EHttpThreadReason;
import com.example.jjoo_argentinian_athletes.util.EHttpManagerValidMethod;
import com.example.jjoo_argentinian_athletes.util.HttpThread;
import com.example.jjoo_argentinian_athletes.util.IRecycleViewClickItem;
import com.google.gson.Gson;

import java.nio.charset.StandardCharsets;
import java.util.List;


public class AthletePreviewAdapter extends RecyclerView.Adapter<AthletePreviewAdapter.AthletePreviewViewHolder>
        implements Handler.Callback{

    private List<Athlete> athleteList;
    private Context context;
    private IRecycleViewClickItem listener;

    public AthletePreviewAdapter(Context context, IRecycleViewClickItem listener, List<Athlete> athleteList) {
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
        Athlete athlete = athleteList.get(position);

        holder.itemAthleteFullname.setText(athlete.getFullName());
        holder.setPosition(position);
        holder.setContext(context);
        holder.setAthlete(athlete);

        if (athlete.getProfilePhotoLocalFilePath() != null) {
            holder.itemAthleteProfilePhoto.setImageBitmap(BitmapFactory.decodeFile(athlete.getProfilePhotoLocalFilePath()));
        } else {
            HttpThread threadGetProfilePhoto = new HttpThread(athlete.getProfilePhotoURL(), EHttpManagerValidMethod.GET ,
                    new Handler(this),
                    EHttpThreadReason.POPULATE_PHOTO, position, true,context);
            threadGetProfilePhoto.start();
        }
    }

    @Override
    public int getItemCount() {
        return this.athleteList.size();
    }

    @Override
    public boolean handleMessage(@NonNull Message msg) {
        String s = new String((byte[]) msg.obj, StandardCharsets.UTF_8);
        athleteList.get(msg.arg1).setProfilePhotoLocalFilePath(s);
        this.notifyItemChanged(msg.arg1);

        return false;
    }

    static class AthletePreviewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView itemAthleteFullname;
        private ImageView itemAthleteProfilePhoto;
        private IRecycleViewClickItem listener;
        private Context context;
        private int position;
        private Athlete athlete;

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

        protected void setAthlete(Athlete athlete) {
            this.athlete = athlete;
        }

        @Override
        public void onClick(View v) {
            listener.onAthleteClick(position);

            Gson gson = new Gson();
            Intent intentActivityAthlete = new Intent(context, ActivityAthlete.class);
            // FIXME: Creo que el Obj Json hace que el obj sea > 1 mb
            intentActivityAthlete.putExtra("ITEM_ATHLETE", gson.toJson(athlete));
            context.startActivity(intentActivityAthlete);
        }
    }
    }
