package com.dimitri.remoiville.mareu.service;

import com.dimitri.remoiville.mareu.model.Meeting;
import com.dimitri.remoiville.mareu.model.Participant;
import com.dimitri.remoiville.mareu.model.Room;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DummyMeetingGenerator {

    private static final Participant DUMMY_MAXIME = new Participant(1,"Maxime", "maxime@lamzone.com");
    private static final Participant DUMMY_ALEX = new Participant(2,"Alex", "alex@lamzone.com");
    private static final Participant DUMMY_PAUL = new Participant(3,"Paul", "paul@lamzone.com");
    private static final Participant DUMMY_VIVIANE = new Participant(4,"Viviane", "viviane@lamzone.com");
    private static final Participant DUMMY_AMADINE = new Participant(5,"Amadine", "amadine@lamzone.com");
    private static final Participant DUMMY_LUC = new Participant(6,"Luc", "luc@lamzone.com");
    private static Participant DUMMY_PIERRE = new Participant(7,"Pierre", "pierre@lamzone.com");
    private static final Participant DUMMY_THEO = new Participant(8,"Théo", "theo@lamzone.com");
    private static Participant DUMMY_GREG = new Participant(9,"Greg", "greg@lamzone.com");
    private static final Participant DUMMY_FLORIAN = new Participant(10,"Florian", "florian@lamzone.com");
    private static final Participant DUMMY_LIONEL = new Participant(11,"Lionel", "lionel@lamzone.com");

    private static final Room DUMMY_PEACH = new Room(1,"Peach", "#EDD9D0");
    private static final Room DUMMY_MARIO = new Room(2,"Mario", "#AECEB8");
    private static final Room DUMMY_LUIGI = new Room(3,"Luigi", "#AECEB8");
    private static final Room DUMMY_BOWSER = new Room(4,"Bowser","#EDD9D0");
    private static Room DUMMY_TOAD = new Room(5,"Toad","#EDD9D0");
    private static final Room DUMMY_YOSHI = new Room(6,"Yoshi","#EDD9D0");
    private static Room DUMMY_WARIO = new Room(7,"Wario","#EDD9D0");
    private static Room DUMMY_DAISY = new Room(8,"Daisy","#EDD9D0");
    private static Room DUMMY_DONKEY = new Room(9,"Donkey","#EDD9D0");
    private static Room DUMMY_WALUIGI = new Room(10,"Waluigi","#EDD9D0");



    public static final List<Meeting> DUMMY_MEETINGS = Arrays.asList(
            new Meeting(1, "Réunion A", "20200623", "14h00", DUMMY_PEACH, Arrays.asList(DUMMY_MAXIME, DUMMY_ALEX, DUMMY_LUC)),
            new Meeting(2, "Réunion B", "20200623", "16h00", DUMMY_MARIO, Arrays.asList(DUMMY_PAUL, DUMMY_VIVIANE)),
            new Meeting(3, "Réunion C", "20200623", "19h00", DUMMY_LUIGI, Arrays.asList(DUMMY_AMADINE, DUMMY_LUC, DUMMY_MAXIME, DUMMY_PAUL)),
            new Meeting(4, "Réunion D", "20200623", "12h00", DUMMY_BOWSER, Arrays.asList(DUMMY_PAUL,DUMMY_THEO, DUMMY_FLORIAN)),
            new Meeting(5, "Réunion E", "20200630", "17h00", DUMMY_LUIGI, Arrays.asList(DUMMY_AMADINE, DUMMY_LUC, DUMMY_MAXIME, DUMMY_PAUL)),
            new Meeting(6, "Réunion F", "20200630", "11h00", DUMMY_YOSHI, Arrays.asList(DUMMY_AMADINE, DUMMY_LUC, DUMMY_MAXIME, DUMMY_PAUL, DUMMY_LIONEL))
    );

    static List<Meeting> generateMeetings() {
        return new ArrayList<>(DUMMY_MEETINGS);
    }
}
