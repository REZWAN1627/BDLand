package com.realstate.bdland.Adapter.RecyclerViewAdapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.realstate.bdland.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RoomDetails_RecyclerView_Spinner_Adapter extends RecyclerView.Adapter<RoomDetails_RecyclerView_Spinner_Adapter.ViewHolder> {
    private static final String TAG = "TAG";


    private ArrayList<String> Names_of_rooms_categories = new ArrayList<>();
    ArrayList<String> selected_room_categories = new ArrayList<>();

    Map<Integer, Integer> mSpinnerSelectedItem = new HashMap<Integer, Integer>(); //Track value of spinner in recycler view


    public ArrayList<Uri> getmImageUrl() {
        return mImageUrl;
    }

    public ArrayList<String> getSelected_room_categories() {
        return selected_room_categories;
    }

    private ArrayList<Uri> mImageUrl = new ArrayList<>();
    private Context mContext;


    public RoomDetails_RecyclerView_Spinner_Adapter(boolean b) {
        if (b) {
            mImageUrl.clear();
            Names_of_rooms_categories.clear();
            selected_room_categories.clear();
        }

    }


    public RoomDetails_RecyclerView_Spinner_Adapter(ArrayList<Uri> mImageUrl, Context mContext) {
        Names_of_rooms_categories.add(0, "select Room Categories");
        Names_of_rooms_categories.add("Bed room");
        Names_of_rooms_categories.add("Dinning room");
        Names_of_rooms_categories.add("Common Bathroom");
        Names_of_rooms_categories.add("Attach bathroom");
        Names_of_rooms_categories.add("Balcony");
        Names_of_rooms_categories.add("Kitchen");
        Names_of_rooms_categories.add("Store room");
        Names_of_rooms_categories.add("Garage");
        this.mImageUrl = mImageUrl;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.carddesign_recyclerview_room_names, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        Log.d(TAG, "onBindViewHolder: called.");
        Glide.with(mContext)
                .asBitmap()
                .load(mImageUrl.get(position))
                .into(holder.imageView);


        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(mContext, R.layout.support_simple_spinner_dropdown_item, Names_of_rooms_categories);
        stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.spinner.setAdapter(stringArrayAdapter);

        if (mSpinnerSelectedItem.containsKey(position)) {
            holder.spinner.setSelection(mSpinnerSelectedItem.get(position));
        }

        holder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (!adapterView.getItemAtPosition(i).equals("select Room Categories")) {
                    mSpinnerSelectedItem.put(position, i);
                    String temp = adapterView.getItemAtPosition(i).toString();
                    selected_room_categories.set(position, temp);
                    Log.d(TAG, "onItemSelected: string ArrayList value ==> " + selected_room_categories.get(position) + "== position ==" + position + " size of arrayList ==> " + selected_room_categories.size()
                            + " arraylist position  ===> " + selected_room_categories.get(0));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Log.d(TAG, "onNothingSelected: called");
                return;
            }
        });
    }

    public void reset() {

        mSpinnerSelectedItem.clear();

    }

    public void replace(int max) { // karon ei ta na korle value direct add korle size barte thakbe new update value gulu niche add hobe

        Log.d(TAG, "replace: size of array " + max);
        for (int i = 0; i < max; i++) {
            selected_room_categories.add(i, "Null");
        }
    }


    @Override
    public int getItemCount() {
        return mImageUrl.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private Spinner spinner;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.recyclerImage);
            spinner = itemView.findViewById(R.id.roomSpinner);
        }
    }
}
