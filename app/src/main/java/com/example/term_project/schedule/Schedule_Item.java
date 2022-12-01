package com.example.term_project.schedule;

public class Schedule_Item {
    String name;
    String date;

    public Schedule_Item(String name, String date) {
        this.name = name;
        this.date = date;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setSchedule_Date(String date) {
        this.date = date;
    }
    public String getSchedule_Date() {
        return date;
    }

}
