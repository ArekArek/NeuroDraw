package com.arek314.neurodraw;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class ResultFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_result, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setList();

    }

    private Map<String, String> drawArmies() {
        final Set<String> players = getPlayers();
        ArrayList<String> drawedArmies = new ArrayList<>();
        Map<String, String> result = new LinkedHashMap<>();

        for (String s : players) {
            drawedArmies.add(drawOriginalArmy(drawedArmies));
            result.put(s, drawedArmies.get(drawedArmies.size() - 1));
        }

        return result;
    }

    private String drawOriginalArmy(ArrayList<String> drawedArmies) {

        final ArrayList<String> armyNames = new ArrayList<>(getArmies());
        String tmpArmyName;
        boolean isOk = false;
        do {
            tmpArmyName = armyNames.get(generateRandomNumbers(armyNames.size()));
            if (!drawedArmies.contains(tmpArmyName))
                isOk = true;
        } while (!isOk);
        return tmpArmyName;
    }

    private String drawOriginalArmyFromBeans(ArrayList<SingleResultBean> data) {
        final ArrayList<String> armyNames = new ArrayList<>(getArmies());
        String tmpArmyName;
        for (SingleResultBean tmp : data) {
            armyNames.remove(tmp.army);
        }
        tmpArmyName = armyNames.get(generateRandomNumbers(armyNames.size()));
        return tmpArmyName;
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

    public void redraw() {
        setList();
    }

    public void redrawOne(int position, ArrayList<SingleResultBean> data) {
        System.out.println(position);
        setList(position, data);
    }

    private void setList() {
        ArrayList<SingleResultBean> resultBeans = getSingleResultBeanList();
        SingleResultAdapter adapter = new SingleResultAdapter(getActivity(), R.layout.single_result, resultBeans);

        ListView listView = (ListView) getView().findViewById(R.id.result_list);
        listView.setAdapter(adapter);
        ((ShowDrawedArmiesActivity) getActivity()).setFirstPlayerText(resultBeans);
    }

    private void setList(int position, ArrayList<SingleResultBean> data) {

        data.set(position, new SingleResultBean(data.get(position).name, drawOriginalArmyFromBeans(data)));
        SingleResultAdapter adapter = new SingleResultAdapter(getActivity(), R.layout.single_result, data);

        ListView listView = (ListView) getView().findViewById(R.id.result_list);
        listView.setAdapter(adapter);


    }

    private ArrayList<SingleResultBean> getSingleResultBeanList() {
        ArrayList<String> data = new ArrayList<>();
        Map<String, String> tmpMap = drawArmies();

        ArrayList<SingleResultBean> singleResultData = new ArrayList<>();

        for (Map.Entry entry : tmpMap.entrySet()) {
            singleResultData.add(new SingleResultBean(entry.getKey().toString(), entry.getValue().toString()));
        }

        return singleResultData;
    }

    private Set<String> getPlayers() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("preferences", Context.MODE_PRIVATE);
        Set<String> defaultSet = new HashSet<>();
        defaultSet.add("error");
        return sharedPreferences.getStringSet("players", defaultSet);
    }

    private Set<String> getArmies() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Set<String> tmpStringSet = new HashSet<>();
        tmpStringSet.add("error");
        return sharedPreferences.getStringSet("accessible_armies", tmpStringSet);
    }


}
