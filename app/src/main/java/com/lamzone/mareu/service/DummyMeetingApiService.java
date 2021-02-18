package com.lamzone.mareu.service;

import com.lamzone.mareu.model.Meeting;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DummyMeetingApiService implements MeetingApiService {

    private final List<Meeting> meetings = DummyMeetingGenerator.generateMeetings();
    private final List<String> rooms = DummyMeetingGenerator.generateRooms();
    private final List<String> meetingColors = DummyMeetingGenerator.generateColors();

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Meeting> getMeeting() {
        return meetings;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getRooms() {
        return rooms;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String randomColors() {
        Random random = new Random();
        int rand = random.nextInt(meetingColors.size());
        return meetingColors.get(rand);
    }

    /**
     * {@inheritDoc}
     * @param meeting
     */
    @Override
    public void createMeeting(Meeting meeting) {
        boolean available = false;
       for (Meeting m : meetings) {
           if (meeting.getTimeForTheMeeting().equals(m.getTimeForTheMeeting()) && meeting.getMeetingPlace().equals(m.getMeetingPlace())) {
               available = true;
               break;
           }
       }
       if(!available) {
           meetings.add(meeting);
       }
    }

    /**
     * {@inheritDoc}
     * @param meeting
     */
    @Override
    public void deleteMeeting(Meeting meeting) {
        meetings.remove(meeting);
    }

    /**
     * {@inheritDoc}
     * @param newText
     */

    @Override
    public List<Meeting> filterMeeting(String newText) {
        String dateMeeting = newText.toLowerCase();
        String rooMeeting = newText.trim();
        List<Meeting> newList = new ArrayList<>();
        for (Meeting m : meetings) {
            if (m.getDate().contains(dateMeeting) || m.getMeetingPlace().contains(rooMeeting) ) {
                newList.add(m);
            }
        }
        return newList;
    }
}
