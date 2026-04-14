package com.flybuys.loyaltyapp.data.model;

public class PointsBalance {
    private int points;
    private double flybuysDoollars;
    private static final double POINTS_TO_DOLLARS_RATE = 0.005; // 200 points = $1

    public PointsBalance(int points) {
        this.points = points;
        this.flybuysDoollars = points * POINTS_TO_DOLLARS_RATE;
    }

    public int getPoints() { return points; }

    public void setPoints(int points) {
        this.points = points;
        this.flybuysDoollars = points * POINTS_TO_DOLLARS_RATE;
    }

    public void addPoints(int additionalPoints) {
        this.points += additionalPoints;
        this.flybuysDoollars = this.points * POINTS_TO_DOLLARS_RATE;
    }

    public double getFlybuysDoollars() { return flybuysDoollars; }

    public String getFormattedPoints() {
        return String.format("%,d", points);
    }

    public String getFormattedDollars() {
        return String.format("$%.2f", flybuysDoollars);
    }
}
