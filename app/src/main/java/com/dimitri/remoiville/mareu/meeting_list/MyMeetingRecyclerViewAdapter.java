package com.dimitri.remoiville.mareu.meeting_list;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.dimitri.remoiville.mareu.R;
import com.dimitri.remoiville.mareu.databinding.FragmentMeetingBinding;
import com.dimitri.remoiville.mareu.event.DeleteMeetingEvent;
import com.dimitri.remoiville.mareu.model.Meeting;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Meeting}.
 */
public class MyMeetingRecyclerViewAdapter extends RecyclerView.Adapter<MyMeetingRecyclerViewAdapter.ViewHolder> {

    private final List<Meeting> mMeetings;

    public MyMeetingRecyclerViewAdapter(List<Meeting> items) {
        mMeetings = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentMeetingBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Meeting meeting = mMeetings.get(position);
        String name = meeting.getName() + " - " + meeting.getTime() + " - " + meeting.getRoom().getName();
        holder.fragmentMeetingBinding.itemListMeetingName.setText(name);
        StringBuilder eMailsList= new StringBuilder();
        for (int i = 0; i < meeting.getParticipants().size(); i++){
            eMailsList.append(meeting.getParticipants().get(i).getEmail());
            if (i != meeting.getParticipants().size() - 1){
                eMailsList.append(", ");
            }
        }
        holder.fragmentMeetingBinding.itemListMeetingParticipant.setText(eMailsList.toString());
        Drawable mDrawable = ContextCompat.getDrawable(holder.itemView.getContext(),R.drawable.round_50dp);
        mDrawable = mDrawable.mutate();
        mDrawable = DrawableCompat.wrap(mDrawable);
        DrawableCompat.setTint(mDrawable, Color.parseColor(meeting.getRoom().getColor()));
        holder.fragmentMeetingBinding.itemListMeetingImage.setImageDrawable(mDrawable);

        holder.fragmentMeetingBinding.itemListDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new DeleteMeetingEvent(meeting));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMeetings.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private FragmentMeetingBinding fragmentMeetingBinding;

        public ViewHolder(FragmentMeetingBinding fragmentMeetingBinding) {
            super(fragmentMeetingBinding.getRoot());
            this.fragmentMeetingBinding = fragmentMeetingBinding;
        }
    }
}