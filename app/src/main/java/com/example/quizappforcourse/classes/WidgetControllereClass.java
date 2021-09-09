package com.example.quizappforcourse.classes;

import android.os.Handler;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;

import java.text.MessageFormat;

public class WidgetControllereClass {
    private View view_progress;
    private TextView text_question_count;
    private double scale = 0.04;
    private int count = 1;

    public WidgetControllereClass(View view_progress, TextView text_question_count) {
        this.view_progress = view_progress;
        this.text_question_count = text_question_count;
    }

    public void updateProgress() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                text_question_count.setText(MessageFormat.format("{0}/25", count));
                count++;
                view_progress.animate().scaleX((float) scale).setDuration(700).setInterpolator(new OvershootInterpolator());
//                view_progress.setScaleX((float) scale);
                if (scale > 1) {
                    return;
                }
                scale += 0.04;
                updateProgress();
            }
        }, 1000);
    }

//    public void updateProgress() {
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                text_question_count.setText(MessageFormat.format("{0}/25", count));
//                count++;
//                view_progress.animate().scaleX((float) scale).setDuration(700).setInterpolator(new OvershootInterpolator());
////                view_progress.setScaleX((float) scale);
//
//                if (scale > 1) {
//                    return;
//                }
//                scale += 0.025;
//                updateProgress();
//            }
//        }, 1000);
//    }
}
