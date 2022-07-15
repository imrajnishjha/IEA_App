package com.example.ieaapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseAuth;

public class MemberProductAdapter extends FirebaseRecyclerAdapter<MemberProductModel,MemberProductAdapter.memberProductViewHolder>{


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MemberProductAdapter(@NonNull FirebaseRecyclerOptions<MemberProductModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull memberProductViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull MemberProductModel model) {

        holder.productName.setText(model.getProductTitle());
        Glide.with(holder.productImg.getContext())
                .load(model.getProductImageUrl())
                .placeholder(R.drawable.iea_logo)
                .error(R.drawable.iea_logo)
                .into(holder.productImg);

        holder.memberProductCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(model.getOwnerEmail().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail().toString().replaceAll("\\.","%7"))){
                    Intent i = new Intent(view.getContext(),memberProductedit.class);
                    i.putExtra("EditItemKey", getRef(position).getKey());
                    view.getContext().startActivity(i);
                    Log.d("Tag", "onClick: "+model.getOwnerEmail()+ FirebaseAuth.getInstance().getCurrentUser().getEmail().toString());
                }



            }
        });

    }

    @NonNull
    @Override
    public memberProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.member_product_item, parent, false);
        return new memberProductViewHolder(view);
    }

    class memberProductViewHolder extends RecyclerView.ViewHolder {

        ImageView productImg;
        TextView productName;
        View memberProductCardView;

        public memberProductViewHolder(@NonNull View itemView) {

            super(itemView);
            productImg = (ImageView) itemView.findViewById(R.id.memberProductImg);
            productName = (TextView) itemView.findViewById(R.id.memberProductName);
            memberProductCardView = itemView;
        }
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }
}