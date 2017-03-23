package me.pulkitkumar.bealert;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pulkit on 23/3/17.
 */

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_BOT = 0;
    private static final int TYPE_ME = 1;
    private static final int TYPE_LOAD = 2;
    private static final int TYPE_FOOTER = 3;
    private static final int TYPE_REPLY = 4;
    private static final int TYPE_WEATHER_CARD = 5;

    private LayoutInflater inflater;
    private Context c;
    private int type;
    private String content;
    private List<Object> listItem = new ArrayList<>();

    public ChatAdapter(Context c, List<Object> listItem) {
        inflater = LayoutInflater.from(c);
        this.c = c;
        //this.content = content;
        this.listItem = listItem;
    }


    public void removeAt(int position) {
        listItem.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, listItem.size());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        if (listItem == null) {
            return 0;
        }
        if (listItem.size() == 0) {
            return 1;
        }
        return listItem.size() + 1;
    }
}
