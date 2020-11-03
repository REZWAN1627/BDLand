package com.realstate.bdland.Adapter.RecyclerViewAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.realstate.bdland.Database.Data_model.SearchResultDataModel;
import com.realstate.bdland.Main_Data_UI.Room_Details_Ui;
import com.realstate.bdland.R;

import java.util.ArrayList;

public class Filter_SearchResult_RecyclerViewAdapter extends RecyclerView.Adapter<Filter_SearchResult_RecyclerViewAdapter.ViewHolder> {

    public static final String TAG = "TAG";
    private ArrayList<SearchResultDataModel> searchResultDataModels = new ArrayList<>();
    private Context context;

    public Filter_SearchResult_RecyclerViewAdapter(ArrayList<SearchResultDataModel> searchResultDataModels, Context context) {
        this.searchResultDataModels = searchResultDataModels;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.carddesign_apartment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.setApartment_frontImage(searchResultDataModels.get(position).getIMAGE_URL());
        holder.setProperty_rent(searchResultDataModels.get(position).getRENT_PER_MONTH());
        holder.setNumberOfBalcony(searchResultDataModels.get(position).getBALCONY());
        holder.setProperty_Type(searchResultDataModels.get(position).getPROPERTY_TYPE());
        holder.setNumberOfKitchen(searchResultDataModels.get(position).getKITCHEN());
        holder.setNumberOfBeds(searchResultDataModels.get(position).getBED_ROOMS());
        holder.setNumberOfBathroom(searchResultDataModels.get(position).getBATHROOMS());
        holder.setArea_location(searchResultDataModels.get(position).getLOCATION());
        holder.setSector_location(searchResultDataModels.get(position).getSECTOR_LOCATION());
        holder.Apartment_frontImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, Room_Details_Ui.class);

                i.putExtra("Location", searchResultDataModels.get(position).getLOCATION());
                i.putExtra("HoldingNumber", searchResultDataModels.get(position).getHOUSE_NUMBER());
                i.putExtra("sectorLocation", searchResultDataModels.get(position).getSECTOR_LOCATION());
                i.putExtra("property_type", searchResultDataModels.get(position).getPROPERTY_TYPE());
                i.putExtra("Rent", searchResultDataModels.get(position).getRENT_PER_MONTH());
                i.putExtra("balcony", searchResultDataModels.get(position).getBALCONY());
                i.putExtra("kitchen", searchResultDataModels.get(position).getKITCHEN());
                i.putExtra("Bedroom", searchResultDataModels.get(position).getBED_ROOMS());
                i.putExtra("bathroom", searchResultDataModels.get(position).getBATHROOMS());
                i.putExtra("Sector location", searchResultDataModels.get(position).getSECTOR_LOCATION());
                i.putExtra("description", searchResultDataModels.get(position).getFLAT_DESCRIPTION());

                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return searchResultDataModels.size();
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
            Area_location = itemView.findViewById(R.id.card_area_location);
            Area_location.setText(area_location);
        }

        public void setSector_location(String sector_location) {
            Sector_location = itemView.findViewById(R.id.card_sector_location);
            Sector_location.setText(sector_location);
        }

        public void setApartment_frontImage(String IMAGE_URL) {
            Apartment_frontImage = mView.findViewById(R.id.apartment_front_pic);
            Glide.with(context).load(IMAGE_URL).into(Apartment_frontImage);

        }

        public void setProperty_Type(String PROPERTY_TYPE) {
            Property_Type = mView.findViewById(R.id.property_type);
            Property_Type.setText(PROPERTY_TYPE);
        }

        public void setProperty_rent(String RENT_PER_MONT) {
            Property_rent = mView.findViewById(R.id.property_rent);
            Property_rent.setText(RENT_PER_MONT);
        }


        public void setNumberOfBeds(String BED_ROOMS) {
            NumberOfBeds = mView.findViewById(R.id.number_of_beds);
            NumberOfBeds.setText(BED_ROOMS);
        }

        public void setNumberOfKitchen(String KITCHEN) {
            NumberOfKitchen = mView.findViewById(R.id.number_of_kitchen);
            NumberOfKitchen.setText(KITCHEN);
        }

        public void setNumberOfBalcony(String BALCONY) {
            NumberOfBalcony = mView.findViewById(R.id.number_of_balcony);
            NumberOfBalcony.setText(BALCONY);
        }

        public void setNumberOfBathroom(String BATHROOMS) {
            NumberOfBathroom = mView.findViewById(R.id.number_of_bathrooms);
            NumberOfBathroom.setText(BATHROOMS);
        }
    }
}
