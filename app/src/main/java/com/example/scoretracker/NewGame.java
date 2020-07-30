package com.example.scoretracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.preference.PreferenceManager;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Set;

public class NewGame extends AppCompatActivity {
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private Spinner dropdown;

    private class NumericKeyBoardTransformationMethod extends PasswordTransformationMethod {
        @Override
        public CharSequence getTransformation(CharSequence source, View view) {
            return source;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);

        ((EditText)findViewById(R.id.MagScore)).setTransformationMethod(new NumericKeyBoardTransformationMethod());
        ((EditText)findViewById(R.id.DPScore)).setTransformationMethod(new NumericKeyBoardTransformationMethod());

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();

        dropdown = findViewById(R.id.GameType);
        String[] items = new String[]{"Hearts", "Pee-Knuckle", "Spades", "Rummy", "Hard Reset"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(adapter);

    }

    public void submit(View view) {
        String type = dropdown.getSelectedItem().toString();

        String magScore = ((EditText)(findViewById(R.id.MagScore))).getText().toString();
        String dpScore = ((EditText)(findViewById(R.id.DPScore))).getText().toString();

        if (type.equals("Hard Reset")) {
            if (magScore.equals("69") && dpScore.equals("69")) {
                editor.clear();
                editor.putStringSet("Scores", new HashSet<String>());
                editor.commit();

                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
            else {
                Toast message = Toast.makeText(this.getApplicationContext(), "Enter the reset code", Toast.LENGTH_LONG);
                message.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL,0,0);
                message.show();
            }
        }
        else if (magScore.equals("Enter Score") || dpScore.equals("Enter Score")) {
            Toast message = Toast.makeText(this.getApplicationContext(), "Enter a score", Toast.LENGTH_LONG);
            message.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL,0,0);
            message.show();
        }
        else {
            if (type.equals("Hearts")) {
                magScore = "" + (100 - Integer.parseInt(magScore));
                dpScore = "" + (100 - Integer.parseInt(dpScore));
            }
            editor.clear();

            Set<String> magSet = prefs.getStringSet("Scores", new HashSet<String>());
            magSet.add((magSet.size() + 1) + ":" + type + ":" + magScore + ":" + dpScore);
            editor.putStringSet("Scores", magSet);
            editor.commit();

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}