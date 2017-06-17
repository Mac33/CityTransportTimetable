package com.freeman.mac.citytransporttimetable;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Mac on 17. 6. 2017.
 */

public class SignsAdapter extends RecyclerView.Adapter<SignsViewHolder>  {


    private List<SignsRowItem> signs;

    public SignsAdapter(List<SignsRowItem> items)
    {
        this.signs = items;
    }



    @Override
    public SignsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.signs_row, parent, false);
        SignsViewHolder holder = new SignsViewHolder(itemView);
        return holder;
    }



    @Override
    public void onBindViewHolder(SignsViewHolder holder, int position) {
        holder.bind(this.signs.get(position));
    }



    @Override
    public int getItemCount() {
        return this.signs.size();
    }
}
