package com.flybuys.loyaltyapp.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.flybuys.loyaltyapp.R;
import com.flybuys.loyaltyapp.data.model.Offer;

import java.util.List;

public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.ViewHolder> {

    public interface OnActivateClickListener {
        void onActivate(Offer offer);
    }

    public interface OnHideClickListener {
        void onHide(Offer offer);
    }

    private List<Offer> offers;
    private final OnActivateClickListener activateListener;
    private final OnHideClickListener hideListener;

    public OfferAdapter(List<Offer> offers,
                        OnActivateClickListener activateListener,
                        OnHideClickListener hideListener) {
        this.offers = offers;
        this.activateListener = activateListener;
        this.hideListener = hideListener;
    }

    public void updateOffers(List<Offer> newOffers) {
        this.offers = newOffers;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_offer_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Offer offer = offers.get(position);
        holder.bind(offer, activateListener, hideListener);
    }

    @Override
    public int getItemCount() {
        return offers.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvStoreLogo;
        private final TextView tvBadgeMultiplier;
        private final TextView tvOfferTitle;
        private final TextView tvOfferDescription;
        private final TextView tvExpiry;
        private final ImageView ivExpiryIcon;
        private final Button btnActivate;
        private final Button btnHide;

        ViewHolder(View itemView) {
            super(itemView);
            tvStoreLogo = itemView.findViewById(R.id.tv_store_logo);
            tvBadgeMultiplier = itemView.findViewById(R.id.tv_badge_multiplier);
            tvOfferTitle = itemView.findViewById(R.id.tv_offer_title);
            tvOfferDescription = itemView.findViewById(R.id.tv_offer_description);
            tvExpiry = itemView.findViewById(R.id.tv_expiry);
            ivExpiryIcon = itemView.findViewById(R.id.iv_expiry_icon);
            btnActivate = itemView.findViewById(R.id.btn_activate);
            btnHide = itemView.findViewById(R.id.btn_hide);
        }

        void bind(Offer offer, OnActivateClickListener activateListener, OnHideClickListener hideListener) {
            tvStoreLogo.setText(offer.getStoreName().toLowerCase());
            tvBadgeMultiplier.setText(offer.getBonusPoints() > 10
                ? "+" + offer.getBonusPoints()
                : offer.getBonusPoints() + "x");
            tvOfferTitle.setText(offer.getTitle());
            tvOfferDescription.setText(offer.getDescription());
            tvExpiry.setText(offer.getExpiryText());

            // Set expiry color
            if (offer.isExpiringSoon()) {
                tvExpiry.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.offer_expiry_red));
                ivExpiryIcon.setImageResource(R.drawable.ic_clock);
            } else {
                tvExpiry.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.offer_expiry_orange));
                ivExpiryIcon.setImageResource(R.drawable.ic_clock_orange);
            }

            // Update button state based on activation
            if (offer.isActivated()) {
                btnActivate.setText("Deactivate");
                btnActivate.setBackgroundTintList(
                    ContextCompat.getColorStateList(itemView.getContext(), R.color.status_success)
                );
            } else {
                btnActivate.setText("Activate");
                btnActivate.setBackgroundTintList(
                    ContextCompat.getColorStateList(itemView.getContext(), R.color.flybuys_blue)
                );
            }

            btnActivate.setOnClickListener(v -> activateListener.onActivate(offer));
            btnHide.setOnClickListener(v -> hideListener.onHide(offer));

            itemView.setContentDescription("Offer: " + offer.getTitle() + " from " + offer.getStoreName());
        }
    }
}
