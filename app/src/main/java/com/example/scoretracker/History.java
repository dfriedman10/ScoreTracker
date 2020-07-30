package com.example.scoretracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class History extends AppCompatActivity {

    Spinner dropdown;
    SharedPreferences prefs;
    String type;
    PQ q;
    LinearLayout l;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        dropdown = findViewById(R.id.SortSpinner);
        String[] items = new String[]{"All Games", "Hearts", "Pee-Knuckle", "Spades", "Rummy"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(adapter);

        l = findViewById(R.id.LinearLayout);

        Set<String> scoreSet = prefs.getStringSet("Scores",new HashSet<String>());

        q = new PQ();

        for (String game : scoreSet) {
            q.add(game.split(":"));
        }

        Log.d("ASDF", q.toString());

        ((Spinner)findViewById(R.id.SortSpinner)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
               updateView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void updateView() {
        l.removeAllViews();
        type = dropdown.getSelectedItem().toString();
        for (String[] info : q) {
            if (type.equals(info[1]) || type.equals("All Games")) {
                Button b = new Button(History.this);
                b.setText("Mag " + info[2] + " - DP " + info[3] + " (" + info[1] + ")");
                l.addView(b);
            }
        }
    }

    private class PQ extends ArrayList<String[]> {

        public boolean add(String[] e) {
            if (size() == 0) {super.add(e); return true;}

            for (int i = 0; i < size(); i++) {
                if (Integer.parseInt(e[0]) > Integer.parseInt(get(i)[0])) {
                    add(i, e);
                    return true;
                }
            }
            super.add(e);
            return true;
        }

        public String toString() {
            String str2 = "";
            for (String[] element : this) {
                String str = "";
                for (String s : element) str += s + " ";
                str2 += str.trim()+", ";
            }
            return str2.length() == 0 ? str2 : str2.substring(0, str2.length()-2);
        }
    }
}