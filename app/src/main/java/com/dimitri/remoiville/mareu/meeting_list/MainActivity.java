package com.dimitri.remoiville.mareu.meeting_list;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.dimitri.remoiville.mareu.R;
import com.dimitri.remoiville.mareu.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private MeetingFragment mMeetingFragment;
    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);
        setSupportActionBar(mBinding.toolbar);

        mMeetingFragment = new MeetingFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.container, mMeetingFragment).commit();

        mBinding.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddMeetingActivity.navigate(MainActivity.this);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}