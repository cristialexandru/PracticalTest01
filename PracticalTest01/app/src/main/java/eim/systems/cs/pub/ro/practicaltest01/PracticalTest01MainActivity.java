package eim.systems.cs.pub.ro.practicaltest01;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class PracticalTest01MainActivity extends AppCompatActivity {

    Button b1;
    Button b2;
    Button bother;

    TextView v1;
    TextView v2;

    int serviceStatus = 0;

    private final static int SECONDARY_ACTIVITY_REQUEST_CODE = 1;

    class ButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Button b = (Button) v;
            if (v == b1)
                v1.setText("" + (Integer.parseInt(v1.getText().toString()) + 1));
            else if (v == b2)
                v2.setText("" + (Integer.parseInt(v2.getText().toString()) + 1));
            int leftNumberOfClicks = Integer.parseInt(v1.getText().toString());
            int rightNumberOfClicks = Integer.parseInt(v2.getText().toString());

            if (leftNumberOfClicks + rightNumberOfClicks > 10
                    && serviceStatus == 0) {
                Intent intent = new Intent(getApplicationContext(), PracticalTest01Service.class);
                intent.putExtra("firstNumber", leftNumberOfClicks);
                intent.putExtra("secondNumber", rightNumberOfClicks);
                getApplicationContext().startService(intent);
                serviceStatus = 1;
            }
        }
    }

    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("[Message]", intent.getStringExtra("message"));
        }
    }
    private IntentFilter intentFilter;

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

        bother = (Button) findViewById(R.id.other);
        bother.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), PracticalTest01SecondaryActivity.class);
                int n = Integer.parseInt(v1.getText().toString()) + Integer.parseInt(v2.getText().toString());
                intent.putExtra("numberOfClicks", n);
                startActivityForResult(intent, SECONDARY_ACTIVITY_REQUEST_CODE);
            }
        });
        intentFilter = new IntentFilter();
        intentFilter.addAction("1");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == SECONDARY_ACTIVITY_REQUEST_CODE) {
            Toast.makeText(this, "The activity returned with result " + resultCode, Toast.LENGTH_LONG).show();
        }
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
    protected  void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);

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
        Intent intent = new Intent(this, PracticalTest01Service.class);
        stopService(intent);
        super.onDestroy();

    }
}
