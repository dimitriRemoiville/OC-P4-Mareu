package com.dimitri.remoiville.mareu;

import com.dimitri.remoiville.mareu.di.DI;
import com.dimitri.remoiville.mareu.model.Meeting;
import com.dimitri.remoiville.mareu.service.DummyMeetingGenerator;
import com.dimitri.remoiville.mareu.service.MeetingApiService;

import org.junit.Before;
import org.junit.Test;
import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

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
}