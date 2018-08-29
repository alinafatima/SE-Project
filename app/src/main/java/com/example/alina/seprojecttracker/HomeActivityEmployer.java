package com.example.alina.seprojecttracker;

import android.Manifest;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeActivityEmployer extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, OnMapReadyCallback, LocationListener, AdapterView.OnItemSelectedListener {
    private ArrayList<LatLng> points;
    Polyline line;
    GoogleMap mGoogleMap;
    SupportMapFragment mapFrag;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    double latitude, longitude;
    ArrayList<LatLng> PolyLinePoints = new ArrayList<LatLng>();
    String name;
    String idemail;
    String text;
    public double latitude1;
    public double longitude2;

    private Spinner dropdown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_employer);
        points = new ArrayList<LatLng>();
        dropdown = (Spinner) findViewById(R.id.spinner1);


        final List<String> items = new ArrayList<String>();
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, items);;

        //final String[] items = new String[]{"Fatima", "Alina", "Abeera", "Rabiah"};
        FirebaseDatabase.getInstance().getReference("Coordinates")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Log.d("Alina", "sksjwd");
                            user User = snapshot.getValue(user.class);
                            items.add(User.username);
                            if (adapter != null) {
                                adapter.notifyDataSetChanged();
                            }
                            Log.d("Alina", User.username);
                            // System.out.println(User.email);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(adapter);
        //getSupportA
        // 0+ctionBar().setTitle("Map Location Activity");
        mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFrag.getMapAsync(this);


        dropdown.setOnItemSelectedListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        //stop location updates when Activity is no longer active
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                buildGoogleApiClient();
                mGoogleMap.setMyLocationEnabled(true);
            } else {
                //Request Location Permission
                checkLocationPermission();
            }
        } else {
            buildGoogleApiClient();
            mGoogleMap.setMyLocationEnabled(true);
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
    }


    @Override
    public void onLocationChanged(Location location) {
        /*mLastLocation = location;
        LatLng latLng = new LatLng(latitude, longitude);

        points.add(latLng);
        //FirebaseDatabase database = FirebaseDatabase.getInstance();
        //DatabaseReference myRef = database.getReference("Coordinates");

        //user coord=new user( location.getLatitude(),location.getLongitude());
        //List<Double> list = Arrays.asList(location.getLatitude(), location.getLongitude());
        //myRef.child(idemail).setValue(coord);

        latLng = new LatLng(latitude1, longitude2);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mCurrLocationMarker = mGoogleMap.addMarker(markerOptions);
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        drawMarker(latLng);
        polyline(latLng);
        mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(11));
        CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(latitude1, longitude2))      // Sets the center of the map to location user
                .zoom(5)                   // Sets the zoom
                .bearing(90)                // Sets the orientation of the camera to east
                .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        */
    }

    private void polyline(LatLng latLng) {

        MarkerOptions markerOptions = new MarkerOptions();

        // Setting latitude and longitude of the marker position
        markerOptions.position(latLng);

        // Setting titile of the infowindow of the marker
        markerOptions.title("Position");

        // Setting the content of the infowindow of the marker
        markerOptions.snippet(latLng.latitude + ", " + latLng.longitude);

        //can move Marker on the all map
        markerOptions.draggable(true);

        // Instantiating the class PolylineOptions to plot polyline in the map
        PolylineOptions polylineOptions = new PolylineOptions();

        // Setting the color of the polyline
        polylineOptions.color(Color.RED);

        // Setting the width of the polyline
        polylineOptions.width(3);

        // Adding the taped point to the ArrayList
        PolyLinePoints.add(latLng);

        // Setting points of polyline
        polylineOptions.addAll(PolyLinePoints);

        // Adding the polyline to the map
        mGoogleMap.addPolyline(polylineOptions);

        // Adding the marker to the map
        mGoogleMap.addMarker(markerOptions);
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {

                new AlertDialog.Builder(this).setTitle("Location Permission Needed").setMessage("This app needs the Location permission, please accept to use location functionality").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ActivityCompat.requestPermissions(HomeActivityEmployer.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
                    }
                }).create().show();


            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mGoogleMap.setMyLocationEnabled(true);
                    }

                } else {
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    private void drawMarker(LatLng point) {
        // Creating an instance of MarkerOptions
        if(mCurrLocationMarker!=null){

            mCurrLocationMarker.remove();
        }
        MarkerOptions markerOptions = new MarkerOptions();

        // Setting latitude and longitude for the marker
        markerOptions.position(point);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));

        // Adding marker on the Google Map
        //mGoogleMap.addMarker(markerOptions);

        mCurrLocationMarker = mGoogleMap.addMarker(markerOptions);
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(point));
        //drawMarker(latLng);
        //polyline(latLng);
        mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(11));
        CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(latitude1, longitude2))      // Sets the center of the map to location user
                .zoom(5)                   // Sets the zoom
                .bearing(90)                // Sets the orientation of the camera to east
                .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // your code here
        text = dropdown.getSelectedItem().toString();
        SharedPreferences prefs = getSharedPreferences("SEProjectTracker", MODE_PRIVATE);//"No name defined" is the default value.
        idemail = prefs.getString("identifierString", "no email");
        Log.d("Alina", "SelectedEmail");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Coordinates").child(text);
        //DatabaseReference myRef2=database.getReference("Coordinates").child(text).child("longitude");
        // Read from the database
        Log.d("Alina", "hererere");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                user value1 = dataSnapshot.getValue(user.class);
                latitude1 = value1.latitude;
                longitude2 = value1.longitude;
                Log.d("Alina", "Value is: " + value1.latitude);
                Log.d("Alina", "Long is : " + value1.longitude);
                LatLng latLng = new LatLng(latitude1, longitude2);
                drawMarker(latLng);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.d("Alina", "Failed to read value.", error.toException());
            }
        });
                /*
                myRef2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        double value2 = dataSnapshot.getValue(double.class);
                        longitude2=value2;
                        Log.d("Alina", "Value is: " + value2);
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.d("Alina", "Failed to read value.", error.toException());
                    }
                });

                */


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
