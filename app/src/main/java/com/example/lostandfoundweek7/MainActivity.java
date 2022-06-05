package com.example.lostandfoundweek7;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ButtonBarLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private Button add_button, show_button, mapButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        add_button = (Button) findViewById(R.id.createButton);
        show_button = (Button) findViewById(R.id.showItem);
        mapButton = (Button) findViewById(R.id.showMapButton);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdvertActivity.class);
                startActivity(intent);
            }
        });

        show_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivityRecyclerView.class);
                startActivity(intent);
            }
        });

        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ItemData.itemList.size() == 0)
                {
                    Toast.makeText(MainActivity.this, "There is no item location to show!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                    startActivity(intent);
                }
            }
        });

    }



}