package com.lamzone.mareu.ui.meeting_list;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lamzone.mareu.DI.Injection;
import com.lamzone.mareu.R;
import com.lamzone.mareu.events.DeleteMeetingEvent;
import com.lamzone.mareu.model.Meeting;
import com.lamzone.mareu.service.MeetingApiService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.text.format.DateFormat.format;

public class MeetingActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    @BindView(R.id.list_meeting_recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private MeetingApiService apiService;
    private MeetingListAdapter adapter;
    private List<Meeting> meetings;
    private List<String> roomsList;
    private int year, month, day;
    private String selectedChoice;
    private String item;
    private androidx.appcompat.widget.SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        apiService = Injection.getMeetingApiService();
        configureRecyclerView();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_meeting_filter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_filter:
                showOptionsDialogueChoice();
                Toast.makeText(this, "Menu Filter", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_search:
                searchView = (androidx.appcompat.widget.SearchView) item.getActionView();
                searchView.setOnQueryTextListener(this);
                Toast.makeText(this, "Search Filter", Toast.LENGTH_SHORT).show();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void configureRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
    }

    private void loadData() {
        meetings = apiService.getMeeting();
        adapter = new MeetingListAdapter(meetings);
        recyclerView.setAdapter(adapter);
    }

    @OnClick(R.id.activity_add_meeting)
    void addMeeting() {
        Context context = getApplicationContext();
        Intent intent = new Intent(context, AddMeetingActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        adapter.updateListMeting(apiService.filterMeeting(query));
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.updateListMeting(apiService.filterMeeting(newText));
        return false;
    }

    private void showOptionsDialogueChoice() {
        String[] choice = {"Date", "Rooms"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("choose filter");
        builder.setSingleChoiceItems(choice, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectedChoice = choice[which];
                Toast.makeText(getApplicationContext(), "You clicked: "+ selectedChoice, Toast.LENGTH_SHORT).show();
            }
        })
       .setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (selectedChoice == null) {
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Please choose filter", Toast.LENGTH_SHORT).show();
                } else if (selectedChoice.indexOf("Rooms", 0) == 0) {
                    configAdapterRooms();
                } else {
                    calendar();
                }
            }
        });
        builder.setNeutralButton("No Filter", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                adapter.updateListMeting(apiService.filterMeeting(""));
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    public void configAdapterRooms() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        roomsList = apiService.getRooms();
        ArrayAdapter<String> adapterRooms = new ArrayAdapter<>(this, android.R.layout.select_dialog_item, roomsList );
        builder.setAdapter(adapterRooms,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                item = roomsList.get(which);
                adapter.updateListMeting(apiService.filterMeeting(item));
                Toast.makeText(MeetingActivity.this, "You Choose " + item, Toast.LENGTH_SHORT).show();
            }
        });
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            apiService.getMeeting().clear();
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
            onRestart();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();

        }
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
                        calendar.set(year, monthOfYear, dayOfMonth );
                        Toast.makeText(getApplicationContext(), format("dd/MM/yyyy",calendar), Toast.LENGTH_SHORT).show();
                        adapter.updateListMeting(apiService.filterMeeting(String.valueOf(format("dd/MM/yyyy",calendar))));
                    }
                }, year, month, day);
        datePickerDialog.show();
        return calendar;
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onDeleteMeeting(DeleteMeetingEvent event) {
        apiService.deleteMeeting(event.meeting);
        loadData();
    }
}