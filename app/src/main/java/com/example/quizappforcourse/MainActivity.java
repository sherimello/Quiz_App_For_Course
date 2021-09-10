package com.example.quizappforcourse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.animation.Animator;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.quizappforcourse.classes.WidgetControllereClass;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private View view_progress;
    private TextView text_question_count, text_option1, text_option2, text_option3, text_option4, text_temp;
    private CardView card_answer, card_questions;
    private ScrollView scroll_contents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view_progress = findViewById(R.id.view_progress);
        text_question_count = findViewById(R.id.text_question_count);
        text_option1 = findViewById(R.id.text_option1);
        text_option2 = findViewById(R.id.text_option2);
        text_option3 = findViewById(R.id.text_option3);
        text_option4 = findViewById(R.id.text_option4);
        card_answer = findViewById(R.id.card_answer);
        card_questions = findViewById(R.id.card_questions);
        scroll_contents = findViewById(R.id.scroll_contents);

        card_answer.setOnClickListener(this);
        text_option1.setOnClickListener(this);
        text_option2.setOnClickListener(this);
        text_option3.setOnClickListener(this);
        text_option4.setOnClickListener(this);

        text_temp = text_option1;

        new WidgetControllereClass(view_progress, text_question_count).updateProgress();

    }

    @Override
    public void onClick(View v) {

        if (v == text_option1) {
            text_temp.setBackgroundTintList(null);
            text_temp = text_option1;
            text_option1.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.BG));
        }
        if (v == text_option2) {
            text_temp.setBackgroundTintList(null);
            text_temp = text_option2;
            text_option2.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.BG));
        }
        if (v == text_option3) {
            text_temp.setBackgroundTintList(null);
            text_temp = text_option3;
            text_option3.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.BG));
        }
        if (v == text_option4) {
            text_temp.setBackgroundTintList(null);
            text_temp = text_option4;
            text_option4.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.BG));
        }

        if (v == card_answer) {

            card_questions.setLayerType(View.LAYER_TYPE_HARDWARE, null);

            if (card_questions.getScaleX() == 1) {
                card_questions.animate().scaleX(.3f).scaleY(.2f).setDuration(500).setInterpolator(new OvershootInterpolator());
                scroll_contents.animate().alpha(0).setDuration(500);
            }

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    card_questions.animate().scaleX(1).scaleY(1).setDuration(500).setInterpolator(new OvershootInterpolator());
                    scroll_contents.animate().alpha(1).setDuration(500);
                }
            }, 500);


        }

    }
}