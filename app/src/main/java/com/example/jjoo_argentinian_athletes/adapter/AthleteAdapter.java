package com.example.jjoo_argentinian_athletes.adapter;

import android.content.Context;
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
import com.example.jjoo_argentinian_athletes.model.Athlete;

import java.util.List;


public class AthleteAdapter extends RecyclerView.Adapter<AthleteAdapter.AthleteViewHolder> implements Handler.Callback {
    private List<Athlete> athleteList;
    private Context context;

    public AthleteAdapter(Context context, List<Athlete> athleteList) {
        this.context = context;
        this.athleteList = athleteList;
    }

    @Override
    public AthleteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_athlete_preview, parent, false);
        return new AthleteViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AthleteViewHolder holder, int position) {
        Athlete athlete = athleteList.get(position);

        holder.itemAthleteFullname.setText(athlete.getFullName());

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

    static class AthleteViewHolder extends RecyclerView.ViewHolder {
        TextView itemAthleteFullname;
        ImageView itemAthleteProfilePhoto;

        AthleteViewHolder(View itemView) {
            super(itemView);
            this.itemAthleteFullname =  itemView.findViewById(R.id.item_athlete_preview_fullname);
            this.itemAthleteProfilePhoto = itemView.findViewById(R.id.item_athlete_preview_profile_photo);
        }
    }
}
