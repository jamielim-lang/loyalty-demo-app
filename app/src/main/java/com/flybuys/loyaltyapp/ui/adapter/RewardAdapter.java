package com.flybuys.loyaltyapp.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.flybuys.loyaltyapp.R;
import com.flybuys.loyaltyapp.data.model.Reward;

import java.util.List;

public class RewardAdapter extends RecyclerView.Adapter<RewardAdapter.ViewHolder> {

    public interface OnRewardClickListener {
        void onRewardClick(Reward reward);
    }

    private final List<Reward> rewards;
    private final OnRewardClickListener listener;

    public RewardAdapter(List<Reward> rewards, OnRewardClickListener listener) {
        this.rewards = rewards;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_reward_grid, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Reward reward = rewards.get(position);
        holder.bind(reward, listener);
    }

    @Override
    public int getItemCount() {
        return rewards.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvRewardTitle;

        ViewHolder(View itemView) {
            super(itemView);
            tvRewardTitle = itemView.findViewById(R.id.tv_reward_title);
        }

        void bind(Reward reward, OnRewardClickListener listener) {
            tvRewardTitle.setText(reward.getTitle());
            itemView.setOnClickListener(v -> listener.onRewardClick(reward));
            itemView.setContentDescription("Redeem: " + reward.getTitle().replace("\n", " "));
        }
    }
}
