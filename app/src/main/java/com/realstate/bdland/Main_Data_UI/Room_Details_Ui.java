package com.realstate.bdland.Main_Data_UI;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.realstate.bdland.Adapter.RecyclerViewAdapter.RoomDetailsRecyclerViewAdapter;
import com.realstate.bdland.R;
import com.realstate.bdland.UI.Individual_MAP_UI;

import java.util.ArrayList;

public class Room_Details_Ui extends AppCompatActivity {

    public static final String TAG = "TAG";
    private RecyclerView recyclerView;
    private RoomDetailsRecyclerViewAdapter roomDetailsRecyclerViewAdapter;
    private ArrayList<Uri> imageList = new ArrayList<Uri>();
    private FirebaseFirestore firebaseFirestore;

    private String location, sector_location;
    private String HoldingNumber;
    private String SectorLocation;
    private String NumberOfBeds, NumberOfKitchen, NumberOfBalcony, NumberOfBathroom, Property_Type, Property_rent, Description;
    private StorageReference storageReference;

    private TextView property_Type, Area_loco;
    private TextView property_rent, Sector_loco, description;

    private TextView numberOfBeds, numberOfKitchen, numberOfBalcony, numberOfBathroom;
    private Button MAP;
    private String Number = "01771821717";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui_room_details);
        recyclerView = findViewById(R.id.roomDetailsRecyclerView);
        property_Type = findViewById(R.id.property_type2);
        property_rent = findViewById(R.id.property_rent2);
        numberOfBeds = findViewById(R.id.number_of_beds2);
        numberOfBathroom = findViewById(R.id.number_of_bathrooms2);
        numberOfBalcony = findViewById(R.id.number_of_balcony2);
        numberOfKitchen = findViewById(R.id.number_of_kitchen2);
        MAP = findViewById(R.id.maaaap);
        Area_loco = findViewById(R.id.area_loco);
        Sector_loco = findViewById(R.id.sector_loco);
        description = findViewById(R.id.tatti_description);


        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        roomDetailsRecyclerViewAdapter = new RoomDetailsRecyclerViewAdapter(imageList, this);
        firebaseFirestore = FirebaseFirestore.getInstance();

        location = getIntent().getStringExtra("Location");
        HoldingNumber = getIntent().getStringExtra("HoldingNumber");
        SectorLocation = getIntent().getStringExtra("sectorLocation");
        NumberOfBalcony = getIntent().getStringExtra("balcony");
        NumberOfBathroom = getIntent().getStringExtra("bathroom");
        NumberOfKitchen = getIntent().getStringExtra("kitchen");
        NumberOfBeds = getIntent().getStringExtra("Bedroom");
        Property_Type = getIntent().getStringExtra("property_type");
        Property_rent = getIntent().getStringExtra("Rent");
        sector_location = getIntent().getStringExtra("Sector location");
        Description = getIntent().getStringExtra("description");


        property_Type.setText(Property_Type);
        property_rent.setText(Property_rent);
        numberOfBeds.setText(NumberOfBeds);
        numberOfBathroom.setText(NumberOfBathroom);
        numberOfBalcony.setText(NumberOfBalcony);
        numberOfKitchen.setText(NumberOfKitchen);
        Area_loco.setText(location);
        Sector_loco.setText(sector_location);
        description.setText(Description);


        Log.d(TAG, "onCreate: location " + location + " holding " + HoldingNumber);

        storageReference = FirebaseStorage.getInstance().getReference().child(SectorLocation).child(location).child(HoldingNumber);
        storageReference.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                Log.d(TAG, "onCreate: location " + location + " holding " + HoldingNumber);

                for (StorageReference fileRef : listResult.getItems()) {
                    fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            imageList.add(uri);
                            Log.d(TAG, "onSuccess: -->" + uri.toString());

                        }
                    }).addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            recyclerView.setAdapter(roomDetailsRecyclerViewAdapter);
                        }
                    });
                }
            }

        });

        MAP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Room_Details_Ui.this, Individual_MAP_UI.class);
                i.putExtra("holdingNumber", HoldingNumber);
                startActivity(i);
                //startActivity(new Intent(Room_Details_Ui.this, MAP_Ui.class));
            }
        });

        findViewById(R.id.callBdland).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + Number));
                startActivity(intent);

            }
        });

    }


}