package com.dimitri.remoiville.mareu.reunion_list;

import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.dimitri.remoiville.mareu.event.DeleteReunionEvent;
import com.dimitri.remoiville.mareu.model.Reunion;
import com.dimitri.remoiville.mareu.R;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Reunion}.
 */
public class MyReunionRecyclerViewAdapter extends RecyclerView.Adapter<MyReunionRecyclerViewAdapter.ViewHolder> {

    private final List<Reunion> mReunions;

    public MyReunionRecyclerViewAdapter(List<Reunion> items) {
        mReunions = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_reunion, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Reunion reunion = mReunions.get(position);
        holder.mReunionName.setText(reunion.getName() +
                " - " + reunion.getTime() + " - " + reunion.getRoom().getName());
        String eMailsList= "";
        for (int i = 0; i < reunion.getParticipants().size(); i++){
            eMailsList = eMailsList + reunion.getParticipants().get(i).getEmail();
            if (i != reunion.getParticipants().size() - 1){
                eMailsList = eMailsList + ", ";
            }
        }
        holder.mReunionParticipants.setText(eMailsList);
        Drawable mDrawable = ContextCompat.getDrawable(holder.itemView.getContext(),R.drawable.round_50dp);
        mDrawable = mDrawable.mutate();
        mDrawable = DrawableCompat.wrap(mDrawable);
        DrawableCompat.setTint(mDrawable, Color.parseColor(reunion.getRoom().getColor()));
        holder.mReunionImage.setImageDrawable(mDrawable);

        holder.mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new DeleteReunionEvent(reunion));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mReunions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mReunionImage;
        public TextView mReunionName;
        public TextView mReunionParticipants;
        public ImageButton mDeleteButton;

        public ViewHolder(View view) {
            super(view);
            mReunionImage = view.findViewById(R.id.item_list_reunion_image);
            mReunionName = view.findViewById(R.id.item_list_reunion_name);
            mReunionParticipants = view.findViewById(R.id.item_list_reunion_participant);
            mDeleteButton = view.findViewById(R.id.item_list_delete_button);
        }
    }
}