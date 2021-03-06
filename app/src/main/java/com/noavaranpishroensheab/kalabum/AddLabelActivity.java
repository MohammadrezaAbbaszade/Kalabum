package com.noavaranpishroensheab.kalabum;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.carto.graphics.Color;
import com.carto.styles.AnimationStyle;
import com.carto.styles.AnimationStyleBuilder;
import com.carto.styles.AnimationType;
import com.carto.styles.MarkerStyle;
import com.carto.styles.MarkerStyleBuilder;
import com.carto.styles.TextStyle;
import com.carto.styles.TextStyleBuilder;
import com.carto.ui.ClickType;
import com.carto.ui.MapEventListener;
import com.carto.vectorelements.VectorElementVector;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.single.PermissionListener;

import org.neshan.common.model.LatLng;
import org.neshan.mapsdk.MapView;
import org.neshan.mapsdk.internal.utils.BitmapUtils;
import org.neshan.mapsdk.model.Label;
import org.neshan.mapsdk.model.Marker;

import java.text.DateFormat;
import java.util.Date;

public class AddLabelActivity extends AppCompatActivity {


    // map UI element
    final int REQUEST_CODE = 123;
    private static final String TAG = AddLabelActivity.class.getName();
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 1000;
    // fastest updates interval - 1 sec
    // location updates will be received if another app is requesting the locations
    // than your app can handle
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 1000;
    final int BASE_MAP_INDEX = 0;
    private MapView map;
    private Label label;
    private FloatingActionButton focusOnUserLocationBtn;
    private Location userLocation;
    private FusedLocationProviderClient fusedLocationClient;
    private SettingsClient settingsClient;
    private LocationRequest locationRequest;
    private LocationSettingsRequest locationSettingsRequest;
    private LocationCallback locationCallback;
    private boolean firstCameraMove = false;
    private boolean firstMapClicked = false;
    private String lastUpdateTime;
    // boolean flag to toggle the ui
    private Boolean mRequestingLocationUpdates;
    private Marker marker;
    private Button submitButton;
    private ImageView locationLabel;
    private AddAddressViewModel addAddressViewModel;
    private ProgressBar progressBar;
    LatLng latLng;

    VectorElementVector userMarkerLayer;
    VectorElementVector markerLayer;
    VectorElementVector lineLayer;
    VectorElementVector polygonLayer;
    private static final String ADDRESS_FOR_RESULT = "address";
    private static final String LATITUDE_FOR_RESULT = "latitude";
    private static final String LONGITUDE_FOR_RESULT = "longitude";


    public static String getAddressForResult() {
        return ADDRESS_FOR_RESULT;
    }

    public static String getLatitudeForResult() {
        return LATITUDE_FOR_RESULT;
    }

    public static String getLongitudeForResult() {
        return LONGITUDE_FOR_RESULT;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // starting app in full screen
        addAddressViewModel = ViewModelProviders.of(this).get(AddAddressViewModel.class);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_add_label);
        progressBar = findViewById(R.id.add_label_progressbar);
        addAddressViewModel.getAddress().observe(this, neshanAddress -> {
            if (neshanAddress != null) {
                Intent intent = new Intent();
                intent.putExtra(ADDRESS_FOR_RESULT, neshanAddress.getFormattedAddress());
                intent.putExtra(LATITUDE_FOR_RESULT, latLng.getLatitude());
                intent.putExtra(LONGITUDE_FOR_RESULT, latLng.getLongitude());
                setResult(Activity.RESULT_OK, intent);
                finish();
            }

        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        getLocationPermission();
        initLayoutReferences();
        initLocation();
        startReceivingLocationUpdates();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startLocationUpdates();
    }

    // Initializing layout references (views, map and map events)
    private void initLayoutReferences() {
        // Initializing views
        initViews();
        // Initializing mapView element
        initMap();

        map.setOnMapClickListener(latLng -> {
            if (!firstMapClicked) {
                map.addMarker(createMarker(latLng, false));
                AddLabelActivity.this.latLng = latLng;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.VISIBLE);
                    }
                });
                addAddressViewModel.getAddress(latLng);
                firstMapClicked = true;
            }
        });
        // when on marker clicked, change marker style to blue

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            map.dispatchTouchEvent(event);
        }
        return super.onTouchEvent(event);
    }

    private Marker createMarker(LatLng loc, boolean userLocation) {
        // Creating animation for marker. We should use an object of type AnimationStyleBuilder, set
        // all animation features on it and then call buildStyle() method that returns an object of type
        // AnimationStyle
        AnimationStyleBuilder animStBl = new AnimationStyleBuilder();
        animStBl.setFadeAnimationType(AnimationType.ANIMATION_TYPE_SMOOTHSTEP);
        animStBl.setSizeAnimationType(AnimationType.ANIMATION_TYPE_SPRING);
        animStBl.setPhaseInDuration(0.5f);
        animStBl.setPhaseOutDuration(0.5f);


        // Creating marker style. We should use an object of type MarkerStyleCreator, set all features on it
        // and then call buildStyle method on it. This method returns an object of type MarkerStyle
        MarkerStyleBuilder markStCr = new MarkerStyleBuilder();
        markStCr.setSize(30f);
        if (userLocation) {
            markStCr.setBitmap(BitmapUtils.createBitmapFromAndroidBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_marker)));

        } else {
            markStCr.setBitmap(BitmapUtils.createBitmapFromAndroidBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_location_red)));

        }
        // AnimationStyle object - that was created before - is used here
        markStCr.setAnimationStyle(animStBl.buildStyle());
        MarkerStyle markSt = markStCr.buildStyle();

        // Creating marker
        return new Marker(loc, markSt);
    }

    private boolean getLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                }, REQUEST_CODE);
                return false;
            }
        }
        return true;
    }

    // We use findViewByID for every element in our layout file here
    private void initViews() {
        map = findViewById(R.id.map);
        focusOnUserLocationBtn = findViewById(R.id.user_location_btn);
        focusOnUserLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                focusOnUserLocation();
            }
        });
    }

    // Initializing map
    private void initMap() {
        // Setting map focal position to a fixed position and setting camera zoom
        map.moveCamera(new LatLng(35.767234, 51.330743), 0);
        map.setZoom(14, 0);
    }

    public void stopLocationUpdates() {
        // Removing location updates
        fusedLocationClient
                .removeLocationUpdates(locationCallback)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(), "Location updates stopped!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void openSettings() {
        Intent intent = new Intent();
        intent.setAction(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package",
                BuildConfig.APPLICATION_ID, null);
        intent.setData(uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void startReceivingLocationUpdates() {
        // Requesting ACCESS_FINE_LOCATION using Dexter library
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        mRequestingLocationUpdates = true;
                        startLocationUpdates();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        if (response.isPermanentlyDenied()) {
                            // open device settings when the permission is
                            // denied permanently
                            openSettings();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(com.karumi.dexter.listener.PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }

                }).check();
    }

    private void startLocationUpdates() {
        settingsClient
                .checkLocationSettings(locationSettingsRequest)
                .addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        Log.i("TAG", "All location settings are satisfied.");

                        //noinspection MissingPermission
                        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());

                        onLocationChange();
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        int statusCode = ((ApiException) e).getStatusCode();
                        switch (statusCode) {
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                Log.i(TAG, "Location settings are not satisfied. Attempting to upgrade " +
                                        "location settings ");
                                try {
                                    // Show the dialog by calling startResolutionForResult(), and check the
                                    // result in onActivityResult().
                                    ResolvableApiException rae = (ResolvableApiException) e;
                                    rae.startResolutionForResult(AddLabelActivity.this, REQUEST_CODE);
                                } catch (IntentSender.SendIntentException sie) {
                                    Log.i(TAG, "PendingIntent unable to execute request.");
                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                String errorMessage = "Location settings are inadequate, and cannot be " +
                                        "fixed here. Fix in Settings.";
                                Log.e(TAG, errorMessage);

                                Toast.makeText(AddLabelActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                        }

                        onLocationChange();
                    }
                });
    }

    private void onLocationChange() {
        if (userLocation != null) {
            if (!firstCameraMove) {
                LatLng LatLng = new LatLng(userLocation.getLatitude(), userLocation.getLongitude());
                map.moveCamera(LatLng, 0);
                map.setZoom(15, 0.25f);
                firstCameraMove = true;
            }
            map.addMarker(createMarker(new LatLng(userLocation.getLatitude(), userLocation.getLongitude()), true));
        }
    }

    public void focusOnUserLocation() {
        if (userLocation != null) {
            LatLng LatLng = new LatLng(userLocation.getLatitude(), userLocation.getLongitude());
            map.moveCamera(LatLng, 0);
            map.setZoom(15, 0.25f);
            map.addMarker(createMarker(LatLng, true));
        }
    }

    private void initLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        settingsClient = LocationServices.getSettingsClient(this);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                // location is received
                userLocation = locationResult.getLastLocation();
                lastUpdateTime = DateFormat.getTimeInstance().format(new Date());

                onLocationChange();
            }
        };

        mRequestingLocationUpdates = false;

        locationRequest = new LocationRequest();
        locationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        locationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(locationRequest);
        locationSettingsRequest = builder.build();

    }

    // This method gets a LatLng as input and adds a label on that position
    private void addLabel(LatLng loc) {
        if (label != null) {
            map.removeLabel(label);
        }
        // Creating text style. We should use an object of type TextStyleBuilder, set all features on it
        // and then call buildStyle method on it. This method returns an object of type TextStyle.
        TextStyleBuilder textStyleBuilder = new TextStyleBuilder();
        textStyleBuilder.setFontSize(15f);
        textStyleBuilder.setColor(new Color((short) 255, (short) 150, (short) 150, (short) 255));
        TextStyle textStyle = textStyleBuilder.buildStyle();

        // Creating label
        label = new Label(loc, textStyle, "???????? ???????????? ??????");

        // Adding marker to labelLayer, or showing label on map!
        map.addLabel(label);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            // Check for the integer request code originally supplied to startResolutionForResult().
            case REQUEST_CODE:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        Log.e(TAG, "User agreed to make required location settings changes.");
                        // Nothing to do. startLocationupdates() gets called in onResume again.
                        break;
                    case Activity.RESULT_CANCELED:
                        Log.e(TAG, "User chose not to make required location settings changes.");
                        mRequestingLocationUpdates = false;
                        break;
                }
                break;
        }
    }
}
