package com.flybuys.loyaltyapp.data.model;

import java.util.ArrayList;
import java.util.List;

public class Reward {
    private String id;
    private String title;
    private String iconName;
    private String description;
    private int pointsRequired;

    public Reward(String id, String title, String iconName, String description, int pointsRequired) {
        this.id = id;
        this.title = title;
        this.iconName = iconName;
        this.description = description;
        this.pointsRequired = pointsRequired;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getIconName() { return iconName; }
    public String getDescription() { return description; }
    public int getPointsRequired() { return pointsRequired; }

    public static List<Reward> getMockRewards() {
        List<Reward> rewards = new ArrayList<>();
        rewards.add(new Reward("r_001", "Money off\nyour shop", "ic_basket", "Redeem at checkout", 2000));
        rewards.add(new Reward("r_002", "Gift Cards", "ic_gift_card", "Choose from top brands", 1000));
        rewards.add(new Reward("r_003", "Velocity\nPoints", "ic_velocity", "Transfer to Velocity", 500));
        rewards.add(new Reward("r_004", "Flybuys\nDollars", "ic_flybuys_dollars", "Convert to cash back", 200));
        rewards.add(new Reward("r_005", "Rewards\nStore", "ic_headphones", "Shop exclusive items", 500));
        rewards.add(new Reward("r_006", "Flybuys\nTravel", "ic_travel", "Book flights & hotels", 5000));
        return rewards;
    }
}
