package warrick.l4ika.aaa24;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.l4ikaa.a24.R;

public class Standard extends AppCompatActivity {
    private boolean numSelected = false;
    private boolean opSelected = false;
    private Button[] opButtons;
    private Button[] numButtons;
    private int[] numbers;
    private int a,b,c,d;
    private int selectedOp = 0;
    private int selectedNum = 0;
    private int numVisible = 4;
    private double score = 0;
    private int unselectedColor, selectedColor;
    private final Handler handler = new Handler();
    private TextView timeText;
    private TextView scoreText;
    private CountDownTimer timer;
    private double highScore;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standard);
        opButtons = new Button[4];
        numButtons = new Button[4];
        opButtons[0] = (Button) findViewById(R.id.plusOpButton);
        opButtons[1] = (Button) findViewById(R.id.minusOpButton);
        opButtons[2] = (Button) findViewById(R.id.multiplyOpButton);
        opButtons[3] = (Button) findViewById(R.id.divisionOpButton);
        numButtons[0] = (Button) findViewById(R.id.num1);
        numButtons[1] = (Button) findViewById(R.id.num2);
        numButtons[2] = (Button) findViewById(R.id.num3);
        numButtons[3] = (Button) findViewById(R.id.num4);
        numbers = new int[4];
        unselectedColor = Color.rgb(98,0,238);
        selectedColor = Color.rgb(156,39,176);
        for ( int i = 0; i < 4; i++ ){
            opButtons[i].setBackgroundColor(unselectedColor);
            numButtons[i].setBackgroundColor(unselectedColor);
        }
        setQuestion();
        opButtons[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                selectOp(0);
            }
        });
        opButtons[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                selectOp(1);
            }
        });
        opButtons[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                selectOp(2);
            }
        });
        opButtons[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                selectOp(3);
            }
        });
        numButtons[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                selectNum(0);
            }
        });
        numButtons[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                selectNum(1);
            }
        });
        numButtons[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                selectNum(2);
            }
        });
        numButtons[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                selectNum(3);
            }
        });
        timeText = (TextView) findViewById(R.id.timeTV);
        scoreText = (TextView) findViewById(R.id.scoreTV);
        scoreText.setText("Score: "+score);
        pref = this.getSharedPreferences("standardHighScore", Context.MODE_PRIVATE);
        editor = pref.edit();

        Button resetButton = (Button) findViewById(R.id.reset);
        Button skipButton = (Button) findViewById(R.id.skip);
        resetButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                softReset();
            }
        });
        skipButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                hardReset();
                score-=0.5;
                scoreText.setText("Score: "+score);
            }
        });
        startTimer();
    }

    void startTimer() {
        timer = new CountDownTimer(120000, 1000) {
            public void onTick(long millisUntilFinished) {
                timeText.setText("Time: "+(int)(millisUntilFinished/1000));
            }
            public void onFinish() {
                highScore = Math.max(score,pref.getFloat("standardHighScore", 0));
                editor.putFloat("standardHighScore",(float)highScore);
                editor.commit();
                goFinishScreen();
            }
        };
        timer.start();
    }

    public void goFinishScreen(){
        Intent intent = new Intent(this, FinishScreen.class);
        intent.putExtra("highScore",highScore+"");
        intent.putExtra("score",score+"");
        intent.putExtra("mode","Standard");
        startActivity(intent);
        finish();
    }

    private void selectOp(int i){
        if ( numSelected ) {
            opButtons[0].setBackgroundColor(unselectedColor);
            opButtons[1].setBackgroundColor(unselectedColor);
            opButtons[2].setBackgroundColor(unselectedColor);
            opButtons[3].setBackgroundColor(unselectedColor);
            if ( i == selectedOp && opSelected){
                opButtons[i].setBackgroundColor(unselectedColor);
                opSelected = false;
            } else if ( i == selectedOp && !opSelected ){
                opButtons[i].setBackgroundColor(selectedColor);
                opSelected = true;
            } else {
                opButtons[i].setBackgroundColor(selectedColor);
                opButtons[selectedOp].setBackgroundColor(unselectedColor);
                opSelected = true;
            }
            selectedOp = i;
        }
    }

    private void selectNum(int i) {
        if (!numSelected) {
            selectedNum = i;
            numSelected = true;
            numButtons[i].setBackgroundColor(selectedColor);
        } else {
            if ( selectedOp == 3 && numButtons[i].getText().equals("0") )
                return;
            opButtons[0].setBackgroundColor(unselectedColor);
            opButtons[1].setBackgroundColor(unselectedColor);
            opButtons[2].setBackgroundColor(unselectedColor);
            opButtons[3].setBackgroundColor(unselectedColor);
            if (i == selectedNum) {
                numButtons[i].setBackgroundColor(unselectedColor);
                opSelected = false;
                numSelected = false;
            } else if (opSelected) {
                if ( selectedOp == 0 ){
                    numbers[i] += numbers[selectedNum];
                } else if ( selectedOp == 1 ){
                    numbers[i] = numbers[selectedNum]-numbers[i];
                } else if ( selectedOp == 2 ){
                    numbers[i] *= numbers[selectedNum];
                } else if ( selectedOp == 3 ){
                    if ( numbers[selectedNum]%numbers[i] != 0 ) {
                        opButtons[3].setBackgroundColor(selectedColor);
                        return;
                    }
                    numbers[i] = numbers[selectedNum]/numbers[i];
                }
                numButtons[selectedNum].setVisibility(View.INVISIBLE);
                numVisible--;
                numButtons[i].setBackgroundColor(selectedColor);
                numButtons[i].setText(""+numbers[i]);
                if ( numVisible == 1 && numbers[i] == 24 ){
                    score++;
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run(){
                            hardReset();
                        }
                    }, 500);
                    scoreText.setText("Score: "+score);
                    return;
                }
                opSelected = false;
                numSelected = true;
                selectedNum = i;
            } else {
                numButtons[selectedNum].setBackgroundColor(unselectedColor);
                numButtons[i].setBackgroundColor(selectedColor);
                selectedNum = i;
            }
        }
    }

    private void softReset(){ //reset button and if answer is not 24
        numButtons[0].setVisibility(View.VISIBLE);
        numButtons[1].setVisibility(View.VISIBLE);
        numButtons[2].setVisibility(View.VISIBLE);
        numButtons[3].setVisibility(View.VISIBLE);
        numButtons[0].setBackgroundColor(unselectedColor);
        numButtons[1].setBackgroundColor(unselectedColor);
        numButtons[2].setBackgroundColor(unselectedColor);
        numButtons[3].setBackgroundColor(unselectedColor);
        opButtons[0].setBackgroundColor(unselectedColor);
        opButtons[1].setBackgroundColor(unselectedColor);
        opButtons[2].setBackgroundColor(unselectedColor);
        opButtons[3].setBackgroundColor(unselectedColor);
        numButtons[0].setText(""+a);
        numButtons[1].setText(""+b);
        numButtons[2].setText(""+c);
        numButtons[3].setText(""+d);
        numbers[0] = a;
        numbers[1] = b;
        numbers[2] = c;
        numbers[3] = d;
        numSelected = false;
        opSelected = false;
        numVisible = 4;
    }

    private void hardReset(){ //skip button and if user reaches 24
        numButtons[0].setVisibility(View.VISIBLE);
        numButtons[1].setVisibility(View.VISIBLE);
        numButtons[2].setVisibility(View.VISIBLE);
        numButtons[3].setVisibility(View.VISIBLE);
        numButtons[0].setBackgroundColor(unselectedColor);
        numButtons[1].setBackgroundColor(unselectedColor);
        numButtons[2].setBackgroundColor(unselectedColor);
        numButtons[3].setBackgroundColor(unselectedColor);
        opButtons[0].setBackgroundColor(unselectedColor);
        opButtons[1].setBackgroundColor(unselectedColor);
        opButtons[2].setBackgroundColor(unselectedColor);
        opButtons[3].setBackgroundColor(unselectedColor);
        numSelected = false;
        opSelected = false;
        numVisible = 4;
        setQuestion();
    }

    private void setQuestion(){
        int num1, num2, num3, num4;
        num1 = (int) (13*Math.random())+1;
        num2 = (int) (13*Math.random())+1;
        num3 = (int) (13*Math.random())+1;
        num4 = (int) (13*Math.random())+1;
        Solver question = new Solver(num1,num2,num3,num4);
        while ( question.isImpossible() ){
            num1 = (int) (13*Math.random()+1);
            num2 = (int) (13*Math.random()+1);
            num3 = (int) (13*Math.random()+1);
            num4 = (int) (13*Math.random()+1);
            question = new Solver(num1,num2,num3,num4);
        }
        a = num1;
        b = num2;
        c = num3;
        d = num4;
        numbers[0] = a;
        numbers[1] = b;
        numbers[2] = c;
        numbers[3] = d;
        numButtons[0].setText(""+num1);
        numButtons[1].setText(""+num2);
        numButtons[2].setText(""+num3);
        numButtons[3].setText(""+num4);
    }
    @Override
    public void onBackPressed(){
        finish();
    }
    @Override
    public void onPause(){
        timer.cancel();
        finish();
        super.onPause();
    }
}