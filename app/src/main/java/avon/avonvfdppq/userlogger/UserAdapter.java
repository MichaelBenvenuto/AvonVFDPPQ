package avon.avonvfdppq.userlogger;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import avon.avonvfdppq.R;

/**
 * Created by Michael on 7/25/2018.
 */

public class UserAdapter extends ArrayAdapter<User> {

    private Context context;
    private List<User> data;
    private int resource;
    private LayoutInflater inflater;

    public UserAdapter(Context context, int res, List<User> data){
        super(context, res, data);

        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.resource = res;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return createItemView(position,convertView,parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return createItemView(position,convertView,parent);
    }

    private View createItemView(int position, View convertView, ViewGroup parent){
        View view = inflater.inflate(resource, parent, false);
        TextView item = view.findViewById(R.id.spinner_name);
        TextView age = view.findViewById(R.id.spinner_age);

        User u = data.get(position);
        item.setText(u.name);
        age.setText(u.clearance);

        return view;
    }
}
