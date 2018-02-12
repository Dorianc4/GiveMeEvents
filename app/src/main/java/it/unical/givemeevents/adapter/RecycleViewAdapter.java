package it.unical.givemeevents.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import it.unical.givemeevents.R;
import it.unical.givemeevents.model.FacebookEvent;
import it.unical.givemeevents.util.GiveMeEventUtils;

/**
 * Created by Yelena on 11/2/2018.
 */

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView evName, evPlace, evMonth, evDay, evTime;
        private ImageView evImage;
        private ImageButton evFavorite;

        public ViewHolder(View itemView) {
            super(itemView);
            evName = (TextView) itemView.findViewById(R.id.txt_EvName);
            evPlace = (TextView) itemView.findViewById(R.id.txt_EvPlace);
            evMonth = (TextView) itemView.findViewById(R.id.txt_EvDateMonth);
            evDay = (TextView) itemView.findViewById(R.id.txt_EvDateDay);
            evTime = (TextView) itemView.findViewById(R.id.txt_EvTime);
            evImage = (ImageView) itemView.findViewById(R.id.img_Event);
            evFavorite = (ImageButton) itemView.findViewById(R.id.btn_Favorite);
        }
    }

    private List<FacebookEvent> findedEvents;
    private Context ctx;

    public RecycleViewAdapter(List<FacebookEvent> findedEvents, Context contex) {
        this.findedEvents = findedEvents;
        ctx = contex;
    }

    public RecycleViewAdapter() {

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_items, parent, false);
        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        FacebookEvent event = findedEvents.get(position);
        holder.evName.setText(event.getName());
        Date evdate = GiveMeEventUtils.createDateFromString(event.getStartTime(), "yyyy-MM-dd'T'HH:mm:ssZ");
        SimpleDateFormat myFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");
        String month = GiveMeEventUtils.createStringfromDate(evdate, "MMM");
        month = month.substring(0, 3);
        String sday = GiveMeEventUtils.createStringfromDate(evdate, "EEEE");
        String nday = GiveMeEventUtils.createStringfromDate(evdate, "d");
        String day = sday + " " + nday;
        String time = GiveMeEventUtils.createStringfromDate(evdate, "HH:mm");
        holder.evMonth.setText(month);

        holder.evDay.setText(day);
        holder.evTime.setText(time);
        if (event.getPlace() != null) {
            String place = event.getPlace().getName();
            holder.evPlace.setText(place);
        } else if (event.getPlaceOwner() != null) {
            String place = event.getPlaceOwner().getName();
            holder.evPlace.setText(place);
        }
        if (event.getPicture() != null) {
            Picasso.with(ctx).load(event.getPicture().getData().getUrl()).placeholder(R.drawable.imagen).into(holder.evImage);
        }

        holder.evFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean favorite = false;
                if (!favorite) {
                    holder.evFavorite.setImageDrawable(ctx.getResources().getDrawable(R.drawable.ic_fav_on));
                    favorite = true;
                } else {
                    holder.evFavorite.setImageDrawable(ctx.getResources().getDrawable(R.drawable.ic_fav_off));
                }
            }

            //Intent detailIntent = new Intent(ctx, )
        });
        Log.d("EVENTID", event.getId());
        Log.d("PLACEID", event.getPlaceOwner().getId());
    }

    @Override
    public int getItemCount() {
        return findedEvents.size();
    }

    public void addEvents(List<FacebookEvent> newEvents) {
        if (!newEvents.isEmpty()) {
            int pos;
            if (findedEvents.size() == 0)
                pos = 0;
            else
                pos = findedEvents.size() - 1;
            this.findedEvents.addAll(newEvents);
            this.notifyItemRangeInserted(pos, newEvents.size());
        }
    }

    public void removeAllEvents() {
        int cant = findedEvents.size();
        this.findedEvents.clear();
        this.notifyItemRangeRemoved(0, cant);
    }

    public List<FacebookEvent> getEvents() {
        return findedEvents;
    }
}
