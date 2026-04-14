package com.flybuys.loyaltyapp.analytics;

/**
 * Type-safe analytics event constants for Flybuys app.
 * All Amplitude event names and property keys are centralized here.
 */
public final class AnalyticsEvent {

    private AnalyticsEvent() {}

    // --- Screen View Events ---
    public static final String SCREEN_VIEW = "screen_view";
    public static final String SCREEN_HOME = "Home";
    public static final String SCREEN_COLLECT = "Collect";
    public static final String SCREEN_WALLET = "Wallet";
    public static final String SCREEN_REDEEM = "Redeem";
    public static final String SCREEN_ACTIVITY = "Activity";
    public static final String SCREEN_PARTNERS = "Partners";
    public static final String SCREEN_OFFER_DETAIL = "Offer Detail";
    public static final String SCREEN_REWARD_DETAIL = "Reward Detail";

    // --- Navigation Events ---
    public static final String NAVIGATION_TAB_SELECTED = "navigation_tab_selected";

    // --- Offer Events ---
    public static final String OFFER_ACTIVATED = "offer_activated";
    public static final String OFFER_DEACTIVATED = "offer_deactivated";
    public static final String OFFER_HIDDEN = "offer_hidden";
    public static final String OFFER_VIEWED = "offer_viewed";
    public static final String OFFERS_VIEW_ALL = "offers_view_all_tapped";

    // --- Redemption Events ---
    public static final String POINTS_REDEEMED = "points_redeemed";
    public static final String REWARD_SELECTED = "reward_selected";
    public static final String CHOOSE_REWARD_TAPPED = "choose_reward_tapped";

    // --- Button Click Events ---
    public static final String BUTTON_CLICK = "button_click";

    // --- Promotion Events ---
    public static final String PROMOTION_TAPPED = "promotion_tapped";
    public static final String PROMOTION_VIEWED = "promotion_viewed";

    // --- Partner Events ---
    public static final String PARTNER_TAPPED = "partner_tapped";

    // --- Session Events ---
    public static final String APP_OPENED = "app_opened";
    public static final String APP_BACKGROUNDED = "app_backgrounded";

    // --- Filter Events ---
    public static final String ACTIVITY_FILTER_APPLIED = "activity_filter_applied";

    // --- Property Keys ---
    public static final class Property {
        private Property() {}

        public static final String SCREEN_NAME = "screen_name";
        public static final String TAB_NAME = "tab_name";
        public static final String OFFER_ID = "offer_id";
        public static final String OFFER_STORE = "offer_store";
        public static final String OFFER_TITLE = "offer_title";
        public static final String OFFER_CATEGORY = "offer_category";
        public static final String OFFER_BONUS_POINTS = "offer_bonus_points";
        public static final String REWARD_ID = "reward_id";
        public static final String REWARD_TYPE = "reward_type";
        public static final String POINTS_AMOUNT = "points_amount";
        public static final String PARTNER_ID = "partner_id";
        public static final String PARTNER_NAME = "partner_name";
        public static final String PARTNER_CATEGORY = "partner_category";
        public static final String PROMOTION_ID = "promotion_id";
        public static final String PROMOTION_TITLE = "promotion_title";
        public static final String BUTTON_NAME = "button_name";
        public static final String FILTER_TYPE = "filter_type";
        public static final String FILTER_VALUE = "filter_value";
        public static final String PREVIOUS_SCREEN = "previous_screen";
    }

    // --- User Property Keys ---
    public static final class UserProperty {
        private UserProperty() {}

        public static final String POINTS_BALANCE = "points_balance";
        public static final String MEMBER_TIER = "member_tier";
        public static final String PREFERRED_STORES = "preferred_stores";
        public static final String MEMBER_ID = "member_id";
        public static final String ACTIVATED_OFFERS_COUNT = "activated_offers_count";
        public static final String AVAILABLE_OFFERS_COUNT = "available_offers_count";
    }
}
