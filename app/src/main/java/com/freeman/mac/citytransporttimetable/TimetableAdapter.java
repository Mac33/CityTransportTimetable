package com.freeman.mac.citytransporttimetable;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Mac on 11. 3. 2017.
 */

public class TimetableAdapter extends RecyclerView.Adapter<TimetableViewHolder> {


    private List<TimetableRow> mRecipeList;


    public TimetableAdapter(@NonNull List<TimetableRow> recipeList) {

        mRecipeList = recipeList;

    }

    @Override
    public TimetableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.timetable_row, parent, false);
        TimetableViewHolder holder = new TimetableViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(TimetableViewHolder holder, int position) {
        holder.bind(mRecipeList.get(position));
    }

    @Override
    public int getItemCount() {
        return mRecipeList.size();
    }
}

