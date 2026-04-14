package com.flybuys.loyaltyapp.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.flybuys.loyaltyapp.R;
import com.flybuys.loyaltyapp.data.model.Partner;

import java.util.List;

public class PartnerAdapter extends RecyclerView.Adapter<PartnerAdapter.ViewHolder> {

    public interface OnPartnerClickListener {
        void onPartnerClick(Partner partner);
    }

    private final List<Partner> partners;
    private final OnPartnerClickListener listener;

    public PartnerAdapter(List<Partner> partners, OnPartnerClickListener listener) {
        this.partners = partners;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_partner_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Partner partner = partners.get(position);
        holder.bind(partner, listener);
    }

    @Override
    public int getItemCount() {
        return partners.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvPartnerInitial;
        private final TextView tvPartnerName;

        ViewHolder(View itemView) {
            super(itemView);
            tvPartnerInitial = itemView.findViewById(R.id.tv_partner_initial);
            tvPartnerName = itemView.findViewById(R.id.tv_partner_name);
        }

        void bind(Partner partner, OnPartnerClickListener listener) {
            // Show first letter as placeholder logo
            tvPartnerInitial.setText(partner.getName().substring(0, 1));
            tvPartnerName.setText(partner.getName());
            itemView.setOnClickListener(v -> listener.onPartnerClick(partner));
            itemView.setContentDescription(partner.getName() + " partner");
        }
    }
}
