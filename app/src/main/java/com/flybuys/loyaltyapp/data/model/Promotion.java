package com.flybuys.loyaltyapp.data.model;

import java.util.ArrayList;
import java.util.List;

public class Promotion {
    private String id;
    private String title;
    private String subtitle;
    private String storeName;
    private String imageRes;
    private String actionText;
    private String type; // "competition", "bonus", "partner"

    public Promotion(String id, String title, String subtitle, String storeName,
                     String imageRes, String actionText, String type) {
        this.id = id;
        this.title = title;
        this.subtitle = subtitle;
        this.storeName = storeName;
        this.imageRes = imageRes;
        this.actionText = actionText;
        this.type = type;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getSubtitle() { return subtitle; }
    public String getStoreName() { return storeName; }
    public String getImageRes() { return imageRes; }
    public String getActionText() { return actionText; }
    public String getType() { return type; }

    public static List<Promotion> getMockPromotions() {
        List<Promotion> promotions = new ArrayList<>();
        promotions.add(new Promotion(
            "promo_001", "European Glassware", "Collect and win", "Coles",
            "promo_glassware", "Shop now", "competition"
        ));
        promotions.add(new Promotion(
            "promo_002", "Golden Coke Can", "WIN 200,000 POINTS", "Coles",
            "promo_coke", "Enter now", "competition"
        ));
        promotions.add(new Promotion(
            "promo_003", "Points Millionaire", "Enter now", "Velocity",
            "promo_velocity", "Enter now", "partner"
        ));
        promotions.add(new Promotion(
            "promo_004", "HCF Offer", "Earn bonus points", "HCF",
            "promo_hcf", "Learn more", "partner"
        ));
        return promotions;
    }
}
