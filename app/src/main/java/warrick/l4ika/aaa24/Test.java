package warrick.l4ika.aaa24;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.l4ikaa.a24.R;

public class Test extends AppCompatActivity {
    private static final Integer[] NUMBERS = {1,2,3,4,5,6,7,8,9,10,11,12,13};
    Spinner spinner1, spinner2, spinner3, spinner4;
    TextView solutionsTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        spinner1 = (Spinner)findViewById(R.id.spinner1);
        spinner2 = (Spinner)findViewById(R.id.spinner2);
        spinner3 = (Spinner)findViewById(R.id.spinner3);
        spinner4 = (Spinner)findViewById(R.id.spinner4);
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(Test.this,
                android.R.layout.simple_spinner_dropdown_item, NUMBERS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        spinner2.setAdapter(adapter);
        spinner3.setAdapter(adapter);
        spinner4.setAdapter(adapter);
        solutionsTV = (TextView) findViewById(R.id.SolutionsTV);
        solutionsTV.setText("");
        Button findSolutions = (Button) findViewById(R.id.findSolutions);
        findSolutions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findAllSolutions();
            }
        });
    }

    private void findAllSolutions(){
        int a,b,c,d;
        a = (int) spinner1.getSelectedItem();
        b = (int) spinner2.getSelectedItem();
        c = (int) spinner3.getSelectedItem();
        d = (int) spinner4.getSelectedItem();
        Solver solver = new Solver(a,b,c,d);
        if ( solver.isImpossible() )
            solutionsTV.setText("Impossible");
        else {
            String solutions = "";
            for ( String s : solver.getSolutions() )
                solutions+=s+"\n";
            solutionsTV.setText(solutions);
        }
    }
}