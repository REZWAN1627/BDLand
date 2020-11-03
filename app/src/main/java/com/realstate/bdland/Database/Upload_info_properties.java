package com.realstate.bdland.Database;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.realstate.bdland.Adapter.ImageViewpagerAdapter.SliderAdapter;
import com.realstate.bdland.Adapter.RecyclerViewAdapter.RoomDetails_RecyclerView_Spinner_Adapter;
import com.realstate.bdland.Database.Data_model.SliderItemDataModel;
import com.realstate.bdland.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.realstate.bdland.Util.Constant.ERROR_DIALOG_REQUEST;
import static com.realstate.bdland.Util.Constant.GALLERY_REQUEST_CODE;
import static com.realstate.bdland.Util.Constant.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION;
import static com.realstate.bdland.Util.Constant.PERMISSIONS_REQUEST_ENABLE_GPS;

public class Upload_info_properties extends AppCompatActivity {

    public static final String TAG = "TAG";


    private StorageReference mStorageRef;
    private FirebaseFirestore firebaseFirestore;
    private DocumentReference documentReference;


    private ArrayList<String> Area_name = new ArrayList<>();
    private ArrayList<String> Sector_Area = new ArrayList<String>();
    private ArrayList<Uri> gallery_uriArrayList = new ArrayList<>();
    private ArrayList<SliderItemDataModel> sliderItemDataModelArrayList = new ArrayList<>();
    private ArrayList<String> imageUrl = new ArrayList<>();


    private Handler slideHandler = new Handler();
    private ViewPager2 viewPager2;

    private RecyclerView recyclerView;
    private RoomDetails_RecyclerView_Spinner_Adapter roomDetails;
    private RoomDetails_RecyclerView_Spinner_Adapter roomDetailsRecyclerViewAdapter;
    private RelativeLayout relativeLayout2, relativeLayout;
    private LinearLayout linearLayout, linearLayout1;


    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private Spinner Sector_area_Spinner;
    private Spinner Area_Location_spinner;
    private EditText House_holding_number;
    private EditText description;
    private EditText property_rent;
    private EditText owner_name;
    private EditText owner_phoneNo;


    private String temp[] = new String[10];
    private int bed = 0, bath = 0, balcony = 0, kitchen = 0;

    int commonBath = 0, attachBath = 0;

    private FusedLocationProviderClient fusedLocationProviderClient;
    private boolean mLocationPermissionGranted = false;
    private boolean flag = false;
    private boolean flag1 = false;
    private boolean ok_btn = false;

    double lat = 0.0;
    double lng = 0.0;

    private GeoPoint geoPoint;
    private TextView upload_tv_btn;
    private TextView room_name;

    private TextView progressDialog_tv;
    private ProgressBar Indicator;

    private AlertDialog.Builder DialogBuilder; // for pop up dialog
    private AlertDialog dialog;//pop up dialog
    int count = 0;
    private NotificationManagerCompat notificationManager;

    private final int progressMax = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_info_properties);

        viewPager2 = findViewById(R.id.imageViewSlider);
        recyclerView = findViewById(R.id.roomDetailsRecyclerView);
        linearLayout1 = findViewById(R.id.relativeLayout1);
        relativeLayout2 = findViewById(R.id.uploadRelativeLayout);
        Sector_area_Spinner = findViewById(R.id.sector_area_location);
        Area_Location_spinner = findViewById(R.id.area_name);
        House_holding_number = findViewById(R.id.holding_number);
        description = findViewById(R.id.description);
        radioGroup = findViewById(R.id.radio_group);
        property_rent = findViewById(R.id.rent_of_property);
        owner_name = findViewById(R.id.Owner_name);
        owner_phoneNo = findViewById(R.id.phone_number_ofOwner);
        upload_tv_btn = findViewById(R.id.uploadTextView);
        room_name = findViewById(R.id.name_Of_property);
        linearLayout = findViewById(R.id.categorylayout);

        mStorageRef = FirebaseStorage.getInstance().getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();
        notificationManager = NotificationManagerCompat.from(this);


        temp[0] = "Null";//spinner Sector selection
        temp[1] = "Null";//spinner Area selection
        temp[2] = "Null";//property description is given or not Y/N
        temp[3] = "NO";//store Y/N
        temp[4] = "NO";//Garage Y/N
        temp[5] = "NO";//common bath Y/N
        temp[6] = "NO";//attach bath Y/N
        temp[7] = "NO";//dinning



        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        spinnerValueSet();
        room_name.setVisibility(View.GONE);
        linearLayout.setVisibility(View.GONE);


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        roomDetails = new RoomDetails_RecyclerView_Spinner_Adapter(gallery_uriArrayList, this);


        Sector_area_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (!adapterView.getItemAtPosition(i).equals("Select Sector")) {
                    temp[0] = adapterView.getItemAtPosition(i).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Area_Location_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (!adapterView.getItemAtPosition(i).equals("select Area")) {
                    temp[1] = adapterView.getItemAtPosition(i).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        upload_tv_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag1 = true;

                sliderItemDataModelArrayList.clear();
                gallery_uriArrayList.clear();
                roomDetailsRecyclerViewAdapter = new RoomDetails_RecyclerView_Spinner_Adapter(true);

                Intent i = new Intent(String.valueOf(MediaStore.Images.Media.EXTERNAL_CONTENT_URI));
                i.setType("image/*");
                i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i, "Select Images"), GALLERY_REQUEST_CODE);
            }
        });


        findViewById(R.id.retake_fromGallery).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sliderItemDataModelArrayList.clear();
                gallery_uriArrayList.clear();
                roomDetails.reset();
                roomDetailsRecyclerViewAdapter = new RoomDetails_RecyclerView_Spinner_Adapter(true);

                Intent i = new Intent(String.valueOf(MediaStore.Images.Media.EXTERNAL_CONTENT_URI));
                i.setType("image/*");
                i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i, "Select Images"), GALLERY_REQUEST_CODE);

            }
        });

        findViewById(R.id.done_now_upload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Upload_info_properties.this, "OK Done", Toast.LENGTH_SHORT).show();

                if (House_holding_number.getText().toString().isEmpty()) {
                    House_holding_number.setError("Field is empty");
                    return;
                }
                try {
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {
                    // TODO: handle exception
                }
                ok_btn = true;


//
//                Intent activityIntent = new Intent(Upload_info_properties.this, Upload_info_properties.class);
//                PendingIntent pendingIntent = PendingIntent.getActivities(getApplicationContext(), 0, new Intent[]{activityIntent}, 0);
//
//
//                final NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_1_ID)
//                        .setSmallIcon(R.drawable.uploadnotification).setContentTitle("Upload").
//                                setContentText("TATTI").setPriority(NotificationCompat.PRIORITY_HIGH).setOngoing(true).
//                                setOnlyAlertOnce(true).
//                                setProgress(progressMax,0,false);
//                notificationManager.notify(1, notification.build());
//
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        SystemClock.sleep(2000);
//                        for (int progress = 0; progress<=progressMax; progress+=10){
//                            notification.setProgress(progressMax,progress,false);
//                            notificationManager.notify(1,notification.build());
//                            SystemClock.sleep(1000);
//                        }
//
//                        notification.setContentText("Upload finish")
//                                .setProgress(0,0,false).setOngoing(false);
//                        notificationManager.notify(1,notification.build());
//
//                    }
//                }).start();



                Log.d(TAG, "onClick: size of array ==> " + gallery_uriArrayList.size());
                for (int i = 0; i <= gallery_uriArrayList.size() - 1; i++) {
                    Log.d(TAG, "onClick: Multiple upload ==> " + i);

                    try {
                        Log.d(TAG, "onClick: Multiple upload ==> " + i);
                        uploadImageToFirebase(roomDetails.getSelected_room_categories().get(i) + i, gallery_uriArrayList.get(i));
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(Upload_info_properties.this, "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                linearLayout1.setVisibility(View.GONE);
                roomDetails.reset();
            }
        });

        findViewById(R.id.exact_location).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {
                    // TODO: handle exception
                }

                Toast.makeText(Upload_info_properties.this, "Getting current Location", Toast.LENGTH_SHORT).show();
                flag = true;
                getLastKnowLocation();

            }
        });




        findViewById(R.id.finish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: " + radioButton.getText());


                try {
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {
                    // TODO: handle exception
                }

                if (!ok_btn) {
                    Toast.makeText(Upload_info_properties.this, "You haven't press OK no need to retake button", Toast.LENGTH_SHORT).show();
                }
                Log.d(TAG, "onClick: == > " + lat + " ==> " + lng);

                if (House_holding_number.getText().toString().isEmpty()) {
                    House_holding_number.setError("Field is empty");
                    return;
                } else if (gallery_uriArrayList.isEmpty()) {
                    Toast.makeText(Upload_info_properties.this, "You Haven't select Images", Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.d(TAG, "onClick: size " + roomDetails.getSelected_room_categories().size());


                for (int i = 0; i < roomDetails.getSelected_room_categories().size(); i++) {
                    if (roomDetails.getSelected_room_categories().get(i).equals("Bed room")) {
                        bed++;

                    } else if (roomDetails.getSelected_room_categories().get(i).equals("Common Bathroom")) {
                        temp[5] = "YES";
                        commonBath++;

                    } else if (roomDetails.getSelected_room_categories().get(i).equals("Dinning room")) {
                        temp[7] = "YES";


                    } else if (roomDetails.getSelected_room_categories().get(i).equals("Attach bathroom")) {
                        temp[6] = "YES";
                        attachBath++;


                    } else if (roomDetails.getSelected_room_categories().get(i).equals("Balcony")) {

                        balcony++;

                    } else if (roomDetails.getSelected_room_categories().get(i).equals("Kitchen")) {

                        kitchen++;
                    } else if (roomDetails.getSelected_room_categories().get(i).equals("Store room")) {
                        temp[3] = "Yes";
                        //nothing
                    } else if (roomDetails.getSelected_room_categories().get(i).equals("Garage")) {
                        temp[4] = "Yes";
                        //nothing
                    } else if (roomDetails.getSelected_room_categories().get(i).equals("Null") || roomDetails.getSelected_room_categories().get(i).equals("select Room Categories")) {
                        Toast.makeText(Upload_info_properties.this, "Your haven't select Room Categories", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                if (temp[0].equals("Null") || temp[1].equals("Null")) {
                    Toast.makeText(Upload_info_properties.this, "You haven't select sector ans area location", Toast.LENGTH_SHORT).show();
                    return;
                }

                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        radioButton = findViewById(checkedId);
                    }
                });

                if (radioButton == null) {
                    Toast.makeText(Upload_info_properties.this, "You haven;t select Property Type", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (owner_name.getText().toString().isEmpty()) {
                    owner_name.setError("Field is empty");
                    return;
                }
                if (owner_phoneNo.getText().toString().isEmpty()) {
                    owner_phoneNo.setError("Phone is empty");
                    return;
                }
                if (description.getText().toString().isEmpty()) {
                    temp[2] = "No Description is available";
                    return;
                }
                if (!flag) {
                    Toast.makeText(Upload_info_properties.this, "You forget to set location. Press Location Button!", Toast.LENGTH_SHORT).show();
                    return;
                }


                ///database upload section


                //--------------------------------------------------


                Log.d(TAG, "onClick: Sector location ==> " + temp[0] + " area location ==> " + temp[1]);
                Log.d(TAG, "onClick: rent" + property_rent.getText().toString());

                Log.d(TAG, "onClick: count of rooms ==> " + bed + " count of kitchen ==> " + kitchen
                        + " count of balcony ==> " + balcony);

                Log.d(TAG, "onClick: dataBase called");
                Log.d(TAG, "onClick: size " + imageUrl.size());
                documentReference = firebaseFirestore.collection("PROPERTIES DETAILS").document("B0-"+House_holding_number.getText().toString());
                Map<String, Object> Client = new HashMap<>();

                Client.put("BED_ROOMS", String.valueOf(bed));
                Client.put("BATHROOMS", String.valueOf(commonBath + attachBath));
                Client.put("STORE_ROOM", temp[3]);
                Client.put("COMMON_BATH", temp[5]);
                Client.put("ATTACHED_BATH", temp[6]);
                Client.put("BALCONY", String.valueOf(balcony));
                Client.put("KITCHEN", String.valueOf(kitchen));
                Client.put("DINNING_ROOM", temp[7]);
                Client.put("RENT_PER_MONTH", property_rent.getText().toString());
                Client.put("HOUSE_NUMBER", "B0-"+House_holding_number.getText().toString());
                Client.put("LOCATION", temp[1]);
                Client.put("SECTOR_LOCATION", temp[0]);
                Client.put("PROPERTY_TYPE", radioButton.getText().toString());
                Client.put("HOUSE_OWNER_NAME", owner_name.getText().toString());
                Client.put("HOUSE_OWNER_PHONE_NUMBER", owner_phoneNo.getText().toString());
                Client.put("FLAT_DESCRIPTION", description.getText().toString());
                Client.put("EXACT_LOCATION", geoPoint);
                Client.put("GARAGE", temp[4]);
                Client.put("IMAGE_URL", imageUrl.get(0));


                documentReference.set(Client).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        property_rent.setText("");
                        owner_name.setText("");
                        owner_phoneNo.setText("");
                        House_holding_number.setText("");
                        description.setText("");

                        Log.d(TAG, "onSuccess: Information stored successfully");
                        Toast.makeText(Upload_info_properties.this, "Information stored successfully", Toast.LENGTH_SHORT).show();


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Upload_info_properties.this, "Error! " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


                temp[0] = "Null";//spinner Sector selection
                temp[1] = "Null";//spinner Area selection
                temp[2] = "Null";//property description is given or not Y/N
                temp[3] = "NO";//store Y/N
                temp[4] = "NO";//Garage Y/N
                temp[5] = "NO";//common bath Y/N
                temp[6] = "NO";//attach bath Y/N
                temp[7] = "No";//dinning Y/N
                bed = 0;
                bath = 0;
                balcony = 0;
                kitchen = 0;
                commonBath = 0;
                attachBath = 0;
                radioGroup.clearCheck();
                Sector_area_Spinner.setSelection(0);
                Area_Location_spinner.setSelection(0);
                room_name.setVisibility(View.GONE);
                upload_tv_btn.setVisibility(View.VISIBLE);
                viewPager2.setVisibility(View.GONE);
                linearLayout.setVisibility(View.GONE);
                sliderItemDataModelArrayList.clear();
                gallery_uriArrayList.clear();
                roomDetails.reset();


            }
        });


    }


    private void getLastKnowLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if (task.isSuccessful()) {

                    Location location = task.getResult();
                    geoPoint = new GeoPoint(location.getLatitude(), location.getLongitude());
                    Log.d(TAG, "onComplete: getlastknowlocation ==> " + geoPoint.getLatitude());
                    Log.d(TAG, "onComplete: ==> getlastknowlocation" + geoPoint.getLongitude());
                    lat = geoPoint.getLatitude();
                    lng = geoPoint.getLatitude();
                    Toast.makeText(Upload_info_properties.this, "latitude is " + lat + " Longitude is " + lng, Toast.LENGTH_SHORT).show();


                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Upload_info_properties.this, "Error Location " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private boolean checkMapServices() {
        if (isServicesOK()) {
            if (isMapsEnabled()) {
                return true;
            }
        }
        return false;
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("This application requires GPS to work properly, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        Intent enableGpsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(enableGpsIntent, PERMISSIONS_REQUEST_ENABLE_GPS);
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public boolean isMapsEnabled() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();
            return false;
        }
        return true;
    }

    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    public boolean isServicesOK() {
        Log.d(TAG, "isServicesOK: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(Upload_info_properties.this);

        if (available == ConnectionResult.SUCCESS) {
            //everything is fine and the user can make map requests
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            //an error occured but we can resolve it
            Log.d(TAG, "isServicesOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(Upload_info_properties.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
    }


    private void spinnerValueSet() {

        Sector_Area.add(0, "Select Sector");
        Sector_Area.add("Nazipur");
        Sector_Area.add("Rajshahi");

        Area_name.add(0, "select Area");
        Area_name.add("Harirampur");
        Area_name.add("Al-hera-Para");
        Area_name.add("Madhrasa-Para");
        Area_name.add("Bus-Stand");
        Area_name.add("Shapahar-Road");
        Area_name.add("Naogaon-Road");
        Area_name.add("Babna-Baz-Road");
        Area_name.add("Matazi-Road");
        Area_name.add("New-MatirRoad");
        Area_name.add("HighSchool-para");
        Area_name.add("Notun-Hat");
        Area_name.add("GirlsHighSchool-Road");
        Area_name.add("Thukni-para");
        Area_name.add("Upozila");
        Area_name.add("Puratun-Bazar");
        Area_name.add("HighSchool/Hospital-Road");
        Area_name.add("MohilaCollage-Road");


        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, Sector_Area);
        stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Sector_area_Spinner.setAdapter(stringArrayAdapter);

        ArrayAdapter<String> stringArrayAdapter1 = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, Area_name);
        stringArrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Area_Location_spinner.setAdapter(stringArrayAdapter1);
    }

    private void uploadImageToFirebase(String name, Uri uri) throws IOException {
        final StorageReference image = mStorageRef.child(temp[0]).child(temp[1]).
                child("B0-"+House_holding_number.getText().toString()).child(name);

        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 15, byteArrayOutputStream);
        byte[] imageData = byteArrayOutputStream.toByteArray();

        image.putBytes(imageData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                if (taskSnapshot.getMetadata() != null) {
                    if (taskSnapshot.getMetadata().getReference() != null) {
                        Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                        result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                imageUrl.add(uri.toString());


                                Log.d(TAG, "onSuccess: uri " + uri.toString());


                            }
                        });
                    }
                }

            }
        });

    }


    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.d(TAG, "onActivityResult: intent data--->" + data);
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(TAG, "onActivityResult: called.");
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ENABLE_GPS: {
                if (mLocationPermissionGranted) {
                    //Nothing
                } else {
                    getLocationPermission();
                }
            }
        }

        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK) {


            if (data.getClipData() != null) {
                room_name.setVisibility(View.VISIBLE);
                upload_tv_btn.setVisibility(View.GONE);
                linearLayout.setVisibility(View.VISIBLE);
                int countClipData = data.getClipData().getItemCount();
                for (int i = 0; i < countClipData; i++) {
                    Log.d(TAG, "onActivityResult: doing: " + i);
                    Uri imageUri = data.getClipData().getItemAt(i).getUri();
                    gallery_uriArrayList.add(i, imageUri);
                    sliderItemDataModelArrayList.add(new SliderItemDataModel(gallery_uriArrayList.get(i)));

                }

                Log.d(TAG, "onActivityResult: done");

                roomDetails.replace(gallery_uriArrayList.size());

                viewPager2.setVisibility(View.VISIBLE);
                linearLayout1.setVisibility(View.VISIBLE);
                recyclerView.setAdapter(roomDetails);

                viewPager2.setAdapter(new SliderAdapter(sliderItemDataModelArrayList, viewPager2));

              /*  viewPager2.setClipToPadding(false);
                viewPager2.setClipChildren(false);
                viewPager2.setOffscreenPageLimit(3);
                viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

                CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
                compositePageTransformer.addTransformer(new MarginPageTransformer(40));
                compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
                    @Override
                    public void transformPage(@NonNull View page, float position) {
                        float r = 1 - Math.abs(position);
                        page.setScaleY(0.85f + r * 0.15f);

                    }
                });
                viewPager2.setPageTransformer(compositePageTransformer);


                viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {

                    @Override
                    public void onPageSelected(int position) {
                        super.onPageSelected(position);
                        slideHandler.removeCallbacks(slideRunnable);
                        slideHandler.postDelayed(slideRunnable, 3000);

                    }
                });*/


            }


        }
    }



   /* private Runnable slideRunnable = new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);

        }
    };*/


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View view = getCurrentFocus();
        if (view != null && (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) && view instanceof EditText && !view.getClass().getName().startsWith("android.webkit.")) {
            int scrcoords[] = new int[2];
            view.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + view.getLeft() - scrcoords[0];
            float y = ev.getRawY() + view.getTop() - scrcoords[1];
            if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom())
                hideKeyboard(this);
        }
        return super.dispatchTouchEvent(ev);
    }

    public static void hideKeyboard(Activity act) {
        if (act != null)
            ((InputMethodManager) act.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow((act.getWindow().getDecorView().getApplicationWindowToken()), 0);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (checkMapServices()) {
            if (mLocationPermissionGranted) {
            } else {
                getLocationPermission();
            }
        }
    }


}