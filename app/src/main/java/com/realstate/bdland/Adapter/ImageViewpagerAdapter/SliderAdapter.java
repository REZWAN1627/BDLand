package com.realstate.bdland.Adapter.ImageViewpagerAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.realstate.bdland.Database.Data_model.SliderItemDataModel;
import com.realstate.bdland.R;

import java.util.ArrayList;


public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderViewHolder> {

    private ArrayList<SliderItemDataModel> sliderItemDataModels = new ArrayList<>();
    private ViewPager2 viewPager2;

    public SliderAdapter(ArrayList<SliderItemDataModel> sliderItemDataModels, ViewPager2 viewPager2) {
        this.sliderItemDataModels = sliderItemDataModels;
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.carddesign_imageholder, parent, false);
        return new SliderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        holder.setImage(sliderItemDataModels.get(position));
    }

    @Override
    public int getItemCount() {
        return sliderItemDataModels.size();
    }

    class SliderViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;

        public SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);

        }

        void setImage(SliderItemDataModel sliderItemDataModel) {
            imageView.setImageURI(sliderItemDataModel.getImage());


        }


    }
  /*  private Runnable slideRunnable = new Runnable() {
        @Override
        public void run() {
            sliderItemDataModels.addAll(sliderItemDataModels);
            notifyDataSetChanged();

        }
    };*/
}
