package com.realstate.bdland.FilterSearch;

import android.os.Bundle;
import android.util.Log;
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
import com.realstate.bdland.Adapter.RecyclerViewAdapter.Filter_SearchResult_RecyclerViewAdapter;
import com.realstate.bdland.Database.Data_model.SearchResultDataModel;
import com.realstate.bdland.R;

import java.util.ArrayList;

public class Filter_SearchResult_UI extends AppCompatActivity {
    public static final String TAG = "TAG";
    private FirebaseFirestore firebaseFirestore;
    private Filter_SearchResult_RecyclerViewAdapter filter_searchResult_recyclerViewAdapter;
    private ArrayList<SearchResultDataModel> searchResultDataModels = new ArrayList<>();
    private RecyclerView recyclerView;
    private String propertyType, bedroom, bathroomTypes, bathroomNumber, balcony, RentMax;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter__search_result_ui);
        recyclerView = findViewById(R.id.searchResult);
        firebaseFirestore = FirebaseFirestore.getInstance();

        propertyType = getIntent().getStringExtra("propertyType");
        bedroom = getIntent().getStringExtra("bedroom");
        bathroomNumber = getIntent().getStringExtra("bathroomNumber");
        balcony = getIntent().getStringExtra("balcony");
        RentMax = getIntent().getStringExtra("RentMax");
        bathroomTypes = getIntent().getStringExtra("bathroomTypes");


        filter_searchResult_recyclerViewAdapter = new Filter_SearchResult_RecyclerViewAdapter(searchResultDataModels, this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(filter_searchResult_recyclerViewAdapter);

        if (bedroom.equals("Any") && bathroomNumber.equals("Any") && bathroomTypes.equals("Both") && balcony.equals("Any")) {

            firebaseFirestore.collection("PROPERTIES DETAILS").whereEqualTo("PROPERTY_TYPE", propertyType)
                    .whereLessThanOrEqualTo("RENT_PER_MONTH", RentMax).
                    addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            Log.d(TAG, "onEvent: is called maxrent " + RentMax);

                            if (value == null) {
                                Toast.makeText(Filter_SearchResult_UI.this, "NO RESULT HAS FOUND!", Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "onEvent: tatti " + error.getMessage());
                            } else {
                                Log.d(TAG, "onEvent: databse base has data");
                                for (DocumentChange doc : value.getDocumentChanges()) {
                                    SearchResultDataModel searchResultDataModel = doc.getDocument().toObject(SearchResultDataModel.class);
                                    searchResultDataModels.add(searchResultDataModel);
                                    Log.d(TAG, "onEvent: searrchabeldata " + value.getDocuments());
                                    filter_searchResult_recyclerViewAdapter.notifyDataSetChanged();
                                    recyclerView.invalidate();
                                }
                            }

                        }
                    });
        } else if (!bedroom.equals("Any") && !bathroomNumber.equals("Any") && !bathroomTypes.equals("Both") && !balcony.equals("Any")) {
            if (bathroomTypes.equals("Attach Bathroom")) {

                firebaseFirestore.collection("PROPERTIES DETAILS").whereEqualTo("PROPERTY_TYPE", propertyType)
                        .whereLessThanOrEqualTo("RENT_PER_MONTH", RentMax).whereEqualTo("ATTACHED_BATH", "YES").whereEqualTo("BALCONY", balcony).
                        whereEqualTo("BATHROOMS", bathroomNumber).whereEqualTo("BED_ROOMS", bedroom).
                        addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                Log.d(TAG, "onEvent: is called maxrent " + RentMax);

                                if (value == null) {
                                    Toast.makeText(Filter_SearchResult_UI.this, "NO RESULT HAS FOUND!", Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "onEvent: tatti " + error.getMessage());
                                } else {
                                    Log.d(TAG, "onEvent: databse base has data");
                                    for (DocumentChange doc : value.getDocumentChanges()) {
                                        SearchResultDataModel searchResultDataModel = doc.getDocument().toObject(SearchResultDataModel.class);
                                        searchResultDataModels.add(searchResultDataModel);
                                        Log.d(TAG, "onEvent: searrchabeldata " + value.getDocuments());
                                        filter_searchResult_recyclerViewAdapter.notifyDataSetChanged();
                                        recyclerView.invalidate();
                                    }
                                }

                            }
                        });

            } else {
                firebaseFirestore.collection("PROPERTIES DETAILS").whereEqualTo("PROPERTY_TYPE", propertyType)
                        .whereLessThanOrEqualTo("RENT_PER_MONTH", RentMax).whereEqualTo("ATTACHED_BATH", "NO").whereEqualTo("BALCONY", balcony).
                        whereEqualTo("BATHROOMS", bathroomNumber).whereEqualTo("BED_ROOMS", bedroom).
                        addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                Log.d(TAG, "onEvent: is called maxrent " + RentMax);

                                if (value == null) {
                                    Toast.makeText(Filter_SearchResult_UI.this, "NO RESULT HAS FOUND!", Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "onEvent: tatti " + error.getMessage());
                                } else {
                                    Log.d(TAG, "onEvent: databse base has data");
                                    for (DocumentChange doc : value.getDocumentChanges()) {
                                        SearchResultDataModel searchResultDataModel = doc.getDocument().toObject(SearchResultDataModel.class);
                                        searchResultDataModels.add(searchResultDataModel);
                                        Log.d(TAG, "onEvent: searrchabeldata " + value.getDocuments());
                                        filter_searchResult_recyclerViewAdapter.notifyDataSetChanged();
                                        recyclerView.invalidate();
                                    }
                                }

                            }
                        });
            }

        } else if (!bedroom.equals("Any") && bathroomNumber.equals("Any") && bathroomTypes.equals("Both") && balcony.equals("Any")) {

            firebaseFirestore.collection("PROPERTIES DETAILS").whereEqualTo("PROPERTY_TYPE", propertyType)
                    .whereLessThanOrEqualTo("RENT_PER_MONTH", RentMax).whereEqualTo("BED_ROOMS", bedroom).
                    addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            Log.d(TAG, "onEvent: is called maxrent " + RentMax);

                            if (value == null) {
                                Toast.makeText(Filter_SearchResult_UI.this, "NO RESULT HAS FOUND!", Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "onEvent: tatti " + error.getMessage());
                            } else {
                                Log.d(TAG, "onEvent: databse base has data");
                                for (DocumentChange doc : value.getDocumentChanges()) {
                                    SearchResultDataModel searchResultDataModel = doc.getDocument().toObject(SearchResultDataModel.class);
                                    searchResultDataModels.add(searchResultDataModel);
                                    Log.d(TAG, "onEvent: searrchabeldata " + value.getDocuments());
                                    filter_searchResult_recyclerViewAdapter.notifyDataSetChanged();
                                    recyclerView.invalidate();
                                }
                            }

                        }
                    });

        } else if (bedroom.equals("Any") && !bathroomNumber.equals("Any") && !bathroomTypes.equals("Both") && !balcony.equals("Any")) {

            if (bathroomTypes.equals("Attach Bathroom")) {

                firebaseFirestore.collection("PROPERTIES DETAILS").whereEqualTo("PROPERTY_TYPE", propertyType)
                        .whereLessThanOrEqualTo("RENT_PER_MONTH", RentMax).whereEqualTo("ATTACHED_BATH", "YES").whereEqualTo("BALCONY", balcony).
                        whereEqualTo("BATHROOMS", bathroomNumber).
                        addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                Log.d(TAG, "onEvent: is called maxrent " + RentMax);

                                if (value == null) {
                                    Toast.makeText(Filter_SearchResult_UI.this, "NO RESULT HAS FOUND!", Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "onEvent: tatti " + error.getMessage());
                                } else {
                                    Log.d(TAG, "onEvent: databse base has data");
                                    for (DocumentChange doc : value.getDocumentChanges()) {
                                        SearchResultDataModel searchResultDataModel = doc.getDocument().toObject(SearchResultDataModel.class);
                                        searchResultDataModels.add(searchResultDataModel);
                                        Log.d(TAG, "onEvent: searrchabeldata " + value.getDocuments());
                                        filter_searchResult_recyclerViewAdapter.notifyDataSetChanged();
                                        recyclerView.invalidate();
                                    }
                                }

                            }
                        });

            } else {
                firebaseFirestore.collection("PROPERTIES DETAILS").whereEqualTo("PROPERTY_TYPE", propertyType)
                        .whereLessThanOrEqualTo("RENT_PER_MONTH", RentMax).whereEqualTo("ATTACHED_BATH", "NO").whereEqualTo("BALCONY", balcony).
                        whereEqualTo("BATHROOMS", bathroomNumber).
                        addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                Log.d(TAG, "onEvent: is called maxrent " + RentMax);

                                if (value == null) {
                                    Toast.makeText(Filter_SearchResult_UI.this, "NO RESULT HAS FOUND!", Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "onEvent: tatti " + error.getMessage());
                                } else {
                                    Log.d(TAG, "onEvent: databse base has data");
                                    for (DocumentChange doc : value.getDocumentChanges()) {
                                        SearchResultDataModel searchResultDataModel = doc.getDocument().toObject(SearchResultDataModel.class);
                                        searchResultDataModels.add(searchResultDataModel);
                                        Log.d(TAG, "onEvent: searrchabeldata " + value.getDocuments());
                                        filter_searchResult_recyclerViewAdapter.notifyDataSetChanged();
                                        recyclerView.invalidate();
                                    }
                                }

                            }
                        });
            }


        } else if (bedroom.equals("Any") && !bathroomNumber.equals("Any") && bathroomTypes.equals("Both") && balcony.equals("Any")) {

            firebaseFirestore.collection("PROPERTIES DETAILS").whereEqualTo("PROPERTY_TYPE", propertyType)
                    .whereLessThanOrEqualTo("RENT_PER_MONTH", RentMax).whereEqualTo("BATHROOMS", bathroomNumber).
                    addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            Log.d(TAG, "onEvent: is called maxrent " + RentMax);

                            if (value == null) {
                                Toast.makeText(Filter_SearchResult_UI.this, "NO RESULT HAS FOUND!", Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "onEvent: tatti " + error.getMessage());
                            } else {
                                Log.d(TAG, "onEvent: databse base has data");
                                for (DocumentChange doc : value.getDocumentChanges()) {
                                    SearchResultDataModel searchResultDataModel = doc.getDocument().toObject(SearchResultDataModel.class);
                                    searchResultDataModels.add(searchResultDataModel);
                                    Log.d(TAG, "onEvent: searrchabeldata " + value.getDocuments());
                                    filter_searchResult_recyclerViewAdapter.notifyDataSetChanged();
                                    recyclerView.invalidate();
                                }
                            }

                        }
                    });

        } else if (!bedroom.equals("Any") && bathroomNumber.equals("Any") && !bathroomTypes.equals("Both") && !balcony.equals("Any")) {

            if (bathroomTypes.equals("Attach Bathroom")) {

                firebaseFirestore.collection("PROPERTIES DETAILS").whereEqualTo("PROPERTY_TYPE", propertyType)
                        .whereLessThanOrEqualTo("RENT_PER_MONTH", RentMax).whereEqualTo("ATTACHED_BATH", "YES").whereEqualTo("BED_ROOMS", bedroom).
                        whereEqualTo("BALCONY", balcony).
                        addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                Log.d(TAG, "onEvent: is called maxrent " + RentMax);

                                if (value == null) {
                                    Toast.makeText(Filter_SearchResult_UI.this, "NO RESULT HAS FOUND!", Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "onEvent: tatti " + error.getMessage());
                                } else {
                                    Log.d(TAG, "onEvent: databse base has data");
                                    for (DocumentChange doc : value.getDocumentChanges()) {
                                        SearchResultDataModel searchResultDataModel = doc.getDocument().toObject(SearchResultDataModel.class);
                                        searchResultDataModels.add(searchResultDataModel);
                                        Log.d(TAG, "onEvent: searrchabeldata " + value.getDocuments());
                                        filter_searchResult_recyclerViewAdapter.notifyDataSetChanged();
                                        recyclerView.invalidate();
                                    }
                                }

                            }
                        });

            } else {
                firebaseFirestore.collection("PROPERTIES DETAILS").whereEqualTo("PROPERTY_TYPE", propertyType)
                        .whereLessThanOrEqualTo("RENT_PER_MONTH", RentMax).whereEqualTo("ATTACHED_BATH", "NO").whereEqualTo("BED_ROOMS", bedroom).
                        whereEqualTo("BALCONY", balcony).
                        addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                Log.d(TAG, "onEvent: is called maxrent " + RentMax);

                                if (value == null) {
                                    Toast.makeText(Filter_SearchResult_UI.this, "NO RESULT HAS FOUND!", Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "onEvent: tatti " + error.getMessage());
                                } else {
                                    Log.d(TAG, "onEvent: databse base has data");
                                    for (DocumentChange doc : value.getDocumentChanges()) {
                                        SearchResultDataModel searchResultDataModel = doc.getDocument().toObject(SearchResultDataModel.class);
                                        searchResultDataModels.add(searchResultDataModel);
                                        Log.d(TAG, "onEvent: searrchabeldata " + value.getDocuments());
                                        filter_searchResult_recyclerViewAdapter.notifyDataSetChanged();
                                        recyclerView.invalidate();
                                    }
                                }

                            }
                        });
            }


        } else if (bedroom.equals("Any") && bathroomNumber.equals("Any") && !bathroomTypes.equals("Both") && balcony.equals("Any")) {
            if (bathroomTypes.equals("Attach Bathroom")) {

                firebaseFirestore.collection("PROPERTIES DETAILS").whereEqualTo("PROPERTY_TYPE", propertyType)
                        .whereLessThanOrEqualTo("RENT_PER_MONTH", RentMax).whereEqualTo("ATTACHED_BATH", "YES").
                        addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                Log.d(TAG, "onEvent: is called maxrent " + RentMax);

                                if (value == null) {
                                    Toast.makeText(Filter_SearchResult_UI.this, "NO RESULT HAS FOUND!", Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "onEvent: tatti " + error.getMessage());
                                } else {
                                    Log.d(TAG, "onEvent: databse base has data");
                                    for (DocumentChange doc : value.getDocumentChanges()) {
                                        SearchResultDataModel searchResultDataModel = doc.getDocument().toObject(SearchResultDataModel.class);
                                        searchResultDataModels.add(searchResultDataModel);
                                        Log.d(TAG, "onEvent: searrchabeldata " + value.getDocuments());
                                        filter_searchResult_recyclerViewAdapter.notifyDataSetChanged();
                                        recyclerView.invalidate();
                                    }
                                }

                            }
                        });

            } else {
                firebaseFirestore.collection("PROPERTIES DETAILS").whereEqualTo("PROPERTY_TYPE", propertyType)
                        .whereLessThanOrEqualTo("RENT_PER_MONTH", RentMax).whereEqualTo("ATTACHED_BATH", "NO").
                        addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                Log.d(TAG, "onEvent: is called maxrent " + RentMax);

                                if (value == null) {
                                    Toast.makeText(Filter_SearchResult_UI.this, "NO RESULT HAS FOUND!", Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "onEvent: tatti " + error.getMessage());
                                } else {
                                    Log.d(TAG, "onEvent: databse base has data");
                                    for (DocumentChange doc : value.getDocumentChanges()) {
                                        SearchResultDataModel searchResultDataModel = doc.getDocument().toObject(SearchResultDataModel.class);
                                        searchResultDataModels.add(searchResultDataModel);
                                        Log.d(TAG, "onEvent: searrchabeldata " + value.getDocuments());
                                        filter_searchResult_recyclerViewAdapter.notifyDataSetChanged();
                                        recyclerView.invalidate();
                                    }
                                }

                            }
                        });
            }

        } else if (!bedroom.equals("Any") && !bathroomNumber.equals("Any") && bathroomTypes.equals("Both") && !balcony.equals("Any")) {

            firebaseFirestore.collection("PROPERTIES DETAILS").whereEqualTo("PROPERTY_TYPE", propertyType)
                    .whereLessThanOrEqualTo("RENT_PER_MONTH", RentMax).whereEqualTo("BED_ROOMS", bedroom).whereEqualTo("BALCONY", balcony).
                    whereEqualTo("BATHROOMS", bathroomNumber).
                    addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            Log.d(TAG, "onEvent: is called maxrent " + RentMax);

                            if (value == null) {
                                Toast.makeText(Filter_SearchResult_UI.this, "NO RESULT HAS FOUND!", Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "onEvent: tatti " + error.getMessage());
                            } else {
                                Log.d(TAG, "onEvent: databse base has data");
                                for (DocumentChange doc : value.getDocumentChanges()) {
                                    SearchResultDataModel searchResultDataModel = doc.getDocument().toObject(SearchResultDataModel.class);
                                    searchResultDataModels.add(searchResultDataModel);
                                    Log.d(TAG, "onEvent: searrchabeldata " + value.getDocuments());
                                    filter_searchResult_recyclerViewAdapter.notifyDataSetChanged();
                                    recyclerView.invalidate();
                                }
                            }

                        }
                    });

        } else if (bedroom.equals("Any") && bathroomNumber.equals("Any") && bathroomTypes.equals("Both") && !balcony.equals("Any")) {
            firebaseFirestore.collection("PROPERTIES DETAILS").whereEqualTo("PROPERTY_TYPE", propertyType)
                    .whereLessThanOrEqualTo("RENT_PER_MONTH", RentMax).whereEqualTo("BALCONY", balcony).
                    addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            Log.d(TAG, "onEvent: is called maxrent " + RentMax);

                            if (value == null) {
                                Toast.makeText(Filter_SearchResult_UI.this, "NO RESULT HAS FOUND!", Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "onEvent: tatti " + error.getMessage());
                            } else {
                                Log.d(TAG, "onEvent: databse base has data");
                                for (DocumentChange doc : value.getDocumentChanges()) {
                                    SearchResultDataModel searchResultDataModel = doc.getDocument().toObject(SearchResultDataModel.class);
                                    searchResultDataModels.add(searchResultDataModel);
                                    Log.d(TAG, "onEvent: searrchabeldata " + value.getDocuments());
                                    filter_searchResult_recyclerViewAdapter.notifyDataSetChanged();
                                    recyclerView.invalidate();
                                }
                            }

                        }
                    });

        } else if (!bedroom.equals("Any") && !bathroomNumber.equals("Any") && !bathroomTypes.equals("Both") && balcony.equals("Any")) {
            if (bathroomTypes.equals("Attach Bathroom")) {

                firebaseFirestore.collection("PROPERTIES DETAILS").whereEqualTo("PROPERTY_TYPE", propertyType)
                        .whereLessThanOrEqualTo("RENT_PER_MONTH", RentMax).whereEqualTo("ATTACHED_BATH", "YES").whereEqualTo("BED_ROOMS", bedroom).
                        whereEqualTo("BATHROOMS", bathroomNumber).
                        addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                Log.d(TAG, "onEvent: is called maxrent " + RentMax);

                                if (value == null) {
                                    Toast.makeText(Filter_SearchResult_UI.this, "NO RESULT HAS FOUND!", Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "onEvent: tatti " + error.getMessage());
                                } else {
                                    Log.d(TAG, "onEvent: databse base has data");
                                    for (DocumentChange doc : value.getDocumentChanges()) {
                                        SearchResultDataModel searchResultDataModel = doc.getDocument().toObject(SearchResultDataModel.class);
                                        searchResultDataModels.add(searchResultDataModel);
                                        Log.d(TAG, "onEvent: searrchabeldata " + value.getDocuments());
                                        filter_searchResult_recyclerViewAdapter.notifyDataSetChanged();
                                        recyclerView.invalidate();
                                    }
                                }

                            }
                        });

            } else {
                firebaseFirestore.collection("PROPERTIES DETAILS").whereEqualTo("PROPERTY_TYPE", propertyType)
                        .whereLessThanOrEqualTo("RENT_PER_MONTH", RentMax).whereEqualTo("ATTACHED_BATH", "NO").whereEqualTo("BED_ROOMS", bedroom).
                        whereEqualTo("BATHROOMS", bathroomNumber).
                        addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                Log.d(TAG, "onEvent: is called maxrent " + RentMax);

                                if (value == null) {
                                    Toast.makeText(Filter_SearchResult_UI.this, "NO RESULT HAS FOUND!", Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "onEvent: tatti " + error.getMessage());
                                } else {
                                    Log.d(TAG, "onEvent: databse base has data");
                                    for (DocumentChange doc : value.getDocumentChanges()) {
                                        SearchResultDataModel searchResultDataModel = doc.getDocument().toObject(SearchResultDataModel.class);
                                        searchResultDataModels.add(searchResultDataModel);
                                        Log.d(TAG, "onEvent: searrchabeldata " + value.getDocuments());
                                        filter_searchResult_recyclerViewAdapter.notifyDataSetChanged();
                                        recyclerView.invalidate();
                                    }
                                }

                            }
                        });
            }

        } else if (bedroom.equals("Any") && !bathroomNumber.equals("Any") && !bathroomTypes.equals("Both") && balcony.equals("Any")) {
            if (bathroomTypes.equals("Attach Bathroom")) {

                firebaseFirestore.collection("PROPERTIES DETAILS").whereEqualTo("PROPERTY_TYPE", propertyType)
                        .whereLessThanOrEqualTo("RENT_PER_MONTH", RentMax).whereEqualTo("ATTACHED_BATH", "YES").
                        whereEqualTo("BATHROOMS", bathroomNumber).
                        addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                Log.d(TAG, "onEvent: is called maxrent " + RentMax);

                                if (value == null) {
                                    Toast.makeText(Filter_SearchResult_UI.this, "NO RESULT HAS FOUND!", Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "onEvent: tatti " + error.getMessage());
                                } else {
                                    Log.d(TAG, "onEvent: databse base has data");
                                    for (DocumentChange doc : value.getDocumentChanges()) {
                                        SearchResultDataModel searchResultDataModel = doc.getDocument().toObject(SearchResultDataModel.class);
                                        searchResultDataModels.add(searchResultDataModel);
                                        Log.d(TAG, "onEvent: searrchabeldata " + value.getDocuments());
                                        filter_searchResult_recyclerViewAdapter.notifyDataSetChanged();
                                        recyclerView.invalidate();
                                    }
                                }

                            }
                        });

            } else {
                firebaseFirestore.collection("PROPERTIES DETAILS").whereEqualTo("PROPERTY_TYPE", propertyType)
                        .whereLessThanOrEqualTo("RENT_PER_MONTH", RentMax).whereEqualTo("ATTACHED_BATH", "NO").
                        whereEqualTo("BATHROOMS", bathroomNumber).
                        addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                Log.d(TAG, "onEvent: is called maxrent " + RentMax);

                                if (value == null) {
                                    Toast.makeText(Filter_SearchResult_UI.this, "NO RESULT HAS FOUND!", Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "onEvent: tatti " + error.getMessage());
                                } else {
                                    Log.d(TAG, "onEvent: databse base has data");
                                    for (DocumentChange doc : value.getDocumentChanges()) {
                                        SearchResultDataModel searchResultDataModel = doc.getDocument().toObject(SearchResultDataModel.class);
                                        searchResultDataModels.add(searchResultDataModel);
                                        Log.d(TAG, "onEvent: searrchabeldata " + value.getDocuments());
                                        filter_searchResult_recyclerViewAdapter.notifyDataSetChanged();
                                        recyclerView.invalidate();
                                    }
                                }

                            }
                        });
            }

        } else if (!bedroom.equals("Any") && bathroomNumber.equals("Any") && bathroomTypes.equals("Both") && !balcony.equals("Any")) {
            firebaseFirestore.collection("PROPERTIES DETAILS").whereEqualTo("PROPERTY_TYPE", propertyType)
                    .whereLessThanOrEqualTo("RENT_PER_MONTH", RentMax).whereEqualTo("BED_ROOMS", bedroom).whereEqualTo("BALCONY", balcony).
                    addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            Log.d(TAG, "onEvent: is called maxrent " + RentMax);

                            if (value == null) {
                                Toast.makeText(Filter_SearchResult_UI.this, "NO RESULT HAS FOUND!", Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "onEvent: tatti " + error.getMessage());
                            } else {
                                Log.d(TAG, "onEvent: databse base has data");
                                for (DocumentChange doc : value.getDocumentChanges()) {
                                    SearchResultDataModel searchResultDataModel = doc.getDocument().toObject(SearchResultDataModel.class);
                                    searchResultDataModels.add(searchResultDataModel);
                                    Log.d(TAG, "onEvent: searrchabeldata " + value.getDocuments());
                                    filter_searchResult_recyclerViewAdapter.notifyDataSetChanged();
                                    recyclerView.invalidate();
                                }
                            }

                        }
                    });

        } else if (bedroom.equals("Any") && bathroomNumber.equals("Any") && !bathroomTypes.equals("Both") && !balcony.equals("Any")) {
            if (bathroomTypes.equals("Attach Bathroom")) {

                firebaseFirestore.collection("PROPERTIES DETAILS").whereEqualTo("PROPERTY_TYPE", propertyType)
                        .whereLessThanOrEqualTo("RENT_PER_MONTH", RentMax).whereEqualTo("ATTACHED_BATH", "YES").whereEqualTo("BALCONY", balcony).
                        addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                Log.d(TAG, "onEvent: is called maxrent " + RentMax);

                                if (value == null) {
                                    Toast.makeText(Filter_SearchResult_UI.this, "NO RESULT HAS FOUND!", Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "onEvent: tatti " + error.getMessage());
                                } else {
                                    Log.d(TAG, "onEvent: databse base has data");
                                    for (DocumentChange doc : value.getDocumentChanges()) {
                                        SearchResultDataModel searchResultDataModel = doc.getDocument().toObject(SearchResultDataModel.class);
                                        searchResultDataModels.add(searchResultDataModel);
                                        Log.d(TAG, "onEvent: searrchabeldata " + value.getDocuments());
                                        filter_searchResult_recyclerViewAdapter.notifyDataSetChanged();
                                        recyclerView.invalidate();
                                    }
                                }

                            }
                        });

            } else {
                firebaseFirestore.collection("PROPERTIES DETAILS").whereEqualTo("PROPERTY_TYPE", propertyType)
                        .whereLessThanOrEqualTo("RENT_PER_MONTH", RentMax).whereEqualTo("ATTACHED_BATH", "NO").whereEqualTo("BALCONY", balcony).
                        addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                Log.d(TAG, "onEvent: is called maxrent " + RentMax);

                                if (value == null) {
                                    Toast.makeText(Filter_SearchResult_UI.this, "NO RESULT HAS FOUND!", Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "onEvent: tatti " + error.getMessage());
                                } else {
                                    Log.d(TAG, "onEvent: databse base has data");
                                    for (DocumentChange doc : value.getDocumentChanges()) {
                                        SearchResultDataModel searchResultDataModel = doc.getDocument().toObject(SearchResultDataModel.class);
                                        searchResultDataModels.add(searchResultDataModel);
                                        Log.d(TAG, "onEvent: searrchabeldata " + value.getDocuments());
                                        filter_searchResult_recyclerViewAdapter.notifyDataSetChanged();
                                        recyclerView.invalidate();
                                    }
                                }

                            }
                        });
            }

        } else if (!bedroom.equals("Any") && !bathroomNumber.equals("Any") && bathroomTypes.equals("Both") && balcony.equals("Any")) {
            firebaseFirestore.collection("PROPERTIES DETAILS").whereEqualTo("PROPERTY_TYPE", propertyType)
                    .whereLessThanOrEqualTo("RENT_PER_MONTH", RentMax).whereEqualTo("BATHROOMS", bathroomNumber).whereEqualTo("BED_ROOMS", bedroom).
                    addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            Log.d(TAG, "onEvent: is called maxrent " + RentMax);

                            if (value == null) {
                                Toast.makeText(Filter_SearchResult_UI.this, "NO RESULT HAS FOUND!", Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "onEvent: tatti " + error.getMessage());
                            } else {
                                Log.d(TAG, "onEvent: databse base has data");
                                for (DocumentChange doc : value.getDocumentChanges()) {
                                    SearchResultDataModel searchResultDataModel = doc.getDocument().toObject(SearchResultDataModel.class);
                                    searchResultDataModels.add(searchResultDataModel);
                                    Log.d(TAG, "onEvent: searrchabeldata " + value.getDocuments());
                                    filter_searchResult_recyclerViewAdapter.notifyDataSetChanged();
                                    recyclerView.invalidate();
                                }
                            }

                        }
                    });
        }

    }
}
