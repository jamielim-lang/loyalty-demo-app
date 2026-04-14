package com.flybuys.loyaltyapp.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.flybuys.loyaltyapp.R;
import com.flybuys.loyaltyapp.data.model.Promotion;

import java.util.List;

public class PromotionAdapter extends RecyclerView.Adapter<PromotionAdapter.ViewHolder> {

    public interface OnPromotionClickListener {
        void onPromotionClick(Promotion promotion);
    }

    private final List<Promotion> promotions;
    private final OnPromotionClickListener listener;

    public PromotionAdapter(List<Promotion> promotions, OnPromotionClickListener listener) {
        this.promotions = promotions;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_promotion_tile, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Promotion promo = promotions.get(position);
        holder.bind(promo, listener);
    }

    @Override
    public int getItemCount() {
        return promotions.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvPromoTitle;
        private final TextView tvStoreBadge;

        ViewHolder(View itemView) {
            super(itemView);
            tvPromoTitle = itemView.findViewById(R.id.tv_promo_title);
            tvStoreBadge = itemView.findViewById(R.id.tv_store_badge);
        }

        void bind(Promotion promo, OnPromotionClickListener listener) {
            tvPromoTitle.setText(promo.getTitle());
            tvStoreBadge.setText(promo.getStoreName());
            itemView.setOnClickListener(v -> listener.onPromotionClick(promo));
            itemView.setContentDescription("Promotion: " + promo.getTitle());
        }
    }
}
