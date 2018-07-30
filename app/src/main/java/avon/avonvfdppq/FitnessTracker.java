package avon.avonvfdppq;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.ToggleButton;

import java.util.ArrayList;

import avon.avonvfdppq.fitnesstracker.FitnessAdapter;
import avon.avonvfdppq.fitnesstracker.FitnessItem;
import avon.avonvfdppq.fitnesstracker.FitnessStatus;
import avon.avonvfdppq.userlogger.User;
import avon.avonvfdppq.userlogger.UserLoader;

public class FitnessTracker extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, CompoundButton.OnCheckedChangeListener {


    private ListView fitnessList;
    private ToggleButton startButton;
    private Button next, fail;
    private Chronometer timer, inactive;
    private boolean timerStarted = false;
    private boolean isInactive = false;

    private ArrayList<FitnessItem> fitnessItems;
    private int currentItem = 0;

    private User user;
    private UserLoader loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitness_tracker);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        loader = new UserLoader(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        fitnessList = (ListView)findViewById(R.id.tracker_list);
        next = (Button)findViewById(R.id.tracker_next);
        fail = (Button)findViewById(R.id.tracker_fail);
        startButton = (ToggleButton)findViewById(R.id.tracker_timer_start);
        timer = (Chronometer)findViewById(R.id.tracker_time);
        inactive = (Chronometer)findViewById(R.id.tracker_inactive);

        next.setOnClickListener(this);
        fail.setOnClickListener(this);
        startButton.setOnClickListener(this);
        startButton.setOnCheckedChangeListener(this);

        fitnessItems = new ArrayList<>();

        String[] fitnessNames = getResources().getStringArray(R.array.fitness_array);

        for(String s : fitnessNames){
            fitnessItems.add(new FitnessItem(s));
        }

        FitnessAdapter adapter = new FitnessAdapter(this, fitnessItems);

        fitnessList.setAdapter(adapter);
        fitnessList.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == next){

            FitnessItem item = fitnessItems.get(currentItem);

            if(this.isInactive) {
                item.elapsedTime += (SystemClock.elapsedRealtime() - item.startTime);
            }else{
                item.inactiveTime += (SystemClock.elapsedRealtime() - item.startInactivity) - inactive.getBase();
            }
            fitnessItems.set(currentItem, item);
            item.status = FitnessStatus.Success;
            item.time = timer.getText().toString();

            if(currentItem == fitnessItems.size() - 1){
                timer.stop();
                inactive.stop();
                inactive.setVisibility(View.INVISIBLE);
            }else {

                currentItem++;

                if(currentItem == fitnessItems.size() - 1){
                    this.next.setText("Finish");
                }

                item = fitnessItems.get(currentItem);
                fitnessItems.set(currentItem, item);
                item.status = FitnessStatus.Pending;
                item.startTime = SystemClock.elapsedRealtime();

                this.startButton.setChecked(true);
            }

        }else if(view == fail){
            FitnessItem item = fitnessItems.get(currentItem);
            item.status = FitnessStatus.Fail;
            item.time = timer.getText().toString();
            fitnessItems.set(currentItem, item);

            inactive.stop();
            inactive.setVisibility(View.INVISIBLE);
            timer.stop();
        }
        FitnessAdapter adapter = new FitnessAdapter(this, fitnessItems);
        fitnessList.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        FitnessItem selectedItem = (FitnessItem)adapterView.getItemAtPosition(i);

        if(selectedItem != null){

        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if(compoundButton == this.startButton){
            this.isInactive = b;
            if(b) {
                if(!timerStarted) {
                    timer.setBase(SystemClock.elapsedRealtime());
                    timer.start();
                    timerStarted = true;

                    this.startButton.setTextOff("Inactive");
                    this.startButton.setTextOn("Active");

                    FitnessItem item = fitnessItems.get(currentItem);
                    item.status = FitnessStatus.Pending;
                    item.startTime = timer.getBase();
                    fitnessItems.set(currentItem, item);

                }else{
                    inactive.setVisibility(View.INVISIBLE);
                    inactive.stop();

                    FitnessItem item = fitnessItems.get(currentItem);
                    item.inactiveTime += (SystemClock.elapsedRealtime() - item.startInactivity) - inactive.getBase();
                    item.startTime = SystemClock.elapsedRealtime();
                    fitnessItems.set(currentItem, item);
                }
            }else{

                inactive.setVisibility(View.VISIBLE);
                inactive.setBase(SystemClock.elapsedRealtime());
                inactive.start();

                FitnessItem item = fitnessItems.get(currentItem);
                item.elapsedTime += (SystemClock.elapsedRealtime() - item.startTime);
                fitnessItems.set(currentItem, item);
            }
        }
    }
}
