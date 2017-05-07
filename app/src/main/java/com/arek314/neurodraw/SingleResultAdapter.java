package com.arek314.neurodraw;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class SingleResultAdapter extends ArrayAdapter<SingleResultBean> {

    Context context;
    int layoutResourceId;
    ArrayList<SingleResultBean> data = new ArrayList<>();

    public SingleResultAdapter(Context context, int layoutResourceId, ArrayList<SingleResultBean> data) {
        super(context, layoutResourceId, data);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.data = data;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View singleResult = convertView;
        SingleResultBeanHolder singleResultBeanHolder;

        if (singleResult == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            singleResult = inflater.inflate(layoutResourceId, parent, false);

            singleResultBeanHolder = new SingleResultBeanHolder();
            singleResultBeanHolder.text = (TextView) singleResult.findViewById(R.id.single_result_text);
            singleResult.setTag(singleResultBeanHolder);

            SingleResultBean object = data.get(position);
            singleResultBeanHolder.text.setText(object.name + " : " + object.army);
            singleResultBeanHolder.text.setTag(position);

            singleResult.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((ShowDrawedArmiesActivity) context).redrawOne(position, data);
                }
            });
        }

        return singleResult;
    }

    static class SingleResultBeanHolder {
        TextView text;
    }


}
