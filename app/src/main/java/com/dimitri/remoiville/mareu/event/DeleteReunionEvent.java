package com.dimitri.remoiville.mareu.event;

import com.dimitri.remoiville.mareu.model.Reunion;

public class DeleteReunionEvent {

    public final Reunion Reunion;

    public DeleteReunionEvent(Reunion reunion) {
        this.Reunion = reunion;
    }
}
