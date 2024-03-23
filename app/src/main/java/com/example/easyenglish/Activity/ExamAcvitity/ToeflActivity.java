package com.example.easyenglish.Activity.ExamAcvitity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.easyenglish.R;

public class ToeflActivity extends AppCompatActivity {
    public ImageButton toeflwritingButton;
    public ImageButton toefllisteningButton;
    public ImageButton toeflreadingButton;
    public ImageButton toeflspeakingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toefl);

        toeflwritingButton=findViewById(R.id.toeflwritingButton);
        toefllisteningButton=findViewById(R.id.toefllisteningButton);
        toeflreadingButton=findViewById(R.id.toeflreadingButton);
        toeflspeakingButton=findViewById(R.id.toeflspeakingButton);
        final Intent it=new Intent(ToeflActivity.this, ExamSelectActivity.class);

        toeflwritingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                it.putExtra("data","托福写作专项练习");
                startActivity(it);
            }
        });

        toefllisteningButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                it.putExtra("data","托福听力专项练习");
                startActivity(it);
            }
        });

        toeflreadingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                it.putExtra("data","托福阅读专项练习");
                startActivity(it);
            }
        });

        toeflspeakingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                it.putExtra("data","托福口语专项练习");
                startActivity(it);
            }
        });
    }
}
