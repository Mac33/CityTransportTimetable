package com.freeman.mac.citytransporttimetable;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.freeman.mac.citytransporttimetable.SearchVehicleByStreetName.ISearchVehicleByStreetName;
import com.freeman.mac.citytransporttimetable.SearchVehicleByStreetName.VehicleSearchByStreetNameFragment;
import com.freeman.mac.citytransporttimetable.db.DataAdapter;
import com.freeman.mac.citytransporttimetable.interfaces.ISelectedItemByInteger;
import com.freeman.mac.citytransporttimetable.model.StringUtils;
import com.freeman.mac.citytransporttimetable.model.TransportTimetables;
import com.freeman.mac.citytransporttimetable.model.Vehicle;

import java.util.ArrayList;



public class MainActivity extends AppCompatActivity {

    private LinearLayout focusDummy;
    private AutoCompleteTextView searchTextView;
    private FrameLayout progressBarHolder;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.initVehicles();
        this.setContentView(R.layout.activity_main);
        this.initSpinner();
        this.initToolbar();
        this.initFindButton();
        this.initStreetAutoCompleteTextView();
        this.ShowVehicleNumbersView();

    }


    private void initSpinner()
    {
        progressBarHolder = (FrameLayout) findViewById(R.id.progressBarHolder);
        this.hideSpinner();
    }



    private void ShowVehicleNumbersView() {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        VehicleNumbersFragment vehicleNumbersFragment = new VehicleNumbersFragment();
        vehicleNumbersFragment.setSelectItemListener(new ISelectedItemByInteger() {
            @Override
            public void OnSelectedItem(int index) {
               showSpinner();
            }
        });
        ft.replace(R.id.fragment_container, vehicleNumbersFragment, VehicleNumbersFragment.Tag);
        ft.commit();

    }



    private void initStreetAutoCompleteTextView() {

        searchTextView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        searchTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    handled = true;
                    search();
                }
                return handled;
            }
        });

        ArrayList<String> streets = this.getStreets();
        ArrayAdapter<String> items = new ArrayAdapter<>(this, R.layout.street_finder, R.id.autoCompleteItem, streets);
        searchTextView.setAdapter(items);
        searchTextView.setThreshold(0);
        searchTextView.setText(StringUtils.Empty);
    }



    private void search() {
        this.hideKeyboard();
        this.showSearchResultFragment();
   }



   private  void  hideKeyboard()
   {
       if (focusDummy==null)
           focusDummy = (LinearLayout) findViewById(R.id.focusDummyView);
       focusDummy.requestFocus();
       InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
       imm.hideSoftInputFromWindow(focusDummy.getWindowToken(), 0);

   }



    private void showSearchResultFragment()
    {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment vehiclesFragment = this.getSupportFragmentManager().findFragmentByTag(VehicleNumbersFragment.Tag);
        Fragment vehicleSearchFragment = this.getSupportFragmentManager().findFragmentByTag(VehicleSearchByStreetNameFragment.Tag);

        if (vehicleSearchFragment == null) {
            vehicleSearchFragment = new VehicleSearchByStreetNameFragment();
            ft.add(R.id.fragment_container, vehicleSearchFragment, VehicleSearchByStreetNameFragment.Tag);
        }


        if (vehiclesFragment.isVisible()) {
            ft.hide(vehiclesFragment);
            ft.addToBackStack(VehicleNumbersFragment.Tag);
            ft.show(vehicleSearchFragment);        }

        ft.commit();

        String streetName = searchTextView.getText().toString();
        ((ISearchVehicleByStreetName)vehicleSearchFragment).setStreetName(streetName);
    }



    private ArrayList<String> getStreets()
    {
        Context urContext = getApplicationContext();
        DataAdapter mDbHelper = new DataAdapter(urContext);
        mDbHelper.createDatabase();
        mDbHelper.open();

        ArrayList<String> streets =  mDbHelper.getStreetNames();
        return  streets;
    }


    private void  initFindButton()
    {
        ImageView imgFind = (ImageView) findViewById(R.id.imgFindStreet);
        imgFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search();
            }
        });
    }



    void initToolbar()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(StringUtils.Empty);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }



    @Override
    public boolean onSupportNavigateUp() {
        this.onBackPressed();
        return true;
    }



    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
            this.clearSearchText();
        }else
        {
            super.onBackPressed();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        this.hideSpinner();
    }

    private  void  clearSearchText()
    {
        searchTextView.setText(StringUtils.Empty);
    }



    public void initVehicles()
    {
        TransportTimetables.getInstance().getVehicles().clear();
        this.addVehicle(1, Vehicle.eVehicleType.Trolleybus,R.color.colorVehicleTrolleybus,R.drawable.number_01,R.raw.vehicle_01);
        this.addVehicle(3, Vehicle.eVehicleType.Trolleybus,R.color.colorVehicleTrolleybus,R.drawable.number_03,R.raw.vehicle_03);
        this.addVehicle(4, Vehicle.eVehicleType.Trolleybus,R.color.colorVehicleTrolleybus,R.drawable.number_04,R.raw.vehicle_04);
        this.addVehicle(5, Vehicle.eVehicleType.Trolleybus,R.color.colorVehicleTrolleybus,R.drawable.number_05,R.raw.vehicle_05);
        this.addVehicle(6, Vehicle.eVehicleType.Trolleybus,R.color.colorVehicleTrolleybus,R.drawable.number_06,R.raw.vehicle_06);
        this.addVehicle(7, Vehicle.eVehicleType.Trolleybus,R.color.colorVehicleTrolleybus,R.drawable.number_07,R.raw.vehicle_07);
        this.addVehicle(14,Vehicle.eVehicleType.Trolleybus,R.color.colorVehicleTrolleybus,R.drawable.number_14,R.raw.vehicle_14);
        this.addVehicle(16,Vehicle.eVehicleType.Trolleybus,R.color.colorVehicleTrolleybus,R.drawable.number_16,R.raw.vehicle_16);

        this.addVehicle(20,Vehicle.eVehicleType.CityBus,R.color.colorVehicleCityBus,R.drawable.number_20 ,R.raw.vehicle_20);
        this.addVehicle(21,Vehicle.eVehicleType.CityBus,R.color.colorVehicleCityBus,R.drawable.number_21 ,R.raw.vehicle_21);
        this.addVehicle(22,Vehicle.eVehicleType.CityBus,R.color.colorVehicleCityBus,R.drawable.number_22 ,R.raw.vehicle_22);
        this.addVehicle(24,Vehicle.eVehicleType.CityBus,R.color.colorVehicleCityBus,R.drawable.number_24 ,R.raw.vehicle_24);
        this.addVehicle(26,Vehicle.eVehicleType.CityBus,R.color.colorVehicleCityBus,R.drawable.number_26 ,R.raw.vehicle_26);
        this.addVehicle(27,Vehicle.eVehicleType.CityBus,R.color.colorVehicleCityBus,R.drawable.number_27 ,R.raw.vehicle_27);
        this.addVehicle(29,Vehicle.eVehicleType.CityBus,R.color.colorVehicleCityBus,R.drawable.number_29 ,R.raw.vehicle_29);
        this.addVehicle(30,Vehicle.eVehicleType.CityBus,R.color.colorVehicleCityBus,R.drawable.number_30 ,R.raw.vehicle_30);
        this.addVehicle(31,Vehicle.eVehicleType.CityBus,R.color.colorVehicleCityBus,R.drawable.number_31 ,R.raw.vehicle_31);
        this.addVehicle(67,Vehicle.eVehicleType.CityBus,R.color.colorVehicleCityBus,R.drawable.number_67 ,R.raw.vehicle_67);

        this.addVehicle(25,Vehicle.eVehicleType.BusForSelectedPassenger,R.color.colorVehicleSelectedPassenger,R.drawable.number_25 ,R.raw.vehicle_25);
        this.addVehicle(35,Vehicle.eVehicleType.BusForSelectedPassenger,R.color.colorVehicleSelectedPassenger,R.drawable.number_35 ,R.raw.vehicle_35);

        this.addVehicle(50,Vehicle.eVehicleType.NightBus,R.color.colorVehicleNightBus,R.drawable.number_50 ,R.raw.vehicle_50);

    }



    private void addVehicle(int number,
                            Vehicle.eVehicleType type,
                            int colorId,
                            int iconResId,
                            int dataId  )

    {
        TransportTimetables.getInstance().setContext(this.getApplicationContext());
        Vehicle vehicle = new Vehicle();
        vehicle.Type = type;
        vehicle.ColorResId = colorId;
        vehicle.IconResId = iconResId;
        vehicle.Number = number;
        vehicle.DataResId = dataId;



        TransportTimetables.getInstance().getVehicles().add(vehicle);
    }



    private  void  showSpinner()
    {
        if(progressBarHolder!=null)
            progressBarHolder.setVisibility(View.VISIBLE);
    }


    private void  hideSpinner()
    {
        if(progressBarHolder!=null)
            progressBarHolder.setVisibility(View.GONE);
    }




}
