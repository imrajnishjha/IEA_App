package com.example.ieaapp;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class MemberNotificationAdapter extends FirebaseRecyclerAdapter<MemberNotificationModel, MemberNotificationAdapter.MemberNotifcationViewHolder> {



    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MemberNotificationAdapter(@NonNull FirebaseRecyclerOptions<MemberNotificationModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MemberNotifcationViewHolder holder, int position, @NonNull MemberNotificationModel model) {
        holder.memberNotificationTitle.setText(model.getNotificationTitle());
        holder.memberNotificationContent.setText(model.getNotificationContent());
        holder.memberNotificationDate.setText(model.getNotificationDate());
    }

    @NonNull
    @Override
    public MemberNotifcationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.member_notification_item,parent,false);
        return new MemberNotifcationViewHolder(view);
    }

    class MemberNotifcationViewHolder extends RecyclerView.ViewHolder{
        View memberNotificationItem;
        TextView memberNotificationTitle, memberNotificationContent, memberNotificationDate;

        public MemberNotifcationViewHolder(@NonNull View itemView) {
            super(itemView);
            memberNotificationItem = itemView;
            memberNotificationTitle = itemView.findViewById(R.id.member_notification_item_title_tv);
            memberNotificationContent = itemView.findViewById(R.id.member_notification_item_content_tv);
            memberNotificationDate = itemView.findViewById(R.id.member_notification_item_date_tv);
        }
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }
}