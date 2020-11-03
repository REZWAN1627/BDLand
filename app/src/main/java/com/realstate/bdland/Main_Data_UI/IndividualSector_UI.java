package com.realstate.bdland.Main_Data_UI;

import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.realstate.bdland.Adapter.RecyclerViewAdapter.IndividualSectorRecyclerViewAdapter;
import com.realstate.bdland.Database.Data_model.IndividualSectorDataModel;
import com.realstate.bdland.R;

import java.util.ArrayList;


public class IndividualSector_UI extends AppCompatActivity {
    public static final String TAG = "TAG";

    private String SectorNames;
    private RecyclerView recyclerView;
    private ArrayList<IndividualSectorDataModel>individualSectorDataModels = new ArrayList<>();
    private IndividualSectorRecyclerViewAdapter individualSectorRecyclerViewAdapter;
    private FirebaseFirestore firebaseFirestore;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_sector_ui);
        recyclerView = findViewById(R.id.sectorRecyclerViewIndividual);
        searchView = findViewById(R.id.search_bars);
        firebaseFirestore = FirebaseFirestore.getInstance();


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        SectorNames = getIntent().getStringExtra("SectorNames");
        individualSectorRecyclerViewAdapter = new IndividualSectorRecyclerViewAdapter(individualSectorDataModels,this);
        recyclerView.setAdapter(individualSectorRecyclerViewAdapter);



        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("PROPERTIES DETAILS").whereEqualTo("LOCATION",SectorNames)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        Log.d(TAG, "onEvent: is called ");
                        if (value == null) {
                            Toast.makeText(IndividualSector_UI.this, "No Record Found", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.d(TAG, "onEvent: has data ");
                            for (DocumentChange doc : value.getDocumentChanges()) {
                                IndividualSectorDataModel individualSectorDataModel = doc.getDocument().toObject(IndividualSectorDataModel.class);
                                individualSectorDataModels.add(individualSectorDataModel);
                                individualSectorRecyclerViewAdapter.notifyDataSetChanged();
                                recyclerView.invalidate();
                            }
                        }
                    }
                });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {
                individualSectorDataModels.clear();
                individualSectorRecyclerViewAdapter.Reset();
                firebaseFirestore.collection("PROPERTIES DETAILS").whereEqualTo("RENT_PER_MONTH", query).whereEqualTo("LOCATION",SectorNames).
                        addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@androidx.annotation.Nullable QuerySnapshot value, @androidx.annotation.Nullable FirebaseFirestoreException error) {

                                if (value == null) {
                                    Log.d(TAG, "onEvent: nai");
                                    firebaseFirestore.collection("PROPERTIES DETAILS").whereEqualTo("BED_ROOMS", query).whereEqualTo("LOCATION",SectorNames).
                                            addSnapshotListener(new EventListener<QuerySnapshot>() {
                                                @Override
                                                public void onEvent(@androidx.annotation.Nullable QuerySnapshot value, @androidx.annotation.Nullable FirebaseFirestoreException error) {

                                                    if (value == null) {
                                                        Toast.makeText(IndividualSector_UI.this, "NO RESULT HAS FOUND!", Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        for (DocumentChange doc : value.getDocumentChanges()) {
                                                            IndividualSectorDataModel individualSectorDataModel = doc.getDocument().toObject(IndividualSectorDataModel.class);
                                                            individualSectorDataModels.add(individualSectorDataModel);
                                                            individualSectorRecyclerViewAdapter.notifyDataSetChanged();
                                                            recyclerView.invalidate();
                                                        }
                                                    }


                                                }
                                            });
                                } else {
                                    for (DocumentChange doc : value.getDocumentChanges()) {
                                        IndividualSectorDataModel individualSectorDataModel = doc.getDocument().toObject(IndividualSectorDataModel.class);
                                        individualSectorDataModels.add(individualSectorDataModel);
                                        individualSectorRecyclerViewAdapter.notifyDataSetChanged();
                                        recyclerView.invalidate();
                                    }

                                }
                            }
                        });
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    individualSectorDataModels.clear();
                    individualSectorRecyclerViewAdapter.Reset();
                    firebaseFirestore.collection("PROPERTIES DETAILS").whereEqualTo("LOCATION",SectorNames).
                            addSnapshotListener(new EventListener<QuerySnapshot>() {
                                @Override
                                public void onEvent(@androidx.annotation.Nullable QuerySnapshot value, @androidx.annotation.Nullable FirebaseFirestoreException error) {
                                    if (value == null) {
                                        Toast.makeText(IndividualSector_UI.this, "No record found", Toast.LENGTH_SHORT).show();
                                    } else {
                                        for (DocumentChange doc : value.getDocumentChanges()) {
                                            IndividualSectorDataModel individualSectorDataModel = doc.getDocument().toObject(IndividualSectorDataModel.class);
                                            individualSectorDataModels.add(individualSectorDataModel);
                                            individualSectorRecyclerViewAdapter.notifyDataSetChanged();
                                            recyclerView.invalidate();
                                        }
                                    }


                                }
                            });
                }
                return true;
            }
        });

    }
}
