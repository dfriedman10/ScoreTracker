package com.example.scoretracker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    public static final String PREFSNAME = "ScoresFile";
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        fetchScores();
    }

    public void fetchScores() {
        Set<String> scoreSet = prefs.getStringSet("Scores",new HashSet<String>());

        int magScore = 0, dpScore = 0;
        for (String game : scoreSet) {
            String[] gameInfo = game.split(":");
            magScore += Integer.parseInt(gameInfo[2]);
            dpScore += Integer.parseInt(gameInfo[3]);
        }

        TextView magBox = findViewById(R.id.MagTotalScore), dpBox = findViewById(R.id.DPTotalScore);
        magBox.setText(magScore+"" + (magScore > dpScore ? new String(Character.toChars(0x1F451)):""));
        dpBox.setText(dpScore+""+ (dpScore > magScore ? new String(Character.toChars(0x1F451)):""));
    }

    public void newGameScreen(View view) {
        Intent intent = new Intent(this, NewGame.class);
        startActivity(intent);
    }

    public void historyScreen(View view) {
        Intent intent = new Intent(this, History.class);
        startActivity(intent);
    }
}