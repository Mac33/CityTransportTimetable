package com.freeman.mac.citytransporttimetable;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;


import java.util.List;

/**
 * Created by Mac on 11. 3. 2017.
 */

public class TimetableAdapter extends ExpandableRecyclerAdapter<MainItem, StreetItem, MainViewHolder, StreetNameViewHolder> {

    private static final int PARENT_STREET_NAME = 0;
    private static final int PARENT_TIMETABLE = 1;
    private static final int CHILD_STREET_NAME = 2;

    private LayoutInflater mInflater;
    private List<MainItem> mRecipeList;

    public TimetableAdapter(Context context, @NonNull List<MainItem> recipeList) {
        super(recipeList);
        mRecipeList = recipeList;
        mInflater = LayoutInflater.from(context);
    }

    @UiThread
    @NonNull
    @Override
    public MainViewHolder onCreateParentViewHolder(@NonNull ViewGroup parentViewGroup, int viewType) {
        View recipeView;
        switch (viewType) {
            default:
            case PARENT_STREET_NAME:
                recipeView = mInflater.inflate(R.layout.main_street_name, parentViewGroup, false);
                break;
            case PARENT_TIMETABLE:
                recipeView = mInflater.inflate(R.layout.timetable_row, parentViewGroup, false);
                break;
        }
        return new MainViewHolder(recipeView);
    }

    @UiThread
    @NonNull
    @Override
    public StreetNameViewHolder onCreateChildViewHolder(@NonNull ViewGroup childViewGroup, int viewType) {
        View ingredientView;
        ingredientView = mInflater.inflate(R.layout.street_name, childViewGroup, false);
        return new StreetNameViewHolder(ingredientView);
    }

    @UiThread
    @Override
    public void onBindParentViewHolder(@NonNull MainViewHolder recipeViewHolder,
                                       int parentPosition,
                                       @NonNull MainItem item) {
        recipeViewHolder.bind(item);
    }


    @UiThread
    @Override
    public void onBindChildViewHolder(@NonNull StreetNameViewHolder ingredientViewHolder,
                                      int parentPosition,
                                      int childPosition,
                                      @NonNull StreetItem ingredient) {
        ingredientViewHolder.bind(ingredient);
    }



    @Override
    public int getParentViewType(int parentPosition) {
        if (parentPosition >= mRecipeList.size()) {
                    return  PARENT_TIMETABLE;
        }
        if ( mRecipeList.get(parentPosition).isStreetView()) {
            return PARENT_STREET_NAME;
        } else {
            return PARENT_TIMETABLE;
        }
    }

    @Override
    public int getChildViewType(int parentPosition, int childPosition) {
        return  CHILD_STREET_NAME;
    }

    @Override
    public boolean isParentViewType(int viewType) {
        return viewType == PARENT_STREET_NAME || viewType == PARENT_TIMETABLE;
    }

    @Override
    public  void  showBottomEdge()
    {
        super.showBottomEdge();
    }
}

