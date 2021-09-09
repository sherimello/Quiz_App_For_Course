package com.example.quizappforcourse;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;

import com.example.quizappforcourse.classes.WidgetControllereClass;

public class MainActivity extends AppCompatActivity {

    private View view_progress;
    private TextView text_question_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view_progress = findViewById(R.id.view_progress);
        text_question_count = findViewById(R.id.text_question_count);

        new WidgetControllereClass(view_progress, text_question_count).updateProgress();

    }

}