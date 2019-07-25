package com.example.malut.javaandroidapp.Model;

import java.util.ArrayList;

public class PersonsList {

        private ArrayList<Person> results;

    public PersonsList() {
        this.results = new ArrayList<>();
    }

    public ArrayList<Person> getResults() {
            return results;
        }

        public void appendList(PersonsList newList){
            this.results.addAll(newList.results);
        }
}
