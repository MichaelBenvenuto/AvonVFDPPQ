package avon.avonvfdppq.userlogger;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Michael on 7/25/2018.
 */

public class UserLoader {

    private SharedPreferences userPrefs;
    private SharedPreferences userData;
    private SharedPreferences userFitness;

    private SharedPreferences.Editor userEditor;
    private SharedPreferences.Editor dataEditor;
    private SharedPreferences.Editor fitnessData;

    private Context c;

    public UserLoader(Context context){
        userPrefs = context.getSharedPreferences("Users", Context.MODE_PRIVATE);
        userData = context.getSharedPreferences("User_Data", Context.MODE_PRIVATE);
        dataEditor = userData.edit();
        userEditor = userPrefs.edit();
        dataEditor.apply();
        userEditor.apply();

        this.c = context;
    }

    public void saveUsers(ArrayList<User> users){
        for(int i = 0; i < users.size(); i++){
            User u = users.get(i);
            userEditor.putString(u.name, "");
            _saveUser(u);
        }
        userEditor.commit();
        dataEditor.commit();
    }

    private void _saveUser(User u){
        dataEditor.putString(u.name + "_Age", u.age);
        dataEditor.putString(u.name + "_Clearance", u.clearance);
    }

    public void saveUser(User u){
        userEditor.putString(u.name, null);
        _saveUser(u);
        dataEditor.commit();
    }

    public ArrayList<User> loadUsers(){
        ArrayList<User> users = new ArrayList<>();

        for(String s : userPrefs.getAll().keySet()){
            User u = new User(s,"","","");

            u.age = userData.getString(u.name + "_Age", "");
            u.clearance = userData.getString(u.name + "_Clearance", "");

            users.add(u);
        }

        return users;
    }

    public SharedPreferences.Editor getUserFitnessEditor(User u){
        return getUserFitness(u).edit();
    }

    public SharedPreferences getUserFitness(User u){
        return c.getSharedPreferences(u.name, Context.MODE_PRIVATE);
    }
}
