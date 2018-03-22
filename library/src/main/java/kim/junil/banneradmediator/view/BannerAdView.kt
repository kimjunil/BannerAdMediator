package kim.junil.banneradmediator.view

import android.content.Context
import android.support.annotation.CallSuper
import android.view.View

abstract class BannerAdView(val context: Context, val visibleTime: Int) {

    var neverLoad = true
    var isLoaded:Boolean = false

    var adListener: AdListener? = null

    abstract fun getAdView() : View

    @CallSuper
    open fun loadAd(){
        if (!neverLoad)
            neverLoad = true
    }

    abstract fun getAdTag(): String


    interface ActivityLifeCycleListener{
        fun onResume()
        fun onPause()
        fun onDestroy()
    }

    interface AdListener{
        fun onAdLoaded(adView: BannerAdView)
        fun onAdFailedToLoad(adView: BannerAdView, errMsg: String)
        fun onAdOpened(adView: BannerAdView)
        fun onAdClicked(adView: BannerAdView)
        fun onAdClosed(adView: BannerAdView)
        fun onAdLeftApplication(adView: BannerAdView)
    }
}