package com.l4ikaa.a24;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button mStartButton = (Button) findViewById(R.id.startButton);
        Log.d("test",mStartButton.toString());
        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                launchGame();
            }
        });
    }

    private void launchGame(){
        Intent intent = new Intent(this, SelectScreen.class);
        startActivity(intent);
    }
}