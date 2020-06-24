package com.dimitri.remoiville.mareu.reunion_list;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.dimitri.remoiville.mareu.R;
import com.dimitri.remoiville.mareu.di.DI;
import com.dimitri.remoiville.mareu.event.DeleteReunionEvent;
import com.dimitri.remoiville.mareu.model.Reunion;
import com.dimitri.remoiville.mareu.model.Room;
import com.dimitri.remoiville.mareu.service.ReunionApiService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Fragment representing a list of Reunions.
 */
public class ReunionFragment extends Fragment {

    private ReunionApiService mApiService;
    private RecyclerView mRecyclerView;
    private Context mContext;
    private List<Reunion> mReunions;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ReunionFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApiService = DI.getReunionApiService();
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reunion_list, container, false);
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
            //TODO
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

    private void initList(List<Reunion> reunions) {
        mRecyclerView.setAdapter(new MyReunionRecyclerViewAdapter(reunions));
    }

    @Subscribe
    public void onDeleteReunion(DeleteReunionEvent event) {
        mApiService.deleteReunion(event.Reunion);
        initList(mReunions);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_filter_by_room) {
            final List<Room> roomsList = mApiService.getRooms();
            final List<Reunion> filteredList = new ArrayList<>();
            final String[] listItems = new String[roomsList.size()];
            final int[] checkedItem = {0};
            final int[] mRoomPosition = new int[1];
            final Room[] pickedRoom = new Room[1];

            for (int i = 0; i < roomsList.size(); i++) {
                listItems[i] = roomsList.get(i).getName();
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle("Sélectionner la salle à filtrer");
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
                    for (Reunion r: mReunions) {
                        if (pickedRoom[0].equals(r.getRoom())) {
                            filteredList.add(r);
                        }
                    }
                    initList(filteredList);
                }
            });
            builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            AlertDialog mDialog = builder.create();
            mDialog.show();
            return true;
        }
        if (id == R.id.action_filter_by_date) {
            final List<Reunion> filteredList = new ArrayList<>();
            final Calendar cldr = Calendar.getInstance();
            int day = cldr.get(Calendar.DAY_OF_MONTH);
            int month = cldr.get(Calendar.MONTH);
            int year = cldr.get(Calendar.YEAR);
            final String[] pickedDate = new String[1];
            DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    int realMonth = month + 1;
                    pickedDate[0] = year
                            + String.format("%02d", realMonth)
                            + String.format("%02d", day);
                    for (Reunion r: mReunions) {
                        if (pickedDate[0].equals(r.getDate())) {
                            filteredList.add(r);
                        }
                    }
                    initList(filteredList);
                }
            }, year, month, day);
            datePickerDialog.show();
            return true;
        }
        if (id == R.id.action_filter_default) {
            defaultFilter();
        }
        return super.onOptionsItemSelected(item);
    }

    private void defaultFilter() {
        mReunions = mApiService.getReunions();
        initList(mReunions);
    }
}