package avon.avonvfdppq;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import avon.avonvfdppq.userlogger.User;
import avon.avonvfdppq.userlogger.UserLoader;

public class TimerActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    private Chronometer timer;
    private ToggleButton toggleButton;
    private Button resetButton;
    private Toolbar toolbar;

    private long elapsedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);


        timer = (Chronometer)findViewById(R.id.timer_timer);
        toggleButton = (ToggleButton)findViewById(R.id.timer_start);
        resetButton = (Button)findViewById(R.id.timer_reset);

        toolbar = (Toolbar)findViewById(R.id.timer_toolbar);

        setSupportActionBar(toolbar);

        ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);
        bar.setDisplayShowHomeEnabled(true);

        toggleButton.setOnCheckedChangeListener(this);
        resetButton.setOnClickListener(this);

        Intent i = getIntent();
        timer.setBase(SystemClock.elapsedRealtime() - i.getLongExtra("ElapsedTime", 0));
        bar.setTitle(i.getStringExtra("Name"));

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if(b){
            timer.setBase(SystemClock.elapsedRealtime() - elapsedTime);
            timer.start();
        }else{
            timer.stop();
            elapsedTime = SystemClock.elapsedRealtime() - timer.getBase();
        }
    }

    @Override
    public void onClick(View view) {
        if(view == resetButton){
            elapsedTime = 0;
            timer.setBase(SystemClock.elapsedRealtime());
        }
    }

    @Override
    public boolean onSupportNavigateUp() {

        Intent i = new Intent(this, FitnessLogger.class);
        i.putExtra("Time", timer.getText());
        i.putExtra("id", getIntent().getIntExtra("id", 0));
        i.putExtra("ElapsedTime", elapsedTime);
        setResult(RESULT_OK, i);

        onBackPressed();
        return true;
    }
}
