package it.unical.givemeevents.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import it.unical.givemeevents.R;
import it.unical.givemeevents.model.EventPlace;

/**
 * Created by Manuel on 19/12/2017.
 */

public class FavoritesAdapter extends BaseAdapter {

    private List<EventPlace> events;

    public FavoritesAdapter(Context ctx, List<EventPlace> events) {
        this.events = events;
        this.ctx = ctx;
    }

    public void addEvents(List<EventPlace> newEvents) {
        if (!newEvents.isEmpty()) {
            this.events.addAll(newEvents);
            this.notifyDataSetChanged();
        }
    }

    public void removeAllEvents() {
        this.events.clear();
        this.notifyDataSetChanged();
    }

    public List<EventPlace> getEvents() {
        return events;
    }

    public void setEvents(List<EventPlace> events) {
        this.events = events;
    }

    private Context ctx;

    @Override
    public int getCount() {
        return events.size();
    }

    @Override
    public Object getItem(int i) {
        return events.get(i);
    }

    @Override
    public long getItemId(int i) {
        return Long.parseLong(events.get(i).getId());
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder;

        if (view == null) {
            view = LayoutInflater.from(ctx).inflate(R.layout.favoritelist_layout, null);
            viewHolder = new ViewHolder();
            viewHolder.name = view.findViewById(R.id.textViewName);
            viewHolder.street = view.findViewById(R.id.textViewStreet);
            viewHolder.cover = view.findViewById(R.id.imageViewCover);
            viewHolder.city = view.findViewById(R.id.textViewCity);
            viewHolder.country = view.findViewById(R.id.textViewCountry);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        EventPlace place = (EventPlace) getItem(i);

        viewHolder.name.setText(place.getName());
        if (place.getLocation() != null) {
            if (place.getLocation().getStreet() != null) {
                viewHolder.street.setText(place.getLocation().getStreet());
            } else {
                viewHolder.street.setText("");
            }
            if (place.getLocation().getCity() != null) {
                viewHolder.city.setText(place.getLocation().getCity());
            } else {
                viewHolder.city.setText("");
            }
            if (place.getLocation().getCountry() != null) {
                viewHolder.country.setText(place.getLocation().getCountry());
            } else {
                viewHolder.country.setText("");
            }
        }

//        if(event.getStartTime()!=null){
//            viewHolder.details.setText(event.getStartTime());
//        }else{
//            viewHolder.details.setText("");
//        }

//        viewHolder.category.setText(event.getCategory());
//        Log.d("PICURL", event.getPicture().getData().getUrl());
        if (place.getPicture() != null && !place.getPicture().isEmpty()) {
            Picasso.with(ctx).load(place.getPicture()).into(viewHolder.cover);
        }

        return view;
    }

    static class ViewHolder {
        TextView name;
        TextView street;
        ImageView cover;
        TextView city;
        TextView country;
    }
}
