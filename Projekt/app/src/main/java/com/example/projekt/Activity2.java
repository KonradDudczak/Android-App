package com.example.projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class Activity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        TextView txt_source = (TextView) findViewById(R.id.txt2);
        TextView txt_destination = (TextView) findViewById(R.id.txt12);
        TextView txt_distance = (TextView) findViewById(R.id.txt5);
        TextView txt_consumption = (TextView) findViewById(R.id.txt6);
        TextView txt_cost = (TextView) findViewById(R.id.txt11);
        Button mButton = (Button) findViewById(R.id.button3);


        String total_price;
        String total_consumption;

        Intent intent = getIntent();
        String price = intent.getStringExtra("price");
        String consumption = intent.getStringExtra("consumption");
        String source = intent.getStringExtra("source");
        String destination = intent.getStringExtra("destination");
        String remote = intent.getStringExtra("remote");

        total_consumption = String.valueOf(Math.round((Double.parseDouble(remote)/100 * Double.parseDouble(consumption))*100)/100.0);
        total_price = String.valueOf((int)(Double.parseDouble(total_consumption)*Double.parseDouble(price)));

        txt_source.setText(source);
        txt_destination.setText(destination);
        txt_distance.setText(remote + "   km");
        txt_consumption.setText(total_consumption + "   litrów" );
        txt_cost.setText(total_price + "   złotych");

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), "SZEROKIEJ DROGI", Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent1);


            }
        });




    }
}