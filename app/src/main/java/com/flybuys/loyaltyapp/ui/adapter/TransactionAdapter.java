package com.flybuys.loyaltyapp.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.flybuys.loyaltyapp.R;
import com.flybuys.loyaltyapp.data.model.Transaction;

import java.util.ArrayList;
import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_TRANSACTION = 0;
    private static final int VIEW_TYPE_FOOTER = 1;

    private List<Transaction> transactions;

    public TransactionAdapter(List<Transaction> transactions) {
        this.transactions = new ArrayList<>(transactions);
    }

    public void updateTransactions(List<Transaction> newTransactions) {
        this.transactions = new ArrayList<>(newTransactions);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return position == transactions.size() ? VIEW_TYPE_FOOTER : VIEW_TYPE_TRANSACTION;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_FOOTER) {
            View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_all_caught_up, parent, false);
            return new FooterViewHolder(view);
        }
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_transaction, parent, false);
        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TransactionViewHolder) {
            Transaction transaction = transactions.get(position);
            ((TransactionViewHolder) holder).bind(transaction, position);
        }
    }

    @Override
    public int getItemCount() {
        return transactions.size() + 1; // +1 for footer
    }

    class TransactionViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvDateHeader;
        private final TextView tvStoreName;
        private final TextView tvStoreLocation;
        private final TextView tvAmount;
        private final TextView tvPoints;

        TransactionViewHolder(View itemView) {
            super(itemView);
            tvDateHeader = itemView.findViewById(R.id.tv_date_header);
            tvStoreName = itemView.findViewById(R.id.tv_store_name);
            tvStoreLocation = itemView.findViewById(R.id.tv_store_location);
            tvAmount = itemView.findViewById(R.id.tv_amount);
            tvPoints = itemView.findViewById(R.id.tv_points);
        }

        void bind(Transaction transaction, int position) {
            tvStoreName.setText(transaction.getStoreName());
            tvStoreLocation.setText(transaction.getStoreLocation());

            if (transaction.getAmount() > 0) {
                tvAmount.setText(String.format("$%.2f", transaction.getAmount()));
            } else {
                tvAmount.setVisibility(View.GONE);
            }

            // Show date header if first item or date changed
            boolean showDate = position == 0
                || !transaction.getDate().equals(transactions.get(position - 1).getDate());
            tvDateHeader.setVisibility(showDate ? View.VISIBLE : View.GONE);
            tvDateHeader.setText(transaction.getDate());

            // Points display
            int points = transaction.getPointsEarned();
            if (points > 0) {
                tvPoints.setText("+" + points);
                tvPoints.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.points_green));
            } else {
                tvPoints.setText(String.valueOf(points));
                tvPoints.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.flybuys_red));
            }

            itemView.setContentDescription(
                transaction.getStoreName() + ", " + transaction.getDate() + ", " +
                (points > 0 ? "+" + points : points) + " points"
            );
        }
    }

    static class FooterViewHolder extends RecyclerView.ViewHolder {
        FooterViewHolder(View itemView) {
            super(itemView);
        }
    }
}
