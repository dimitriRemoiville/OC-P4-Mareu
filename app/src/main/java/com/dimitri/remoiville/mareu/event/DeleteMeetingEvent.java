package com.dimitri.remoiville.mareu.event;

import com.dimitri.remoiville.mareu.model.Meeting;

public class DeleteMeetingEvent {

    public final Meeting Meeting;

    public DeleteMeetingEvent(Meeting meeting) {
        this.Meeting = meeting;
    }
}
