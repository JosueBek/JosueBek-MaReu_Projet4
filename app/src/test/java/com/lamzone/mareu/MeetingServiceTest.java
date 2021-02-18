package com.lamzone.mareu;

import android.graphics.Color;

import com.lamzone.mareu.DI.Injection;
import com.lamzone.mareu.model.Meeting;
import com.lamzone.mareu.service.DummyMeetingGenerator;
import com.lamzone.mareu.service.MeetingApiService;

import org.hamcrest.Matchers;
import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit test on Meeting service
 */
public class MeetingServiceTest {

    private MeetingApiService service;

    @Before
    public void setup() {
        service = Injection.getNewInstanceApiService();
    }

    @Test
    public void getMeetingWithSuccess() {
        List<Meeting> meetings = service.getMeeting();
        List<Meeting> expectedMeeting = DummyMeetingGenerator.DUMMY_MEETINGS;
        contains(meetings, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedMeeting.toArray()));
    }

    @Test
    public void getRoomsWithSuccess() {
        List<String> rooms = service.getRooms();
        List<String> expectedRooms = DummyMeetingGenerator.DUMMY_ROOMS;
        contains(rooms, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedRooms.toArray()));
    }

    @Test
    public void createMeetingWithSuccess() {
        List<String> rooms = service.getRooms();
        String room = rooms.get(0);
        String colors = service.randomColors();
        int color = Color.parseColor(colors);
        Meeting meetingToAdd = new Meeting(System.currentTimeMillis(), color, "Réunion D", "18h00", room, "maxim@lamezone.com, luc@lamezon.com", "18/03/2021");
        Meeting meeting = new Meeting(System.currentTimeMillis(), color, "Réunion A", "14h00", room, "maxim@lamezone.com, luc@lamezon.com", "15/03/2021");
        service.createMeeting(meetingToAdd);
        service.createMeeting(meeting);
        contains(2, service.getMeeting().size());
        assertTrue(service.getMeeting().contains(meetingToAdd));
        assertFalse(service.getMeeting().contains(meeting));
    }

    @Test
    public void deleteMeetingWithSuccess() {
        Meeting meetingToDelete = service.getMeeting().get(0);
        service.deleteMeeting(meetingToDelete);
        assertFalse(service.getMeeting().contains(meetingToDelete));
    }
    @Test
    public void filterMeetingWithSuccess() {
        Meeting meeting = service.getMeeting().get(0);
        Meeting meeting1 = service.getMeeting().get(1);
        String room = meeting.getMeetingPlace();
        String date = meeting1.getDate();
        assertEquals(1,service.filterMeeting(room).size());
        Matchers.contains(1,service.filterMeeting(date).size());
        assertTrue( service.filterMeeting(room).contains(meeting));
        assertTrue( service.filterMeeting(date).contains(meeting1));
    }

    @Test
    public void getRandomColorWithSuccess() {
        String colors = service.randomColors();
        int color = Color.parseColor(colors);
        service.randomColors();
        contains(1,color);
    }
}