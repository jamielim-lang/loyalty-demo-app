package com.flybuys.loyaltyapp.data.model;

import java.util.ArrayList;
import java.util.List;

public class Offer {
    private String id;
    private String storeName;
    private String title;
    private String description;
    private String badgeText;
    private String expiryText;
    private boolean isExpiringSoon;
    private boolean isActivated;
    private int bonusPoints;
    private String imageUrl;
    private String logoUrl;
    private String category;

    public Offer(String id, String storeName, String title, String description,
                 String badgeText, String expiryText, boolean isExpiringSoon,
                 boolean isActivated, int bonusPoints, String imageUrl,
                 String logoUrl, String category) {
        this.id = id;
        this.storeName = storeName;
        this.title = title;
        this.description = description;
        this.badgeText = badgeText;
        this.expiryText = expiryText;
        this.isExpiringSoon = isExpiringSoon;
        this.isActivated = isActivated;
        this.bonusPoints = bonusPoints;
        this.imageUrl = imageUrl;
        this.logoUrl = logoUrl;
        this.category = category;
    }

    public String getId() { return id; }
    public String getStoreName() { return storeName; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getBadgeText() { return badgeText; }
    public String getExpiryText() { return expiryText; }
    public boolean isExpiringSoon() { return isExpiringSoon; }
    public boolean isActivated() { return isActivated; }
    public void setActivated(boolean activated) { isActivated = activated; }
    public int getBonusPoints() { return bonusPoints; }
    public String getImageUrl() { return imageUrl; }
    public String getLogoUrl() { return logoUrl; }
    public String getCategory() { return category; }

    public static List<Offer> getMockOffers() {
        List<Offer> offers = new ArrayList<>();
        offers.add(new Offer(
            "offer_001",
            "Coles",
            "Collect 5x points",
            "on every transaction in-store or online at Coles.",
            "5x POINTS",
            "Ends today",
            true,
            false,
            5,
            "coles_offer",
            "coles_logo",
            "Grocery"
        ));
        offers.add(new Offer(
            "offer_002",
            "Officeworks",
            "Collect 3x points",
            "when you spend $50 or more at Officeworks.",
            "3x POINTS",
            "3 days left",
            false,
            false,
            3,
            "officeworks_offer",
            "officeworks_logo",
            "Office"
        ));
        offers.add(new Offer(
            "offer_003",
            "Kmart",
            "200 bonus points",
            "when you spend $40 or more at Kmart.",
            "200 BONUS",
            "5 days left",
            false,
            false,
            200,
            "kmart_offer",
            "kmart_logo",
            "Retail"
        ));
        return offers;
    }
}
