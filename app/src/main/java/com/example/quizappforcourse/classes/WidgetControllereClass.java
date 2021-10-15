package com.example.quizappforcourse.classes;

import android.os.Handler;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;

import java.text.MessageFormat;

public class WidgetControllereClass {
    private View view_progress;
    private TextView text_question_count;
    private double scale = 0;
    private int count = 1;
    private long total_questions;

    public WidgetControllereClass(View view_progress, TextView text_question_count, long total_questions) {
        this.view_progress = view_progress;
        this.text_question_count = text_question_count;
        this.total_questions = total_questions;

    }

    public void updateProgress() {

        scale += 1 / (float)total_questions;
        text_question_count.setText(MessageFormat.format("{0}/{1}", count, total_questions));
        count++;
        view_progress.animate().scaleX((float) scale).setDuration(700).setInterpolator(new OvershootInterpolator());

    }

}
