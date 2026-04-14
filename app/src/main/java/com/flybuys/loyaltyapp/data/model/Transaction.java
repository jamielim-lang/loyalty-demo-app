package com.flybuys.loyaltyapp.data.model;

import java.util.ArrayList;
import java.util.List;

public class Transaction {
    private String id;
    private String date;
    private String storeName;
    private String storeLocation;
    private double amount;
    private int pointsEarned;
    private String type; // "earn" or "redeem"
    private String description;

    public Transaction(String id, String date, String storeName, String storeLocation,
                       double amount, int pointsEarned, String type, String description) {
        this.id = id;
        this.date = date;
        this.storeName = storeName;
        this.storeLocation = storeLocation;
        this.amount = amount;
        this.pointsEarned = pointsEarned;
        this.type = type;
        this.description = description;
    }

    public String getId() { return id; }
    public String getDate() { return date; }
    public String getStoreName() { return storeName; }
    public String getStoreLocation() { return storeLocation; }
    public double getAmount() { return amount; }
    public int getPointsEarned() { return pointsEarned; }
    public String getType() { return type; }
    public String getDescription() { return description; }

    public boolean isEarn() { return "earn".equals(type); }

    public static List<Transaction> getMockTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(
            "txn_001", "30 April 2025", "Coles", "Cfc Nsw",
            108.80, 108, "earn", "Grocery shopping"
        ));
        transactions.add(new Transaction(
            "txn_002", "28 April 2025", "Kmart", "Parramatta NSW",
            55.00, 55, "earn", "Retail purchase"
        ));
        transactions.add(new Transaction(
            "txn_003", "25 April 2025", "Officeworks", "Chatswood NSW",
            89.95, 90, "earn", "Office supplies"
        ));
        transactions.add(new Transaction(
            "txn_004", "22 April 2025", "Flybuys", "Redemption",
            0, -500, "redeem", "Flybuys Dollars redeemed"
        ));
        transactions.add(new Transaction(
            "txn_005", "20 April 2025", "Coles", "Bondi NSW",
            143.20, 143, "earn", "Weekly grocery shop"
        ));
        transactions.add(new Transaction(
            "txn_006", "18 April 2025", "Liquorland", "Manly NSW",
            45.00, 45, "earn", "Beverages"
        ));
        return transactions;
    }
}
