package com.realstate.bdland;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

import java.util.concurrent.TimeUnit;

import static com.realstate.bdland.Util.Constant.ERROR_DIALOG_REQUEST;
import static com.realstate.bdland.Util.Constant.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION;
import static com.realstate.bdland.Util.Constant.PERMISSIONS_REQUEST_ENABLE_GPS;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "TAG";
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 0;
    private FirebaseAuth mAuth;
    private FirebaseAuth firebaseAuth;

    private EditText otpNumberOne, getOtpNumberTwo, getOtpNumberThree, getOtpNumberFour, getOtpNumberFive, otpNumberSix;

    private LinearLayout linearLayout;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    private Boolean otpValid = true;

    private CountryCodePicker countryCodePicker;

    private String verificationId;

    private PhoneAuthProvider.ForceResendingToken token;
    private Boolean VerificationOnProgress = false;

    private AlertDialog.Builder DialogBuilder; // for pop up dialog
    private AlertDialog dialog;//pop up dialog


    private EditText pop_phoneNumber_txt;

    private ProgressBar PopUpProgressBar;

    private TextView pop_process_phone_txt;
    private TextView phone_number;
    private Button pop_phone_verify_btn;
    private Button pop_email_verify_btn;
    private Boolean flag = false;//to hide resend button
    private Button pop_resend_btn;

    private String countryCode;

    private Boolean mLocationPermissionGranted = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.sign_in_with_google).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GSignIn();
            }
        });

        findViewById(R.id.phoneLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // startActivity(new Intent(MainActivity.this, Filter_Search_UI.class));
                CreateNewVerifiedDialog();
            }
        });
    }

    private void CreateNewVerifiedDialog() {

        Log.d(TAG, "CreateNewVerifiedDialog: is called");

        DialogBuilder = new AlertDialog.Builder(this);

        final View verificationPopUp = getLayoutInflater().inflate(R.layout.phone_verification_popup, null);//layout

        pop_phoneNumber_txt = verificationPopUp.findViewById(R.id.user_phone_otp_pop);
        PopUpProgressBar = verificationPopUp.findViewById(R.id.progressBar_for_otp);
        pop_process_phone_txt = verificationPopUp.findViewById(R.id.SendingOtp);
        pop_phone_verify_btn = verificationPopUp.findViewById(R.id.phone_auth_btn_pop);
        pop_resend_btn = verificationPopUp.findViewById(R.id.POP_phone_auth_resend_btn);
        linearLayout = verificationPopUp.findViewById(R.id.OTP_liner_layout);

        otpNumberOne = verificationPopUp.findViewById(R.id.otpNumberOne);
        getOtpNumberTwo = verificationPopUp.findViewById(R.id.optNumberTwo);
        getOtpNumberThree = verificationPopUp.findViewById(R.id.otpNumberThree);
        getOtpNumberFour = verificationPopUp.findViewById(R.id.otpNumberFour);
        getOtpNumberFive = verificationPopUp.findViewById(R.id.otpNumberFive);
        otpNumberSix = verificationPopUp.findViewById(R.id.optNumberSix);

        firebaseAuth = FirebaseAuth.getInstance();


        countryCodePicker = verificationPopUp.findViewById(R.id.ccp_pop);

        DialogBuilder.setView(verificationPopUp);
        dialog = DialogBuilder.create();
        dialog.show();

        pop_phone_verify_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d(TAG, "onClick: verify button pressed");

                if (!VerificationOnProgress) {
                    if (!pop_phoneNumber_txt.getText().toString().isEmpty() && pop_phoneNumber_txt.getText().toString().length() == 10) {
                        countryCode = "+" + countryCodePicker.getSelectedCountryCode() + pop_phoneNumber_txt.getText().toString();
                        Log.d(TAG, "OnClick: Phone number --> " + countryCode);
                        PopUpProgressBar.setVisibility(View.VISIBLE);
                        pop_process_phone_txt.setText("Sending OTP..");
                        pop_process_phone_txt.setVisibility(View.VISIBLE);
                        Log.d(TAG, "onClick: sending number " + countryCode);
                        sendOTP(countryCode);
                    } else {
                        pop_phoneNumber_txt.setError("Phone Number is not Valid!");
                    }

                } else {
                    validateField(otpNumberOne);
                    validateField(getOtpNumberTwo);
                    validateField(getOtpNumberThree);
                    validateField(getOtpNumberFour);
                    validateField(getOtpNumberFive);
                    validateField(otpNumberSix);
                    if (otpValid) {
                        // send otp to the user
                        String otp = otpNumberOne.getText().toString() + getOtpNumberTwo.getText().toString() + getOtpNumberThree.getText().toString() + getOtpNumberFour.getText().toString() +
                                getOtpNumberFive.getText().toString() + otpNumberSix.getText().toString();

                        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, otp);

                        verifyAuthentication(credential);

                    }
                }


            }
        });

        pop_resend_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: resend button is called");
                PopUpProgressBar.setVisibility(View.VISIBLE);
                resendOTP(countryCode);
            }
        });

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                Log.d(TAG, "onCodeSent: is called");

                verificationId = s;
                token = forceResendingToken;
                PopUpProgressBar.setVisibility(View.GONE);
                VerificationOnProgress = true;
                pop_phone_verify_btn.setText("Verify");
                pop_process_phone_txt.setVisibility(View.GONE);
                pop_resend_btn.setVisibility(View.GONE);
                linearLayout.setVisibility(View.VISIBLE);


            }

            @Override
            public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
                Log.d(TAG, "onCodeAutoRetrievalTimeOut: is called");
                super.onCodeAutoRetrievalTimeOut(s);
                if (flag) {
                    pop_resend_btn.setVisibility(View.GONE);

                } else {
                    pop_resend_btn.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                Log.d(TAG, "onVerificationCompleted: is called");
                verifyAuthentication(phoneAuthCredential);

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(MainActivity.this, "OTP Verification Failed" + e.getMessage(),
                        Toast.LENGTH_SHORT).show();

            }
        };

    }

    private void verifyAuthentication(PhoneAuthCredential credential) {
        Log.d(TAG, "verifyAuthentication: is called");


        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Authentication Success", Toast.LENGTH_SHORT).show();
                    countryCodePicker.setVisibility(View.GONE);
                    pop_phoneNumber_txt.setVisibility(View.GONE);
                    PopUpProgressBar.setVisibility(View.GONE);
                    pop_phone_verify_btn.setVisibility(View.GONE);
                    pop_process_phone_txt.setVisibility(View.GONE);
                    pop_resend_btn.setVisibility(View.GONE);
                    linearLayout.setVisibility(View.GONE);
                    flag = true;
                    startActivity(new Intent(MainActivity.this, Selection_UI.class));
                } else {
                    Toast.makeText(MainActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void sendOTP(String phoneNumber) {
        Log.d(TAG, "sendOTP: is called");
        PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNumber, 60, TimeUnit.SECONDS, this, mCallbacks);

    }

    public void resendOTP(String phoneNumber) {
        Log.d(TAG, "resendOTP: is called");
        PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNumber, 60, TimeUnit.SECONDS, this, mCallbacks, token);
    }

    public void validateField(EditText field) {
        Log.d(TAG, "validateField: is called");
        if (field.getText().toString().isEmpty()) {
            field.setError("Required");
            otpValid = false;
        } else {
            Log.d(TAG, "validateField: is true for otp");
            otpValid = true;
        }
    }

    private void GSignIn() {

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: called.");
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ENABLE_GPS: {
                if (mLocationPermissionGranted) {
                    ///kisu korte hobe

                    Toast.makeText(this, "Enable", Toast.LENGTH_SHORT).show();
                } else {
                    getLocationPermission();
                }
            }
        }
        Log.d(TAG, "onActivityResult: called " + requestCode + "===>" + resultCode + data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Log.d(TAG, "onActivityResult: called");
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);

                FirebaseAuthWithGoogle(account.getIdToken());

            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.d(TAG, "onActivityResult: " + e.getMessage());
                Toast.makeText(this, "failed to create " + e.getMessage(), Toast.LENGTH_SHORT).show();
                // ...
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {

            String UserEmail = currentUser.getEmail();
            startActivity(new Intent(MainActivity.this, Selection_UI.class).putExtra("CurrentUserEmail", UserEmail));
            finish();

        }
    }

    private void FirebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    String UserEmail = mAuth.getCurrentUser().getEmail();
                    startActivity(new Intent(MainActivity.this, Selection_UI.class).putExtra("CurrentUserEmail", UserEmail));
                    finish();

                } else {

                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(MainActivity.this, "ERROR! " + e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (checkMapServices()) {
            if (mLocationPermissionGranted) {
                Toast.makeText(this, "Enable", Toast.LENGTH_SHORT).show();
            } else {
                getLocationPermission();
            }
        }
    }


    //permission section-------------------------------------------------------------------------------------------------------


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
            //kisu korte hobe
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    public boolean isServicesOK() {
        Log.d(TAG, "isServicesOK: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);

        if (available == ConnectionResult.SUCCESS) {
            //everything is fine and the user can make map requests
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            //an error occured but we can resolve it
            Log.d(TAG, "isServicesOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
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


}