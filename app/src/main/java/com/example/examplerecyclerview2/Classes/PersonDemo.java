package com.example.examplerecyclerview2.Classes;

import android.net.Uri;

import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.internal.StorageReferenceUri;

import java.io.Serializable;

public class PersonDemo implements Serializable {

    private String personName;
    private String personImage;

    public PersonDemo() {
    }

    public PersonDemo(String personName, String personImage) {
        this.personName = personName;
        this.personImage = personImage;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPersonImage() {
        return personImage;
    }

    public void setPersonImage(String personImage) {
        this.personImage = personImage;
    }

    @Override
    public String toString() {
        return "PersonDemo{" +
                "personImage=" + personImage +
                ", personName='" + personName + '\'' +
                '}';
    }
}
