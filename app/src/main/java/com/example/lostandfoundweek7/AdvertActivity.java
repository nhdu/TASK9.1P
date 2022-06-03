package com.example.lostandfoundweek7;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

public class AdvertActivity extends AppCompatActivity {
    RadioButton lostButton, foundButton;
    TextView name, phone, description, date, location;
    String type;
    Button saveButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_advert);
        lostButton = (RadioButton) findViewById(R.id.lostRadio);
        foundButton = (RadioButton) findViewById(R.id.foundRadio);
        name = findViewById(R.id.posterName);
        phone = findViewById(R.id.posterPhone);
        description = findViewById(R.id.posterDescription);
        date = findViewById(R.id.posterDate);
        location = findViewById(R.id.posterLocation);
        saveButton = (Button) findViewById(R.id.saveButton);

        lostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((RadioButton) v).isChecked();
                // Check which radiobutton was pressed
                if (checked){
                    // Do your coding
                    type = "LOST";
                }
            }
        });

        foundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((RadioButton) v).isChecked();
                // Check which radiobutton was pressed
                if (checked){
                    // Do your coding
                    type = "FOUND";
                }
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemData itemData = new ItemData(name.getText().toString(), phone.getText().toString(), description.getText().toString(),
                        date.getText().toString(), location.getText().toString(), type.toString(), ItemData.itemList.size());
                saveItemDataToDatabase(itemData);
            }
        });

    }

    private void saveItemDataToDatabase(ItemData itemData)
    {
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        sqLiteManager.addItemToDatabase(itemData);
        finish();
    }
}