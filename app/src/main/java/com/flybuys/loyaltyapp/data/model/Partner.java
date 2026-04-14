package com.flybuys.loyaltyapp.data.model;

import java.util.ArrayList;
import java.util.List;

public class Partner {
    private String id;
    private String name;
    private String category;
    private int logoResId; // drawable resource id placeholder

    public Partner(String id, String name, String category, int logoResId) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.logoResId = logoResId;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public int getLogoResId() { return logoResId; }

    public static List<Partner> getMockPartners() {
        List<Partner> partners = new ArrayList<>();
        // Shopping partners
        partners.add(new Partner("p_001", "Bunnings Warehouse", "Shopping", 0));
        partners.add(new Partner("p_002", "Coles", "Shopping", 0));
        partners.add(new Partner("p_003", "First Choice Liquor Market", "Shopping", 0));
        partners.add(new Partner("p_004", "Kmart", "Shopping", 0));
        partners.add(new Partner("p_005", "Liquorland", "Shopping", 0));
        partners.add(new Partner("p_006", "Officeworks", "Shopping", 0));
        partners.add(new Partner("p_007", "Reddy Express", "Shopping", 0));
        partners.add(new Partner("p_008", "Swaggle", "Shopping", 0));
        partners.add(new Partner("p_009", "Target", "Shopping", 0));
        // Financial partners
        partners.add(new Partner("p_010", "HCF", "Financial", 0));
        partners.add(new Partner("p_011", "Velocity Frequent Flyer", "Travel", 0));
        return partners;
    }
}
