package com.flybuys.loyaltyapp.ui.wallet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.flybuys.loyaltyapp.analytics.AnalyticsEvent;
import com.flybuys.loyaltyapp.analytics.AnalyticsManager;
import com.flybuys.loyaltyapp.data.model.PointsBalance;
import com.flybuys.loyaltyapp.data.model.User;
import com.flybuys.loyaltyapp.databinding.FragmentWalletBinding;

public class WalletFragment extends Fragment {

    private FragmentWalletBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWalletBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        User user = User.getMockUser();
        PointsBalance balance = new PointsBalance(user.getPointsBalance());

        binding.tvMemberNumber.setText(user.getMemberId());
        binding.tvCardPoints.setText(balance.getFormattedPoints());

        binding.cardAddCard.setOnClickListener(v ->
            AnalyticsManager.getInstance().trackButtonClick("Add Card", AnalyticsEvent.SCREEN_WALLET)
        );

        AnalyticsManager.getInstance().trackScreenView(AnalyticsEvent.SCREEN_WALLET);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
