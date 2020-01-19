package com.example.yeerun;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.yeerun.R;
import com.example.yeerun.Rout;

import java.util.ArrayList;
import java.util.List;

public class RoutesListAdapter extends ArrayAdapter<Rout> {
    public static final String TAG="RoutesListAdapter";
    private Context mcontext;
    int mResource;
    public RoutesListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Rout> routes) {
        super(context, resource, routes);
        mcontext=context;
        mResource=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String name=getItem(position).getName();
        Double length=getItem(position).getLength();
        String time=getItem(position).getTime();

        Rout rout= new Rout(name,length,time);
        LayoutInflater inflater= LayoutInflater.from(mcontext);
        convertView= inflater.inflate(mResource,parent,false);
        TextView tname= (TextView) convertView.findViewById(R.id.textView1);
        TextView tlength= (TextView) convertView.findViewById(R.id.textView2);
        TextView ttime= (TextView) convertView.findViewById(R.id.textView3);
        tname.setText(name);
        tlength.setText(length+"");
        ttime.setText(time);
        return convertView;

    }
}
