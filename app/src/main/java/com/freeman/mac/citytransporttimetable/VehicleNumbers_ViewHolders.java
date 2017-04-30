package com.freeman.mac.citytransporttimetable;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.freeman.mac.citytransporttimetable.interfaces.ISelectedItemByInteger;
import com.freeman.mac.citytransporttimetable.model.Vehicle;
import com.freeman.mac.citytransporttimetable.model.VehicleCategory;

/**
 * Created by Mac on 2. 4. 2017.
 */

public class VehicleNumbers_ViewHolders extends RecyclerView.ViewHolder {

    ImageView[] images = new ImageView[14];
    private ISelectedItemByInteger selectVehicle;
    private TextView description;
    private VehicleCategory vehicleCategory;
    private ImageView mainVehicleIcon;
    private int categoryColor;


    public VehicleNumbers_ViewHolders(View itemView) {
        super(itemView);

        images[0] = (ImageView) itemView.findViewById(R.id.imgNumber01);
        images[1] = (ImageView) itemView.findViewById(R.id.imgNumber02);
        images[2] = (ImageView) itemView.findViewById(R.id.imgNumber03);
        images[3] = (ImageView) itemView.findViewById(R.id.imgNumber04);
        images[4] = (ImageView) itemView.findViewById(R.id.imgNumber05);
        images[5] = (ImageView) itemView.findViewById(R.id.imgNumber06);
        images[6] = (ImageView) itemView.findViewById(R.id.imgNumber07);
        images[7] = (ImageView) itemView.findViewById(R.id.imgNumber11);
        images[8] = (ImageView) itemView.findViewById(R.id.imgNumber12);
        images[9] = (ImageView) itemView.findViewById(R.id.imgNumber13);
        images[10] = (ImageView) itemView.findViewById(R.id.imgNumber14);
        images[11] = (ImageView) itemView.findViewById(R.id.imgNumber15);
        images[12] = (ImageView) itemView.findViewById(R.id.imgNumber16);
        images[13] = (ImageView) itemView.findViewById(R.id.imgNumber17);
        description = (TextView) itemView.findViewById(R.id.VehicleCategoryDesription);
        mainVehicleIcon = (ImageView) itemView.findViewById(R.id.mainIcon);

    }

    public void bind(VehicleCategory vehicleCategory) {
        this.vehicleCategory = vehicleCategory;
        for (ImageView item : images) {
            item.setImageResource(android.R.color.transparent);
            item.setVisibility(View.INVISIBLE);
        }

        int index = 0;
        int vehicleColor = Integer.MIN_VALUE;

        for (Vehicle item : this.vehicleCategory.Vehicles) {
            images[index].setVisibility(View.VISIBLE);
            images[index].setImageResource(item.IconResId);
            if(vehicleColor == Integer.MIN_VALUE)
            {
                vehicleColor = ContextCompat.getColor(this.itemView.getContext(), item.ColorResId);
            }

            images[index].setColorFilter(vehicleColor);
            subscribeView(images[index], item.Number);
            index++;

        }
        description.setText(this.vehicleCategory.Description);
        this.setMainVehicleIcon(vehicleColor);
    }


    private void setMainVehicleIcon(int color) {
        if (this.vehicleCategory.Type == Vehicle.eVehicleType.Trolleybus) {
            mainVehicleIcon.setImageResource(R.drawable.trolleybus_main);
        } else {
            mainVehicleIcon.setImageResource(R.drawable.bus_main);
        }
        mainVehicleIcon.setColorFilter(color);

    }

    public void setSelectVehicle(ISelectedItemByInteger listener) {
        this.selectVehicle = listener;
    }


    void subscribeView(View view, int number) {
        view.setTag(number);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int number = (int) view.getTag();
                selectVehicle.OnSelectedItem(number);
            }
        });
    }


}
