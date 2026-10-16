# 🍁 Chinar Prompts — The Modern AI Prompt Book

[![Android CI](https://github.com/actions/workflows/build-apk.yml/badge.svg)](https://github.com)
[![Platform](https://img.shields.io/badge/Platform-Android%207.0%2B%20%28API%2024%E2%80%9336%29-blue.svg)](https://developer.android.com)
[![Language](https://img.shields.io/badge/Language-Kotlin-purple.svg)](https://kotlinlang.org)
[![UI](https://img.shields.io/badge/UI-Jetpack%20Compose%20%2B%20Material%203-green.svg)](https://developer.android.com/jetpack/compose)

An animated, premium digital ebook and reference reader for **"The Modern AI Prompt Book"**, featuring all 28 original pages, 119 curated image & video prompts, Kashmir Chinar tree royal aesthetics, and 3D page-curling physics.

---

## 📱 Features

* **Complete 28-Page Original Book**:
  * **Page 1 (Cover)**: Royal Chinar artwork, subtitle, book statistics, and quick navigation.
  * **Page 2 (Guide & Contents I)**: *How to Use This Book*, *Reusable Quality Add-On*, and categories 1–6.
  * **Page 3 (Contents II)**: Categories 7–12 with direct page jump links.
  * **Pages 4–27 (119 Prompts)**: All 119 prompts across 12 categories (*Miniature Worlds, Architecture & Interiors, Cinematic Portraits, Nature & Wildlife, Futuristic & Sci-Fi, Food & Product, Fantasy & Surreal, Travel & Culture, Technology & Gadgets, Vehicles & Motion, Social Media & Creator, Motion & Video*).
  * **Page 28 (Checklist & Tips)**: Interactive prompt customization checklist and pro tips.
* **Animated 3D Page Turning**:
  * Realistic 3D perspective curl with simulated lighting and paper fold shadows.
  * Natural horizontal touch swipe gestures (swipe left for next, right for previous).
  * Animated Next/Previous buttons and quick page jump scrubber slider.
  * **Reduced Motion mode** in Settings for accessibility or instant transitions.
* **1-Tap Copy & Sharing**:
  * Instant clipboard copy with animated feedback and toast confirmation.
  * Native Android share sheet to broadcast prompts to any app.
* **Full-Text Live Search**:
  * Search over 119 prompt titles, body text, and categories.
  * Live search keyword highlighting.
  * Filter chips for *All*, *Images*, and *Videos*.
* **Saved & Bookmarks**:
  * Dedicated screen for starred prompts and bookmarked pages.
  * Persistent on-device storage using Android SharedPreferences.
* **Three Reader Themes**:
  * **Dark Navy**: Deep sapphire navy, amber gold, and purple accents.
  * **Daylight Parchment**: Elegant warm paper tone with autumn crimson highlights.
  * **AMOLED Midnight**: True black #000000 for battery saving and contrast.
* **Distraction-Free Mode**: Tap anywhere to hide bars for pure reading immersion.

---

## 📥 How to Download & Install the APK from GitHub

### Option A: From GitHub Actions (Latest Build)
1. Navigate to the **Actions** tab on your GitHub repository.
2. Click on the latest workflow run named **"Build & Release Android APK"**.
3. Scroll down to the **Artifacts** section at the bottom of the page.
4. Click **`chinar-prompts-debug-apk`** to download the zip file containing `app-debug.apk`.
5. Unzip and transfer `app-debug.apk` to your Android device to install!

### Option B: From GitHub Releases
1. On the repository home page, look at the right sidebar under **Releases**.
2. Click the latest release tag (e.g., `v1.0.0`).
3. Under **Assets**, click **`app-debug.apk`** to download directly.

---

## 🛠️ Building Locally

### Prerequisites
* Android Studio Iguana / Ladybug or newer
* JDK 17 or JDK 21
* Android SDK 36

### Build Commands
```bash
# Clone the repository
git clone <your-repo-url>
cd chinar-prompts

# Make Gradle wrapper executable
chmod +x gradlew

# Run unit and Robolectric tests
./gradlew :app:testDebugUnitTest

# Assemble Debug APK
./gradlew assembleDebug

# Output APK location:
# app/build/outputs/apk/debug/app-debug.apk
```

---

## 🔒 Security & Privacy

* Keystores, signing keys, and `.env` files are strictly excluded via `.gitignore`.
* Offline-first architecture: Reading progress, bookmarks, and favorites are stored purely on your local device.
