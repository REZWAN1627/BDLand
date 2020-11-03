package com.realstate.bdland.Database.Data_model;

import android.net.Uri;

public class SliderItemDataModel {

    private Uri Image;

    public Uri getImage() {
        return Image;
    }

    public SliderItemDataModel(Uri image) {
        Image = image;
    }
}
