package com.flybuys.loyaltyapp.ui.redeem;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.flybuys.loyaltyapp.analytics.AnalyticsEvent;
import com.flybuys.loyaltyapp.analytics.AnalyticsManager;
import com.flybuys.loyaltyapp.data.model.Reward;
import com.flybuys.loyaltyapp.databinding.FragmentRedeemBinding;
import com.flybuys.loyaltyapp.ui.adapter.RewardAdapter;

import java.util.List;

public class RedeemFragment extends Fragment {

    private FragmentRedeemBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRedeemBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupRewardsGrid();
        setupClickListeners();

        binding.tvPromotionsHeader.setText("Promotions  1");

        AnalyticsManager.getInstance().trackScreenView(AnalyticsEvent.SCREEN_REDEEM);
    }

    private void setupRewardsGrid() {
        List<Reward> rewards = Reward.getMockRewards();
        RewardAdapter adapter = new RewardAdapter(rewards, reward -> {
            AnalyticsManager.getInstance().trackRewardSelected(reward);
            Toast.makeText(requireContext(),
                "Selected: " + reward.getTitle().replace("\n", " "),
                Toast.LENGTH_SHORT).show();
        });

        // 3-column grid layout
        binding.rvRewards.setLayoutManager(new GridLayoutManager(requireContext(), 3));
        binding.rvRewards.setAdapter(adapter);
    }

    private void setupClickListeners() {
        binding.bannerBunnings.setOnClickListener(v -> {
            AnalyticsManager.getInstance().trackButtonClick("Bunnings Banner", AnalyticsEvent.SCREEN_REDEEM);
            AnalyticsManager.getInstance().trackPromotionTapped("bunnings_promo", "Bunnings Points Redemption");
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
