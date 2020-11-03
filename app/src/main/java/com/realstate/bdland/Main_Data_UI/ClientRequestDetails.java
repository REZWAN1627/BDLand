package com.realstate.bdland.Main_Data_UI;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.realstate.bdland.Adapter.RecyclerViewAdapter.ClientRecyclerAdapter;
import com.realstate.bdland.Database.Data_model.ClientDataRequestModel;
import com.realstate.bdland.R;

import java.util.ArrayList;

public class ClientRequestDetails extends AppCompatActivity {

    public static final String TAG = "TAG";
    private RecyclerView recyclerView;

    private ArrayList<ClientDataRequestModel> clientDataRequestModelArrayList;
    private FirebaseFirestore firebaseFirestore;
    private ClientRecyclerAdapter clientRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_request_details);

        recyclerView = findViewById(R.id.clientRequestRecycler);
        clientDataRequestModelArrayList = new ArrayList<>();
        clientRecyclerAdapter = new ClientRecyclerAdapter(clientDataRequestModelArrayList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(clientRecyclerAdapter);

        firebaseFirestore = FirebaseFirestore.getInstance();

        firebaseFirestore.collection("CLIENT REQUEST").
                addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (value.isEmpty()) {
                            Log.d(TAG, "onEvent: is called");

                        } else {
                            Log.d(TAG, "onEvent: is not emptyu ");
                        }

                        for (DocumentChange doc : value.getDocumentChanges()) {
                            Log.d(TAG, "onEvent: is called loop");
                            ClientDataRequestModel clientDataRequestModel = doc.getDocument().toObject(ClientDataRequestModel.class);
                            Log.d(TAG, "onEvent: data " + clientDataRequestModel.getCLIENT_ID());
                            clientDataRequestModelArrayList.add(clientDataRequestModel);
                            Log.d(TAG, "onEvent: clientDataRequestModelArrayList " + clientDataRequestModelArrayList.get(0).getCLIENT_ID());
                            clientRecyclerAdapter.notifyDataSetChanged();
                            recyclerView.invalidate();

                        }

                    }
                });


    }
}