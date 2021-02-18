package com.lamzone.mareu.service;

import com.lamzone.mareu.model.Meeting;

import java.util.List;

/**
 * Meeting API client
 */
public interface MeetingApiService {

    /**
     * Get all my Meeting
     * @return {@link List}
     */
    List<Meeting> getMeeting();

    /**
     * Get all my Rooms
     * @return {@link List}
     */
    List<String> getRooms();

    /**
     * Create a meeting
     * @param meeting
     */
    void createMeeting (Meeting meeting);

    /**
     * Deletes a Meeting
     * @param meeting
     */
    void deleteMeeting (Meeting meeting);

    /**
     * Filter a meeting date and room
     * @param newText
     */
    List<Meeting> filterMeeting (String newText);

    /**
     * Generate a color Meeting
     */
    String randomColors ();
}
