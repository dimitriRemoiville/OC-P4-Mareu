package com.dimitri.remoiville.mareu.service;

import com.dimitri.remoiville.mareu.model.Participant;
import com.dimitri.remoiville.mareu.model.Reunion;
import com.dimitri.remoiville.mareu.model.Room;

import java.util.List;

public interface ReunionApiService {

    List<Reunion> getReunions();

    void deleteReunion(Reunion reunion);

    void createReunion(Reunion reunion);

    void clearReunions();

    List<Room> getRooms();

    List<Participant> getParticipants();

}
