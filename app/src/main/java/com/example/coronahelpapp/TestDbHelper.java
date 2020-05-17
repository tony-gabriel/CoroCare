package com.example.coronahelpapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.coronahelpapp.Constants.DataContract.DataTable;

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


        final String SQL_CREATE_DATA_TABLE = "CREATE TABLE " +
                DataTable.TABLE_NAME + " ( " +
                DataTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DataTable.COLUMN_TOTAL_CASE + " INTEGER, " +
                DataTable.COLUMN_ACTIVE_CASE + " INTEGER, " +
                DataTable.COLUMN_TOTAL_RECOVERY + " INTEGER, " +
                DataTable.COLUMN_TOTAL_DEATH + " INTEGER " + ")";

        db.execSQL(SQL_CREATE_DATA_TABLE);


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
        db.execSQL("DROP TABLE IF EXISTS " + DataTable.TABLE_NAME);
        onCreate(db);

    }

    private void FillQuestionsTable() {
        Question q1 = new Question("Are you having fever, tiredness and dry cough?", "Yes", "No", 1, 2);
        addQuestion(q1);
        Question q2 = new Question("Are you having a runny nose, sore throat or diarrhea?", "Yes", "No", 1, 2);
        addQuestion(q2);
        Question q3 = new Question("Are you experiencing difficulty in breathing?", "Yes", "No", 1, 2);
        addQuestion(q3);
        Question q4 = new Question("Are you experiencing shortness of breath?", "Yes", "No", 1, 2);
        addQuestion(q4);
        Question q5 = new Question("Can you hold your breath for 10seconds without coughing or feeling severe discomfort?", "Yes", "No", 1, 2);
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

    public void insertLocation(Double lat, Double lng, String marker) {

        db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(LocationTable.COLUMN_LATITUDE, lat);
        cv.put(LocationTable.COLUMN_LONGITUDE, lng);
        cv.put(LocationTable.COLUMN_MARKER_TITLE, marker);

        db.insert(LocationTable.TABLE_NAME, null, cv);

    }


    //TODO: call this method to save values from Api.
    public void insertData(int total, int active, int recovery, int death) {

        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(DataTable.COLUMN_TOTAL_CASE, total);
        cv.put(DataTable.COLUMN_ACTIVE_CASE, active);
        cv.put(DataTable.COLUMN_TOTAL_RECOVERY, recovery);
        cv.put(DataTable.COLUMN_TOTAL_DEATH, death);

        db.insert(DataTable.TABLE_NAME, null, cv);
    }

    public Cursor locationData() {

        db = this.getWritableDatabase();

        Cursor location = db.rawQuery("SELECT * FROM " + LocationTable.TABLE_NAME, null);
        return location;
    }

    public ArrayList<String> dateData() {

        db = this.getWritableDatabase();
        ArrayList<String> dates = new ArrayList<>();

        Cursor c = db.rawQuery("SELECT " + LocationTable.COLUMN_MARKER_TITLE + " FROM " + LocationTable.TABLE_NAME, null);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            dates.add(c.getString(c.getColumnIndex(LocationTable.COLUMN_MARKER_TITLE)));
        }

        c.close();

        return dates;

    }


    public ArrayList<Integer> totalCaseData() {

        db = this.getWritableDatabase();
        ArrayList<Integer> totalCase = new ArrayList<>();

        Cursor c = db.rawQuery("SELECT " + DataTable.COLUMN_TOTAL_CASE + " FROM " + DataTable.TABLE_NAME, null);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            totalCase.add(c.getInt(c.getColumnIndex(DataTable.COLUMN_TOTAL_CASE)));
        }

        c.close();

        return totalCase;

    }

    public ArrayList<Integer> activeCaseData() {

        db = this.getWritableDatabase();
        ArrayList<Integer> activeCase = new ArrayList<>();

        Cursor c = db.rawQuery("SELECT " + DataTable.COLUMN_ACTIVE_CASE + " FROM " + DataTable.TABLE_NAME, null);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            activeCase.add(c.getInt(c.getColumnIndex(DataTable.COLUMN_ACTIVE_CASE)));
        }

        c.close();

        return activeCase;

    }

    public ArrayList<Integer> recoveryData() {

        db = this.getWritableDatabase();
        ArrayList<Integer> recovered = new ArrayList<>();

        Cursor c = db.rawQuery("SELECT " + DataTable.COLUMN_TOTAL_RECOVERY + " FROM " + DataTable.TABLE_NAME, null);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            recovered.add(c.getInt(c.getColumnIndex(DataTable.COLUMN_TOTAL_RECOVERY)));
        }

        c.close();

        return recovered;

    }

    public ArrayList<Integer> totalDeathData() {

        db = this.getWritableDatabase();
        ArrayList<Integer> deaths = new ArrayList<>();

        Cursor c = db.rawQuery("SELECT " + DataTable.COLUMN_TOTAL_DEATH + " FROM " + DataTable.TABLE_NAME, null);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            deaths.add(c.getInt(c.getColumnIndex(DataTable.COLUMN_TOTAL_DEATH)));
        }

        c.close();

        return deaths;

    }

    public ArrayList<Integer> queryXData() {

        db = this.getWritableDatabase();
        ArrayList<Integer> xData = new ArrayList<>();

        Cursor c = db.rawQuery("SELECT " + DataTable._ID + " FROM " + DataTable.TABLE_NAME, null);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            xData.add(c.getInt(c.getColumnIndex(DataTable._ID)));
        }

        c.close();

        return xData;

    }

}
