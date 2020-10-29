package com.example.coronahelpapp;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coronahelpapp.Constructors.Question;
import com.example.coronahelpapp.Database.TestDbHelper;

import java.util.List;

import static com.example.coronahelpapp.R.color.colorAccent;
import static com.example.coronahelpapp.R.color.viewColor;

public class SelfTest extends AppCompatActivity {


    LinearLayout starter_layout;
    TextView test_main, test_counter, textDescription, Instruction, textTitle;
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

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.viewColor));


        starter_layout = findViewById(R.id.linear_starter);

        test_main = findViewById(R.id.text_check);
        test_counter = findViewById(R.id.test_count);
        textDescription = findViewById(R.id.textDescription);
        textTitle = findViewById(R.id.textViewSF);

        radioGroup = findViewById(R.id.radio_group);
        rbYes = findViewById(R.id.radioButtonYes);
        rbNo = findViewById(R.id.radioButtonNo);

        startCheck = findViewById(R.id.start_check);
        btnNextFinish = findViewById(R.id.btn_NextFinish);

        TestDbHelper dbHelper = new TestDbHelper(this);
        questionList = dbHelper.getAllQuestions();
        questionCountTotal = questionList.size();

        ShowNextQuestion();

        ColorStateList colorStateList = new ColorStateList(
                new int[][]{
                        new int[]{-android.R.attr.state_checked}, //disabled
                        new int[]{android.R.attr.state_checked} //enabled
                },
                new int[]{
                        Color.DKGRAY, //disabled
                        viewColor //enabled
                }
        );

        rbNo.setButtonTintList(colorStateList);
        rbYes.setButtonTintList(colorStateList);


        startCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                starter_layout.setVisibility(View.GONE);
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

    private void CheckAnswer() {
        answered = true;

        RadioButton rbSelected = findViewById(radioGroup.getCheckedRadioButtonId());
        int AnswerNr = radioGroup.indexOfChild(rbSelected) + 1;

        String dbQuestion = currentQuestion.getQuestion();
        String AnswerNrText;
        String QandA;
        StringBuilder testSummary = new StringBuilder();
        StringBuilder testSummary1 = new StringBuilder();
        StringBuilder testSummary2 = new StringBuilder();
        StringBuilder testSummary3 = new StringBuilder();
        StringBuilder testSummary4 = new StringBuilder();
        StringBuilder testSummary5 = new StringBuilder();
        StringBuilder testSummary6 = new StringBuilder();
        StringBuilder testSummary7 = new StringBuilder();

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
                testSummary1.append("(1) ").append(QandA);
                break;

            case 2:
                if (AnswerNr == currentQuestion.getAnswerNr1()) {
                    q2 = true;
                } else {
                    q2 = false;
                }

                QandA = dbQuestion + "  Answer: " + AnswerNrText;
                testSummary2.append(testSummary1).append("\n").append("(2) ").append(QandA);
                break;

            case 3:
                if (AnswerNr == currentQuestion.getAnswerNr1()) {
                    q3 = true;
                } else {
                    q3 = false;
                }

                QandA = dbQuestion + "  Answer: " + AnswerNrText;
                testSummary3.append(testSummary2).append("\n").append("(3) ").append(QandA);
                break;

            case 4:
                if (AnswerNr == currentQuestion.getAnswerNr1()) {
                    q4 = true;
                } else {
                    q4 = false;
                }

                QandA = dbQuestion + "  Answer: " + AnswerNrText;
                testSummary4.append(testSummary3).append("\n").append("(4) ").append(QandA);
                break;

            case 5:
                if (AnswerNr == currentQuestion.getAnswerNr1()) {
                    q5 = true;
                } else {
                    q5 = false;
                }

                QandA = dbQuestion + "  Answer: " + AnswerNrText;
                testSummary5.append(testSummary4).append("\n").append("(5) ").append(QandA);
                break;

            case 6:
                if (AnswerNr == currentQuestion.getAnswerNr1()) {
                    q6 = true;
                } else {
                    q6 = false;
                }

                QandA = dbQuestion + "  Answer: " + AnswerNrText;
                testSummary6.append(testSummary5).append("\n").append("(6) ").append(QandA);
                break;

            case 7:
                if (AnswerNr == currentQuestion.getAnswerNr1()) {
                    q7 = true;
                } else {
                    q7 = false;
                }

                QandA = dbQuestion + "  Answer: " + AnswerNrText;
                testSummary7.append(testSummary6).append("\n").append("(7) " + QandA);
                break;

            case 8:
                if (AnswerNr == currentQuestion.getAnswerNr1()) {
                    q8 = true;
                } else {
                    q8 = false;
                }

                QandA = dbQuestion + "  Answer: " + AnswerNrText;
                testSummary7.append(testSummary6).append("\n").append("(7) " + QandA);
                break;
        }
        testSummary.append("Test Summary").append("\n").append(testSummary7);
        Log.i("Summary :", String.valueOf(testSummary));
    }

    private void FinishTest() {
        test_main.setVisibility(View.INVISIBLE);
        radioGroup.setVisibility(View.INVISIBLE);
        btnNextFinish.setVisibility(View.INVISIBLE);
        test_counter.setVisibility(View.INVISIBLE);
        starter_layout.setVisibility(View.VISIBLE);


        if (q1 && q2 && q3 && !q4 && q5) {

            Positive();
        } else if (q1 && q2 && q3 && q5) {

            Positive();
        } else if (q1 && q2 && q5) {

            Positive();
        } else if (q1 && q5) {

            Positive();
        } else if (q2 && q3 && q5) {

            Positive();
        } else if (q1 && q2 && q3) {

            Positive();
        } else if (q1 && q2 && q3 && q5 && q6) {

            Positive();
        } else if (q1 && q3 && q6) {

            Positive();
        } else if (q1 && q2 && !q4) {

            Positive();
        } else if (q1 && q3) {

            Positive();
        } else if (q2 && q3 && q6) {

            Positive();
        } else if (q8) {

            Positive();
        } else {

            Negative();
        }

    }

    private void Positive() {

        // Instruction.setVisibility(View.GONE);
        textTitle.setText(getString(R.string.text_result));
        textDescription.setText(getString(R.string.text_result_positive));

        startCheck.setText(getString(R.string.btn_text_report));
        startCheck.setBackgroundColor(getColor(colorAccent));
        startCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelfTest.this, Report.class));
                finish();
            }
        });

    }

    private void Negative() {

        //  Instruction.setVisibility(View.GONE);
        textTitle.setText(getString(R.string.text_result));
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
        finish();
    }
}
