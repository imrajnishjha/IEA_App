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
    protected void onBindViewHolder(@NonNull memberProductViewHolder holder, int position, @NonNull MemberProductModel model) {

        holder.productName.setText(model.getProductTitle());
        Glide.with(holder.productImg.getContext())
                .load(model.getProductImageUrl())
                .placeholder(R.drawable.iea_logo)
                .error(R.drawable.iea_logo)
                .into(holder.productImg);

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