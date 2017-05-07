package com.arek314.neurodraw;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class StartPlayersFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        View view = inflater.inflate(R.layout.start_players_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setList();
    }

    private void setList() {
        SinglePlayerAdapter adapter = new SinglePlayerAdapter(getActivity(), R.layout.single_player, getSinglePlayerBeanList());

        ListView listView = (ListView) getView().findViewById(R.id.start_players_list);
        listView.setAdapter(adapter);
    }

    private ArrayList<SinglePlayerBean> getSinglePlayerBeanList() {
        final ArrayList<String> players = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.players)));
        ArrayList<SinglePlayerBean> singlePlayerData = new ArrayList<>();

        for (String name : players) {
            singlePlayerData.add(new SinglePlayerBean(name));
        }

        return singlePlayerData;
    }
}
