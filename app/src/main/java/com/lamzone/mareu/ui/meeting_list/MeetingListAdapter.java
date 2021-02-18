package com.lamzone.mareu.ui.meeting_list;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lamzone.mareu.R;
import com.lamzone.mareu.events.DeleteMeetingEvent;
import com.lamzone.mareu.model.Meeting;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class MeetingListAdapter extends RecyclerView.Adapter<ListMeetingViewHolder> {

    private List<Meeting> meetings;

    public MeetingListAdapter(List<Meeting> items) {
        this.meetings = items;
    }

    @NonNull
    @Override
    public ListMeetingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.fragment_meeting, parent,false);
        return new ListMeetingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListMeetingViewHolder holder, int position) {
        Meeting meeting = meetings.get(position);
        holder.meetingParticipants.setText(meeting.getMeetingParticipants());
        holder.color.setColorFilter(meeting.getColor());
        holder.meetingName.setText(String.format("%s - %s - %s", meeting.getMeetingSubject(), meeting
                .getTimeForTheMeeting(), meeting
                .getMeetingPlace()));

        holder.detailsMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                String[] items = new String[]{"SUBJECT : " + meeting.getMeetingSubject(), "TIME : " + meeting.getTimeForTheMeeting(), "ROOMS : " + meeting.getMeetingPlace(), "DATE : " + meeting.getDate()};
                builder.setTitle("Meeting");
                builder.setIcon(R.drawable.ic_logo_lamezone);
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String maReu = items[which];
                        Toast.makeText(context, " You just clicked " + maReu, Toast.LENGTH_SHORT).show();
                    }
                }).setCancelable(true).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                     dialog.dismiss();
                   }
               });

                builder.show();
            }
        });

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new DeleteMeetingEvent(meeting));
            }
        });
    }

    public void updateListMeting(List<Meeting> newList) {
        meetings = new ArrayList<>();
        meetings.addAll(newList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return meetings.size();
    }
}