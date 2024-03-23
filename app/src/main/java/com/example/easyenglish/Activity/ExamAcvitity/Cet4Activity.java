package com.example.easyenglish.Activity.ExamAcvitity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.easyenglish.R;

public class Cet4Activity extends AppCompatActivity {
    public ImageButton cet4writingButton;
    public ImageButton cet4listeningButton;
    public ImageButton cet4readingButton;
    public ImageButton cet4translatingButton;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cet4);

        cet4writingButton=findViewById(R.id.cet4writingButton);
        cet4listeningButton=findViewById(R.id.cet4listeningButton);
        cet4readingButton=findViewById(R.id.cet4readingButton);
        cet4translatingButton=findViewById(R.id.cet4translatingButton);
        final Intent it=new Intent(Cet4Activity.this, ExamSelectActivity.class);

        cet4writingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                it.putExtra("data","四级写作专项练习");
                startActivity(it);
            }
        });

        cet4listeningButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                it.putExtra("data","四级听力专项练习");
                startActivity(it);
            }
        });

        cet4readingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                it.putExtra("data","四级阅读专项练习");
                startActivity(it);
            }
        });

        cet4translatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                it.putExtra("data","四级翻译专项练习");
                startActivity(it);
            }
        });
    }
}