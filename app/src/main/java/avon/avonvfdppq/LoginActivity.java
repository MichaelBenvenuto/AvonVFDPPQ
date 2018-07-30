package avon.avonvfdppq;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.Loader;
import android.database.Cursor;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Calendar;

import avon.avonvfdppq.userlogger.User;
import avon.avonvfdppq.userlogger.UserAdapter;
import avon.avonvfdppq.userlogger.UserLoader;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor>, DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */

    // UI references.
    private View mLoginFormView;

    private Button continueButton, useToday;
    private EditText name, age, date, clearance;
    private Spinner users;

    private ArrayList<User> userList;

    private User userLogin = null;
    private UserLoader userLoader;

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        pref = getApplicationContext().getSharedPreferences("Fitness", 0);
        editor = pref.edit();

        continueButton = (Button) findViewById(R.id.sign_in_button);

        name = (EditText)findViewById(R.id.name);

        age = (EditText)findViewById(R.id.age);

        date = (EditText)findViewById(R.id.date);
        useToday = (Button)findViewById(R.id.useToday);

        clearance = (EditText)findViewById(R.id.clearance);

        mLoginFormView = findViewById(R.id.login_form);

        userLoader = new UserLoader(this);

        users = (Spinner)findViewById(R.id.user_spinner);

        userList = userLoader.loadUsers();

        UserAdapter dataAdapter = new UserAdapter(this, R.layout.spinner_item, userList);

        users.setAdapter(dataAdapter);

        users.setOnItemSelectedListener(this);

        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH) + 1;
        int year = c.get(Calendar.YEAR);

        date.setText(month + "/" + day + "/" + year);

        editor.apply();

        date.setText(pref.getString("Date", month + "/" + day + "/" + year));
    }

    public void onClick(View v){
        if(v == useToday){
            Calendar c = Calendar.getInstance();
            int day = c.get(Calendar.DAY_OF_MONTH);
            int month = c.get(Calendar.MONTH) + 1;
            int year = c.get(Calendar.YEAR);
            date.setText(month + "/" + day + "/" + year);
        }else if(v == continueButton){
            Intent i = new Intent(this, FitnessTracker.class);

            User u = new User(name.getText().toString(), age.getText().toString(), date.getText().toString(), clearance.getText().toString());

            if(!userListContains(u)){
                userList.add(u);
            }

            userLoader.saveUsers(userList);

            i.putExtra("User", u);

            editor.apply();

            startActivity(i);
        }
    }

    private boolean userListContains(User u){
        boolean b = false;
        for(int i = 0; i < userList.size(); i++){
            b |= userList.get(i).equals(u);
        }
        return b;
    }

    public void onDateClick(View v){
        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH) + 1;
        int year = c.get(Calendar.YEAR);
        new DatePickerDialog(this, this, year, month, day).show();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return null;
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        date.setText(month + "/" + day + "/" + year);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        this.userLogin = userList.get(i);

        this.name.setText(this.userLogin.name);
        this.age.setText(this.userLogin.age);
        this.date.setText(this.userLogin.date);
        this.clearance.setText(this.userLogin.clearance);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}

































