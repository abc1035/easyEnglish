package com.example.easyenglish.Activity.ExamAcvitity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.easyenglish.R;

public class Cet6Activity extends AppCompatActivity {
    public ImageButton cet6writingButton;
    public ImageButton cet6listeningButton;
    public ImageButton cet6readingButton;
    public ImageButton cet6translatingButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cet6);

        cet6writingButton=findViewById(R.id.cet6writingButton);
        cet6listeningButton=findViewById(R.id.cet6listeningButton);
        cet6readingButton=findViewById(R.id.cet6readingButton);
        cet6translatingButton=findViewById(R.id.cet6translatingButton);
        final Intent it=new Intent(Cet6Activity.this, ExamSelectActivity.class);

        cet6writingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                it.putExtra("data","六级写作专项练习");
                startActivity(it);
            }
        });

        cet6listeningButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                it.putExtra("data","六级听力专项练习");
                startActivity(it);
            }
        });

        cet6readingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                it.putExtra("data","六级阅读专项练习");
                startActivity(it);
            }
        });

        cet6translatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                it.putExtra("data","六级翻译专项练习");
                startActivity(it);
            }
        });
    }
}
