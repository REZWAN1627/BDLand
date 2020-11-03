package com.realstate.bdland.Adapter.RecyclerViewAdapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.realstate.bdland.Database.Data_model.IndividualSectorDataModel;
import com.realstate.bdland.Main_Data_UI.Room_Details_Ui;
import com.realstate.bdland.R;

import java.util.ArrayList;

public class IndividualSectorRecyclerViewAdapter extends RecyclerView.Adapter<IndividualSectorRecyclerViewAdapter.ViewHolder> {
    public static final String TAG = "TAG";

    private ArrayList<IndividualSectorDataModel> individualSectorDataModels = new ArrayList<>();
    private Context context;

    public IndividualSectorRecyclerViewAdapter(ArrayList<IndividualSectorDataModel> individualSectorDataModels, Context context) {
        this.individualSectorDataModels = individualSectorDataModels;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.carddesignindividualsectorproperties, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: is called ");

        holder.setApartment_frontImage(individualSectorDataModels.get(position).getIMAGE_URL());
        holder.setProperty_rent(individualSectorDataModels.get(position).getRENT_PER_MONTH());
        holder.setNumberOfBalcony(individualSectorDataModels.get(position).getBALCONY());
        holder.setProperty_Type(individualSectorDataModels.get(position).getPROPERTY_TYPE());
        holder.setNumberOfKitchen(individualSectorDataModels.get(position).getKITCHEN());
        holder.setNumberOfBeds(individualSectorDataModels.get(position).getBED_ROOMS());
        holder.setNumberOfBathroom(individualSectorDataModels.get(position).getBATHROOMS());
        holder.setArea_location(individualSectorDataModels.get(position).getLOCATION());
        holder.setSector_location(individualSectorDataModels.get(position).getSECTOR_LOCATION());
        holder.Apartment_frontImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, Room_Details_Ui.class);

                i.putExtra("Location", individualSectorDataModels.get(position).getLOCATION());
                i.putExtra("HoldingNumber", individualSectorDataModels.get(position).getHOUSE_NUMBER());
                i.putExtra("sectorLocation", individualSectorDataModels.get(position).getSECTOR_LOCATION());
                i.putExtra("property_type", individualSectorDataModels.get(position).getPROPERTY_TYPE());
                i.putExtra("Rent", individualSectorDataModels.get(position).getRENT_PER_MONTH());
                i.putExtra("balcony", individualSectorDataModels.get(position).getBALCONY());
                i.putExtra("kitchen", individualSectorDataModels.get(position).getKITCHEN());
                i.putExtra("Bedroom", individualSectorDataModels.get(position).getBED_ROOMS());
                i.putExtra("bathroom", individualSectorDataModels.get(position).getBATHROOMS());
                i.putExtra("Sector location", individualSectorDataModels.get(position).getSECTOR_LOCATION());
                i.putExtra("description", individualSectorDataModels.get(position).getFLAT_DESCRIPTION());

                context.startActivity(i);
            }
        });

    }
    public void Reset() {
        individualSectorDataModels.clear();

    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: "+individualSectorDataModels.size());
        return individualSectorDataModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView Apartment_frontImage;
        private TextView Property_Type;
        private TextView Property_rent;
        private TextView Area_location;
        private TextView Sector_location;

        private View mView;

        private TextView NumberOfBeds, NumberOfKitchen, NumberOfBalcony, NumberOfBathroom;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setArea_location(String area_location) {
            Area_location = itemView.findViewById(R.id.card_area_locations);
            Area_location.setText(area_location);
        }

        public void setSector_location(String sector_location) {
            Sector_location = itemView.findViewById(R.id.card_sector_locations);
            Sector_location.setText(sector_location);
        }

        public void setApartment_frontImage(String IMAGE_URL) {
            Apartment_frontImage = mView.findViewById(R.id.apartment_front_pics);
            Glide.with(context).load(IMAGE_URL).into(Apartment_frontImage);

        }

        public void setProperty_Type(String PROPERTY_TYPE) {
            Property_Type = mView.findViewById(R.id.property_types);
            Property_Type.setText(PROPERTY_TYPE);
        }

        public void setProperty_rent(String RENT_PER_MONT) {
            Property_rent = mView.findViewById(R.id.property_rents);
            Property_rent.setText(RENT_PER_MONT);
        }


        public void setNumberOfBeds(String BED_ROOMS) {
            NumberOfBeds = mView.findViewById(R.id.number_of_bedss);
            NumberOfBeds.setText(BED_ROOMS);
        }

        public void setNumberOfKitchen(String KITCHEN) {
            NumberOfKitchen = mView.findViewById(R.id.number_of_kitchens);
            NumberOfKitchen.setText(KITCHEN);
        }

        public void setNumberOfBalcony(String BALCONY) {
            NumberOfBalcony = mView.findViewById(R.id.number_of_balconys);
            NumberOfBalcony.setText(BALCONY);
        }

        public void setNumberOfBathroom(String BATHROOMS) {
            NumberOfBathroom = mView.findViewById(R.id.number_of_bathroomss);
            NumberOfBathroom.setText(BATHROOMS);
        }
    }
}
