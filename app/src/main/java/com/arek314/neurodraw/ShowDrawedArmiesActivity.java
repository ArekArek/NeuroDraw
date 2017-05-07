package com.arek314.neurodraw;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;


public class ShowDrawedArmiesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_drawed_armies);
    }

    public void redraw(View view) {
        ResultFragment firstFragment = (ResultFragment) getFragmentManager().findFragmentById(R.id.first_fragment);
        firstFragment.redraw();
    }

    public void redrawOne(int position, ArrayList<SingleResultBean> data) {
        ResultFragment firstFragment = (ResultFragment) getFragmentManager().findFragmentById(R.id.first_fragment);
        firstFragment.redrawOne(position, data);
    }

    public void setFirstPlayerText(ArrayList<SingleResultBean> data) {

        ArrayList<String> players = new ArrayList<>();
        ArrayList<String> armies = new ArrayList<>();
        for (SingleResultBean tmp : data) {
            players.add(tmp.name);
            armies.add(tmp.army);
        }

        TextView firstPlayerView = (TextView) findViewById(R.id.first_player);

        if (armies.contains("Dancer"))
            firstPlayerView.setText(getResources().getString(R.string.first) + " " + players.get(armies.indexOf("Dancer")));
        else if (armies.contains("Vegas"))
            firstPlayerView.setText(getResources().getString(R.string.last) + " " + players.get(armies.indexOf("Vegas")));
        else
            firstPlayerView.setText(getResources().getString(R.string.first) + " " + players.get(generateRandomNumbers(players.size())));
    }

    private int generateRandomNumbers(int max) {
        try {
            SecureRandom randomGenerator = SecureRandom.getInstance("SHA1PRNG");
            return randomGenerator.nextInt(max);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return 0;
    }

}


