package com.realstate.bdland.FilterSearch;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.realstate.bdland.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import me.bendik.simplerangeview.SimpleRangeView;

public class Filter_Search_UI extends AppCompatActivity {

    public static final String TAG = "TAG";
    private SimpleRangeView simpleRangeView;
    private TextView textView;
    private int max, min;

    private RadioGroup radioGroup_propertyType, radioGroup_Bedrooms, radioGroup_BathroomsTypes, radioGroup_BathroomsNumber, RadioGroup_Balcony;
    private RadioButton radioPropertyType, radioBathroomTypes, radioBathroomNumbers, radioBeedrooms, radioBelcony;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui_filter_search);
        radioGroup_propertyType = findViewById(R.id.radio_groupfilter);
        radioGroup_Bedrooms = findViewById(R.id.radio_groupfilter3);
        radioGroup_BathroomsTypes = findViewById(R.id.radio_groupfiltero2);
        radioGroup_BathroomsNumber = findViewById(R.id.radio_groupfilter2);
        RadioGroup_Balcony = findViewById(R.id.radio_groupfilter4);


        simpleRangeView = findViewById(R.id.range_bar);
        textView = findViewById(R.id.rangeViewer);

        // SelectedID =  radioGroup_propertyType.getCheckedRadioButtonId();


        simpleRangeView.setOnChangeRangeListener(new SimpleRangeView.OnChangeRangeListener() {
            @Override
            public void onRangeChanged(@NotNull SimpleRangeView simpleRangeView, int i, int i1) {
                textView.setText(String.valueOf(i) + ",000TK" + "-" + String.valueOf(i1) + ",000TK");
                Log.d(TAG, "onCreate: onRangeChanged 1st==>" + textView.getText().toString());
                min = i;
                max = i1;
            }
        });


        simpleRangeView.setOnTrackRangeListener(new SimpleRangeView.OnTrackRangeListener() {
            @Override
            public void onStartRangeChanged(@NotNull SimpleRangeView simpleRangeView, int i) {
                textView.setText(String.valueOf(i) + ",000TK");
                Log.d(TAG, "onCreate: onStartRangeChanged 2nd==>" + textView.getText().toString());
            }

            @Override
            public void onEndRangeChanged(@NotNull SimpleRangeView simpleRangeView, int i) {

                textView.setText(String.valueOf(i) + ",000TK");
                Log.d(TAG, "onCreate: onEndRangeChanged 3rd====>" + textView.getText().toString());

            }
        });

        simpleRangeView.setOnRangeLabelsListener(new SimpleRangeView.OnRangeLabelsListener() {
            @Nullable
            @Override
            public String getLabelTextForPosition(@NotNull SimpleRangeView simpleRangeView, int i, @NotNull SimpleRangeView.State state) {
                return String.valueOf(i);
            }
        });


        radioGroup_propertyType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioPropertyType = findViewById(checkedId);

            }

        });

        radioGroup_BathroomsTypes.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioBathroomTypes = findViewById(checkedId);
            }
        });

        radioGroup_BathroomsNumber.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioBathroomNumbers = findViewById(checkedId);
            }
        });

        radioGroup_Bedrooms.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioBeedrooms = findViewById(checkedId);

            }
        });
        RadioGroup_Balcony.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioBelcony = findViewById(checkedId);

            }
        });

        findViewById(R.id.pageresetter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioGroup_BathroomsTypes.clearCheck();
                radioGroup_BathroomsNumber.clearCheck();
                radioGroup_Bedrooms.clearCheck();
                radioGroup_propertyType.clearCheck();
                RadioGroup_Balcony.clearCheck();
            }
        });


        findViewById(R.id.show_property).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (radioBeedrooms == null || radioBathroomNumbers == null
                        || radioBathroomTypes == null || radioPropertyType == null || radioBelcony.getText() == null || radioBathroomTypes == null) {
                    Toast.makeText(Filter_Search_UI.this, "Some of the selection is messing", Toast.LENGTH_SHORT).show();
                    return;

                }

                Intent i = new Intent(Filter_Search_UI.this, Filter_SearchResult_UI.class);
                i.putExtra("RentMax", String.valueOf(max * 1000));
                i.putExtra("propertyType", radioPropertyType.getText());
                i.putExtra("bedroom", radioBeedrooms.getText());
                i.putExtra("bathroomTypes", radioBathroomTypes.getText());
                i.putExtra("bathroomNumber", radioBathroomNumbers.getText());
                i.putExtra("balcony", radioBelcony.getText().toString());
                startActivity(i);


            }
        });


    }


}