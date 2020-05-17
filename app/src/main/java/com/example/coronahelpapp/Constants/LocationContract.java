package com.example.coronahelpapp.Constants;

import android.provider.BaseColumns;

public final class LocationContract {

    LocationContract() {
    }

    public static class LocationTable implements BaseColumns {

        public static final String TABLE_NAME = "locations";
        public static final String COLUMN_LATITUDE = "latitude";
        public static final String COLUMN_LONGITUDE = "longitude";
        public static final String COLUMN_MARKER_TITLE = "marker_title";
    }
}
