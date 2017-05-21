package com.freeman.mac.citytransporttimetable.components;

/**
 * Created by Mac on 30. 4. 2017.
 */

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;
import com.freeman.mac.citytransporttimetable.interfaces.*;



public class ObservableScrollView extends android.support.v4.widget.NestedScrollView {

    private IScrollViewListener scrollViewListener = null;

    public ObservableScrollView(Context context) {
        super(context);
    }

    public ObservableScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public ObservableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScrollViewListener(IScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if(scrollViewListener != null) {
            scrollViewListener.onScrollChanged(this, x, y, oldx, oldy);
        }
    }

}
