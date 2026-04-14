package com.flybuys.loyaltyapp.data.model;

public class User {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String memberId;
    private int pointsBalance;
    private double flybuysDoollarsBalance;
    private String memberTier;
    private String[] preferredStores;

    public User(String id, String firstName, String lastName, String email,
                String memberId, int pointsBalance, double flybuysDoollarsBalance,
                String memberTier, String[] preferredStores) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.memberId = memberId;
        this.pointsBalance = pointsBalance;
        this.flybuysDoollarsBalance = flybuysDoollarsBalance;
        this.memberTier = memberTier;
        this.preferredStores = preferredStores;
    }

    public String getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getMemberId() { return memberId; }
    public int getPointsBalance() { return pointsBalance; }
    public void setPointsBalance(int pointsBalance) { this.pointsBalance = pointsBalance; }
    public double getFlybuysDoollarsBalance() { return flybuysDoollarsBalance; }
    public String getMemberTier() { return memberTier; }
    public String[] getPreferredStores() { return preferredStores; }

    public String getDisplayName() {
        return firstName + " " + lastName;
    }

    public static User getMockUser() {
        return new User(
            "user_001",
            "Jamie",
            "Smith",
            "jamie.smith@email.com",
            "1234 5678 9012",
            1278,
            6.39,
            "Standard",
            new String[]{"Coles", "Kmart", "Officeworks"}
        );
    }
}
