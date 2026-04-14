package com.flybuys.loyaltyapp.ui.collect;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.flybuys.loyaltyapp.R;
import com.flybuys.loyaltyapp.analytics.AnalyticsEvent;
import com.flybuys.loyaltyapp.analytics.AnalyticsManager;
import com.flybuys.loyaltyapp.data.model.Offer;
import com.flybuys.loyaltyapp.databinding.FragmentCollectBinding;
import com.flybuys.loyaltyapp.ui.adapter.OfferAdapter;

import java.util.List;

public class CollectFragment extends Fragment {

    private FragmentCollectBinding binding;
    private List<Offer> offers;
    private OfferAdapter offerAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCollectBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        offers = Offer.getMockOffers();

        setupOffersSection();
        setupClickListeners();
        setupSwipeRefresh();

        AnalyticsManager.getInstance().trackScreenView(AnalyticsEvent.SCREEN_COLLECT);
    }

    private void setupOffersSection() {
        long count = offers.stream().filter(o -> !o.isActivated()).count();
        binding.tvOffersHeader.setText("Offers just for you  " + count);

        offerAdapter = new OfferAdapter(offers,
            offer -> {
                // Activate
                offer.setActivated(true);
                offerAdapter.notifyDataSetChanged();
                updateOfferHeader();
                AnalyticsManager.getInstance().trackOfferActivated(offer);
            },
            offer -> {
                // Hide/Deactivate
                offers.remove(offer);
                offerAdapter.notifyDataSetChanged();
                updateOfferHeader();
                AnalyticsManager.getInstance().trackOfferHidden(offer);
            }
        );

        binding.rvOffers.setLayoutManager(
            new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        );
        binding.rvOffers.setAdapter(offerAdapter);
    }

    private void updateOfferHeader() {
        long count = offers.stream().filter(o -> !o.isActivated()).count();
        binding.tvOffersHeader.setText("Offers just for you  " + count);
        AnalyticsManager.getInstance().updateOfferCounts(
            (int) offers.stream().filter(o -> !o.isActivated()).count(),
            (int) offers.stream().filter(Offer::isActivated).count()
        );
    }

    private void setupClickListeners() {
        binding.tvViewAll.setOnClickListener(v -> {
            AnalyticsManager.getInstance().trackButtonClick("View All Offers", AnalyticsEvent.SCREEN_COLLECT);
        });
    }

    private void setupSwipeRefresh() {
        binding.swipeRefresh.setColorSchemeColors(
            requireContext().getColor(R.color.flybuys_blue),
            requireContext().getColor(R.color.flybuys_red)
        );
        binding.swipeRefresh.setOnRefreshListener(() -> {
            offers = Offer.getMockOffers();
            offerAdapter.updateOffers(offers);
            updateOfferHeader();
            binding.swipeRefresh.setRefreshing(false);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
