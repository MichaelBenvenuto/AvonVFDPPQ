package avon.avonvfdppq.fitnesslogger;

/**
 * Created by Michael on 7/20/2018.
 */

public class FitnessItem{
    public String name;
    public String time;

    public long elapsedTime;

    public FitnessItem(String name){
        this.name = name;
        this.time = "00:00";
    }

    public FitnessItem(String name, String time){
        this.name = name;
        this.time = time;
    }
}
