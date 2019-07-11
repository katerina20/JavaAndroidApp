package com.example.malut.javaandroidapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.malut.javaandroidapp.Model.Person;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private EditText nameEditText;
    private EditText surnameEditText;
    private EditText ageEditText;
    private Button enterButton;

    private Person person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameEditText = findViewById(R.id.input_name);
        surnameEditText = findViewById(R.id.input_surname);
        ageEditText = findViewById(R.id.input_age);
        enterButton = findViewById(R.id.enter_button);
        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                person = new Person(nameEditText.getText().toString(),
                        surnameEditText.getText().toString(),
                        Integer.parseInt(ageEditText.getText().toString()));

                Intent intent = new Intent(MainActivity.this, InfoActivity.class);
                startActivity(intent);
            }
        });

    }
}
