package com.freeman.mac.citytransporttimetable;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Mac on 20. 5. 2017.
 */

public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

    private int columns;
    private  int spacing;

    public GridSpacingItemDecoration(int columns, int spacing) {
        this.columns= columns;
        this.spacing = spacing;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view); // item position

        if (position < parent.getAdapter().getItemCount() - columns )
        {
            if (position < columns )
            {
                outRect.top = 1 * this.spacing;
            }
            else
            {
                outRect.top = -2 * this.spacing;
            }
            outRect.bottom = 0;
        }

    }
}