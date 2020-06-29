package com.dimitri.remoiville.mareu.meeting_list;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dimitri.remoiville.mareu.R;
import com.dimitri.remoiville.mareu.di.DI;
import com.dimitri.remoiville.mareu.event.DeleteMeetingEvent;
import com.dimitri.remoiville.mareu.model.Meeting;
import com.dimitri.remoiville.mareu.model.Room;
import com.dimitri.remoiville.mareu.service.MeetingApiService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Fragment representing a list of Reunions.
 */
public class MeetingFragment extends Fragment {

    private MeetingApiService mApiService;
    private RecyclerView mRecyclerView;
    private Context mContext;
    private List<Meeting> mMeetings;
    private final List<Meeting> mFilteredMeetingList = new ArrayList<>();
    // No filter = false / Filter = true
    private boolean mFilter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApiService = DI.getMeetingApiService();
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meeting_list, container, false);
        mContext = view.getContext();
        mRecyclerView = (RecyclerView) view;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        return view;
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mApiService.clearMeeting();
            defaultFilter();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        defaultFilter();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    private void initList(List<Meeting> meetings) {
        mRecyclerView.setAdapter(new MyMeetingRecyclerViewAdapter(meetings));
    }

    @Subscribe
    public void onDeleteMeeting(DeleteMeetingEvent event) {
        mApiService.deleteMeeting(event.Meeting);
        if (mFilter) {
            mFilteredMeetingList.remove(event.Meeting);
            filter();
        } else {
            defaultFilter();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_filter_by_room) {
            filterByRoom();
            return true;
        }
        if (id == R.id.action_filter_by_date) {
            filterByDate();
            return true;
        }
        if (id == R.id.action_filter_default) {
            defaultFilter();
        }
        return super.onOptionsItemSelected(item);
    }

    private void filterByDate() {
        mFilteredMeetingList.clear();
        final Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        final String[] pickedDate = new String[1];
        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                int realMonth = month + 1;
                pickedDate[0] = year
                        + String.format("%02d", realMonth)
                        + String.format("%02d", day);
                for (Meeting r: mMeetings) {
                    if (pickedDate[0].equals(r.getDate())) {
                        mFilteredMeetingList.add(r);
                    }
                }
                filter();
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    private void filterByRoom() {
        mFilteredMeetingList.clear();
        final List<Room> roomsList = mApiService.getRooms();
        final String[] listItems = new String[roomsList.size()];
        final int[] checkedItem = {0};
        final int[] mRoomPosition = new int[1];
        final Room[] pickedRoom = new Room[1];

        for (int i = 0; i < roomsList.size(); i++) {
            listItems[i] = roomsList.get(i).getName();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(R.string.txt_dialog_filter_by_room);
        builder.setSingleChoiceItems(listItems, checkedItem[0], new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int position) {
                mRoomPosition[0] = position;
            }
        });
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                checkedItem[0] = mRoomPosition[0];
                pickedRoom[0] = roomsList.get(mRoomPosition[0]);
                for (Meeting r: mMeetings) {
                    if (pickedRoom[0].equals(r.getRoom())) {
                        mFilteredMeetingList.add(r);
                    }
                }
                filter();
            }
        });
        builder.setNegativeButton(R.string.txt_cancel_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog mDialog = builder.create();
        mDialog.show();
    }

    private void defaultFilter() {
        mFilter = false;
        mMeetings = mApiService.getMeetings();
        initList(mMeetings);
    }

    private void filter() {
        mFilter = true;
        initList(mFilteredMeetingList);
    }
}