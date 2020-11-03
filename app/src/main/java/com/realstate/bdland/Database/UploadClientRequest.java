package com.realstate.bdland.Database;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.realstate.bdland.R;

import java.util.HashMap;
import java.util.Map;

public class UploadClientRequest extends AppCompatActivity {
    public static final String TAG = "TAG";
    private EditText Name, Number, clientRequirement, Rent, occupation, familyMember, requireSectorOptional;

    private FirebaseFirestore firebaseFirestore;
    private DocumentReference documentReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_client_request);

        Name = findViewById(R.id.name_client);
        Number = findViewById(R.id.Phone_client);
        clientRequirement = findViewById(R.id.client_Requirement);
        Rent = findViewById(R.id.client_rent);
        occupation = findViewById(R.id.clinet_Occupation_details);
        familyMember = findViewById(R.id.client_familyMember);
        requireSectorOptional = findViewById(R.id.client_require_sector);


        firebaseFirestore = FirebaseFirestore.getInstance();


        findViewById(R.id.clintRequestUpload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Name.getText().toString().isEmpty() || Number.getText().toString().isEmpty() || clientRequirement.getText().toString().isEmpty() || Rent.getText().toString().isEmpty() ||
                        occupation.getText().toString().isEmpty() || familyMember.getText().toString().isEmpty()) {
                    Toast.makeText(UploadClientRequest.this, "One of the Field is Empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                documentReference = firebaseFirestore.collection("CLIENT REQUEST").document("DBLand" + Number.getText().toString());
                Map<String, Object> Client = new HashMap<>();

                Client.put("CLIENT_NAME", Name.getText().toString());
                Client.put("CLIENT_PHONE_NUMBER", Number.getText().toString());
                Client.put("CLIENT_REQUIREMENT", clientRequirement.getText().toString());
                Client.put("CLIENT_REQUEST_RENT", Rent.getText().toString());
                Client.put("CLIENT_OCCUPATION", occupation.getText().toString());
                Client.put("CLIENT_FAMILY_MEMBER", familyMember.getText().toString());
                Client.put("CLIENT_REQUESTED_SECTOR", requireSectorOptional.getText().toString());
                Client.put("REQUEST_STATUS", "NO");
                Client.put("CLIENT_ID", "DBLand" + Number.getText().toString());

                documentReference.set(Client).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Name.setText("");
                        Number.setText("");
                        clientRequirement.setText("");
                        Rent.setText("");
                        occupation.setText("");
                        familyMember.setText("");
                        requireSectorOptional.setText("");

                        Log.d(TAG, "onSuccess: Information stored successfully");
                        Toast.makeText(UploadClientRequest.this, "Information stored successfully", Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UploadClientRequest.this, "Error! " + e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });


            }
        });

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