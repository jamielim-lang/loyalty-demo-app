package com.flybuys.loyaltyapp;

import com.flybuys.loyaltyapp.data.model.PointsBalance;

import org.junit.Test;
import static org.junit.Assert.*;

public class PointsBalanceTest {

    @Test
    public void pointsToDoollarsConversion_isCorrect() {
        PointsBalance balance = new PointsBalance(1278);
        assertEquals(1278, balance.getPoints());
        assertEquals(6.39, balance.getFlybuysDoollars(), 0.01);
    }

    @Test
    public void addPoints_updatesBalance() {
        PointsBalance balance = new PointsBalance(1000);
        balance.addPoints(200);
        assertEquals(1200, balance.getPoints());
        assertEquals(6.0, balance.getFlybuysDoollars(), 0.01);
    }

    @Test
    public void formattedPoints_includesCommas() {
        PointsBalance balance = new PointsBalance(1278);
        assertEquals("1,278", balance.getFormattedPoints());
    }

    @Test
    public void formattedDollars_includesDollarSign() {
        PointsBalance balance = new PointsBalance(1278);
        assertTrue(balance.getFormattedDollars().startsWith("$"));
    }
}
