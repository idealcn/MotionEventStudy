package com.idealcn.event.study;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        HorizontalScrollView
      final  Button btn  = findViewById(R.id.btn);
      final TextView textView = findViewById(R.id.textView);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.animate().translationX(-300).translationY(-50).start();
//                textView.scrollTo(-300,-300);
            }
        });
    }
}
