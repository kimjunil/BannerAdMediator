package kim.junil.banneradmediator.view

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import kim.junil.banneradmediator.BannerAdMediator

class BannerAdMediationLayout : FrameLayout {

    lateinit var bannerAdMediator: BannerAdMediator

    constructor(ctx: Context) : super(ctx)

    constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs)

    fun startMediation(){
        Log.d("BannerAdMediationLayout", "startMediation()")
        bannerAdMediator.listener = object : BannerAdMediator.ReceivedNextBannerListener {
            override fun onReceived(adView: BannerAdView) {
                Handler(Looper.getMainLooper()).post {
                    removeAllViews()
                    addView(adView.getAdView())

                    if (!adView.isLoaded){
                        if (adView.neverLoad) {
                            adView.loadAd()
                        }else{
                            bannerAdMediator.skipBanner(adView)
                        }

                    }
                }
            }
        }
        bannerAdMediator.startMediation()
    }

    fun onResume() {
        if (!bannerAdMediator.isStarted){
            bannerAdMediator.startMediation()
        }

        (0..childCount)
                .filter { getChildAt(it) is BannerAdView.ActivityLifeCycleListener }
                .forEach { (getChildAt(it) as BannerAdView.ActivityLifeCycleListener).onResume() }
    }

    fun onPause() {
        if (bannerAdMediator.isStarted){
            bannerAdMediator.stopMediation()
        }

        (0..childCount)
                .filter { getChildAt(it) is BannerAdView.ActivityLifeCycleListener }
                .forEach { (getChildAt(it) as BannerAdView.ActivityLifeCycleListener).onPause() }
    }

    fun onDestroy() {
        if (bannerAdMediator.isStarted){
            bannerAdMediator.stopMediation()
        }

        (0..childCount)
                .filter { getChildAt(it) is BannerAdView.ActivityLifeCycleListener }
                .forEach { (getChildAt(it) as BannerAdView.ActivityLifeCycleListener).onDestroy() }
    }
}