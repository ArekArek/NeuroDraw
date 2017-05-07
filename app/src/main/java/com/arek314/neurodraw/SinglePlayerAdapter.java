package com.arek314.neurodraw;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Switch;

import java.util.ArrayList;

public class SinglePlayerAdapter extends ArrayAdapter<SinglePlayerBean> {

    Context context;
    int layoutResourceId;
    ArrayList<SinglePlayerBean> data = new ArrayList<>();

    public SinglePlayerAdapter(Context context, int layoutResourceId, ArrayList<SinglePlayerBean> data) {
        super(context, layoutResourceId, data);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View singlePlayer = convertView;
        SinglePlayerBeanHolder singlePlayerBeanHolder;

        if (singlePlayer == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            singlePlayer = inflater.inflate(layoutResourceId, parent, false);

            singlePlayerBeanHolder = new SinglePlayerBeanHolder();
            singlePlayerBeanHolder.name = (EditText) singlePlayer.findViewById(R.id.single_player_name);
            singlePlayerBeanHolder.toggle = (Switch) singlePlayer.findViewById(R.id.single_player_toggle);
            singlePlayer.setTag(singlePlayerBeanHolder);
        } else {
            singlePlayerBeanHolder = (SinglePlayerBeanHolder) singlePlayer.getTag();
        }

        SinglePlayerBean object = data.get(position);
        singlePlayerBeanHolder.name.setText(object.name);
        singlePlayerBeanHolder.name.setTag(position);
        singlePlayerBeanHolder.toggle.setTag(position);

        return singlePlayer;
    }

    static class SinglePlayerBeanHolder {
        EditText name;
        Switch toggle;
    }


}
