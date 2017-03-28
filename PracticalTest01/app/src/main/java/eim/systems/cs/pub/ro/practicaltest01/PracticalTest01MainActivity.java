package eim.systems.cs.pub.ro.practicaltest01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class PracticalTest01MainActivity extends AppCompatActivity {

    Button b1;
    Button b2;

    TextView v1;
    TextView v2;

    class ButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Button b = (Button) v;
            if (v == b1)
                v1.setText("" + (Integer.parseInt(v1.getText().toString()) + 1));
            else if (v == b2)
                v2.setText("" + (Integer.parseInt(v2.getText().toString()) + 1));
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_main);
        b1 = (Button) findViewById(R.id.button1);
        b2 = (Button) findViewById(R.id.button2);

        v1 = (TextView) findViewById(R.id.text_view1);
        v2 = (TextView) findViewById(R.id.text_view2);
        b1.setOnClickListener(new ButtonListener());
        b2.setOnClickListener(new ButtonListener());


    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        v1 = (TextView) findViewById(R.id.text_view1);
        v2 = (TextView) findViewById(R.id.text_view2);

        savedInstanceState.putString(Constants.T1, v1.getText().toString());
        savedInstanceState.putString(Constants.T2, v2.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        b1 = (Button) findViewById(R.id.button1);
        b2 = (Button) findViewById(R.id.button2);

        v1 = (TextView) findViewById(R.id.text_view1);
        v2 = (TextView) findViewById(R.id.text_view2);

        if ((savedInstanceState != null) &&
                (savedInstanceState.getString(Constants.T1) != null) &&
                (savedInstanceState.getString(Constants.T2) != null)) {
            v1.setText(savedInstanceState.getString(Constants.T1));
            v2.setText(savedInstanceState.getString(Constants.T2));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
