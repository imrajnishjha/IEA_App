package com.example.ieaapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class MyGrievancesAdapter extends FirebaseRecyclerAdapter<MyGrievanceModel, MyGrievancesAdapter.MyGrievanceViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MyGrievancesAdapter(@NonNull FirebaseRecyclerOptions<MyGrievanceModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyGrievanceViewHolder holder, int position, @NonNull MyGrievanceModel model) {
        holder.myGrievanceDept.setText(model.getDepartment());
        holder.myGrievanceDesc.setText(model.getComplain());
        holder.myGrievanceStatus.setText(model.getStatus());

    }

    @NonNull
    @Override
    public MyGrievanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_grievance_item, parent,false);
        return new MyGrievanceViewHolder(view);
    }

    class MyGrievanceViewHolder extends RecyclerView.ViewHolder{

        View myGrievanceView;
        TextView myGrievanceDept, myGrievanceDesc, myGrievanceStatus;

        public MyGrievanceViewHolder(@NonNull View itemView) {
            super(itemView);

            myGrievanceDept = (TextView) itemView.findViewById(R.id.my_grievances_dept_tv);
            myGrievanceDesc = (TextView) itemView.findViewById(R.id.my_grievances_desc_tv);
            myGrievanceStatus = (TextView) itemView.findViewById(R.id.my_grievances_status_tv);
            myGrievanceView = itemView;
        }
    }
}
