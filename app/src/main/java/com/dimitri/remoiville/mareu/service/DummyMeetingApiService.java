package com.dimitri.remoiville.mareu.service;

import com.dimitri.remoiville.mareu.model.Participant;
import com.dimitri.remoiville.mareu.model.Meeting;
import com.dimitri.remoiville.mareu.model.Room;

import java.util.ArrayList;
import java.util.List;

public class DummyMeetingApiService implements MeetingApiService {

    private List<Meeting> mMeetings = DummyMeetingGenerator.generateMeetings();
    private List<Meeting> mFilteredMeetingList = new ArrayList<>();
    private final List<Room> rooms = Room.getList();
    private final List<Participant> participants = Participant.getList();

    @Override
    public List<Meeting> getMeetings() {
        return mMeetings;
    }

    @Override
    public void deleteMeeting(Meeting meeting) {
        mMeetings.remove(meeting);
    }

    @Override
    public void createMeeting(Meeting meeting) {
        mMeetings.add(meeting);
    }

    @Override
    public void clearMeeting() {
        mMeetings.clear();
        mMeetings = DummyMeetingGenerator.generateMeetings();
    }

    @Override
    public List<Meeting> getFilteredMeetings() {
        return mFilteredMeetingList;
    }

    @Override
    public void deleteFilteredMeeting(Meeting meeting) {
        mFilteredMeetingList.remove(meeting);
    }

    @Override
    public void addFilteredMeeting(Meeting meeting) {
        mFilteredMeetingList.add(meeting);
    }

    @Override
    public void clearFilteredMeeting() {
        mFilteredMeetingList.clear();
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

