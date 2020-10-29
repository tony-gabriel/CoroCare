package com.example.coronahelpapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.coronahelpapp.Constructors.Question;

import java.util.ArrayList;
import java.util.List;

import static com.example.coronahelpapp.Constants.LocationContract.LocationTable;
import static com.example.coronahelpapp.Constants.TestContract.QuestionsTable;

public class TestDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Corocare.db";
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase db;

    public TestDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        this.db = db;

        final String SQL_CREATE_LOCATIONS_TABLE = "CREATE TABLE " +
                LocationTable.TABLE_NAME + " ( " +
                LocationTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                LocationTable.COLUMN_LATITUDE + " REAL, " +
                LocationTable.COLUMN_LONGITUDE + " REAL, " +
                LocationTable.COLUMN_MARKER_TITLE + " VARCHAR " + ")";

        db.execSQL(SQL_CREATE_LOCATIONS_TABLE);


        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuestionsTable.TABLE_NAME + " ( " +
                QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionsTable.COLUMN_ANSWER_NR_1 + " INTEGER, " +
                QuestionsTable.COLUMN_ANSWER_NR_2 + " INTEGER " + ")";

        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);

        FillQuestionsTable();

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + LocationTable.TABLE_NAME);
        onCreate(db);

    }

    private void FillQuestionsTable() {
        Question q1 = new Question("Are you having fever, tiredness, and dry cough?", "Yes", "No", 1, 2);
        addQuestion(q1);
        Question q2 = new Question("Are you having a runny nose, sore throat or diarrhea?", "Yes", "No", 1, 2);
        addQuestion(q2);
        Question q3 = new Question("Are you experiencing difficulty in breathing or shortness of breath?", "Yes", "No", 1, 2);
        addQuestion(q3);
        Question q4 = new Question("Can you hold your breath for 10seconds without coughing or feeling severe discomfort?", "Yes", "No", 1, 2);
        addQuestion(q4);
        Question q5 = new Question("Are you experiencing loss in your sense of smell and/or taste?", "Yes", "No", 1, 2);
        addQuestion(q5);
        Question q6 = new Question("Have you traveled to any of the countries or states within Nigeria, with high case index of COVID-19 recently?", "Yes", "No", 1, 2);
        addQuestion(q6);
        Question q7 = new Question("Have you been in contact with someone with respiratory illness in the past 14 days?", "Yes", "No", 1, 2);
        addQuestion(q7);
        Question q8 = new Question("Have you been in close contact with someone who has been confirmed as having COVID-19 within 14 days of your symptoms starting?", "Yes", "No", 1, 2);
        addQuestion(q8);
    }

    private void addQuestion(Question question) {

        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuestionsTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuestionsTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuestionsTable.COLUMN_ANSWER_NR_1, question.getAnswerNr1());
        cv.put(QuestionsTable.COLUMN_ANSWER_NR_2, question.getAnswerNr2());
        db.insert(QuestionsTable.TABLE_NAME, null, cv);
    }

    public List<Question> getAllQuestions() {

        List<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME, null);

        if (c.moveToFirst()) {

            do {

                Question question = new Question();
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setAnswerNr1(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR_1)));
                question.setAnswerNr2(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR_2)));
                questionList.add(question);

            } while (c.moveToNext());
        }

        c.close();
        return questionList;
    }

//    public boolean insertLocation(Double lat, Double lng, String marker) {
//
//        db = getWritableDatabase();
//        ContentValues cv = new ContentValues();
//
//        cv.put(LocationTable.COLUMN_LATITUDE, lat);
//        cv.put(LocationTable.COLUMN_LONGITUDE, lng);
//        cv.put(LocationTable.COLUMN_MARKER_TITLE, marker);
//
//       long result = db.insert(LocationTable.TABLE_NAME, null, cv);
//
//       if (result == -1) return false;
//       else return true;
//    }

//    public Cursor locationData() {
//
//        db = this.getWritableDatabase();
//
//        Cursor location = db.rawQuery("SELECT * FROM " + LocationTable.TABLE_NAME, null);
//        return location;
//    }

}
