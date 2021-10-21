package com.example.quizappforcourse.activity;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.example.quizappforcourse.R;
import com.example.quizappforcourse.classes.QuizDataStructure;
import com.example.quizappforcourse.classes.WidgetControllereClass;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private View view_progress;
    private TextView text_message, text_counter, text_score_percentage, text_question, text_question_count, text_option1, text_option2, text_option3, text_option4, text_temp;
    private CardView card_answer, card_questions, card_score;
    private ScrollView scroll_contents;
    private ProgressBar progress_score;
    private Button button_play_again, button_quit;
    private String quizID, selected_answer = "";
    private int index = 0, score_progress = 0;
    private long total_questions = 0;
    private ArrayList<QuizDataStructure> arrayList;
    private ArrayList<String> user_answers, correct_answers;
    private WidgetControllereClass widgetControllereClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        quizID = getIntent().getStringExtra("quizID");

        view_progress = findViewById(R.id.view_progress);
        text_question_count = findViewById(R.id.text_question_count);
        text_question = findViewById(R.id.text_question);
        text_option1 = findViewById(R.id.text_option1);
        text_option2 = findViewById(R.id.text_option2);
        text_option3 = findViewById(R.id.text_option3);
        text_option4 = findViewById(R.id.text_option4);
        text_message = findViewById(R.id.text_message);
        text_score_percentage = findViewById(R.id.text_score_percentage);
        text_counter = findViewById(R.id.text_counter);
        card_answer = findViewById(R.id.card_answer);
        card_score = findViewById(R.id.card_score);
        progress_score = findViewById(R.id.progress_score);
        card_questions = findViewById(R.id.card_questions);
        scroll_contents = findViewById(R.id.scroll_contents);
        button_play_again = findViewById(R.id.button_play_again);
        button_quit = findViewById(R.id.button_quit);

        card_answer.setOnClickListener(this);
        text_option1.setOnClickListener(this);
        text_option2.setOnClickListener(this);
        text_option3.setOnClickListener(this);
        text_option4.setOnClickListener(this);
        button_play_again.setOnClickListener(this);
        button_quit.setOnClickListener(this);

        text_temp = text_option1;

        arrayList = new ArrayList<>();
        user_answers = new ArrayList<>();
        correct_answers = new ArrayList<>();

        getQuizData();

    }

    private void getQuizData() {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Questions");
        databaseReference.child(quizID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                total_questions = snapshot.getChildrenCount();

                text_question_count.setText(MessageFormat.format("0/{0}", total_questions));

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    arrayList.add(new QuizDataStructure(Objects.requireNonNull(dataSnapshot.child("que").getValue()).toString(),
                            Objects.requireNonNull(dataSnapshot.child("_1").getValue()).toString(),
                            Objects.requireNonNull(dataSnapshot.child("_2").getValue()).toString(),
                            Objects.requireNonNull(dataSnapshot.child("_3").getValue()).toString(),
                            Objects.requireNonNull(dataSnapshot.child("_4").getValue()).toString()
                    ));

                }

                widgetControllereClass = new WidgetControllereClass(view_progress, text_question_count, total_questions);
                assignDataToUI();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void assignDataToUI() {
        text_question.setText(arrayList.get(index).getQue());
        text_option1.setText(arrayList.get(index).get_1());
        text_option2.setText(arrayList.get(index).get_2());
        text_option3.setText(arrayList.get(index).get_3());
        text_option4.setText(arrayList.get(index).get_4());
    }

    @Override
    public void onClick(View v) {

        if (v == button_play_again) {
            resetEverything();
        }

        if (v == button_quit) {
            exitApp();
        }

        if (v == text_option1) {
            handleClicks(text_option1);
        }
        if (v == text_option2) {
            handleClicks(text_option2);
        }
        if (v == text_option3) {
            handleClicks(text_option3);
        }
        if (v == text_option4) {
            handleClicks(text_option4);
        }

        if (v == card_answer) {

            if (selected_answer.isEmpty()) {
                Toast.makeText(getApplicationContext(), "please select an answer first...", Toast.LENGTH_SHORT).show();
                return;
            }

            card_questions.setLayerType(View.LAYER_TYPE_HARDWARE, null);

            widgetControllereClass.updateProgress();

            if (card_questions.getScaleX() == 1) {
                card_questions.animate().scaleX(.3f).scaleY(.2f).setDuration(500).setInterpolator(new OvershootInterpolator());
                scroll_contents.animate().alpha(0).setDuration(500);
            }

            new Handler().postDelayed(() -> {
                card_questions.animate().scaleX(1).scaleY(1).setDuration(500).setInterpolator(new OvershootInterpolator());
                scroll_contents.animate().alpha(1).setDuration(500);

                user_answers.add(selected_answer);


                //gets called when last index is reached...
                if (index + 1 == total_questions) {

                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Answers");
                    databaseReference.child(quizID).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                correct_answers.add(Objects.requireNonNull(dataSnapshot.getValue()).toString());
                            }
                            makeScoreUIVisible();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    return;
                }

                index++;
                text_counter.setText(String.valueOf(index + 1));
                assignDataToUI();
            }, 500);
        }
    }

    private void exitApp() {
        finishAffinity();
    }

    private void resetEverything() {
        text_question_count.setText(MessageFormat.format("0/{0}", total_questions));
        index = 0;
        score_progress = 0;
        total_questions = 0;
        user_answers.clear();
        correct_answers.clear();
        arrayList.clear();
        getQuizData();
//        assignDataToUI();
        view_progress.setScaleX(0);
        text_counter.setText(String.valueOf(index + 1));
        card_score.setVisibility(View.GONE);
    }

    private void makeScoreUIVisible() {

        for (int i = 0; i < correct_answers.size(); i++) {

            if (user_answers.get(i).equals(correct_answers.get(i))) {

                score_progress += (100 / correct_answers.size());

            }
        }

        text_score_percentage.setText(MessageFormat.format("{0}%", score_progress));
        card_score.setVisibility(View.VISIBLE);

        Toast.makeText(getApplicationContext(), String.valueOf(score_progress), Toast.LENGTH_SHORT).show();

        ObjectAnimator progressAnimator;
        progressAnimator = ObjectAnimator.ofInt(progress_score, "progress", 0, score_progress);
        progressAnimator.setDuration(1000);
        progressAnimator.setInterpolator(new OvershootInterpolator());
        progressAnimator.start();

        displayMessage();

    }

    private void displayMessage() {

        if (score_progress < 65) {
            text_message.setText(R.string.good);
            return;
        }
        if (score_progress < 80) {
            text_message.setText(R.string.awesome);
            return;
        }
        text_message.setText(R.string.best);
    }

    private void handleClicks(TextView tv) {
        selected_answer = tv.getText().toString().trim();
        Toast.makeText(getApplicationContext(), selected_answer, Toast.LENGTH_SHORT).show();
        text_temp.setBackgroundTintList(null);
        text_temp = tv;
        tv.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.BG));
    }
}