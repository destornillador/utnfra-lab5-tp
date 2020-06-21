package com.example.jjoo_argentinian_athletes.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jjoo_argentinian_athletes.R;
import com.example.jjoo_argentinian_athletes.activity.ActivityAthlete;
import com.example.jjoo_argentinian_athletes.activity.ActivityMenuHome;
import com.example.jjoo_argentinian_athletes.activity.ActivityMenuSearchByName;
import com.example.jjoo_argentinian_athletes.model.Athlete;
import com.example.jjoo_argentinian_athletes.util.IRecycleViewClickItem;

import java.util.List;


public class AthleteAdapter extends RecyclerView.Adapter<AthleteAdapter.AthleteViewHolder> implements Handler.Callback {

    private List<Athlete> athleteList;
    private Context context;
    private IRecycleViewClickItem listener;

    public AthleteAdapter(Context context, List<Athlete> athleteList, IRecycleViewClickItem listener) {
        this.athleteList = athleteList;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public AthleteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_athlete_preview, parent, false);
        return new AthleteViewHolder(v, listener);
    }

    @Override
    public void onBindViewHolder(AthleteViewHolder holder, int position) {
        Athlete athlete = athleteList.get(position);

        holder.itemAthleteFullname.setText(athlete.getFullName());
        holder.setPosition(position);
        holder.setContext(context);

        if (athlete.getProfilePhotoBinary() != null) {
            holder.itemAthleteProfilePhoto.setImageBitmap(BitmapFactory.decodeByteArray(athlete.getProfilePhotoBinary(),
                    0, athlete.getProfilePhotoBinary().length));
        } /*else {
            HttpThread httpThread = new HttpThread(athlete.getProfilePhotoURL(), "GET", new Handler(this),
                    EHttpThreadReason.POPULATE_PHOTO, position);
            httpThread.start();
        }*/
    }

    @Override
    public int getItemCount() {
        return this.athleteList.size();
    }

    @Override
    public boolean handleMessage(@NonNull Message msg) {
        byte[] profilePhoto = (byte[]) msg.obj;
        athleteList.get(msg.arg1).setProfilePhotoBinary(profilePhoto);
        this.notifyItemChanged(msg.arg1);

        return false;
    }

    static class AthleteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView itemAthleteFullname;
        private ImageView itemAthleteProfilePhoto;
        private IRecycleViewClickItem listener;
        private Context context;
        private int position;

        AthleteViewHolder(View itemView, IRecycleViewClickItem listener) {
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


        @Override
        public void onClick(View v) {
            listener.onItemClick(position);
            Intent intent_activity_athlete = new Intent(context, ActivityAthlete.class);
            context.startActivity(intent_activity_athlete);
        }
    }
}
