package com.dimitri.remoiville.mareu.model;

import java.util.ArrayList;
import java.util.List;

public class Room {

    /** Identifier */
    private long id;

    /** Name */
    private String name;

    /** Color */
    private String color;

    private static final List<Room> list = new ArrayList<Room>();
    {
        Room.list.add(this);
    }

    public static List<Room> getList() {
        return list;
    }

    public Room(long id, String name, String color) {
        this.id = id;
        this.name = name;
        this.color = color;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
