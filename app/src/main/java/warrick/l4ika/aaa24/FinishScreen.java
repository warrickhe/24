package warrick.l4ika.aaa24;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.l4ikaa.a24.R;

public class FinishScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_screen);
        Intent i = getIntent();
        String mode = i.getStringExtra("mode");
        String score = i.getStringExtra("score");
        String highScore = i.getStringExtra("highScore");
        TextView sTV = findViewById(R.id.fScoreTV);
        TextView hsTV = findViewById(R.id.highScoreTV);
        sTV.setText(""+score);
        hsTV.setText(""+highScore);
        TextView modeTV = findViewById(R.id.modeTV);
        modeTV.setText(mode);
        Button retry = (Button) findViewById(R.id.retry);
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( mode.equals("Standard") )
                    startStandard();
                else if (mode.equals("Hard") )
                    startHard();
                finish();
            }
        });
    }

    public void startStandard(){
        Intent intent = new Intent(this, Standard.class);
        startActivity(intent);
    }

    public void startHard(){
        Intent intent = new Intent(this, Hard.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed(){
        finish();
    }

}