package com.dimitri.remoiville.mareu.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Model object representing a Participant
 */
public class Participant {

    /** Identifier */
    private long id;

    /** Full name */
    private String name;

    /** Email address */
    private String email;

    private static final List<Participant> list = new ArrayList<Participant>();
    {
        Participant.list.add(this);
    }

    public static List<Participant> getList() {
        return list;
    }

    public Participant(long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}