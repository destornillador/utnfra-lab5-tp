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
import com.example.jjoo_argentinian_athletes.model.Sport;
import com.example.jjoo_argentinian_athletes.util.IRecycleViewClickItem;
import com.google.gson.Gson;

import java.util.List;


public class SportCategoryAdapter extends RecyclerView.Adapter<SportCategoryAdapter.SportCategoryViewHolder>
        implements Handler.Callback{

    private List<Sport> sportList;
    private Context context;
    private IRecycleViewClickItem listener;

    public SportCategoryAdapter(Context context, IRecycleViewClickItem listener, List<Sport> sportList) {
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
        Sport sport = sportList.get(position);

        holder.itemSportName.setText(sport.getName());
        holder.setPosition(position);
        holder.setContext(context);
        holder.setSport(sport);

        if (sport.getLogoBinary() != null) {
            holder.itemSportLogo.setImageBitmap(BitmapFactory.decodeByteArray(sport.getLogoBinary(),
                    0, sport.getLogoBinary().length));
        } /*else {
            HttpThread httpThread = new HttpThread(athlete.getProfilePhotoURL(), "GET", new Handler(this),
                    EHttpThreadReason.POPULATE_PHOTO, position);
            httpThread.start();
        }*/
    }

    @Override
    public int getItemCount() {
        return this.sportList.size();
    }

    @Override
    public boolean handleMessage(@NonNull Message msg) {
        byte[] logoBinary = (byte[]) msg.obj;
        sportList.get(msg.arg1).setLogoBinary(logoBinary);
        this.notifyItemChanged(msg.arg1);

        return false;
    }

    static class SportCategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView itemSportName;
        private ImageView itemSportLogo;
        private IRecycleViewClickItem listener;
        private Context context;
        private int position;
        private Sport sport;

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

        protected void setSport(Sport sport) {
            this.sport = sport;
        }

        @Override
        public void onClick(View v) {
            listener.onAthleteClick(position);

            /*
            TODO: Aca habria que meter el logo:sport como Toolbar
            Gson gson = new Gson();
            Intent intentActivityAthlete = new Intent(context, ActivityAthlete.class);
            intentActivityAthlete.putExtra("ITEM_SPORT", gson.toJson(athlete));
            context.startActivity(intentActivityAthlete);
            */
        }
    }
}
