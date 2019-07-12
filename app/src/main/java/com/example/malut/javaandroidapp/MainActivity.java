package com.example.malut.javaandroidapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
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
    private TextView errorInputText;

    private Person person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameEditText = findViewById(R.id.input_name);
        surnameEditText = findViewById(R.id.input_surname);
        ageEditText = findViewById(R.id.input_age);
        enterButton = findViewById(R.id.enter_button);
        errorInputText = findViewById(R.id.error_input_text);

        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ifInputCorrect()){
                    person = new Person(nameEditText.getText().toString(),
                            surnameEditText.getText().toString(),
                            Integer.parseInt(ageEditText.getText().toString()));

                    Intent intent = new Intent(MainActivity.this, InfoActivity.class);
                    intent.putExtra("person_info", person);

                    startActivity(intent);
                } else
                    errorInputText.setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        cleanFields();
        nameEditText.requestFocus();
    }

    private boolean ifInputCorrect(){
        if (nameEditText.getText().toString().isEmpty())
            return false;
        if (surnameEditText.getText().toString().isEmpty())
            return false;
        return ifAgeCorrect(ageEditText.getText().toString());
    }

    private boolean ifAgeCorrect(String str){
        if (str.isEmpty())
            return false;
        if (Integer.parseInt(str) < 0 || Integer.parseInt(str) > 150)
            return false;
        return !(str.matches(".*\\D.*"));
    }

    private void cleanFields(){
        cleanOneField(nameEditText);
        cleanOneField(surnameEditText);
        cleanOneField(ageEditText);
    }

    private void cleanOneField(EditText v){
        v.setText("");
    }
}
