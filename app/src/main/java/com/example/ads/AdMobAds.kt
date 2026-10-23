package com.example.ads

import android.app.Activity
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.example.BuildConfig
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.ump.ConsentRequestParameters
import com.google.android.ump.UserMessagingPlatform

object AdMobAds {
    fun initialize(activity: Activity, onReady: () -> Unit) {
        val consentInformation = UserMessagingPlatform.getConsentInformation(activity)
        val params = ConsentRequestParameters.Builder().build()
        consentInformation.requestConsentInfoUpdate(activity, params, {
            UserMessagingPlatform.loadAndShowConsentFormIfRequired(activity) {
                if (consentInformation.canRequestAds()) initializeSdk(activity, onReady)
            }
        }, {
            if (consentInformation.canRequestAds()) initializeSdk(activity, onReady)
        })
    }

    private fun initializeSdk(activity: Activity, onReady: () -> Unit) {
        Thread {
            MobileAds.initialize(activity) {
                activity.runOnUiThread(onReady)
            }
        }.start()
    }
}

@Composable
fun AdMobBanner(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    AndroidView(
        modifier = modifier.fillMaxWidth(),
        factory = { viewContext ->
            AdView(viewContext).apply {
                adUnitId = BuildConfig.ADMOB_BANNER_AD_UNIT_ID
                val widthDp = (viewContext.resources.displayMetrics.widthPixels /
                    viewContext.resources.displayMetrics.density).toInt().coerceAtLeast(1)
                setAdSize(AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(viewContext, widthDp))
                adListener = object : AdListener() {}
                loadAd(AdRequest.Builder().build())
            }
        },
        update = { it.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT) }
    )
    DisposableEffect(context) {
        onDispose { }
    }
}
