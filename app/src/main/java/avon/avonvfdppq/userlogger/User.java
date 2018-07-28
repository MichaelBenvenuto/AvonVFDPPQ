package avon.avonvfdppq.userlogger;

import android.content.SharedPreferences;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Michael on 7/21/2018.
 */

public class User implements Serializable{
    public String name;
    public String age;
    public String date;
    public String clearance;

    private SharedPreferences pref = null;
    private SharedPreferences.Editor editor = null;

    public User(String name, String age, String date, String clearance){
        this.name = name;
        this.age = age;
        this.date = date;
        this.clearance = clearance;
    }

    public boolean equals(User u) {
        return (u.name.equals(this.name)) &&
                (u.age.equals(this.age)) &&
                (u.clearance.equals(this.clearance));
    }
}
