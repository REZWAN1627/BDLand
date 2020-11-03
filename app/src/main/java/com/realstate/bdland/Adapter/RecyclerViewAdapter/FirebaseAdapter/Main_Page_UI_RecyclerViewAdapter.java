package com.realstate.bdland.Adapter.RecyclerViewAdapter.FirebaseAdapter;

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
import com.realstate.bdland.Database.Data_model.DataHouseDetails;
import com.realstate.bdland.Main_Data_UI.Room_Details_Ui;
import com.realstate.bdland.R;

import java.util.ArrayList;

public class Main_Page_UI_RecyclerViewAdapter extends RecyclerView.Adapter<Main_Page_UI_RecyclerViewAdapter.ViewHolder> {

    public static final String TAG = "TAG";
    private ArrayList<DataHouseDetails> dataHouseDetails = new ArrayList<>();
    private Context context;

    public Main_Page_UI_RecyclerViewAdapter(ArrayList<DataHouseDetails> dataHouseDetails, Context context) {
        this.dataHouseDetails = dataHouseDetails;
        this.context = context;
    }

    @NonNull
    @Override
    public Main_Page_UI_RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.carddesign_apartment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Main_Page_UI_RecyclerViewAdapter.ViewHolder holder, final int position) {

        String apartType = dataHouseDetails.get(position).getPROPERTY_TYPE();
        Log.d(TAG, "onBindViewHolder: +" + apartType + " position " + position);
        holder.setProperty_Type(apartType);

        holder.setApartment_frontImage(dataHouseDetails.get(position).getIMAGE_URL());
        holder.setProperty_rent(dataHouseDetails.get(position).getRENT_PER_MONTH());
        holder.setNumberOfBalcony(dataHouseDetails.get(position).getBALCONY());

        holder.setNumberOfKitchen(dataHouseDetails.get(position).getKITCHEN());
        holder.setNumberOfBeds(dataHouseDetails.get(position).getBED_ROOMS());
        holder.setNumberOfBathroom(dataHouseDetails.get(position).getBATHROOMS());
        holder.setArea_location(dataHouseDetails.get(position).getLOCATION());
        holder.setSector_location(dataHouseDetails.get(position).getSECTOR_LOCATION());

        Log.d(TAG, "onBindViewHolder: " + dataHouseDetails.get(position).getBED_ROOMS());

        holder.Apartment_frontImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, Room_Details_Ui.class);

                i.putExtra("Location", dataHouseDetails.get(position).getLOCATION());
                i.putExtra("HoldingNumber", dataHouseDetails.get(position).getHOUSE_NUMBER());
                i.putExtra("sectorLocation", dataHouseDetails.get(position).getSECTOR_LOCATION());
                i.putExtra("property_type", dataHouseDetails.get(position).getPROPERTY_TYPE());
                i.putExtra("Rent", dataHouseDetails.get(position).getRENT_PER_MONTH());
                i.putExtra("balcony", dataHouseDetails.get(position).getBALCONY());
                i.putExtra("kitchen", dataHouseDetails.get(position).getKITCHEN());
                i.putExtra("Bedroom", dataHouseDetails.get(position).getBED_ROOMS());
                i.putExtra("bathroom", dataHouseDetails.get(position).getBATHROOMS());
                i.putExtra("Sector location", dataHouseDetails.get(position).getSECTOR_LOCATION());
                i.putExtra("description", dataHouseDetails.get(position).getFLAT_DESCRIPTION());

                context.startActivity(i);
            }
        });


    }

    public void Reset() {
        dataHouseDetails.clear();

    }

    @Override
    public int getItemCount() {
        return dataHouseDetails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView Apartment_frontImage;
        private TextView Property_Type;
        private TextView Property_rent;
        private TextView Location;
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
