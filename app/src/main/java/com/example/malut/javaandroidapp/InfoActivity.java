package com.example.malut.javaandroidapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.malut.javaandroidapp.Model.Person;

import java.util.Objects;

public class InfoActivity extends AppCompatActivity {

    private TextView infoName;
    private TextView infoSurname;
    private TextView infoAge;
    private Button closeButton;

    private Person person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        Objects.requireNonNull(getSupportActionBar()).hide();
        person = getIntent().getParcelableExtra("person_info");

        infoName = findViewById(R.id.info_name);
        infoSurname = findViewById(R.id.info_surname);
        infoAge = findViewById(R.id.info_age);
        closeButton = findViewById(R.id.close_button);

        setInfo();

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void setInfo(){
        infoName.setText(person.getName());
        infoSurname.setText(person.getSurname());
        infoAge.setText(String.valueOf(person.getAge()));
    }
}
