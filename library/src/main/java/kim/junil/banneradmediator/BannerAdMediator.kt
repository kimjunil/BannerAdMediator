package kim.junil.banneradmediator

import android.os.Handler
import android.os.Message
import android.util.Log
import kim.junil.banneradmediator.utility.random
import kim.junil.banneradmediator.view.BannerAdView
import java.lang.ref.WeakReference
import java.util.*
import java.util.concurrent.Executors

class BannerAdMediator{

    lateinit var listener: ReceivedNextBannerListener
    internal var handler: Handler = BannerHandler(this)

    private val bannerAdViews: ArrayList<Pair<BannerAdView, Int>> = arrayListOf()
    var isStarted = false

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
            isStarted = true
            getNextAdView()
        }
    }

    fun stopMediation(){
        handler.removeCallbacksAndMessages(null)
        isStarted = false
        Log.d("ad-", "stopMediation()")
        Log.d("ad-", "=============================")
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

    private val adListener = object : BannerAdView.AdListener {
        override fun onAdLoaded(adView: BannerAdView) {
            Log.d("ad-" + adView.getAdTag(), "onAdLoaded()")
        }

        override fun onAdFailedToLoad(adView: BannerAdView, errMsg: String) {
            Log.d("ad-" + adView.getAdTag(), "onAdFailedToLoad($errMsg)")
            handler.removeCallbacksAndMessages(null)
            getNextAdView()
        }

        override fun onAdOpened(adView: BannerAdView) {
            Log.d("ad-" + adView.getAdTag(), "onAdOpened()")

        }

        override fun onAdClicked(adView: BannerAdView) {
            Log.d("ad-" + adView.getAdTag(), "onAdClicked()")

        }

        override fun onAdClosed(adView: BannerAdView) {
            Log.d("ad-" + adView.getAdTag(), "onAdClosed()")

        }

        override fun onAdLeftApplication(adView: BannerAdView) {
            Log.d("ad-" + adView.getAdTag(), "onAdLeftApplication()")

        }
    }

    class BannerHandler(bannerAdMediator: BannerAdMediator) : Handler() {
        private val bannerAdMediator: WeakReference<BannerAdMediator> = WeakReference(bannerAdMediator)

        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            Log.d("ad-", "handleMessage")
            bannerAdMediator.get()?.getNextAdView()
        }
    }

    interface ReceivedNextBannerListener{
        fun onReceived(adView: BannerAdView)
    }
}