package com.realstate.bdland.Main_Data_UI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.realstate.bdland.Adapter.RecyclerViewAdapter.FirebaseAdapter.Main_Page_UI_RecyclerViewAdapter;
import com.realstate.bdland.Adapter.RecyclerViewAdapter.SectorViewRecyclerAdapter;
import com.realstate.bdland.Database.Data_model.DataHouseDetails;
import com.realstate.bdland.FilterSearch.Filter_Search_UI;
import com.realstate.bdland.R;
import com.realstate.bdland.UI.MAP_Ui;

import java.util.ArrayList;

public class Main_page_UI extends AppCompatActivity {

    public static final String TAG = "TAG";
    private RecyclerView recyclerView;

    private ArrayList<DataHouseDetails> dataHouseDetailsList =  new ArrayList<>();
    private FirebaseFirestore firebaseFirestore;
    private Main_Page_UI_RecyclerViewAdapter mainPageUIRecyclerViewAdapter;
    private RecyclerView recyclerViewSector;
    private SectorViewRecyclerAdapter sectorViewRecyclerAdapter;

    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();

    private SearchView searchView;
    private TextView notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui_main_page);
        recyclerView = findViewById(R.id.property_details_recyclerView);
        searchView = findViewById(R.id.search_bar);
        notification = findViewById(R.id.notification2);
        recyclerViewSector = findViewById(R.id.sectorRecyclerView);
        firebaseFirestore = FirebaseFirestore.getInstance();

        getImages();

        mainPageUIRecyclerViewAdapter = new Main_Page_UI_RecyclerViewAdapter(dataHouseDetailsList, this);
        sectorViewRecyclerAdapter = new SectorViewRecyclerAdapter(mNames,mImageUrls,this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewSector.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setHasFixedSize(true);
        recyclerViewSector.setHasFixedSize(true);
        recyclerView.setAdapter(mainPageUIRecyclerViewAdapter);
        recyclerViewSector.setAdapter(sectorViewRecyclerAdapter);

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main_page_UI.this, ClientRequestDetails.class));
            }
        });




        firebaseFirestore.collection("PROPERTIES DETAILS").
                addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if (value == null) {
                            Toast.makeText(Main_page_UI.this, "No record found", Toast.LENGTH_SHORT).show();
                        } else {
                            for (DocumentChange doc : value.getDocumentChanges()) {
                                DataHouseDetails dataHouseDetails = doc.getDocument().toObject(DataHouseDetails.class);
                                dataHouseDetailsList.add(dataHouseDetails);
                                mainPageUIRecyclerViewAdapter.notifyDataSetChanged();

                            }
                        }


                    }
                });

        findViewById(R.id.imageButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main_page_UI.this, Filter_Search_UI.class));
            }
        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {
                dataHouseDetailsList.clear();
                mainPageUIRecyclerViewAdapter.Reset();

                Log.d(TAG, "onQueryTextSubmit: " + query);
                firebaseFirestore.collection("PROPERTIES DETAILS").whereEqualTo("RENT_PER_MONTH", query).
                        addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                                if (value.isEmpty()) {
                                    Log.d(TAG, "onEvent: nai");
                                    firebaseFirestore.collection("PROPERTIES DETAILS").whereEqualTo("BED_ROOMS", query).
                                            addSnapshotListener(new EventListener<QuerySnapshot>() {
                                                @Override
                                                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                                                    if (value == null) {
                                                        Toast.makeText(Main_page_UI.this, "NO RESULT HAS FOUND!", Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        for (DocumentChange doc : value.getDocumentChanges()) {
                                                            DataHouseDetails dataHouseDetails = doc.getDocument().toObject(DataHouseDetails.class);
                                                            dataHouseDetailsList.add(dataHouseDetails);
                                                            mainPageUIRecyclerViewAdapter.notifyDataSetChanged();
                                                            recyclerView.invalidate();
                                                        }
                                                    }


                                                }
                                            });
                                } else {
                                    for (DocumentChange doc : value.getDocumentChanges()) {
                                        DataHouseDetails dataHouseDetails = doc.getDocument().toObject(DataHouseDetails.class);
                                        dataHouseDetailsList.add(dataHouseDetails);
                                        mainPageUIRecyclerViewAdapter.notifyDataSetChanged();
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
                    mainPageUIRecyclerViewAdapter.Reset();
                    dataHouseDetailsList.clear();
                    firebaseFirestore.collection("PROPERTIES DETAILS").
                            addSnapshotListener(new EventListener<QuerySnapshot>() {
                                @Override
                                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                    if (value == null) {
                                        Toast.makeText(Main_page_UI.this, "No record found", Toast.LENGTH_SHORT).show();
                                    } else {
                                        for (DocumentChange doc : value.getDocumentChanges()) {
                                            DataHouseDetails dataHouseDetails = doc.getDocument().toObject(DataHouseDetails.class);
                                            dataHouseDetailsList.add(dataHouseDetails);
                                            mainPageUIRecyclerViewAdapter.notifyDataSetChanged();
                                            recyclerView.invalidate();
                                        }
                                    }


                                }
                            });
                }
                return true;
            }
        });

        findViewById(R.id.locationOnMap).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Main_page_UI.this, MAP_Ui.class));

            }
        });

    }

    private void getImages() {

        mImageUrls.add("https://i.redd.it/0h2gm1ix6p501.jpg");
        mNames.add("Harirampur");

        mImageUrls.add("https://i.redd.it/0h2gm1ix6p501.jpg");
        mNames.add("Al-hera-Para");

        mImageUrls.add("https://i.redd.it/qn7f9oqu7o501.jpg");
        mNames.add("Madhrasa-para");

        mImageUrls.add("https://i.redd.it/j6myfqglup501.jpg");
        mNames.add("Bus-Stand");

        mImageUrls.add("https://i.redd.it/0h2gm1ix6p501.jpg");
        mNames.add("Notun-Hat");

        mImageUrls.add("https://i.redd.it/k98uzl68eh501.jpg");
        mNames.add("HighSchool-para");

        mImageUrls.add("https://i.imgur.com/ZcLLrkY.jpg");
        mNames.add("Upozila");
    }

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

}
