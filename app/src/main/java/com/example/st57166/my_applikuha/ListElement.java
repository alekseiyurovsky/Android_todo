package com.example.st57166.my_applikuha;

import java.io.Serializable;

public class ListElement implements Serializable {
    private int id;
    private String name;
    private boolean isChecked;


    public ListElement() {
        super();
    }



    ListElement(int id, String name, boolean isChecked){
        this.id = id;
        this.name = name;
        this.isChecked = isChecked;
    }

    ListElement(int id, String name){
        this.id = id;
        this.name = name;
        this.isChecked = false;
    }


    int GetId() { return id;}
    public void SetId(int newId) {id = newId;}

    String GetName() { return name;}
    public void SetName(String newName) { name = newName;}

    boolean GetCheckedStatus() { return isChecked;}
    public void SetCheckedStatus(boolean newStatus) { isChecked = newStatus;}

}
