package com.flybuys.loyaltyapp.analytics;

import android.content.Context;
import android.util.Log;

import com.amplitude.android.Amplitude;
import com.amplitude.android.Configuration;
import com.amplitude.core.events.Identify;
import com.flybuys.loyaltyapp.data.model.Offer;
import com.flybuys.loyaltyapp.data.model.Partner;
import com.flybuys.loyaltyapp.data.model.Reward;
import com.flybuys.loyaltyapp.data.model.User;

import org.json.JSONObject;

/**
 * Singleton AnalyticsManager for Amplitude event tracking.
 *
 * Usage:
 *   AnalyticsManager.getInstance().trackScreenView(AnalyticsEvent.SCREEN_HOME);
 */
public class AnalyticsManager {

    private static final String TAG = "AnalyticsManager";
    // Replace with your actual Amplitude API key
    private static final String AMPLITUDE_API_KEY = "YOUR_AMPLITUDE_API_KEY";

    private static volatile AnalyticsManager instance;
    private Amplitude amplitude;
    private boolean isInitialized = false;

    private AnalyticsManager() {}

    public static AnalyticsManager getInstance() {
        if (instance == null) {
            synchronized (AnalyticsManager.class) {
                if (instance == null) {
                    instance = new AnalyticsManager();
                }
            }
        }
        return instance;
    }

    /**
     * Initialize Amplitude SDK. Call this from Application.onCreate().
     */
    public void initialize(Context context) {
        if (isInitialized) return;
        try {
            amplitude = new Amplitude(new Configuration(
                AMPLITUDE_API_KEY,
                context.getApplicationContext()
            ));
            isInitialized = true;
            Log.d(TAG, "Amplitude initialized successfully");
        } catch (Exception e) {
            Log.e(TAG, "Failed to initialize Amplitude: " + e.getMessage());
        }
    }

    // --- Screen Tracking ---

    public void trackScreenView(String screenName) {
        JSONObject props = new JSONObject();
        try {
            props.put(AnalyticsEvent.Property.SCREEN_NAME, screenName);
        } catch (Exception ignored) {}
        track(AnalyticsEvent.SCREEN_VIEW, props);
        Log.d(TAG, "Screen view: " + screenName);
    }

    // --- Navigation Tracking ---

    public void trackNavigation(String tabName, String previousScreen) {
        JSONObject props = new JSONObject();
        try {
            props.put(AnalyticsEvent.Property.TAB_NAME, tabName);
            props.put(AnalyticsEvent.Property.PREVIOUS_SCREEN, previousScreen);
        } catch (Exception ignored) {}
        track(AnalyticsEvent.NAVIGATION_TAB_SELECTED, props);
    }

    // --- Offer Tracking ---

    public void trackOfferActivated(Offer offer) {
        JSONObject props = buildOfferProperties(offer);
        track(AnalyticsEvent.OFFER_ACTIVATED, props);
        Log.d(TAG, "Offer activated: " + offer.getId());
    }

    public void trackOfferDeactivated(Offer offer) {
        JSONObject props = buildOfferProperties(offer);
        track(AnalyticsEvent.OFFER_DEACTIVATED, props);
    }

    public void trackOfferHidden(Offer offer) {
        JSONObject props = buildOfferProperties(offer);
        track(AnalyticsEvent.OFFER_HIDDEN, props);
    }

    public void trackOfferViewed(Offer offer) {
        JSONObject props = buildOfferProperties(offer);
        track(AnalyticsEvent.OFFER_VIEWED, props);
    }

    private JSONObject buildOfferProperties(Offer offer) {
        JSONObject props = new JSONObject();
        try {
            props.put(AnalyticsEvent.Property.OFFER_ID, offer.getId());
            props.put(AnalyticsEvent.Property.OFFER_STORE, offer.getStoreName());
            props.put(AnalyticsEvent.Property.OFFER_TITLE, offer.getTitle());
            props.put(AnalyticsEvent.Property.OFFER_CATEGORY, offer.getCategory());
            props.put(AnalyticsEvent.Property.OFFER_BONUS_POINTS, offer.getBonusPoints());
        } catch (Exception ignored) {}
        return props;
    }

    // --- Redemption Tracking ---

    public void trackPointsRedeemed(Reward reward, int pointsAmount) {
        JSONObject props = new JSONObject();
        try {
            props.put(AnalyticsEvent.Property.REWARD_ID, reward.getId());
            props.put(AnalyticsEvent.Property.REWARD_TYPE, reward.getTitle());
            props.put(AnalyticsEvent.Property.POINTS_AMOUNT, pointsAmount);
        } catch (Exception ignored) {}
        track(AnalyticsEvent.POINTS_REDEEMED, props);
        Log.d(TAG, "Points redeemed: " + pointsAmount + " for " + reward.getTitle());
    }

    public void trackRewardSelected(Reward reward) {
        JSONObject props = new JSONObject();
        try {
            props.put(AnalyticsEvent.Property.REWARD_ID, reward.getId());
            props.put(AnalyticsEvent.Property.REWARD_TYPE, reward.getTitle());
        } catch (Exception ignored) {}
        track(AnalyticsEvent.REWARD_SELECTED, props);
    }

    // --- Button Click Tracking ---

    public void trackButtonClick(String buttonName, String screenName) {
        JSONObject props = new JSONObject();
        try {
            props.put(AnalyticsEvent.Property.BUTTON_NAME, buttonName);
            props.put(AnalyticsEvent.Property.SCREEN_NAME, screenName);
        } catch (Exception ignored) {}
        track(AnalyticsEvent.BUTTON_CLICK, props);
    }

    // --- Partner Tracking ---

    public void trackPartnerTapped(Partner partner) {
        JSONObject props = new JSONObject();
        try {
            props.put(AnalyticsEvent.Property.PARTNER_ID, partner.getId());
            props.put(AnalyticsEvent.Property.PARTNER_NAME, partner.getName());
            props.put(AnalyticsEvent.Property.PARTNER_CATEGORY, partner.getCategory());
        } catch (Exception ignored) {}
        track(AnalyticsEvent.PARTNER_TAPPED, props);
    }

    // --- Promotion Tracking ---

    public void trackPromotionTapped(String promotionId, String promotionTitle) {
        JSONObject props = new JSONObject();
        try {
            props.put(AnalyticsEvent.Property.PROMOTION_ID, promotionId);
            props.put(AnalyticsEvent.Property.PROMOTION_TITLE, promotionTitle);
        } catch (Exception ignored) {}
        track(AnalyticsEvent.PROMOTION_TAPPED, props);
    }

    // --- Activity Filter Tracking ---

    public void trackActivityFilterApplied(String filterType, String filterValue) {
        JSONObject props = new JSONObject();
        try {
            props.put(AnalyticsEvent.Property.FILTER_TYPE, filterType);
            props.put(AnalyticsEvent.Property.FILTER_VALUE, filterValue);
        } catch (Exception ignored) {}
        track(AnalyticsEvent.ACTIVITY_FILTER_APPLIED, props);
    }

    // --- User Properties ---

    public void setUserProperties(User user) {
        if (!isInitialized || amplitude == null) return;
        try {
            Identify identify = new Identify();
            identify.set(AnalyticsEvent.UserProperty.POINTS_BALANCE, user.getPointsBalance());
            identify.set(AnalyticsEvent.UserProperty.MEMBER_TIER, user.getMemberTier());
            identify.set(AnalyticsEvent.UserProperty.MEMBER_ID, user.getMemberId());
            amplitude.identify(identify);
            amplitude.setUserId(user.getId());
        } catch (Exception e) {
            Log.e(TAG, "Failed to set user properties: " + e.getMessage());
        }
    }

    public void updatePointsBalance(int newBalance) {
        if (!isInitialized || amplitude == null) return;
        try {
            Identify identify = new Identify();
            identify.set(AnalyticsEvent.UserProperty.POINTS_BALANCE, newBalance);
            amplitude.identify(identify);
        } catch (Exception e) {
            Log.e(TAG, "Failed to update points balance: " + e.getMessage());
        }
    }

    public void updateOfferCounts(int available, int activated) {
        if (!isInitialized || amplitude == null) return;
        try {
            Identify identify = new Identify();
            identify.set(AnalyticsEvent.UserProperty.AVAILABLE_OFFERS_COUNT, available);
            identify.set(AnalyticsEvent.UserProperty.ACTIVATED_OFFERS_COUNT, activated);
            amplitude.identify(identify);
        } catch (Exception e) {
            Log.e(TAG, "Failed to update offer counts: " + e.getMessage());
        }
    }

    // --- Core track method ---

    private void track(String eventName, JSONObject properties) {
        if (!isInitialized || amplitude == null) {
            Log.w(TAG, "Analytics not initialized. Skipping event: " + eventName);
            return;
        }
        try {
            amplitude.track(eventName, properties);
        } catch (Exception e) {
            Log.e(TAG, "Failed to track event " + eventName + ": " + e.getMessage());
        }
    }

    public void flush() {
        if (isInitialized && amplitude != null) {
            amplitude.flush();
        }
    }
}
