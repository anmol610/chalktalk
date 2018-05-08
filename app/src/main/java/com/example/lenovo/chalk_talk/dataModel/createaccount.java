package com.example.lenovo.chalk_talk.dataModel;

/**
 * Created by lenovo on 03-Apr-18.
 */

public class createaccount {
    public String name,dob,qualification,address, gender;

    createaccount()
    {

    }

    public createaccount(String name, String dob, String qualification, String address, String gender)

    {
        this.name = name;
        this.dob = dob;
        this.address= address;
        this.qualification= qualification;
        this.gender = gender;
    }
}
