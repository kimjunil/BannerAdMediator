package kim.junil.banneradmediator

import android.os.Handler
import android.os.Message
import android.util.Log
import kim.junil.banneradmediator.utility.random
import kim.junil.banneradmediator.view.BannerAdView
import java.util.*
import java.util.concurrent.Executors

class BannerAdMediator{

    lateinit var listener: ReceivedNextBannerListener
    internal var handler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            Log.d("ad-", "handleMessage")
            getNextAdView()
        }
    }

    private val bannerAdViews: ArrayList<Pair<BannerAdView, Int>> = arrayListOf()

    fun addBanner(bannerAdView: BannerAdView, ratio: Int){
        bannerAdView.adListener = adListener
        bannerAdViews.add(Pair(bannerAdView,ratio))
    }

    fun removeBanner(adTag: String){
        var adView = bannerAdViews.first { it.first.getAdTag() == adTag }
        bannerAdViews.remove(adView)
    }

    fun startMediation(){
        Executors.newSingleThreadExecutor().execute {
            Log.d("ad-", "=============================")
            Log.d("ad-", "startMediation()")
            getNextAdView()
        }
    }

    private fun getNextAdView(){
        Log.d("ad-", "getNextAdView()")
        val maxRatio = bannerAdViews.sumBy { it.second }
        var randomValue = Int.random(maxRatio)

        val sortedAdViewList = bannerAdViews.sortedBy { it.second }
        for (adView in sortedAdViewList){
            randomValue -= adView.second
            if (randomValue < 0){
                Log.d("ad-"+adView.first.getAdTag(), adView.first.getAdTag()+" is next")
                Log.d("ad-"+adView.first.getAdTag(), "NextDelay-${adView.first.visibleTime*1000}")
                listener.onReceived(adView.first)
                handler.postDelayed({getNextAdView()}, adView.first.visibleTime*1000L)
                return
            }
        }
    }

    private val adListener = object : BannerAdView.AdListener{
        override fun onAdLoaded(adView: BannerAdView) {
            Log.d("ad-"+adView.getAdTag(), "onAdLoaded()")
        }

        override fun onAdFailedToLoad(adView: BannerAdView, errMsg: String) {
            Log.d("ad-"+adView.getAdTag(), "onAdLoaded()")
            handler.removeCallbacksAndMessages(null)
            getNextAdView()
        }

        override fun onAdOpened(adView: BannerAdView) {
            Log.d("ad-"+adView.getAdTag(), "onAdLoaded()")

        }

        override fun onAdClicked(adView: BannerAdView) {
            Log.d("ad-"+adView.getAdTag(), "onAdLoaded()")

        }

        override fun onAdClosed(adView: BannerAdView) {
            Log.d("ad-"+adView.getAdTag(), "onAdLoaded()")

        }

        override fun onAdLeftApplication(adView: BannerAdView) {
            Log.d("ad-"+adView.getAdTag(), "onAdLoaded()")

        }

    }

    interface ReceivedNextBannerListener{
        fun onReceived(adView: BannerAdView)
    }
}