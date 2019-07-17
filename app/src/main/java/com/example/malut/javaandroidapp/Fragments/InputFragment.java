package com.example.malut.javaandroidapp.Fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.malut.javaandroidapp.Model.Person;
import com.example.malut.javaandroidapp.R;
import com.example.malut.javaandroidapp.Services.OnPersonInfoPass;


/**
 * A simple {@link Fragment} subclass.
 */
public class InputFragment extends Fragment {

    private EditText nameEditText;
    private EditText surnameEditText;
    private EditText ageEditText;
    private Button enterButton;
    private TextView errorInputText;

    private Person person;
    private OnPersonInfoPass onPersonInfoPass;

    public InputFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_input, container, false);

        nameEditText = v.findViewById(R.id.input_name);
        surnameEditText = v.findViewById(R.id.input_surname);
        ageEditText = v.findViewById(R.id.input_age);
        enterButton = v.findViewById(R.id.enter_button);
        errorInputText = v.findViewById(R.id.error_input_text);

        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ifInputCorrect()){
                    person = new Person(nameEditText.getText().toString(),
                            surnameEditText.getText().toString(),
                            Integer.parseInt(ageEditText.getText().toString()));
                    onPersonInfoPass.onPersonInfoPass(person);
                } else
                    errorInputText.setVisibility(View.VISIBLE);
            }
        });

        return v;
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onPersonInfoPass = (OnPersonInfoPass) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement onSomeEventListener");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        cleanFields();
        nameEditText.requestFocus();
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
