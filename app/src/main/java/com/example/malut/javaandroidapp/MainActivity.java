package com.example.malut.javaandroidapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.malut.javaandroidapp.Model.Person;
import com.example.malut.javaandroidapp.Services.OnPersonInfoPass;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity  implements OnPersonInfoPass{


    private InputFragment inputFragment;

    private Person person;
    private OnPersonInfoPass onPersonInfoPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputFragment = (InputFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_input);

    }



    @Override
    public void onPersonInfoPass(Person person) {
        this.person = person;
        Intent intent = new Intent(MainActivity.this, InfoActivity.class);
                    intent.putExtra("person_info", person);

                    startActivity(intent);

    }
}
