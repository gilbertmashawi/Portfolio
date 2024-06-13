package com.example.whatsapclone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class feedback extends AppCompatActivity {
    DatabaseReference database;
    RatingBar rb;
    Button btn;
    TextView tView;
    EditText feedbackwrite;
    int randomnumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        rb=findViewById(R.id.ratingBar1);
        btn=findViewById(R.id.feebacksend);
        feedbackwrite=findViewById(R.id.feedbackWrite);
        tView=findViewById(R.id.tView);
        randomnumber=new Random().nextInt(100);


        database=FirebaseDatabase.getInstance().getReferenceFromUrl("https://whatsap-clone-1be7a-default-rtdb.firebaseio.com/");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int noofstars = rb.getNumStars();
                float getrating = rb.getRating();
                String stringfeedbackwrite=feedbackwrite.getText().toString();

                if(!String.valueOf(noofstars).isEmpty() && !stringfeedbackwrite.isEmpty()){
                    database.child("feedback").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            database.child("feedback").child("comments "+String.valueOf(randomnumber)).child("rating").setValue(String.valueOf(getrating));
                            database.child("feedback").child("comments "+String.valueOf(randomnumber)).child("message").setValue(stringfeedbackwrite);
                            Toast.makeText(feedback.this, "feed back send successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(feedback.this,SettingsActivity.class));
                            finish();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
                else {
                    Toast.makeText(feedback.this,"let us know something",Toast.LENGTH_SHORT).show();
                }





            }
        });

    }
}