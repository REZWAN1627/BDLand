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
import androidx.annotation.Nullable;
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
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.realstate.bdland.Database.Data_model.User_Location_DataModel;
import com.realstate.bdland.R;

import java.util.ArrayList;

import static com.realstate.bdland.Util.Constant.MAPVIEW_BUNDLE_KEY;

public class MAP_Ui extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener, GoogleMap.OnMapClickListener {

    public static final String TAG = "TAG";

    private GoogleMap mGoogleMap;

    private BottomSheetBehavior mBottomSheetBehavior;
    private View bottomSheet;
    private MapView mMapView;
    private FirebaseFirestore firebaseFirestore;
    private TextView ownerName, ownerPhoneNumber, properties_details, ownerDescription;


    private ArrayList<User_Location_DataModel> user_location_dataModels = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map__ui);


        ownerName = findViewById(R.id.sheet_user_name);
        ownerPhoneNumber = findViewById(R.id.sheet_user_phone);
        // ownerDescription = findViewById(R.id.sheet_user_description);

        mMapView = findViewById(R.id.MapOfHouseLocation);
        bottomSheet = findViewById(R.id.bottomSheet);
        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        firebaseFirestore = FirebaseFirestore.getInstance();
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


        firebaseFirestore.collection("PROPERTIES DETAILS").
                addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        for (DocumentChange doc : value.getDocumentChanges()) {
                            User_Location_DataModel user_location_dataModel = doc.getDocument().toObject(User_Location_DataModel.class);
                            user_location_dataModels.add(user_location_dataModel);

                        }
                        getUserLocation();
                        Log.d(TAG, "onEvent: is called and finish");

                    }
                });


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

        for (int i = 0; i < user_location_dataModels.size() - 1; i++) {
            DocumentReference location = FirebaseFirestore.getInstance().collection("PROPERTIES DETAILS").document(user_location_dataModels.get(i).getHOUSE_NUMBER());
            final int finalI = i;
            location.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        if (task.getResult().toObject(User_Location_DataModel.class) != null) {
                            user_location_dataModels.add(task.getResult().toObject(User_Location_DataModel.class));

                            Log.d(TAG, "onComplete: location ==> " + user_location_dataModels.get(finalI).getEXACT_LOCATION().getLatitude() + " another ==> "
                                    + user_location_dataModels.get(finalI).getEXACT_LOCATION().getLongitude());

                            mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(user_location_dataModels.get(finalI).getEXACT_LOCATION().getLatitude(),
                                    user_location_dataModels.get(finalI).getEXACT_LOCATION().getLongitude()))
                                    .title(user_location_dataModels.get(finalI).getHOUSE_OWNER_NAME()).snippet("RENT: " + user_location_dataModels.get(finalI).getRENT_PER_MONTH() + "\n"
                                            + "Phone NO: " + user_location_dataModels.get(finalI).getHOUSE_OWNER_PHONE_NUMBER() + "\n" + "BedRooms: " + user_location_dataModels.get(finalI).getBED_ROOMS() +
                                            "\n" + "Bathrooms: " + user_location_dataModels.get(finalI).getBATHROOMS())
                                    .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.map_marker)));

                            LatLng latLng = new LatLng(user_location_dataModels.get(0).getEXACT_LOCATION().getLatitude(), user_location_dataModels.get(0).getEXACT_LOCATION().getLongitude());
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
    public void onMapReady(GoogleMap map) {
        mGoogleMap = map;
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
        map.setMyLocationEnabled(true);
        mGoogleMap.setOnInfoWindowClickListener(this);
        mGoogleMap.setOnMapClickListener(this);

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
    public void onInfoWindowClick(Marker marker) {
        ownerName.setText(marker.getTitle());
        ownerPhoneNumber.setText(marker.getSnippet());
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

    }


    @Override
    public void onMapClick(LatLng latLng) {
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

    }
}