package avon.avonvfdppq;

import android.content.DialogInterface;
import android.content.Intent;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import avon.avonvfdppq.fitnesslogger.FitnessAdapter;
import avon.avonvfdppq.fitnesslogger.FitnessItem;
import avon.avonvfdppq.userlogger.User;
import avon.avonvfdppq.userlogger.UserLoader;

public class FitnessLogger extends AppCompatActivity implements AdapterView.OnItemClickListener, DialogInterface.OnClickListener {


    /*
    String[] fitness = {"Don PPE", "Don SCBA", "Mounting and Dismounting Apparatus", "Deployment of Valve Device",
            "24\' Ladder Raise", "Ladder Climb with Tool", "24\' Ladder Lower & Removal", "Use of Extrication/Ventilation Tool", "Hose Deployment",
            "Hose Line Repositioning", "Attack Line Operation", "Carry and Stow Power Saws or Vent Fan", "Hose Roll and Storage"};*/

    private ListView list;
    private ArrayList<FitnessItem> fitnessList;
    private FitnessAdapter fitnessAdapter;

    private Toolbar toolbar;

    private User user;

    private UserLoader loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loader = new UserLoader(this);

        setContentView(R.layout.activity_fitness_logger);
        list = (ListView)findViewById(R.id.fitnessList);

        fitnessList = new ArrayList<>();
        String[] fitnessNames = getResources().getStringArray(R.array.fitness_array);

        fitnessAdapter = new FitnessAdapter(this, R.id.fitnessList, fitnessList);

        list.setAdapter(fitnessAdapter);

        list.setOnItemClickListener(this);

        toolbar = (Toolbar)findViewById(R.id.fitness_toolbar);

        setSupportActionBar(toolbar);

        ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);
        bar.setDisplayShowHomeEnabled(true);

        Intent data = getIntent();

        user = (User)data.getSerializableExtra("User");

        SharedPreferences userPrefs = loader.getUserFitness(user);

        for(int i = 0; i < fitnessNames.length; i++){
            FitnessItem item = new FitnessItem(fitnessNames[i]);

            item.name = fitnessNames[i];
            item.time = userPrefs.getString(fitnessNames[i], "00:00");
            item.elapsedTime = userPrefs.getLong(fitnessNames[i] + "_elapsed", 0);

            fitnessList.add(item);
        }

        getSupportActionBar().setTitle(user.name);
        getSupportActionBar().setSubtitle(user.date);
    }

    @Override
    public boolean onSupportNavigateUp() {
        _saveData();
        onBackPressed();
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        FitnessItem selectedItem = (FitnessItem)adapterView.getItemAtPosition(i);

        if(selectedItem != null){
            Intent timer = new Intent(this, TimerActivity.class);

            timer.putExtra("Name", selectedItem.name);
            timer.putExtra("Time", selectedItem.time);
            timer.putExtra("ElapsedTime", selectedItem.elapsedTime);
            timer.putExtra("id", i);

            startActivityForResult(timer, 1);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                int id = data.getIntExtra("id", 0);

                FitnessItem i = fitnessList.get(id);
                FitnessItem newItem = new FitnessItem(i.name, data.getStringExtra("Time"));
                newItem.elapsedTime = data.getLongExtra("ElapsedTime", 0);
                fitnessList.set(id, newItem);
                fitnessAdapter = new FitnessAdapter(this, R.id.fitnessList, fitnessList);
                ((BaseAdapter)list.getAdapter()).notifyDataSetChanged();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.fitness_menu, menu);
        return true;
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int which) {
        switch (which){
            case DialogInterface.BUTTON_POSITIVE:

                break;

            case DialogInterface.BUTTON_NEGATIVE:
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.fitness_menu_save:

                _saveData();
                return true;

            case R.id.fitness_menu_clear:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Are you sure you want to clear all of your data?").setPositiveButton("Yes", this).setNegativeButton("No", this).show();

                return true;

            case R.id.fitness_menu_export:

                Intent si = new Intent();
                si.setAction(Intent.ACTION_SEND);

                String data = this.user.name + "-" + this.user.age + "-" + this.user.clearance + "\nName\tTime\n";

                for(int i = 0; i < fitnessList.size(); i++){
                    FitnessItem fitem = fitnessList.get(i);
                    data += fitem.name + "\t" + fitem.time + "\n";
                }

                si.putExtra(Intent.EXTRA_TEXT, data);
                si.setType("text/plain");
                startActivity(si);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void _saveData(){
        SharedPreferences.Editor userEditor = loader.getUserFitnessEditor(user);

        for(int i = 0; i < fitnessList.size(); i++){
            FitnessItem fitem = fitnessList.get(i);
            userEditor.putString(fitem.name, fitem.time);
            userEditor.putLong(fitem.name + "_elapsed", fitem.elapsedTime);
        }

        userEditor.commit();
    }
}
