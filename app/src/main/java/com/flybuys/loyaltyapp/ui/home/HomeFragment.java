package com.flybuys.loyaltyapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.flybuys.loyaltyapp.R;
import com.flybuys.loyaltyapp.analytics.AnalyticsEvent;
import com.flybuys.loyaltyapp.analytics.AnalyticsManager;
import com.flybuys.loyaltyapp.data.model.Offer;
import com.flybuys.loyaltyapp.data.model.PointsBalance;
import com.flybuys.loyaltyapp.data.model.Promotion;
import com.flybuys.loyaltyapp.data.model.User;
import com.flybuys.loyaltyapp.databinding.FragmentHomeBinding;
import com.flybuys.loyaltyapp.ui.adapter.PromotionAdapter;

import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private User currentUser;
    private PointsBalance pointsBalance;
    private List<Offer> offers;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        currentUser = User.getMockUser();
        pointsBalance = new PointsBalance(currentUser.getPointsBalance());
        offers = Offer.getMockOffers();

        setupGreeting();
        setupPointsBalance();
        setupPromotions();
        setupOfferCounts();
        setupClickListeners();

        // Track screen view
        AnalyticsManager.getInstance().trackScreenView(AnalyticsEvent.SCREEN_HOME);

        // Setup pull-to-refresh
        binding.swipeRefresh.setColorSchemeColors(
            requireContext().getColor(R.color.flybuys_blue),
            requireContext().getColor(R.color.flybuys_red)
        );
        binding.swipeRefresh.setOnRefreshListener(() -> {
            refreshData();
            binding.swipeRefresh.setRefreshing(false);
        });
    }

    private void setupGreeting() {
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        String greeting;
        if (hour < 12) {
            greeting = getString(R.string.greeting_morning, currentUser.getFirstName());
        } else if (hour < 17) {
            greeting = getString(R.string.greeting_afternoon, currentUser.getFirstName());
        } else {
            greeting = getString(R.string.greeting_evening, currentUser.getFirstName());
        }
        binding.tvGreeting.setText(greeting);
    }

    private void setupPointsBalance() {
        binding.tvPointsBalance.setText(pointsBalance.getFormattedPoints());
        binding.tvPointsValue.setText("worth " + pointsBalance.getFormattedDollars() + " Flybuys Dollars");
    }

    private void setupPromotions() {
        List<Promotion> promotions = Promotion.getMockPromotions();
        PromotionAdapter adapter = new PromotionAdapter(promotions, promotion -> {
            AnalyticsManager.getInstance().trackPromotionTapped(
                promotion.getId(), promotion.getTitle()
            );
        });
        binding.rvPromotions.setLayoutManager(
            new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        );
        binding.rvPromotions.setAdapter(adapter);
    }

    private void setupOfferCounts() {
        long available = offers.stream().filter(o -> !o.isActivated()).count();
        long activated = offers.stream().filter(Offer::isActivated).count();
        binding.tvAvailableCount.setText(String.valueOf(available));
        binding.tvActivatedCount.setText(String.valueOf(activated));
        AnalyticsManager.getInstance().updateOfferCounts((int) available, (int) activated);
    }

    private void setupClickListeners() {
        binding.ivProfile.setOnClickListener(v -> {
            AnalyticsManager.getInstance().trackButtonClick("Profile", AnalyticsEvent.SCREEN_HOME);
        });

        binding.btnChooseReward.setOnClickListener(v -> {
            AnalyticsManager.getInstance().trackButtonClick("Choose Reward", AnalyticsEvent.SCREEN_HOME);
            // Navigate to Redeem tab
            requireActivity().findViewById(R.id.navigation_redeem).performClick();
        });

        binding.cardAvailableOffers.setOnClickListener(v -> {
            AnalyticsManager.getInstance().trackButtonClick("Available Offers", AnalyticsEvent.SCREEN_HOME);
            // Navigate to Collect tab
            requireActivity().findViewById(R.id.navigation_collect).performClick();
        });

        binding.cardActivatedOffers.setOnClickListener(v -> {
            AnalyticsManager.getInstance().trackButtonClick("Activated Offers", AnalyticsEvent.SCREEN_HOME);
            requireActivity().findViewById(R.id.navigation_collect).performClick();
        });

        binding.cardPartnersPromo.setOnClickListener(v -> {
            AnalyticsManager.getInstance().trackButtonClick("View Partners", AnalyticsEvent.SCREEN_HOME);
            Navigation.findNavController(v).navigate(R.id.action_home_to_partners);
        });
    }

    private void refreshData() {
        setupPointsBalance();
        setupOfferCounts();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
