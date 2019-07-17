package com.example.malut.javaandroidapp;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.malut.javaandroidapp.Fragments.InfoFragment;
import com.example.malut.javaandroidapp.Model.Person;

import java.util.Objects;

public class InfoActivity extends AppCompatActivity {

    private Person person;
    private Fragment infoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        Objects.requireNonNull(getSupportActionBar()).hide();

        person = getIntent().getParcelableExtra("person_info");
        infoFragment = (InfoFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_info);
        ((InfoFragment) infoFragment).displayInfo(person);
    }
}
