package com.flybuys.loyaltyapp.ui.partner;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.flybuys.loyaltyapp.analytics.AnalyticsEvent;
import com.flybuys.loyaltyapp.analytics.AnalyticsManager;
import com.flybuys.loyaltyapp.data.model.Partner;
import com.flybuys.loyaltyapp.databinding.FragmentPartnerBinding;
import com.flybuys.loyaltyapp.ui.adapter.PartnerAdapter;

import java.util.List;
import java.util.stream.Collectors;

public class PartnerFragment extends Fragment {

    private FragmentPartnerBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPartnerBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<Partner> allPartners = Partner.getMockPartners();

        // Shopping partners
        List<Partner> shoppingPartners = allPartners.stream()
            .filter(p -> "Shopping".equals(p.getCategory()))
            .collect(Collectors.toList());

        PartnerAdapter shoppingAdapter = new PartnerAdapter(shoppingPartners, partner -> {
            AnalyticsManager.getInstance().trackPartnerTapped(partner);
        });
        binding.rvShoppingPartners.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        binding.rvShoppingPartners.setAdapter(shoppingAdapter);

        // Financial partners
        List<Partner> financialPartners = allPartners.stream()
            .filter(p -> "Financial".equals(p.getCategory()) || "Travel".equals(p.getCategory()))
            .collect(Collectors.toList());

        if (financialPartners.isEmpty()) {
            binding.tvFinancialHeader.setVisibility(View.GONE);
            binding.rvFinancialPartners.setVisibility(View.GONE);
        } else {
            PartnerAdapter financialAdapter = new PartnerAdapter(financialPartners, partner -> {
                AnalyticsManager.getInstance().trackPartnerTapped(partner);
            });
            binding.rvFinancialPartners.setLayoutManager(new GridLayoutManager(requireContext(), 2));
            binding.rvFinancialPartners.setAdapter(financialAdapter);
        }

        AnalyticsManager.getInstance().trackScreenView(AnalyticsEvent.SCREEN_PARTNERS);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
