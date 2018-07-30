package avon.avonvfdppq.fitnesstracker;

/**
 * Created by Michael on 7/30/2018.
 */

public class FitnessItem {
    public String name;
    public String time;
    public FitnessStatus status;

    public long elapsedTime;

    public long startTime = 0;
    public long startInactivity = 0;

    public long inactiveTime;

    public FitnessItem(String name){
        this.name = name;
        this.time = "00:00";
        this.status = FitnessStatus.NotStarted;

        this.elapsedTime = 0;
        this.inactiveTime = 0;
    }


}
