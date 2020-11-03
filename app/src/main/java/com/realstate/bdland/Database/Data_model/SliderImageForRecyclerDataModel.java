package com.realstate.bdland.Database.Data_model;

import android.net.Uri;

public class SliderImageForRecyclerDataModel {

    private Uri Image;

    public SliderImageForRecyclerDataModel(Uri image) {
        Image = image;
    }

    public Uri getImage() {
        return Image;
    }

}
