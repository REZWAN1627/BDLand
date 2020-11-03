package com.realstate.bdland.Adapter.RecyclerViewAdapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.realstate.bdland.Adapter.ImageViewpagerAdapter.SlideAdapterinRecyclerView;
import com.realstate.bdland.Database.Data_model.SliderImageForRecyclerDataModel;
import com.realstate.bdland.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RoomDetailsRecyclerViewAdapter extends RecyclerView.Adapter<RoomDetailsRecyclerViewAdapter.ViewHolder> {
    public static final String TAG = "TAG";
    private boolean zoomOut = false;
    private ArrayList<Uri> imageList = new ArrayList<>();
    private Context mContext;
    private int next = 1;
    private ArrayList<SliderImageForRecyclerDataModel> sliderImageForRecyclerDataModels = new ArrayList<>();


    public RoomDetailsRecyclerViewAdapter(ArrayList<Uri> imageList, Context mContext) {
        this.imageList = imageList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.roomdetailsimageviewholder, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Picasso.get().load(imageList.get(position)).into(holder.imageView);


        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                arraySetter();
                viewDialog();
            }
        });


    }

    private void arraySetter() {
        Log.d(TAG, "arraySetter: is called");

        for (int i = 0; i < imageList.size(); i++) {
            Log.d(TAG, "arraySetter: size " + imageList.size() + " count " + i);
            sliderImageForRecyclerDataModels.add(new SliderImageForRecyclerDataModel(imageList.get(i)));
        }
    }


    private void viewDialog() {

        final Dialog nagDialog = new Dialog(mContext, R.style.imagepopup);
        nagDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        nagDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        nagDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        nagDialog.setCancelable(false);
        nagDialog.setCanceledOnTouchOutside(true);
        nagDialog.setContentView(R.layout.imageviewinpopup);
        TabLayout tabLayout = nagDialog.findViewById(R.id.designTablayout);
        final ViewPager2 viewPager2 = (ViewPager2) nagDialog.findViewById(R.id.popImage);
        Button button = nagDialog.findViewById(R.id.next_buttonpop);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nagDialog.dismiss();
            }
        });

        viewPager2.setAdapter(new SlideAdapterinRecyclerView(sliderImageForRecyclerDataModels, viewPager2));
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {

            }
        });
        tabLayoutMediator.attach();


        nagDialog.show();
    }


    @Override
    public int getItemCount() {
        return imageList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageid);

        }
    }


}
