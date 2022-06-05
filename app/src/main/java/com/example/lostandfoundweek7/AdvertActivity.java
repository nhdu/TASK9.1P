package com.example.lostandfoundweek7;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.net.ssl.SSLEngineResult;

public class AdvertActivity extends AppCompatActivity {
    RadioButton lostButton, foundButton;
    TextView name, phone, description, date, locationText;
    String type;
    Button saveButton, currentLocationButton;
    double longitude, latitude;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;

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
        locationText = findViewById(R.id.posterLocation);
        saveButton = (Button) findViewById(R.id.saveButton);
        currentLocationButton = (Button) findViewById(R.id.getLocationButton);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        Places.initialize(getApplicationContext(), "AIzaSyAteRMqKg4qD388DgvX4mJq23Sok27MJzI");
        ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        Intent data = result.getData();
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            // There are no request codes

                            Place place = Autocomplete.getPlaceFromIntent(data);
                            locationText.setText(place.getAddress());
                            longitude = place.getLatLng().longitude;
                            latitude = place.getLatLng().latitude;
                        } else if (result.getResultCode() == AutocompleteActivity.RESULT_ERROR) {
                            Status status = Autocomplete.getStatusFromIntent(data);
                            Toast.makeText(AdvertActivity.this, status.getStatusMessage(), Toast.LENGTH_LONG).show();

                        }
                    }
                });

        //Set EditText non focusable;
        locationText.setFocusable(false);
        locationText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.NAME);
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fieldList).build(AdvertActivity.this);
                //Start activity result
                someActivityResultLauncher.launch(intent);
            }
        });
        lostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((RadioButton) v).isChecked();
                // Check which radiobutton was pressed
                if (checked) {
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
                if (checked) {
                    // Do your coding
                    type = "FOUND";
                }
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemData itemData = new ItemData(name.getText().toString(), phone.getText().toString(), description.getText().toString(),
                        date.getText().toString(), locationText.getText().toString(), type.toString(), ItemData.itemList.size(), longitude, latitude);
                saveItemDataToDatabase(itemData);
            }
        });

        currentLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
            }
        });

    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            ActivityCompat.requestPermissions(AdvertActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }

        LocationRequest mLocationRequest = LocationRequest.create();
        mLocationRequest.setInterval(60000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationCallback mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                Toast.makeText(getApplicationContext()," location result is  " + locationResult, Toast.LENGTH_LONG).show();

                if (locationResult == null) {
                    Toast.makeText(getApplicationContext(),"current location is null ", Toast.LENGTH_LONG).show();
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    if (location != null) {
                        Toast.makeText(getApplicationContext(),"current location is " + location.getLongitude(), Toast.LENGTH_LONG).show();
                        locationText.setText("LONGITUDE: " + String.valueOf(location.getLongitude()) + ", LATITUDE: " + String.valueOf(location.getLatitude()));
                        longitude = location.getLongitude();
                        latitude = location.getLatitude();
                        //TODO: UI updates.
                    }
                }
            }
        };

        fusedLocationProviderClient.requestLocationUpdates(mLocationRequest, mLocationCallback, null);
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null)
                {
                    Toast.makeText(getApplicationContext(),"longitude and latitude is received " + location.getLongitude(), Toast.LENGTH_LONG).show();
                    locationText.setText("LONGITUDE: " + String.valueOf(location.getLongitude()) + ", LATITUDE: " + String.valueOf(location.getLatitude()));
                    longitude = location.getLongitude();
                    latitude = location.getLatitude();
                }
            }
        });
    }


    private void saveItemDataToDatabase(ItemData itemData)
    {
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        sqLiteManager.addItemToDatabase(itemData);
        sqLiteManager.populateItemListArray();
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (REQUEST_CODE)
        {
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    getLocation();
                }
                break;
        }
    }
}