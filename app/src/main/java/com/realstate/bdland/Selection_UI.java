package com.realstate.bdland;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.realstate.bdland.Database.UploadClientRequest;
import com.realstate.bdland.Database.Upload_info_properties;
import com.realstate.bdland.Main_Data_UI.ClientRequestDetails;
import com.realstate.bdland.Main_Data_UI.Main_page_UI;

import java.util.Arrays;
import java.util.List;

public class Selection_UI extends AppCompatActivity {
    private FirebaseAuth mAuth;
    public static final String TAG = "TAG";

    private List<AuthUI.IdpConfig> providers = Arrays.asList(
            new AuthUI.IdpConfig.GoogleBuilder().build());
    private NotificationManagerCompat notificationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui_selection);
        mAuth = FirebaseAuth.getInstance();


        findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //logout from firebase auth fully to see google account dialogBox again
                AuthUI.getInstance()
                        .signOut(Selection_UI.this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(Selection_UI.this, "User Signed Out", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Selection_UI.this, MainActivity.class));
                                finish();
                            }
                        });
            }
        });


        findViewById(R.id.clintRequest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Selection_UI.this, ClientRequestDetails.class));
            }
        });
        findViewById(R.id.uploadRequest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Selection_UI.this, UploadClientRequest.class));
            }
        });

        findViewById(R.id.search_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Selection_UI.this, Main_page_UI.class));

            }
        });
        findViewById(R.id.upload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Selection_UI.this, Upload_info_properties.class));
            }
        });




    }


}