package com.example.coronahelpapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class SelfTest extends AppCompatActivity {


    LinearLayout starter_layout;
    TextView test_main, test_counter, textDescription, Instruction;
    RadioGroup radioGroup;
    RadioButton rbYes, rbNo;
    Button startCheck, btnNextFinish;

    private List<Question> questionList;

    private int questionCounter;
    private int questionCountTotal;
    private Question currentQuestion;
    private boolean answered, q1, q2, q3, q4, q5, q6, q7, q8;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_test);


        starter_layout = findViewById(R.id.linear_starter);

        test_main = findViewById(R.id.text_check);
        test_counter = findViewById(R.id.test_count);
        Instruction = findViewById(R.id.textView1);
        textDescription = findViewById(R.id.textDescription);

        radioGroup = findViewById(R.id.radio_group);
        rbYes = findViewById(R.id.radioButtonYes);
        rbNo = findViewById(R.id.radioButtonNo);

        startCheck = findViewById(R.id.start_check);
        btnNextFinish = findViewById(R.id.btn_NextFinish);

        TestDbHelper dbHelper = new TestDbHelper(this);
        questionList = dbHelper.getAllQuestions();
        questionCountTotal = questionList.size();

        ShowNextQuestion();

        startCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                starter_layout.setVisibility(View.INVISIBLE);
                test_main.setVisibility(View.VISIBLE);
                radioGroup.setVisibility(View.VISIBLE);
                btnNextFinish.setVisibility(View.VISIBLE);
                test_counter.setVisibility(View.VISIBLE);

            }
        });

        btnNextFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!answered) {
                    if (rbYes.isChecked() || rbNo.isChecked()) {
                        CheckAnswer();
                        ShowNextQuestion();
                    } else {
                        Toast.makeText(SelfTest.this, "Please Select an answer", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }


    private void ShowNextQuestion() {
        radioGroup.clearCheck();

        if (questionCounter < questionCountTotal) {
            currentQuestion = questionList.get(questionCounter);

            test_main.setText(currentQuestion.getQuestion());
            rbYes.setText(currentQuestion.getOption1());
            rbNo.setText(currentQuestion.getOption2());

            questionCounter++;

            test_counter.setText("Check: " + questionCounter + "/" + questionCountTotal);
            answered = false;

            if (questionCounter == questionCountTotal) {

                btnNextFinish.setText(getString(R.string.btn_text_finish));
            }

        } else {
            FinishTest();
        }
    }

    private void FinishTest() {
        test_main.setVisibility(View.INVISIBLE);
        radioGroup.setVisibility(View.INVISIBLE);
        btnNextFinish.setVisibility(View.INVISIBLE);
        test_counter.setVisibility(View.INVISIBLE);
        starter_layout.setVisibility(View.VISIBLE);


        if (q1 && q2 && q3 && q4 && !q5) {

            Positive();
        } else if (q1 && q6) {

            Positive();
        } else if (q1 == true && q3 == true && q6 == true) {

            Positive();
        } else if (q1 && q2 && !q5) {

            Positive();
        } else if (q8) {

            Positive();
        } else {

            Negative();
        }

    }

    private void CheckAnswer() {
        answered = true;

        RadioButton rbSelected = findViewById(radioGroup.getCheckedRadioButtonId());
        int AnswerNr = radioGroup.indexOfChild(rbSelected) + 1;

        String dbQuestion = currentQuestion.getQuestion();
        String AnswerNrText;
        String QandA;

        if (AnswerNr == 1) {

            AnswerNrText = "YES";
        } else {

            AnswerNrText = "NO";
        }

        switch (questionCounter) {

            // TODO: Take all QandA and save on firebase database

            case 1:
                if (AnswerNr == currentQuestion.getAnswerNr1()) {
                    q1 = true;
                } else {
                    q1 = false;
                }

                QandA = dbQuestion + "  Answer: " + AnswerNrText;

                Log.i("Result", QandA);
                break;

            case 2:
                if (AnswerNr == currentQuestion.getAnswerNr1()) {
                    q2 = true;
                } else {
                    q2 = false;
                }

                QandA = dbQuestion + "  Answer: " + AnswerNrText;
                Log.i("Result", QandA);
                break;

            case 3:
                if (AnswerNr == currentQuestion.getAnswerNr1()) {
                    q3 = true;
                } else {
                    q3 = false;
                }

                QandA = dbQuestion + "  Answer: " + AnswerNrText;
                Log.i("Result", QandA);
                break;

            case 4:
                if (AnswerNr == currentQuestion.getAnswerNr1()) {
                    q4 = true;
                } else {
                    q4 = false;
                }

                QandA = dbQuestion + "  Answer: " + AnswerNrText;
                Log.i("Result", QandA);
                break;

            case 5:
                if (AnswerNr == currentQuestion.getAnswerNr1()) {
                    q5 = true;
                } else {
                    q5 = false;
                }

                QandA = dbQuestion + "  Answer: " + AnswerNrText;
                Log.i("Result", QandA);
                break;

            case 6:
                if (AnswerNr == currentQuestion.getAnswerNr1()) {
                    q6 = true;
                } else {
                    q6 = false;
                }

                QandA = dbQuestion + "  Answer: " + AnswerNrText;
                Log.i("Result", QandA);
                break;

            case 7:
                if (AnswerNr == currentQuestion.getAnswerNr1()) {
                    q7 = true;
                } else {
                    q7 = false;
                }

                QandA = dbQuestion + "  Answer: " + AnswerNrText;
                Log.i("Result", QandA);
                break;

            case 8:
                if (AnswerNr == currentQuestion.getAnswerNr1()) {
                    q8 = true;
                } else {
                    q8 = false;
                }

                QandA = dbQuestion + "  Answer: " + AnswerNrText;
                Log.i("Result", QandA);
                break;
        }


    }

    private void Positive() {

        Instruction.setText(getString(R.string.text_result));
        textDescription.setTextColor(getColor(R.color.resultTextColor));
        textDescription.setText(getString(R.string.text_result_positive));

        startCheck.setText(getString(R.string.btn_text_report));
        startCheck.setBackgroundColor(getColor(R.color.colorAccent));
        startCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelfTest.this, Report.class));
                finish();
            }
        });

    }

    private void Negative() {

        Instruction.setText(getString(R.string.text_result));
        textDescription.setTextColor(getColor(R.color.resultTextColor));
        textDescription.setText(getString(R.string.text_result_negative));

        startCheck.setText(getString(R.string.btn_text_finish));
        startCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    public void back(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
