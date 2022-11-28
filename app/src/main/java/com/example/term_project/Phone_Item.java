package com.example.term_project;

public class Phone_Item {
    String name;
    String phone_number;
    int imageId;

    public Phone_Item(String name, String phone_number) {
        this.name = name;
        this.phone_number = phone_number;
    }

    public Phone_Item(String name, String phone_number, int imageId) {
        this.name = name;
        this.phone_number = phone_number;
        this.imageId = imageId;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
    public String getPhone_number() {
        return phone_number;
    }

    public void setImageIdId(int imageId) {
        this.imageId = imageId;
    }
    public int getImageIdId() {
        return imageId;
    }

}
