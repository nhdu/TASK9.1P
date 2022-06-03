package com.example.lostandfoundweek7;

import java.util.ArrayList;
import java.util.Date;

public class ItemData {

    public static ArrayList<ItemData> itemList = new ArrayList<>();

    String name;
    String phone;
    String description;
    String date;
    String location;
    String type;
    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ItemData(String name, String phone, String description, String date, String location, String type, int ID) {
        this.name = name;
        this.phone = phone;
        this.description = description;
        this.date = date;
        this.location = location;
        this.type = type;
        this.id = ID;
    }
}
