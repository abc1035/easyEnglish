package com.example.easyenglish.Activity.ExamAcvitity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.easyenglish.R;

public class IeltsActivity extends AppCompatActivity {
    public ImageButton ieltswritingButton;
    public ImageButton ieltslisteningButton;
    public ImageButton ieltsreadingButton;
    public ImageButton ieltsspeakingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ielts);

        ieltswritingButton=findViewById(R.id.ieltswritingButton);
        ieltslisteningButton=findViewById(R.id.ieltslisteningButton);
        ieltsreadingButton=findViewById(R.id.ieltsreadingButton);
        ieltsspeakingButton=findViewById(R.id.ieltsspeakingButton);
        final Intent it=new Intent(IeltsActivity.this, ExamSelectActivity.class);

        ieltswritingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                it.putExtra("data","雅思写作专项练习");
                startActivity(it);
            }
        });

       ieltslisteningButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                it.putExtra("data","雅思听力专项练习");
                startActivity(it);
            }
        });

        ieltsreadingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                it.putExtra("data","雅思阅读专项练习");
                startActivity(it);
            }
        });

        ieltsspeakingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                it.putExtra("data","雅思口语专项练习");
                startActivity(it);
            }
        });
    }
}
