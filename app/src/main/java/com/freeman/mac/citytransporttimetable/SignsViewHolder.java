package com.freeman.mac.citytransporttimetable;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Mac on 17. 6. 2017.
 */

public class SignsViewHolder extends  RecyclerView.ViewHolder{


    private TextView signTextView;
    private TextView descriptionTextView;
    private TextView dashSignTextView;


    public SignsViewHolder(View itemView) {
        super(itemView);
        this.signTextView = (TextView) itemView.findViewById(R.id.SignsRowSign);
        this.descriptionTextView = (TextView) itemView.findViewById(R.id.SignsRowDescription);
        this.dashSignTextView = (TextView) itemView.findViewById(R.id.SignsRowDash);
    }



    public void bind(@NonNull SignsRowItem item) {

        if (item.Sign.isEmpty()){
            this.signTextView.setVisibility(View.GONE);
            this.dashSignTextView.setVisibility(View.GONE);
        }else{
            this.signTextView.setText(item.Sign);
        }
        this.descriptionTextView.setText(item.Description);

    }


}
