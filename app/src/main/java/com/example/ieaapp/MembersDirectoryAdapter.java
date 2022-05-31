package com.example.ieaapp;

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

public class MembersDirectoryAdapter extends FirebaseRecyclerAdapter<MembersDirectoryModel, MembersDirectoryAdapter.MembersDirectoryViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MembersDirectoryAdapter(@NonNull FirebaseRecyclerOptions<MembersDirectoryModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MembersDirectoryViewHolder holder, int position, @NonNull MembersDirectoryModel model) {
        holder.name2.setText(model.getName2());
        holder.company.setText(model.getCompany());

        Glide.with(holder.members_dir_img.getContext())
                .load(model.getPurl2())
                .placeholder(R.drawable.iea_logo)
                .circleCrop()
                .error(R.drawable.iea_logo)
                .into(holder.members_dir_img);
    }

    @NonNull
    @Override
    public MembersDirectoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.members_directory_item,parent,false);
        return new MembersDirectoryViewHolder(view2);
    }

    class MembersDirectoryViewHolder extends RecyclerView.ViewHolder{

        ImageView members_dir_img;
        TextView name2, company;

        public MembersDirectoryViewHolder(@NonNull View itemView) {
            super(itemView);

            members_dir_img = (ImageView) itemView.findViewById(R.id.members_directory_profile_picture);
            name2 = (TextView) itemView.findViewById(R.id.members_directory_name);
            company = (TextView) itemView.findViewById(R.id.members_directory_company_name);
        }
    }
}
