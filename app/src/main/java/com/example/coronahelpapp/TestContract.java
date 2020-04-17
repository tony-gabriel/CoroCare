package com.example.coronahelpapp;

import android.provider.BaseColumns;

public final class TestContract {

    TestContract() {
    }

    public static class QuestionsTable implements BaseColumns {

        public static final String TABLE_NAME = "test_questions";
        public static final String COLUMN_QUESTION = "question";
        public static final String COLUMN_OPTION1 = "option1";
        public static final String COLUMN_OPTION2 = "option2";
        public static final String COLUMN_ANSWER_NR_1 = "answer_nr_1";
        public static final String COLUMN_ANSWER_NR_2 = "answer_nr_2";

    }
}
