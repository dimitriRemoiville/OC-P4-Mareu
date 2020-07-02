package com.dimitri.remoiville.mareu;

import com.dimitri.remoiville.mareu.di.DI;
import com.dimitri.remoiville.mareu.model.Meeting;
import com.dimitri.remoiville.mareu.model.Participant;
import com.dimitri.remoiville.mareu.model.Room;
import com.dimitri.remoiville.mareu.service.DummyMeetingGenerator;
import com.dimitri.remoiville.mareu.service.MeetingApiService;

import org.junit.Before;
import org.junit.Test;
import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static org.junit.Assert.*;

/**
 * Unit test on Meeting service
 */
@RunWith(JUnit4.class)
public class MeetingServiceUnitTest {

    private MeetingApiService service;

    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
    }

    @Test
    public void getMeetingsWithSuccess() {
        List<Meeting> meetings = service.getMeetings();
        List<Meeting> expectedMeetings = DummyMeetingGenerator.DUMMY_MEETINGS;

        assertThat(meetings, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedMeetings.toArray()));
    }

    @Test
    public void deleteMeetingWithSuccess() {
        Meeting meetingToDelete = service.getMeetings().get(0);
        service.deleteMeeting(meetingToDelete);
        assertFalse(service.getMeetings().contains(meetingToDelete));
    }

    @Test
    public void clearMeetingWithSuccess() {
        List<Meeting> expectedMeetings = DummyMeetingGenerator.DUMMY_MEETINGS;
        Meeting meetingToDelete = service.getMeetings().get(0);
        service.deleteMeeting(meetingToDelete);
        meetingToDelete = service.getMeetings().get(1);
        service.deleteMeeting(meetingToDelete);
        service.clearMeeting();
        List<Meeting> meetings = service.getMeetings();

        assertThat(meetings, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedMeetings.toArray()));
    }

    @Test
    public void addMeetingWithSuccess() {
        final Calendar calendar = Calendar.getInstance();
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        final int month = calendar.get(Calendar.MONTH) + 1;
        final int year = calendar.get(Calendar.YEAR);
        final String currentDate = year
                + String.format(Locale.FRANCE,"%02d", month)
                + String.format(Locale.FRANCE,"%02d", day);
        List<Room> rooms  = service.getRooms();
        List<Participant> participants = service.getParticipants();
        Meeting meeting = new Meeting(100,"Réunion Test", currentDate, "14h25",
                rooms.get(0),participants);
        service.createMeeting(meeting);
        List<Meeting> meetings = service.getMeetings();
        assertTrue(meetings.contains(meeting));
    }

    @Test
    public void filterWithSuccess() {
        List<Meeting> meetings = service.getMeetings();
        service.addFilteredMeeting(meetings.get(0));
        List<Meeting> filteredMeetings = service.getFilteredMeetings();
        assertTrue(filteredMeetings.contains(meetings.get(0)));
    }
}