package com.lamzone.mareu.ui.meeting_list;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.lamzone.mareu.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListMeetingViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.item_list_avatar)
    public ImageView color;
    @BindView(R.id.item_list_name)
    public TextView meetingName;
    @BindView(R.id.item_list_delete_button)
    public ImageButton deleteButton;
    @BindView(R.id.item_list_meeting_participants)
    public TextView meetingParticipants;
    @BindView(R.id.Layout_detail_meeting)
    public ConstraintLayout detailsMeeting;

    public ListMeetingViewHolder(@NonNull View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
