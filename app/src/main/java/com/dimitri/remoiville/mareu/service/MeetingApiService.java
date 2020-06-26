package com.dimitri.remoiville.mareu.service;

import com.dimitri.remoiville.mareu.model.Meeting;
import com.dimitri.remoiville.mareu.model.Participant;
import com.dimitri.remoiville.mareu.model.Room;

import java.util.List;

public interface MeetingApiService {

    List<Meeting> getMeetings();

    void deleteMeeting(Meeting meeting);

    void createMeeting(Meeting meeting);

    void clearMeeting();

    List<Room> getRooms();

    List<Participant> getParticipants();

}
