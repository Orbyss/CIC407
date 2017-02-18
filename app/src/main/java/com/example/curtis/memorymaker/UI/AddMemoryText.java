package com.example.curtis.memorymaker.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.curtis.memorymaker.business.Helper;
import com.example.curtis.memorymaker.models.Memory;
import com.example.curtis.memorymaker.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.raizlabs.android.dbflow.data.Blob;

import java.io.IOException;

public class AddMemoryText extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private Memory mMemory;

    private TextView txtInputMemory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_memory_text);

        mMemory = (Memory) getIntent().getSerializableExtra("MemoryData");

        txtInputMemory = (TextView) findViewById(R.id.txt_input_memory);

        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.d("Permission:", "Nope");
            return;
        }
        else {
            Log.d("Permission:", "Granted");
        }

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            Toast.makeText(getApplicationContext(), String.valueOf(mLastLocation.getLatitude()) + ", "
                    + String.valueOf(mLastLocation.getLongitude()), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d("Connection: ", "Suspended - " + String.valueOf(i));
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
            Log.d("Connect fail result: ", connectionResult.getErrorMessage());
    }

    public void storeMemory(View view) {
        mMemory.setLocation(mLastLocation.toString());
        mMemory.setDescription(txtInputMemory.getText().toString());

        Uri imageUri = Uri.parse(mMemory.getId());

        //                Bitmap imageBitmap = ((BitmapDrawable)imgView.getDrawable()).getBitmap();
//                mMemory = new Memory(selectedImageUri.toString(), new Blob(Helper.getImageAsByteArray(imageBitmap)), null, null);

        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            mMemory.setImage(new Blob(Helper.getImageAsByteArray(bitmap)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        mMemory.save();

        Intent intent = new Intent(getApplicationContext(), MainMenu.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

/**
    private boolean isLocationEnabled() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }
**/
}
