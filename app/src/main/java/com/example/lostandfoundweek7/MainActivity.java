package com.example.lostandfoundweek7;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    private Button add_button, show_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        add_button = (Button) findViewById(R.id.createButton);
        show_button = (Button) findViewById(R.id.showItem);

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

    }



}