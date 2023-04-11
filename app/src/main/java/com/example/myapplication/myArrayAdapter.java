package com.example.myapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class myArrayAdapter extends ArrayAdapter<ObjetUser> {
    private int res;
    Context c;
    ArrayList<ObjetUser> Al = new ArrayList<ObjetUser>();

    public myArrayAdapter(@NonNull Context context, int resource, @NonNull List<ObjetUser> objects) {
        super(context, resource, objects);
        this.res = resource;
        this.c = context;
        this.Al = (ArrayList<ObjetUser>) objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        try {
            convertView = LayoutInflater.from(c).inflate(res,parent,false);

            ObjetUser o = Al.get(position);

            //4 textview
            TextView user = (TextView) convertView.findViewById(R.id.txtUser_);
            TextView name =  (TextView) convertView.findViewById(R.id.txtName_);
            TextView phone = (TextView) convertView.findViewById(R.id.txtPhone_);
            TextView loc =  (TextView) convertView.findViewById(R.id.txtLoc_);


            user.setText(o.getUsername());
            name.setText(o.getName());
            phone.setText(o.getPhone());
            loc.setText(o.getLocalisation());

        }
        catch (Exception exception){
            Log.i("toutou" ,exception.getMessage());
        }




        return convertView;
    }
}
