package com.realstate.bdland.Adapter.RecyclerViewAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.realstate.bdland.Main_Data_UI.IndividualSector_UI;
import com.realstate.bdland.R;

import java.util.ArrayList;

public class SectorViewRecyclerAdapter extends RecyclerView.Adapter<SectorViewRecyclerAdapter.ViewHolder> {
    public static final String TAG = "TAG";
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrl = new ArrayList<>();
    private Context mContext;
    public SectorViewRecyclerAdapter(ArrayList<String> mNames, ArrayList<String> mImageUrl, Context mContext) {
        this.mNames = mNames;
        this.mImageUrl = mImageUrl;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardsesign_sector, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        Glide.with(mContext).asBitmap().load(mImageUrl.get(position)).into(holder.imageView);
        holder.textView.setText(mNames.get(position));
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, IndividualSector_UI.class).putExtra("SectorNames",mNames.get(position)));
            }
        });

    }


    @Override
    public int getItemCount() {
        return mImageUrl.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;
        private Button button;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.sectorImage);
            textView = itemView.findViewById(R.id.card_sector_name);
            button = itemView.findViewById(R.id.sectorButton);
        }
    }
}
