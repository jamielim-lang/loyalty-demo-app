import * as amplitude from '@amplitude/unified';
import { sessionReplayPlugin } from '@amplitude/plugin-session-replay-browser';

// Add session replay plugin before init
const sessionReplayTracking = sessionReplayPlugin({ sampleRate: 1 });
amplitude.add(sessionReplayTracking);

// Initialize Amplitude once, client-side only
amplitude.initAll('73fc93b12b686414b58014f53a33755b', {
  analytics: { autocapture: true }
});

// ── Custom event helpers (called by index.html inline JS) ──

window.ampTrack = {
  screenView(screenName) {
    amplitude.track('screen_view', { screen_name: screenName });
  },

  navigate(from, to) {
    amplitude.track('navigation', { from_screen: from, to_screen: to });
  },

  offerActivated(offerId, store, badge) {
    amplitude.track('offer_activated', { offer_id: offerId, store, badge });
  },

  offerDeactivated(offerId, store) {
    amplitude.track('offer_deactivated', { offer_id: offerId, store });
  },

  offerHidden(offerId, store) {
    amplitude.track('offer_hidden', { offer_id: offerId, store });
  },

  rewardTapped(rewardName) {
    amplitude.track('reward_tapped', { reward_name: rewardName });
  },

  partnerTapped(partnerName) {
    amplitude.track('partner_tapped', { partner_name: partnerName });
  },

  setUserProperties(points, activatedCount) {
    const identifyEvent = new amplitude.Identify();
    identifyEvent.set('points_balance', points);
    identifyEvent.set('activated_offers', activatedCount);
    amplitude.identify(identifyEvent);
  },

  login(userId) {
    amplitude.setUserId(userId);
    amplitude.track('login', { user_id: userId });
  },

  logout() {
    amplitude.track('logout');
    amplitude.reset();
  }
};
