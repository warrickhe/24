    package warrick.l4ika.aaa24;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.l4ikaa.a24.R;

    public class SelectScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_screen);
        Button mPracticeButton = (Button) findViewById(R.id.practiceButton);
        Log.d("test",mPracticeButton.toString());
        mPracticeButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    startPractice();
            }
        });
        Button standard = (Button) findViewById(R.id.standardButton);
        Button hard = (Button) findViewById(R.id.hardButton);
        standard.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startStandard();
            }
        });
        hard.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startHard();
            }
        });
        Button test = (Button) findViewById(R.id.testButton);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTest();
            }
        });
    }

    public void startPractice(){
        Intent intent = new Intent(this, Practice.class);
        startActivity(intent);
    }
    public void startStandard(){
        Intent intent = new Intent(this, Standard.class);
        startActivity(intent);
    }
    public void startHard(){
        Intent intent = new Intent(this, Hard.class);
        startActivity(intent);
    }
    public void startTest(){
        Intent intent = new Intent(this, Test.class);
        startActivity(intent);
    }
}