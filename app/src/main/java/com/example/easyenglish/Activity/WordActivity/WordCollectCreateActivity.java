package com.example.easyenglish.Activity.WordActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSONObject;
import com.example.easyenglish.R;

public class WordCollectCreateActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_collect_create);
        findViewById(R.id.word_collect_create_exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        findViewById(R.id.word_collect_create_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        findViewById(R.id.word_collect_create_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                EditText Name=findViewById(R.id.word_collect_create_name);
                EditText Note=findViewById(R.id.word_collect_create_note);
                final String name=Name.getText().toString().trim();
                final String note=Note.getText().toString().trim();
                if(name.equals(""))
                    Toast.makeText(WordCollectCreateActivity.this,"请输入用户名",Toast.LENGTH_SHORT).show();
                else if(note.equals(""))
                    Toast.makeText(WordCollectCreateActivity.this,"请输入昵称",Toast.LENGTH_SHORT).show();
                else{
                    new Thread(CollectRun).start();
                }
                Toast.makeText(getApplicationContext(),"添加成功",Toast.LENGTH_SHORT).show();
            }
            Runnable CollectRun=new Runnable() {
                @Override
                public void run() {
                    try{
                        EditText Name=findViewById(R.id.word_collect_create_name);
                        EditText Note=findViewById(R.id.word_collect_create_note);
                        final String name=Name.getText().toString().trim();
                        final String note=Note.getText().toString().trim();
                        JSONObject object = new JSONObject();
                    }
                    catch(Exception e){
                        String TAG="CollectCreate";
                        Log.e(TAG,Log.getStackTraceString(e));
                        System.out.println("failed");
                    }

                }

            };
        });

    }

}
