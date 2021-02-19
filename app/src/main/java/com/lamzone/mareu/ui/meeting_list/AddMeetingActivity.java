package com.lamzone.mareu.ui.meeting_list;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputLayout;
import com.lamzone.mareu.DI.Injection;
import com.lamzone.mareu.R;
import com.lamzone.mareu.model.Meeting;
import com.lamzone.mareu.service.MeetingApiService;

import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.text.format.DateFormat.format;
import static java.lang.System.currentTimeMillis;

public class AddMeetingActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.avatar_color)
    ImageView mColor;
    @BindView(R.id.meetingLytSubject)
    TextInputLayout meetingSubject;
    @BindView(R.id.meetingLytTime)
    TextInputLayout meetingTime;
    @BindView(R.id.rooms_meeting_selected)
    MaterialAutoCompleteTextView meetingPlace;
    @BindView(R.id.meetingLytParticipants)
    TextInputLayout participants;
    @BindView(R.id.date_meeting)
    TextInputLayout meetingDate;
    @BindView(R.id.create_meeting)
    AppCompatButton createMeeting;

    private MeetingApiService apiService;
    private List<String> roomsList;
    private int colors;
    private String color;
    private int year, month, day, mHour, mMinute;
    private List<String> mails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meeting);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        apiService = Injection.getMeetingApiService();

        meetingPlace.setOnClickListener(this);
        meetingDate.getEditText().setOnClickListener(this::onClick);
        meetingTime.getEditText().setOnClickListener(this::onClick);
        initMeeting();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initMeeting() {
        color = apiService.randomColors();
        colors = Color.parseColor(color);
        mColor.setColorFilter(colors);
        meetingSubject.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                createMeeting.setEnabled(s.length() > 0);
            }
        });
    }

    @OnClick(R.id.create_meeting)
    void addMeeting() {
        Meeting meeting = new Meeting(
                currentTimeMillis(),
                colors,
                meetingSubject.getEditText().getText().toString(),
                meetingTime.getEditText().getText().toString(),
                meetingPlace.getText().toString(),
                participants.getEditText().getText().toString(),
                meetingDate.getEditText().getText().toString()
        );

        if (meetingSubject.getEditText().getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Please Enter Subject", Toast.LENGTH_SHORT).show();
        } else if (meetingTime.getEditText().getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Please Enter Time", Toast.LENGTH_SHORT).show();
        } else if (meetingDate.getEditText().getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Please choose date", Toast.LENGTH_SHORT).show();
        } else if (!Pattern.matches("^((\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*)*([,] )*)*$", participants.getEditText().getText().toString())) {
            Toast.makeText(this , "Please enter Valid email" , Toast.LENGTH_SHORT).show();
        } else if (meetingPlace.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please choose rooms", Toast.LENGTH_SHORT).show();
        } else {
            apiService.createMeeting(meeting);
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        if (v == meetingDate.getEditText()) {
            calendar();
        } else if (v == meetingTime.getEditText()) {
            time();
        } else {
            configRooms();
        }
    }

    public void configRooms() {
        roomsList = apiService.getRooms();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, roomsList);
        meetingPlace.setAdapter(adapter);
    }

    public Calendar calendar() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        calendar.set(year, monthOfYear, dayOfMonth);
                        meetingDate.getEditText().setText(format("dd/MM/yyyy", calendar));
                    }
                }, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(currentTimeMillis());
        datePickerDialog.setTitle("Date of meeting");
        datePickerDialog.setIcon(R.drawable.ic_logo_lamezone);
        datePickerDialog.show();
        return calendar;
    }

    public Calendar time() {
        Calendar time = Calendar.getInstance();
        mHour = time.get(Calendar.HOUR_OF_DAY);
        mMinute = time.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minutes) {
                        String minPrecedes = "";
                        if (minutes < 10) {
                            minPrecedes = "0";
                        }
                        meetingTime.getEditText().setText(String.format("%dh%s%d", hourOfDay, minPrecedes, minutes));
                    }
                }, mHour, mMinute, true);
        timePickerDialog.show();
        return time;
    }
}