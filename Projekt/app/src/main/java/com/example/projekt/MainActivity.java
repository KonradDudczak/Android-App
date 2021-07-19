package com.example.projekt;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.net.ssl.SSLEngineResult;

public class MainActivity extends AppCompatActivity {

    EditText edt_source;
    EditText edt_destination;
    EditText edt_fuelprice;
    EditText edt_fuelconsumption;
    Button mButton;
    String sType;
    String route;
    double lat1 = 0, long1 = 0, lat2 = 0, long2 = 0;
    int flag = 0;

    private void distance(double lat1, double long1, double lat2, double long2) {
        double longDiff = long1 - long2;
        double distance = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos((deg2rad(lat1)))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(longDiff));
        distance = Math.acos(distance);

        distance = rad2deg(distance);

        // w milach
        distance = distance * 60 * 1.1515;
        //w kilometrach
        distance = distance * 1.609344 * 1.15;
        distance = (int)distance;


        route = String.valueOf(distance);
    }

    private double rad2deg(double distance) {
        return (distance * 180.0 / Math.PI);
    }


    private double deg2rad(double lat1) {
        return (lat1*Math.PI/180.0);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edt_source = (EditText) findViewById(R.id.edt2);
        edt_destination = (EditText) findViewById(R.id.edt3);
        edt_fuelprice = (EditText) findViewById(R.id.edt4);
        edt_fuelconsumption = (EditText) findViewById(R.id.edt5);
        mButton = (Button) findViewById(R.id.button2);

        Places.initialize(getApplicationContext(), "AIzaSyCsUrEfi-7DsNxkqg7jWVnaXoZrg49hGPw");
        edt_source.setFocusable(false);
        edt_source.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sType = "source";

                List<Place.Field> fields = Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG);
                Intent intent = new Autocomplete.IntentBuilder(
                        AutocompleteActivityMode.OVERLAY, fields
                ).build(MainActivity.this);

                startActivityForResult(intent, 100);


            }
        });

        edt_destination.setFocusable(false);
        edt_destination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sType = "destination";

                List<Place.Field> fields = Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG);
                Intent intent = new Autocomplete.IntentBuilder(
                        AutocompleteActivityMode.OVERLAY, fields
                ).build(MainActivity.this);

                startActivityForResult(intent, 100);
            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityTwo();
            }

        });


    }

    private void openActivityTwo(){
        Intent intent = new Intent(this, Activity2.class);

        String price = edt_fuelprice.getText().toString();
        String consumption = edt_fuelconsumption.getText().toString();
        String source = edt_source.getText().toString();
        String destination = edt_destination.getText().toString();
        String remote = String.valueOf(route);

        if(price.equals("") || consumption.equals("") || source.equals("") || destination.equals("")){
            Toast.makeText(this, "Wszystkie pola musza byc wypełnione", Toast.LENGTH_LONG).show();
        }


        else {

            intent.putExtra("price", price );
            intent.putExtra("consumption", consumption);
            intent.putExtra("source", source);
            intent.putExtra("destination", destination);
            intent.putExtra("remote", remote);
            startActivity(intent);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


            if (requestCode == 100 && resultCode == RESULT_OK && sType.equals("source")) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                flag++;
                edt_source.setText(place.getAddress());
                String sSource = String.valueOf(place.getLatLng());
                sSource = sSource.replaceAll("lat/lng: ", "");
                sSource = sSource.replace("(", "");
                sSource = sSource.replace(")", "");
                String[] split = sSource.split(",");
                lat1 = Double.parseDouble(split[0]);
                long1 = Double.parseDouble(split[1]);

            }
            if(requestCode == 100 && resultCode == RESULT_OK && sType.equals("destination")) {
                    Place place = Autocomplete.getPlaceFromIntent(data);
                    flag++;
                    edt_destination.setText(place.getAddress());
                    String sDestination = String.valueOf(place.getLatLng());
                    sDestination = sDestination.replaceAll("lat/lng: ", "");
                    sDestination = sDestination.replace("(", "");
                    sDestination = sDestination.replace(")", "");
                    String[] split2 = sDestination.split(",");
                    lat2 = Double.parseDouble(split2[0]);
                    long2 = Double.parseDouble(split2[1]);


            }

        if (flag>=2){distance(lat1, long1, lat2, long2);}

            
            if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                Status status = Autocomplete.getStatusFromIntent(data);

                Toast.makeText(this, status.getStatusMessage()
                        , Toast.LENGTH_SHORT).show();
            }


    }




}
