package com.freeman.mac.citytransporttimetable.StreetNameActivity;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.freeman.mac.citytransporttimetable.R;
import com.freeman.mac.citytransporttimetable.interfaces.ISelectedItem;
import com.freeman.mac.citytransporttimetable.model.Street;

import java.util.List;

/**
 * Created by Mac on 18. 3. 2017.
 */


public class StreetName_Adapter extends RecyclerView.Adapter<StreetName_ViewHolder> {

    private List<Street> streets = null;


    private ISelectedItem paStreetSelectedListener = null;

    public StreetName_Adapter(List<Street> streets) {
        this.streets = streets;
    }

    @Override
    public StreetName_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_street_name, parent, false);

        StreetName_ViewHolder holder = new StreetName_ViewHolder(itemView);
        holder.setSelectedStreetListener(new ISelectedItem() {
            @Override
            public void OnSelectedItem(int index) {
                setStreet(index);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(StreetName_ViewHolder holder, int position) {
        Street item = this.streets.get(position);
        holder.bind(item.Name);
    }

    public void setStreetSelectedListener(ISelectedItem listener) {
        this.paStreetSelectedListener = listener;
    }

    private void setStreet(int index) {
        if (this.paStreetSelectedListener != null) {
            this.paStreetSelectedListener.OnSelectedItem(index);
        }
    }

    @Override
    public int getItemCount() {
        return this.streets.size();
    }
}
