package com.example.lostandfoundweek7;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class ItemDetailActivity extends AppCompatActivity {
    RadioButton lostButton, foundButton;
    TextView name, phone, description, date, location;
    Button removeButton;
    String itemType, nameDetail, phoneDetail, descDetail, dateDetail, locationDetail;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_details);
        lostButton = (RadioButton) findViewById(R.id.lostDetail);
        foundButton = (RadioButton) findViewById(R.id.foundDetail);
        name = findViewById(R.id.nameDetail);
        phone = findViewById(R.id.phoneDetail);
        description = findViewById(R.id.descriptionDetail);
        date = findViewById(R.id.dateDetail);
        location = findViewById(R.id.locationDetail);
        removeButton = (Button) findViewById(R.id.removeButton);

        Intent intent = getIntent();
        itemType = intent.getStringExtra("type");
        nameDetail = intent.getStringExtra("name");
        phoneDetail = intent.getStringExtra("phone");
        descDetail = intent.getStringExtra("description");
        dateDetail = intent.getStringExtra("date");
        locationDetail = intent.getStringExtra("location");
        id = intent.getIntExtra("id",0);


        if (itemType.equals("FOUND"))
        {
            foundButton.setChecked(true);
            lostButton.setChecked(false);
        }
        else {
            lostButton.setChecked(true);
            foundButton.setChecked(false);
        }

        name.setText(nameDetail);
        phone.setText(phoneDetail);
        description.setText(descDetail);
        date.setText(dateDetail);
        location.setText(locationDetail);

        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteManager sqLiteManager = new SQLiteManager(v.getContext());
                String id_string = String.valueOf(id);
                Integer deletedRow = sqLiteManager.deleteItem(id_string);


                if (deletedRow > 0)
                {
                    Toast.makeText(ItemDetailActivity.this, "DATA DELETED", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(ItemDetailActivity.this, "DATA NOT DELETED", Toast.LENGTH_LONG).show();
                }
                finish();

            }
        });




    }
}