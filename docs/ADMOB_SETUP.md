# AdMob setup — Chinar Prompt

The app uses Google's official Android Mobile Ads SDK and UMP consent SDK. The checked-in build defaults now point to the owner's real AdMob app and banner unit:

- App ID: `ca-app-pub-9824029664771146~9582260329`
- Banner unit: `ca-app-pub-9824029664771146/5643015311`

These IDs were created in the owner's AdMob account for **Chinar Prompt**. Do not share account passwords, payment information, signing keys, or API keys.

## Placement and behavior

A single real AdMob banner is shown beneath the reader navigation controls. It is hidden in distraction-free reading mode and does not cover prompt text, search, copy, bookmarks, or navigation. No interstitial or rewarded ad is used for ordinary prompt access.

Before requesting ads, the app requests current UMP consent information and shows a consent form when Google requires one. If consent cannot be obtained, the banner remains unavailable rather than blocking the app.

## AdMob readiness

The app was added to AdMob as an Android app that is not yet listed in a supported public app store. AdMob currently marks it **Requires review**. The new banner unit can take up to about one hour to begin serving. Normal serving and revenue still require a supported public store listing, linking that listing to this AdMob app, completion of privacy/Data Safety requirements, account verification, and Google's app-readiness review.

## Release checklist

1. Build using the real IDs already configured in `app/build.gradle.kts`.
2. Keep the UMP privacy and messaging configuration current in AdMob.
3. Install the release build and verify the banner on small screens and landscape orientation.
4. Never click your own live ads. Use Google's test IDs or a registered test device for any click/interactions testing.
5. Publish the app in a supported app store and link that public listing to AdMob. GitHub APK distribution alone does not satisfy AdMob app-readiness review.
6. Complete the store privacy/Data Safety declarations and any required `app-ads.txt` setup.
