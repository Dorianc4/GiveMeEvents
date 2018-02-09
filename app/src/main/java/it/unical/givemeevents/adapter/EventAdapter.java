package it.unical.givemeevents.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import it.unical.givemeevents.R;
import it.unical.givemeevents.model.FacebookEvent;

/**
 * Created by Manuel on 19/12/2017.
 */

public class EventAdapter extends BaseAdapter {

    private List<FacebookEvent> events;

    public EventAdapter(Context ctx, List<FacebookEvent> events) {
        this.events = events;
        this.ctx = ctx;
    }

    public void addEvents(List<FacebookEvent> newEvents){
        if(!newEvents.isEmpty()) {
            this.events.addAll(newEvents);
            this.notifyDataSetChanged();
        }
    }

    public void removeAllEvents(){
        this.events.clear();
        this.notifyDataSetChanged();
    }

    public List<FacebookEvent> getEvents() {
        return events;
    }

    public void setEvents(List<FacebookEvent> events) {
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

        if(view==null){
            view = LayoutInflater.from(ctx).inflate(R.layout.eventlist_layout, null);
            viewHolder = new ViewHolder();
            viewHolder.name = view.findViewById(R.id.textViewName);
            viewHolder.placeName = view.findViewById(R.id.textViewPlaceName);
            viewHolder.cover = view.findViewById(R.id.imageViewCover);
            viewHolder.category = view.findViewById(R.id.textViewCategory);
            viewHolder.details = view.findViewById(R.id.textViewDetails);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }

        FacebookEvent event = (FacebookEvent) getItem(i);

        viewHolder.name.setText(event.getName());
        if(event.getPlace()!=null){
            viewHolder.placeName.setText(event.getPlace().getName());
        }else{
            viewHolder.placeName.setText("");
        }

        if(event.getStartTime()!=null){
            viewHolder.details.setText(event.getStartTime());
        }else{
            viewHolder.details.setText("");
        }

        viewHolder.category.setText(event.getCategory());
//        Log.d("PICURL", event.getPicture().getData().getUrl());
        if(event.getPicture()!=null) {
            Picasso.with(ctx).load(event.getPicture().getData().getUrl()).into(viewHolder.cover);
        }
        Log.d("EVENTID", event.getId());

        return view;
    }

    static class ViewHolder {
        TextView name;
        TextView placeName;
        ImageView cover;
        TextView category;
        TextView details;
    }
}
