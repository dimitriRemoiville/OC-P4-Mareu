package com.dimitri.remoiville.mareu.model;

import java.util.List;
import java.util.Objects;

/**
 * Model object representing a Reunion
 */
public class Reunion {

    /** Identifier */
    private long id;

    /** Name */
    private String name;

    /** Date - YYYYMMDD */
    private String date;

    /** Start time */
    private String time;

    /** Meeting room */
    private Room room;

    /** List of meeting participants */
    private List<Participant> participants;


    public Reunion(long id, String name, String date, String time, Room room, List<Participant> participants) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
        this.room = room;
        this.participants = participants;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reunion reunion = (Reunion) o;
        return id == reunion.id &&
                Objects.equals(name, reunion.name) &&
                Objects.equals(date, reunion.date) &&
                Objects.equals(time, reunion.time) &&
                Objects.equals(room, reunion.room) &&
                Objects.equals(participants, reunion.participants);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, date, time, room, participants);
    }


}