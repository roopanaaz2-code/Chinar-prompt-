# AdMob setup — Chinar Prompt

The app currently uses Google's official Android Mobile Ads SDK and UMP consent SDK. Development builds default to Google's test app ID and banner unit ID:

- App ID: `ca-app-pub-3940256099942544~3347511713`
- Banner test unit: `ca-app-pub-3940256099942544/9214589741`

These are test IDs only. They do not represent the owner's AdMob account and do not generate revenue.

## Configure real IDs

Do not commit real IDs if the repository policy requires them to remain outside source control. Supply them as Gradle properties when building:

```bash
./gradlew assembleRelease \
  -PADMOB_APP_ID=ca-app-pub-XXXXXXXXXXXXXXXX~YYYYYYYYYY \
  -PADMOB_BANNER_ID=ca-app-pub-XXXXXXXXXXXXXXXX/ZZZZZZZZZZ
```

The same properties can be placed in a local, uncommitted `~/.gradle/gradle.properties` file. The manifest app ID and the banner unit are separate values.

## Placement and behavior

A single banner is shown beneath the reader navigation controls. It is hidden in distraction-free reading mode and does not cover prompt text, search, copy, bookmarks, or navigation. No interstitial or rewarded ad is used for ordinary prompt access.

Before requesting ads, the app requests current UMP consent information and shows a consent form when Google requires one. If consent cannot be obtained, the banner remains unavailable rather than blocking the app.

## Release checklist

1. Configure the real AdMob app ID and banner unit ID locally.
2. Create the appropriate privacy and messaging configuration in AdMob.
3. Test with Google's test IDs or a registered test device. Never click test or live ads.
4. Verify the banner does not cover reader controls on small screens and landscape orientation.
5. Publish the app in a supported app store and link that public listing to AdMob before expecting normal ad serving or revenue. GitHub APK distribution alone does not satisfy AdMob app-readiness review.
6. Complete the store privacy/Data safety declarations and any required app-ads.txt setup.
