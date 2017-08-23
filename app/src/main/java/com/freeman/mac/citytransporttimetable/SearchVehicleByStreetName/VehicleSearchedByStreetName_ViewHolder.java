package com.freeman.mac.citytransporttimetable.SearchVehicleByStreetName;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.freeman.mac.citytransporttimetable.R;
import com.freeman.mac.citytransporttimetable.model.TransportTimetables;
import com.freeman.mac.citytransporttimetable.model.Vehicle;

import org.w3c.dom.Text;


/**
 * Created by Mac on 8. 8. 2017.
 */

public class VehicleSearchedByStreetName_ViewHolder extends RecyclerView.ViewHolder {

    ImageView imgNumber;
    ImageView imgLowVehicleSign;
    TextView txtDirectionName;
    TextView txtTime;
    TextView txtAdditionalInfo;


    public VehicleSearchedByStreetName_ViewHolder (View itemView) {
        super(itemView);

        this.imgNumber  = (ImageView)itemView.findViewById(R.id.imgVehicleSearchNumber);
        this.imgLowVehicleSign  = (ImageView)itemView.findViewById(R.id.imgLowVehicleSign);
        this.txtDirectionName= (TextView) itemView.findViewById(R.id.txtDirectionName);
        this.txtTime= (TextView) itemView.findViewById(R.id.txtTime);
        this.txtAdditionalInfo= (TextView) itemView.findViewById(R.id.txtAdditionalInfo);


    }

    public void bind(VehicleSearchedByStreetName item )
    {

        String time = String.format("%02d",item.Hour) + ":" + String.format("%02d",item.Minute);
        this.txtTime.setText(time);
        this.txtDirectionName.setText(item.DirectionName);

        Vehicle   vehicle = TransportTimetables.getInstance().getVehicle(item.Number);
        if (vehicle!=null)
        {
            this.imgNumber.setImageResource(vehicle.IconResId);
            Integer vehicleColor = ContextCompat.getColor(this.itemView.getContext(), vehicle.ColorResId);
            this.imgNumber.setColorFilter(vehicleColor);

        }
        if(item.Signs.contains("n"))
        {
            this.imgLowVehicleSign.setVisibility(View.VISIBLE);
        }else {
            this.imgLowVehicleSign.setVisibility(View.GONE);
        }

        this.txtAdditionalInfo.setText(item.getAdditionalInfo());

    }
}


