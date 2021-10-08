package com.example.quizappforcourse.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.quizappforcourse.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class ChooseQuizID extends AppCompatActivity implements View.OnClickListener {

    private EditText edit_quizID;
    private Button button_attend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_quiz_id);

        edit_quizID = findViewById(R.id.edit_quizID);
        button_attend = findViewById(R.id.button_attend);

        button_attend.setOnClickListener(this);

    }

    private void verifyQuizID() {
        // Write a message to the database
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("QuizID");
        // Read from the database
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


//                if (snapshot.hasChild("-MkRKc0W5fLBS4klTmAQ")) {
//                    Toast.makeText(getApplicationContext(), "exists!", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                Toast.makeText(getApplicationContext(), "does not exist!", Toast.LENGTH_SHORT).show(
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (Objects.requireNonNull(dataSnapshot.getValue()).toString().equals(edit_quizID.getText().toString().trim())){
                        Toast.makeText(getApplicationContext(), "exists!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class).putExtra("quizID", edit_quizID.getText().toString().trim()));
                        finish();
                        return;
                    }
                }
                Toast.makeText(getApplicationContext(), "doesn't exist!", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        if (v == button_attend) {
            if(edit_quizID.getText().toString().trim().isEmpty()) {
                Snackbar.make(button_attend, "field cannot be empty!", Snackbar.LENGTH_SHORT).show();
                return;
            }
            verifyQuizID();
        }
    }
}