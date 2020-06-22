package com.dimitri.remoiville.mareu.reunion_list;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dimitri.remoiville.mareu.R;
import com.dimitri.remoiville.mareu.di.DI;
import com.dimitri.remoiville.mareu.model.Reunion;
import com.dimitri.remoiville.mareu.service.ReunionApiService;

import java.util.List;

/**
 * Fragment representing a list of Reunions.
 */
public class ReunionFragment extends Fragment {

    private ReunionApiService mApiService;
    private RecyclerView mRecyclerView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ReunionFragment() {
    }

    /**
     * Create and return a new instance of Reunion Fragment
     * @return
     */
    public static ReunionFragment newInstance() {
        return new ReunionFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApiService = DI.getReunionApiService();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reunion_list, container, false);
        Context context = view.getContext();
        mRecyclerView = (RecyclerView) view;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initList();
    }

    private void initList() {
        List<Reunion> reunions = mApiService.getReunions();
        mRecyclerView.setAdapter(new MyReunionRecyclerViewAdapter(reunions));
    }
}