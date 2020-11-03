package com.realstate.bdland.Adapter.ImageViewpagerAdapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.realstate.bdland.Database.Data_model.SliderImageForRecyclerDataModel;
import com.realstate.bdland.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SlideAdapterinRecyclerView extends RecyclerView.Adapter<SlideAdapterinRecyclerView.ViewHolder> {
    public static final String TAG = "TAG";
    private ArrayList<SliderImageForRecyclerDataModel> sliderImageForRecyclerDataModels = new ArrayList<>();
    private ViewPager2 viewPager2;

    public SlideAdapterinRecyclerView(ArrayList<SliderImageForRecyclerDataModel> sliderImageForRecyclerDataModels, ViewPager2 viewPager2) {
        this.sliderImageForRecyclerDataModels = sliderImageForRecyclerDataModels;
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardimageholderinrecyclerview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        holder.setImage(sliderImageForRecyclerDataModels.get(position));


    }


    @Override
    public int getItemCount() {
        return sliderImageForRecyclerDataModels.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageholderinrecyclerview);
        }

        void setImage(SliderImageForRecyclerDataModel sliderImageForRecyclerDataModel) {
            Log.d(TAG, "setImage: " + sliderImageForRecyclerDataModel.getImage());

            Picasso.get().load(sliderImageForRecyclerDataModel.getImage())
                    .rotate(90).into(imageView);


        }
    }


}
