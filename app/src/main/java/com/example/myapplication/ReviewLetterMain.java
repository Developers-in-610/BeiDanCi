package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class ReviewLetterMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_letter_main);

        ImageButton button=(ImageButton)findViewById(R.id.fullSpell);
        ImageButton button1=(ImageButton)findViewById(R.id.chooseLetter);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ReviewLetterMain.this,ReviewLetter1.class));
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ReviewLetterMain.this,ReviewLetter2.class));
            }
        });

    }
}
