package avon.avonvfdppq.fitnesslogger;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import avon.avonvfdppq.R;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by Michael on 7/20/2018.
 */

public class FitnessAdapter extends ArrayAdapter<FitnessItem> {
    private ArrayList<FitnessItem> items;
    private FitnessViewHolder fitnessHolder;

    private LayoutInflater vi;

    private class FitnessViewHolder{
        TextView name;
        TextView time;
    }

    public FitnessAdapter(Context context, int resId, ArrayList<FitnessItem> items1){
        super(context, resId, items1);
        this.items = items1;
        vi = (LayoutInflater)context.getSystemService(LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int pos, View convertView, ViewGroup parent){
        View v = convertView;
        if(v == null){
            v = vi.inflate(R.layout.list_item, null);
            fitnessHolder = new FitnessViewHolder();
            fitnessHolder.name = (TextView)v.findViewById(R.id.name_fitness);
            fitnessHolder.time = (TextView)v.findViewById(R.id.time_fitness);
            v.setTag(fitnessHolder);
        }else{
            fitnessHolder = (FitnessViewHolder)v.getTag();
        }

        FitnessItem item = items.get(pos);

        if(item != null){
            fitnessHolder.name.setText(item.name);
            fitnessHolder.time.setText(item.time);
        }

        return v;
    }

    public void setData(ArrayList<FitnessItem> items1){
        this.items = items1;
    }
}