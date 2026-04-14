package com.flybuys.loyaltyapp.ui.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.flybuys.loyaltyapp.R;
import com.flybuys.loyaltyapp.analytics.AnalyticsEvent;
import com.flybuys.loyaltyapp.analytics.AnalyticsManager;
import com.flybuys.loyaltyapp.data.model.Transaction;
import com.flybuys.loyaltyapp.databinding.FragmentActivityBinding;
import com.flybuys.loyaltyapp.ui.adapter.TransactionAdapter;

import java.util.List;

public class ActivityFragment extends Fragment {

    private FragmentActivityBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentActivityBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<Transaction> transactions = Transaction.getMockTransactions();

        TransactionAdapter adapter = new TransactionAdapter(transactions);
        binding.rvTransactions.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvTransactions.setAdapter(adapter);

        binding.btnFilter.setOnClickListener(v -> {
            AnalyticsManager.getInstance().trackButtonClick("Filter", AnalyticsEvent.SCREEN_ACTIVITY);
            Toast.makeText(requireContext(), "Filter options", Toast.LENGTH_SHORT).show();
        });

        binding.swipeRefresh.setColorSchemeColors(
            requireContext().getColor(R.color.flybuys_blue)
        );
        binding.swipeRefresh.setOnRefreshListener(() -> {
            adapter.updateTransactions(Transaction.getMockTransactions());
            binding.swipeRefresh.setRefreshing(false);
        });

        AnalyticsManager.getInstance().trackScreenView(AnalyticsEvent.SCREEN_ACTIVITY);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
