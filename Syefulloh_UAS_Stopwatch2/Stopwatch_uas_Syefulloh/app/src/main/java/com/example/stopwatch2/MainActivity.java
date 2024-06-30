package com.example.stopwatch2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private int sec = 0;
    private boolean is_running;
    private boolean was_running;
    int hours,
            minutes,
            secs,
            ms;
    private int seconds = 0;
    private boolean running;

    // simple count variable to count number of laps
    int lapCount = 0;
private TextView t_view,ms_view,timeLap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        t_view = (TextView)findViewById(R.id.time_view);
        ms_view = (TextView)findViewById(R.id.ms_view);
        timeLap= (TextView)findViewById(R.id.timeLapse);
//        if (savedInstanceState != null) {
//            sec = savedInstanceState.getInt("seconds");
//            is_running = savedInstanceState.getBoolean("running");
//            was_running = savedInstanceState .getBoolean("wasRunning");
//        }
//        running_Timer();
        runTimer();
    }
    private void runTimer() {

        // creating handler
        final Handler handlertime = new Handler();

        // creating handler
        final Handler handlerMs = new Handler();

        handlertime.post(new Runnable() {@Override

        public void run() {
            hours = seconds / 3600;
            minutes = (seconds % 3600) / 60;
            secs = seconds % 60;

            // if running increment the seconds
            if (running) {
                String time = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, secs);

                t_view.setText(time);

                seconds++;
            }

            handlertime.postDelayed(this, 1000);
        }
        });

        handlerMs.post(new Runnable() {@Override
        public void run() {

            if (ms >= 99) {
                ms = 0;
            }

            // if running increment the ms
            if (running) {
                String msString = String.format(Locale.getDefault(), "%02d", ms);
                ms_view.setText(msString);

                ms++;
            }
            handlerMs.postDelayed(this, 1);
        }
        });

    }

    @Override
    public void onSaveInstanceState(
            Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("seconds", sec);
        savedInstanceState.putBoolean("running", is_running);
        savedInstanceState.putBoolean("wasRunning", was_running);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        was_running = is_running;
        is_running = false;
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if (was_running) {
            is_running = true;
        }
    }

    public void onClickStart(View view)
    {
//        is_running = true;
        running = true;
    }


    public void onClickStop(View view)
    {
//        is_running = false;
        running = false;
    }

    public void onClickReset(View view)
    {
//        is_running = false;
//        sec = 0;
        running = false;
        seconds = 0;
        lapCount = 0;

        // setting the text view to zero
        t_view.setText("00:00:00");
        ms_view.setText("00");
        timeLap.setText("");
    }

    private void running_Timer()
    {

        final TextView t_View = (TextView)findViewById(R.id.time_view);
        final Handler handle = new Handler();

        handle.post(new Runnable() {
            @Override

            public void run()
            {
                int hrs = sec / 3600;
                int mins = (sec % 3600) / 60;
                int secs = sec % 60;

                String time_t = String .format(Locale.getDefault(), "    %d:%02d:%02d   ", hrs,mins, secs);

                t_View.setText(time_t);

                if (is_running) {
                    sec++;
                }

                handle.postDelayed(this, 1000);
            }
        });
    }

    public void onClickLap(View view) {
        lapCount++;

        String laptext = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, secs);
        String msString = String.format(Locale.getDefault(), "%02d", ms);

        // adding ms to lap text
        laptext = laptext + ":" + msString;

        if (lapCount >= 10) {
            laptext = " Lap " + lapCount + " -------------       " + laptext + " \n " + timeLap.getText();
        } else {
            laptext = " Lap " + lapCount + " ---------------       " + laptext + " \n " + timeLap.getText();

        }

        //showing simple toast message to user
//        Toast.makeText(MainActivity2.this, "Lap " + lapCount, Toast.LENGTH_SHORT).show();

        // showing the lap text
        timeLap.setText(laptext);
    }
}