package com.realstate.bdland.UI;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.realstate.bdland.Database.Data_model.IndividualUserLocationDataModel;
import com.realstate.bdland.Database.Data_model.User_Location_DataModel;
import com.realstate.bdland.R;

import java.util.ArrayList;

import static com.realstate.bdland.Util.Constant.MAPVIEW_BUNDLE_KEY;

public class Individual_MAP_UI extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnInfoWindowClickListener {
    public static final String TAG = "TAG";


    private GoogleMap mGoogleMap;


    private boolean mLocationPermissionGranted = false;

    String location;

    private BottomSheetBehavior mBottomSheetBehavior;
    private View bottomSheet;


    private MapView mMapView;

    private Bundle bundle;
    private FirebaseFirestore firebaseFirestore;

    private String holdingNumber;
    private TextView ownerName1, ownerPhoneNumber1, houseRent, ownerDescription;


    private ArrayList<IndividualUserLocationDataModel> individualUserLocationDataModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual__map_ui);


        ownerName1 = findViewById(R.id.sheet_user_name2);
        ownerPhoneNumber1 = findViewById(R.id.sheet_user_phone2);
        mMapView = findViewById(R.id.individualMapOfHouseLocation);
        bottomSheet = findViewById(R.id.bottomSheetindividual);
        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        firebaseFirestore = FirebaseFirestore.getInstance();
        holdingNumber = getIntent().getStringExtra("holdingNumber");
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        mBottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_COLLAPSED:

                    case BottomSheetBehavior.STATE_DRAGGING:
                        Log.d(TAG, "onStateChanged: slide");
                        break;

                    case BottomSheetBehavior.STATE_EXPANDED:
                        Log.d(TAG, "onStateChanged: collapsed");
                        break;

                    case BottomSheetBehavior.STATE_HIDDEN:

                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        initGoogleMap(savedInstanceState);
        getUserLocation();


        firebaseFirestore = FirebaseFirestore.getInstance();


    }

    private void initGoogleMap(Bundle savedInstanceState) {
        // *** IMPORTANT ***
        // MapView requires that the Bundle you pass contain _ONLY_ MapView SDK
        // objects or sub-Bundles.
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }

        mMapView.onCreate(mapViewBundle);

        mMapView.getMapAsync(this);
    }

    public void getUserLocation() {
        Log.d(TAG, "getUserLocation: is called ");

        DocumentReference location = FirebaseFirestore.getInstance().collection("PROPERTIES DETAILS").document(holdingNumber);
        location.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().toObject(User_Location_DataModel.class) != null) {
                        individualUserLocationDataModels.add(task.getResult().toObject(IndividualUserLocationDataModel.class));

                        Log.d(TAG, "onComplete: location ==> " + individualUserLocationDataModels.get(0).getEXACT_LOCATION().getLatitude() + " another ==> "

                                + individualUserLocationDataModels.get(0).getEXACT_LOCATION().getLongitude());
                        mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(individualUserLocationDataModels.get(0).getEXACT_LOCATION().getLatitude(),
                                individualUserLocationDataModels.get(0).getEXACT_LOCATION().getLongitude()))
                                .title(individualUserLocationDataModels.get(0).getHOUSE_OWNER_NAME()).snippet("RENT: " + individualUserLocationDataModels.get(0).getRENT_PER_MONTH() + "\n"
                                        + "Phone NO: " + individualUserLocationDataModels.get(0).getHOUSE_OWNER_PHONE_NUMBER() + "\n" + "BedRooms: "
                                        + individualUserLocationDataModels.get(0).getBED_ROOMS() + "\n" + "Bathrooms: " + individualUserLocationDataModels.get(0)
                                        .getBATHROOMS())
                                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.map_marker)));

                        LatLng latLng = new LatLng(individualUserLocationDataModels.get(0).getEXACT_LOCATION().getLatitude(), individualUserLocationDataModels.get(0).getEXACT_LOCATION().getLongitude());
                        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
                                latLng, 15);
                        mGoogleMap.animateCamera(cameraUpdate);


                        mGoogleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                            @Override
                            public View getInfoWindow(Marker marker) {
                                return null;
                            }

                            @Override
                            public View getInfoContents(Marker marker) {
                                LinearLayout info = new LinearLayout(getApplicationContext());
                                info.setOrientation(LinearLayout.VERTICAL);

                                TextView title = new TextView(getApplicationContext());
                                title.setTextColor(Color.BLACK);
                                title.setGravity(Gravity.CENTER);
                                title.setTypeface(null, Typeface.BOLD);
                                title.setText(marker.getTitle());

                                TextView snippet = new TextView(getApplicationContext());
                                snippet.setTextColor(Color.GRAY);
                                snippet.setText(marker.getSnippet());

                                info.addView(title);
                                info.addView(snippet);

                                return info;
                            }
                        });
                    } else {
                        Log.d(TAG, "onComplete: tatti is real 2");
                    }
                } else {
                    Log.d(TAG, "onComplete: tatti is real 1");
                }
            }
        });


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        Log.d(TAG, "onMapReady: " + mGoogleMap);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        googleMap.setMyLocationEnabled(true);
        mGoogleMap.setOnInfoWindowClickListener(this);
        mGoogleMap.setOnMapClickListener(this);

    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int VectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, VectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    @Override
    public void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mMapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mMapView.onStop();
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        ownerName1.setText(marker.getTitle());
        ownerPhoneNumber1.setText(marker.getSnippet());
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }


    @Override
    public void onMapClick(LatLng latLng) {
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

    }
}