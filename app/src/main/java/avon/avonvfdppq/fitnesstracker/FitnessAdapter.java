package avon.avonvfdppq.fitnesstracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Formatter;

import avon.avonvfdppq.R;

/**
 * Created by Michael on 7/30/2018.
 */

public class FitnessAdapter extends ArrayAdapter<FitnessItem> {

    private ArrayList<FitnessItem> data;
    private Context context;
    private LayoutInflater inflater;
    private FitnessViewHolder fitnessHolder;

    private class FitnessViewHolder{
        TextView name;
        TextView time;
        TextView status;
        TextView inactiveTime;
        TextView elapsedTime;
    }

    public FitnessAdapter(Context context, ArrayList<FitnessItem> objects) {
        super(context, R.layout.tracker_list_item, objects);
        this.data = objects;
        this.context = context;

        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if(v == null){
            v = inflater.inflate(R.layout.tracker_list_item, null);
            fitnessHolder = new FitnessViewHolder();

            fitnessHolder.name = (TextView)v.findViewById(R.id.tlist_name);
            fitnessHolder.time = (TextView)v.findViewById(R.id.tlist_time);
            fitnessHolder.status = (TextView)v.findViewById(R.id.tlist_status);
            fitnessHolder.inactiveTime = (TextView)v.findViewById(R.id.tlist_inactive_time);
            fitnessHolder.elapsedTime = (TextView)v.findViewById(R.id.tlist_elapsed_time);

            v.setTag(fitnessHolder);
        }else{
            fitnessHolder = (FitnessViewHolder)v.getTag();
        }

        FitnessItem item = data.get(position);

        if(item != null){
            fitnessHolder.name.setText(item.name);
            fitnessHolder.time.setText(item.time);
            fitnessHolder.status.setText(item.status.toString());

            long seconds = item.elapsedTime / 1000, minutes, hours;
            hours = (seconds / 3600);
            seconds %= 3600;
            minutes = (seconds / 60);
            seconds %= 60;

            if(hours > 0) {
                fitnessHolder.elapsedTime.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
            }else{
                fitnessHolder.elapsedTime.setText(String.format("%02d:%02d", minutes, seconds));
            }

            seconds = item.inactiveTime / 1000;
            hours = (seconds / 3600);
            seconds %= 3600;
            minutes = (seconds / 60);
            seconds %= 60;

            if(hours > 0) {
                fitnessHolder.inactiveTime.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
            }else{
                fitnessHolder.inactiveTime.setText(String.format("%02d:%02d", minutes, seconds));
            }
        }

        return v;
    }
}
