package com.lamzone.mareu.model;

import androidx.annotation.Nullable;

import java.util.Objects;

public class Meeting {

    /**
     * Identifier
     */
    private long id;

    /**
     * Time for the meeting
     */
    private String timeForTheMeeting;

    /**
     * Meeting room
     */
    private String meetingPlace;

    /**
     * Subject of the meeting
     */
    private String meetingSubject;

    /**
     * List of participants
     */
    private String meetingParticipants;

    /**
     * Color meeting
     */
    private int color;

    /**
     * Date meeting
     */
    private String date;

    /**
     * Constructor
     *
     * @param id
     * @param color
     * @param timeForTheMeeting
     * @param meetingPlace
     * @param meetingParticipants
     */

    public Meeting(long id, int color, String meetingSubject, String timeForTheMeeting, String meetingPlace, String meetingParticipants, String date) {
        this.id = id;
        this.timeForTheMeeting = timeForTheMeeting;
        this.meetingPlace = meetingPlace;
        this.meetingSubject = meetingSubject;
        this.meetingParticipants = meetingParticipants;
        this.color = color;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public String getTimeForTheMeeting() {
        return timeForTheMeeting;
    }

    public String getMeetingPlace() {
        return meetingPlace;
    }

    public String getMeetingSubject() {
        return meetingSubject;
    }

    public String getMeetingParticipants() {
        return meetingParticipants;
    }

    public int getColor() {
        return color;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTimeForTheMeeting(String timeForTheMeeting) {
        this.timeForTheMeeting = timeForTheMeeting;
    }

    public void setMeetingPlace(String meetingPlace) {
        this.meetingPlace = meetingPlace;
    }

    public void setMeetingSubject(String meetingSubject) {
        this.meetingSubject = meetingSubject;
    }

    public void setMeetingParticipants(String meetingParticipants) {
        this.meetingParticipants = meetingParticipants;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Meeting meeting = (Meeting) obj;
        return Objects.equals(hashCode(), meeting);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(meetingPlace);
    }
}
