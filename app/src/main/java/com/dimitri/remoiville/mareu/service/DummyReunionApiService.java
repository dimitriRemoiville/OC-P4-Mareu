package com.dimitri.remoiville.mareu.service;

import com.dimitri.remoiville.mareu.model.Participant;
import com.dimitri.remoiville.mareu.model.Reunion;
import com.dimitri.remoiville.mareu.model.Room;

import java.util.List;

public class DummyReunionApiService implements ReunionApiService {

    private List<Reunion> reunions = DummyReunionGenerator.generateReunions();
    private List<Room> rooms = Room.getList();
    private List<Participant> participants = Participant.getList();

    @Override
    public List<Reunion> getReunions() {
        return reunions;
    }

    @Override
    public void deleteReunion(Reunion reunion) {
        reunions.remove(reunion);
    }

    @Override
    public void createReunion(Reunion reunion) {
        reunions.add(reunion);
    }

    @Override
    public void clearReunions() {
        reunions.clear();
        reunions = DummyReunionGenerator.generateReunions();
    }

    @Override
    public List<Room> getRooms() {
        return rooms;
    }

    @Override
    public List<Participant> getParticipants() {
        return participants;
    }
}

