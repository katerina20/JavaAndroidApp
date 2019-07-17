package com.example.malut.javaandroidapp.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.malut.javaandroidapp.InfoActivity;
import com.example.malut.javaandroidapp.Model.Person;
import com.example.malut.javaandroidapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class InfoFragment extends Fragment {

    private TextView infoName;
    private TextView infoSurname;
    private TextView infoAge;
    private Button closeButton;

    public InfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_info, container, false);

        infoName = v.findViewById(R.id.info_name);
        infoSurname = v.findViewById(R.id.info_surname);
        infoAge = v.findViewById(R.id.info_age);
        closeButton = v.findViewById(R.id.close_button);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        return v;
    }

    public void displayInfo(Person person){
        infoName.setText(person.getName());
        infoSurname.setText(person.getSurname());
        infoAge.setText(String.valueOf(person.getAge()));
    }

}
