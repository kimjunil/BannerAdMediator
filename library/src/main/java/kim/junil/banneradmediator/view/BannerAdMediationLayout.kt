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
                    if ( adView.getAdTag().equals("adfit")){
                        adView.getAdView().visibility = View.VISIBLE
                    }
                    if (!adView.isLoaded)
                        adView.loadAd()
                }
            }
        }
        bannerAdMediator.startMediation()
    }

    fun onResume() {
        for (i in 0..childCount){
            if (getChildAt(i) is BannerAdView.ActivityLifeCycleListener){
                (getChildAt(i) as BannerAdView.ActivityLifeCycleListener).onResume()
            }
        }
    }

    fun onPause() {
        for (i in 0..childCount){
            if (getChildAt(i) is BannerAdView.ActivityLifeCycleListener){
                (getChildAt(i) as BannerAdView.ActivityLifeCycleListener).onPause()
            }
        }
    }

    fun onDestroy() {
        for (i in 0..childCount){
            if (getChildAt(i) is BannerAdView.ActivityLifeCycleListener){
                (getChildAt(i) as BannerAdView.ActivityLifeCycleListener).onDestroy()
            }
        }
    }
}