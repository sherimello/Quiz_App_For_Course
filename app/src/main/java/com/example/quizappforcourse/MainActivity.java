package com.example.quizappforcourse;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.OvershootInterpolator;

public class MainActivity extends AppCompatActivity {

    private View view_progress;
    private double scale = 0.025;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view_progress = findViewById(R.id.view_progress);

        updateProgress();

    }

    private void updateProgress() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                view_progress.animate().scaleX((float) scale).setDuration(700).setInterpolator(new OvershootInterpolator());
//                view_progress.setScaleX((float) scale);
                scale += 0.025;
                if (scale > 1) {
                    return;
                }
                updateProgress();
            }
        }, 1000);
    }
}