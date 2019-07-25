package com.example.malut.javaandroidapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Person implements Parcelable {

    private String name;
    private String surname;
    private int age;
    private int image;

    public Person(String name, String surname, int age, int image) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.image = image;
    }

    protected Person(Parcel in) {
        name = in.readString();
        surname = in.readString();
        age = in.readInt();
        image = in.readInt();
    }

    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public int getAge() {
        return age;
    }

    public int getImage() {
        return image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(surname);
        dest.writeInt(age);
        dest.writeInt(image);
    }
}
