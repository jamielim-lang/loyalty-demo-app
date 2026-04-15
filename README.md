# Amplibuys — Loyalty App Analytics Demo

A fictional loyalty rewards app built to demonstrate [Amplitude Analytics](https://amplitude.com) capabilities. The app simulates a mobile loyalty experience (points, offers, wallet, redemptions) entirely in the browser — no backend, no real credentials, no affiliation with any real brand.

**Live demo:** https://jamielim-lang.github.io/loyalty-demo-app/

---

## What this is

Amplibuys is a single-page HTML/CSS/JS mockup styled as a mobile app inside a phone shell. It instruments Amplitude Analytics with:

- **Autocapture** — clicks, page views, and form interactions tracked automatically
- **Session Replay** — full DOM session recordings at 100% sample rate
- **Custom events** — named business events for every meaningful user action
- **User identity** — `setUserId` on login, `reset()` on logout
- **User properties** — points balance and activated offer count synced via `Identify`

---

## Architecture

```
web/
├── app.js          ← Amplitude SDK init + window.ampTrack helpers
├── index.html      ← Entire UI: screens, styles, JS state, event wiring
├── bundle.js       ← Compiled output (esbuild IIFE, committed for local dev)
├── package.json    ← npm dependencies (only @amplitude/unified)
└── node_modules/   ← gitignored

.github/
└── workflows/
    └── deploy.yml  ← CI: install → build → deploy to GitHub Pages
```

### Data flow

```
User interaction (click / form)
        │
        ▼
  index.html inline JS
  (navigate, doLogin, toggleActivate, etc.)
        │
        ▼
  window.ampTrack.*()          ← thin event helpers in app.js
        │
        ▼
  @amplitude/unified SDK
  ┌─────┴──────────────────────────────────┐
  │  amplitude.track(event, properties)    │  ← custom events
  │  amplitude.identify(identifyEvent)     │  ← user properties
  │  amplitude.setUserId(email)            │  ← identity
  │  amplitude.reset()                     │  ← logout / anonymous
  └─────┬──────────────────────────────────┘
        │  HTTP (sendBeacon / fetch)
        ▼
  Amplitude ingestion API
  ┌───────────────────┐
  │  Analytics data   │  ← events, user properties
  │  Session Replay   │  ← DOM snapshots (rrweb)
  └───────────────────┘
```

### Screen navigation

```
┌──────────────────────────────────────────────┐
│              Login overlay                   │
│  (shown on load, dismissed on valid login)   │
└────────────────────┬─────────────────────────┘
                     │ doLogin()
                     ▼
┌──────────────────────────────────────────────┐
│                 Phone shell                  │
│  ┌──────────────────────────────────────┐    │
│  │           Active screen              │    │
│  │                                      │    │
│  │  home ──► collect ──► wallet         │    │
│  │    │                    │            │    │
│  │    └──► redeem ◄───► activity        │    │
│  │              │                       │    │
│  │              └──► partners           │    │
│  └──────────────────────────────────────┘    │
│  [ Home ][ Collect ][ Wallet ][ Redeem ][ Activity ]  │
└──────────────────────────────────────────────┘
```

---

## Amplitude configuration

All Amplitude config lives in [`web/app.js`](web/app.js).

| Setting | Value | Notes |
|---|---|---|
| API key | `73fc93b12b686414b58014f53a33755b` | Project: Amplibuys Demo |
| SDK | `@amplitude/unified` | Bundles Analytics + Session Replay |
| `analytics.autocapture` | `true` | Tracks clicks, page views, form inputs automatically |
| `sessionReplay.sampleRate` | `1` | 100% of sessions recorded (demo setting — reduce in production) |

---

## Custom events

These are fired via `window.ampTrack.*()` helpers defined in `app.js`.

| Event name | Trigger | Properties |
|---|---|---|
| `login` | User submits the login form | `user_id` (email entered) |
| `logout` | User taps the profile icon | — |
| `screen_view` | Any screen becomes active | `screen_name` |
| `navigation` | User switches tabs | `from_screen`, `to_screen` |
| `offer_activated` | User taps Activate on an offer | `offer_id`, `store`, `badge` |
| `offer_deactivated` | User taps Deactivate on an active offer | `offer_id`, `store` |
| `offer_hidden` | User taps Hide on an offer | `offer_id`, `store` |
| `reward_tapped` | User taps a reward option | `reward_name` |
| `partner_tapped` | User taps a partner tile | `partner_name` |

### User properties (set via `Identify`)

| Property | Type | Set when |
|---|---|---|
| `points_balance` | number | Login, and after every offer activation/deactivation |
| `activated_offers` | number | Login, and after every offer activation/deactivation |

### Identity lifecycle

```
Page load
  └─► anonymous device ID (Amplitude default)

doLogin(email)
  └─► amplitude.setUserId(email)
      amplitude.track('login', { user_id: email })
      amplitude.identify({ points_balance, activated_offers })

doLogout()
  └─► amplitude.track('logout')
      amplitude.reset()           ← clears userId, generates new device ID
```

---

## App state & defaults

All state is in-memory (no persistence). Defaults on load:

| Field | Default | Description |
|---|---|---|
| `state.points` | `1278` | Starting points balance |
| `state.offers` | 3 offers | Coles 5x, Officeworks 3x, Kmart +200 |
| `state.offers[*].activated` | `false` | All offers start deactivated |
| `state.offers[*].hidden` | `false` | All offers start visible |
| `state.transactions` | 6 rows | Hardcoded transaction history |
| `state.partners.shopping` | 9 partners | Bunnings, Coles, First Choice, Kmart, Liquorland, Officeworks, Reddy Express, Swaggle, Target |
| `state.partners.financial` | 2 partners | HCF, Velocity Frequent Flyer |

**Points conversion:** 1 point = $0.005 (i.e. 200 points = $1.00)

**Offer activation bonus:** +50 points per activation (demo mechanic only)

### Demo login credentials

| Field | Value |
|---|---|
| Email | Any valid email format |
| Password | `amplibuys` |

---

## Screens

| Screen | Nav tab | Key interactions tracked |
|---|---|---|
| Home | Home | Points display, Choose reward → Redeem, View partners → Partners |
| Collect | Collect | Activate / Deactivate / Hide offers |
| Wallet | Wallet | Member card display (static) |
| Redeem | Redeem | Reward option taps, promotions |
| Activity | Activity | Transaction history (static) |
| Partners | (from Home banner) | Partner tile taps |

---

## Local development

### Prerequisites

- Node.js 20+
- Python 3 (for local HTTP server)

### Install & build

```bash
cd web
npm install
npx esbuild app.js --bundle --format=iife --outfile=bundle.js --platform=browser
```

### Serve locally

```bash
# From repo root
python3 -m http.server 3001 --directory web
# Open http://localhost:3001
```

Or use the Claude Code launch config (`.claude/launch.json`) which starts the server automatically in the preview panel.

---

## Deployment

Pushes to `main` trigger the [deploy workflow](.github/workflows/deploy.yml) automatically.

```
git push origin main
        │
        ▼
GitHub Actions (ubuntu-latest)
  1. actions/checkout@v4
  2. actions/setup-node@v4  (Node 20)
  3. npm install            (web/ directory)
  4. esbuild → bundle.js    (IIFE format, browser platform)
  5. Stage: cp index.html + bundle.js → dist/
  6. actions/upload-pages-artifact@v3
  7. actions/deploy-pages@v4
        │
        ▼
GitHub Pages → https://jamielim-lang.github.io/loyalty-demo-app/
```

Only `index.html` and `bundle.js` are deployed — `node_modules` never leaves CI.

All GitHub Actions are pinned to exact SHAs for supply chain security.

---

## Project structure

```
.
├── README.md
├── web/
│   ├── app.js              # Amplitude SDK init, window.ampTrack helpers
│   ├── index.html          # Complete single-page app (UI + state + JS)
│   ├── bundle.js           # Compiled bundle (committed; regenerated in CI)
│   └── package.json        # { "@amplitude/unified": "^1.x" }
├── .github/
│   └── workflows/
│       └── deploy.yml      # GitHub Pages CI/CD
├── .claude/
│   └── launch.json         # Claude Code dev server configs
└── app/                    # Original Android XML/Java prototype (reference only)
```

---

## Notes

- **No real data is collected.** The demo uses a real Amplitude project key for instrumentation purposes. Session Replay is at 100% sample rate — suitable for demos, not production.
- **No backend.** All state is in-memory JavaScript. Refreshing the page resets everything.
- **This is not affiliated with any real loyalty program.** Amplibuys is a fictional brand created solely for analytics demonstration.
