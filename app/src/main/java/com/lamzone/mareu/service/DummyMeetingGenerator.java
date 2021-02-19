package com.lamzone.mareu.service;

import android.graphics.Color;

import com.lamzone.mareu.model.Meeting;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public abstract class DummyMeetingGenerator {

    static List<Meeting> generateMeetings() {
        return new ArrayList<>(DUMMY_MEETINGS);
    }

    public static List<Meeting> DUMMY_MEETINGS = asList(
            new Meeting(1, Color.BLUE, "Réunion A", "14h00" , "Peach", "maxime@lamzone.com, caroline@lamezone.com", "15/03/2021"),
            new Meeting(2, Color.MAGENTA, "Réunion B", "16h00",   "Mario", "paul@lamzone.com, eric@lamezone.com", "10/03/2021"),
            new Meeting(3, Color.RED, "Réunion C", "19h00", "Luigi", "amandine@lamzone.com, alain@lamezone.com", "12/03/2021")
    );

    static List<String> generateRooms () {
        return new ArrayList<>(DUMMY_ROOMS);
    }

    public static List<String> DUMMY_ROOMS = asList(
            "Peach", "Mario", "Luigi",
            "Runner", "Dark", "Data",
            "Crypt", "Underfoot",
            "Studio", "Corner"
    );

    static List<String> generateColors () {
        return new ArrayList<>(DUMMY_COLORS);
    }

   public static List<String> DUMMY_COLORS = asList(
            "#39add1", "#3079ab", "#c25975",
            "#e15258", "#f9845b", "#838cc7",
            "#7d669e", "#53bbb4", "#51b46d",
            "#e0ab18", "#637a91", "#f092b0",
            "#b7c0c7"
    );

    }
