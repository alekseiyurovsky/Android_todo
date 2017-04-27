package com.example.st57166.my_applikuha;

public class ListElement {
    private String name;
    private boolean isChecked;
    private String message;


    ListElement(String name, boolean isChecked, String message){
    this.name = name;
    this.isChecked = isChecked;
    this.message = message;
    }

    ListElement(String name, boolean isChecked){
        this.name = name;
        this.isChecked = isChecked;
        this.message = "";
    }

    ListElement(String name){
        this.name = name;
        this.isChecked = false;
        this.message = "";
    }


    String GetName() { return name;}
    public void SetName(String newName) { name = newName;}

    boolean GetCheckedStatus() { return isChecked;}
    public void SetCheckedStatus(boolean newStatus) { isChecked = newStatus;}

    String GetMessage() { return message;}
    public void SetMessage(String newMessage) { message = newMessage;}

}
