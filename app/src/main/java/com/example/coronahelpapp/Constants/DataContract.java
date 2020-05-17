package com.example.coronahelpapp.Constants;

import android.provider.BaseColumns;

public final class DataContract {


    DataContract() {
    }

    public static class DataTable implements BaseColumns {

        public static final String TABLE_NAME = "coronaData";
        public static final String COLUMN_TOTAL_CASE = "total_cases";
        public static final String COLUMN_ACTIVE_CASE = "active_cases";
        public static final String COLUMN_TOTAL_RECOVERY = "total_recovery";
        public static final String COLUMN_TOTAL_DEATH = "total_death";

    }

}
