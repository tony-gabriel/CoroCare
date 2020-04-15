package com.example.coronahelpapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static android.graphics.Color.RED;

public class SelfTest extends AppCompatActivity {

    LinearLayout starter_layout, result_layout;
    TextView test_main, test_counter, resultText;
    RadioGroup radioGroup;
    RadioButton rbYes, rbNo;
    Button startCheck, btnNextFinish, btnFinish;

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
        result_layout.findViewById(R.id.result_layout);

        test_main = findViewById(R.id.text_check);
        test_counter = findViewById(R.id.test_count);
        resultText = findViewById(R.id.result_text);

        radioGroup = findViewById(R.id.radio_group);
        rbYes = findViewById(R.id.radioButtonYes);
        rbNo = findViewById(R.id.radioButtonNo);

        startCheck = findViewById(R.id.start_check);
        btnNextFinish = findViewById(R.id.btn_NextFinish);
        btnFinish = findViewById(R.id.btn_finish_test);

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
                    } else {
                        Toast.makeText(SelfTest.this, "Please Select an answer", Toast.LENGTH_SHORT).show();
                    }
                } else {

                    ShowNextQuestion();
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


        } else {
            btnNextFinish.setText("Finish");
            FinishTest();
        }
    }

    private void FinishTest() {
        test_main.setVisibility(View.INVISIBLE);
        radioGroup.setVisibility(View.INVISIBLE);
        btnNextFinish.setVisibility(View.INVISIBLE);
        test_counter.setVisibility(View.INVISIBLE);
        result_layout.setVisibility(View.VISIBLE);


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

        // TODO: Check if AnswerNr is yes or no then log to firebase database
        String dbQuestion = currentQuestion.getQuestion();
        String AnswerNrText;
        String QandA;

        if (AnswerNr == 1) {

            AnswerNrText = "YES";
        } else {

            AnswerNrText = "NO";
        }

        switch (questionCounter) {

            case 1:
                if (AnswerNr == currentQuestion.getAnswerNr1()) {
                    q1 = true;
                } else {
                    q1 = false;
                }

                QandA = dbQuestion + "  Answer: " + AnswerNrText;
                break;

            case 2:
                if (AnswerNr == currentQuestion.getAnswerNr1()) {
                    q2 = true;
                } else {
                    q2 = false;
                }

                QandA = dbQuestion + "  Answer: " + AnswerNrText;
                break;

            case 3:
                if (AnswerNr == currentQuestion.getAnswerNr1()) {
                    q3 = true;
                } else {
                    q3 = false;
                }

                QandA = dbQuestion + "  Answer: " + AnswerNrText;
                break;

            case 4:
                if (AnswerNr == currentQuestion.getAnswerNr1()) {
                    q4 = true;
                } else {
                    q4 = false;
                }

                QandA = dbQuestion + "  Answer: " + AnswerNrText;
                break;

            case 5:
                if (AnswerNr == currentQuestion.getAnswerNr1()) {
                    q5 = true;
                } else {
                    q5 = false;
                }

                QandA = dbQuestion + "  Answer: " + AnswerNrText;
                break;

            case 6:
                if (AnswerNr == currentQuestion.getAnswerNr1()) {
                    q6 = true;
                } else {
                    q6 = false;
                }

                QandA = dbQuestion + "  Answer: " + AnswerNrText;
                break;

            case 7:
                if (AnswerNr == currentQuestion.getAnswerNr1()) {
                    q7 = true;
                } else {
                    q7 = false;
                }

                QandA = dbQuestion + "  Answer: " + AnswerNrText;
                break;

            case 8:
                if (AnswerNr == currentQuestion.getAnswerNr1()) {
                    q8 = true;
                } else {
                    q8 = false;
                }

                QandA = dbQuestion + "  Answer: " + AnswerNrText;
                break;
        }


    }

    private void Positive() {

        resultText.setText("You have possible symptoms of COVID-19. " +
                "Please press the button below to contact the National Center for Disease Control (NCDC) nearest to you, " +
                "while observing the standard guideline for self isolation for the safety of people around you.");

        btnFinish.setText("REPORT");
        btnFinish.setBackgroundColor(Color.RED);
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelfTest.this, Report.class));
            }
        });

    }

    private void Negative() {

        resultText.setText("You do not need to take a COVID-19 test. " +
                "Please contact your doctor for medical attention if you are experiencing some of symptoms. " +
                "Remember to wash your hands and maintain social distancing");

        btnFinish.setOnClickListener(new View.OnClickListener() {
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
