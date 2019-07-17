package com.example.malut.javaandroidapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.malut.javaandroidapp.Fragments.InfoFragment;
import com.example.malut.javaandroidapp.Fragments.InputFragment;
import com.example.malut.javaandroidapp.Model.Person;
import com.example.malut.javaandroidapp.Services.OnPersonInfoPass;

import java.util.Objects;

public class MainActivity extends AppCompatActivity  implements OnPersonInfoPass{


    private InputFragment inputFragment;
    private InfoFragment infoFragment;

    private boolean inLandscapeMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inLandscapeMode = findViewById(R.id.fragment_info) != null;
        inputFragment = (InputFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_input);
        if (inLandscapeMode){
            infoFragment = (InfoFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_info);
            Objects.requireNonNull(getSupportActionBar()).hide();
        }


    }



    @Override
    public void onPersonInfoPass(Person person) {
        if (inLandscapeMode) {
            infoFragment.displayInfo(person);
        } else {
            Intent intent = new Intent(MainActivity.this, InfoActivity.class);
            intent.putExtra("person_info", person);
            startActivity(intent);
        }

    }
}
