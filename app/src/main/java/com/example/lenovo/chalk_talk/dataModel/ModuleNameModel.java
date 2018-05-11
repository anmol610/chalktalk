package com.example.lenovo.chalk_talk.dataModel;

/**
 * Created by charanghumman on 11/05/18.
 */

public class ModuleNameModel {


    public String name ,  type  , content_type , module_id ;


    public ModuleNameModel()
    {

    }


    public ModuleNameModel(String name , String type , String content_type)
    {
        this.name = name ;

        this.type = type ;

        this.content_type = content_type ;
    }
}
