package com.dimitri.remoiville.mareu.service;

import com.dimitri.remoiville.mareu.model.Participant;
import com.dimitri.remoiville.mareu.model.Reunion;
import com.dimitri.remoiville.mareu.model.Room;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DummyReunionGenerator {

    private static Participant DUMMY_MAXIME = new Participant(1,"Maxime", "maxime@lamzone.com");
    private static Participant DUMMY_ALEX = new Participant(2,"Alex", "alex@lamzone.com");
    private static Participant DUMMY_PAUL = new Participant(3,"Paul", "paul@lamzone.com");
    private static Participant DUMMY_VIVIANE = new Participant(4,"Viviane", "viviane@lamzone.com");
    private static Participant DUMMY_AMADINE = new Participant(5,"Amadine", "amadine@lamzone.com");
    private static Participant DUMMY_LUC = new Participant(6,"Luc", "luc@lamzone.com");

    private static Room DUMMY_PEACH = new Room(1,"Peach", "#EDD9D0");
    private static Room DUMMY_MARIO = new Room(2,"Mario", "#AECEB8");
    private static Room DUMMY_LUIGI = new Room(3,"Luigi", "#AECEB8");
    private static Room DUMMY_BOWSER = new Room(4,"Bowser","#EDD9D0");



    public static final List<Reunion> DUMMY_REUNIONS = Arrays.asList(
            new Reunion(1,"Réunion A", "14h00",DUMMY_PEACH, Arrays.asList(DUMMY_MAXIME, DUMMY_ALEX, DUMMY_LUC)),
            new Reunion(2,"Réunion B", "16h00",DUMMY_MARIO, Arrays.asList(DUMMY_PAUL, DUMMY_VIVIANE)),
            new Reunion(3,"Réunion C", "19h00",DUMMY_LUIGI, Arrays.asList(DUMMY_AMADINE, DUMMY_LUC, DUMMY_MAXIME, DUMMY_PAUL)),
            new Reunion(4,"Réunion D", "12h00", DUMMY_BOWSER, Arrays.asList(DUMMY_PAUL))
    );

    static List<Reunion> generateReunions() {
        return new ArrayList<>(DUMMY_REUNIONS);
    }
}
