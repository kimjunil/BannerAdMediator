package kim.junil.banneradmediator.sample

import android.content.Context
import android.view.View
import com.fsn.cauly.CaulyAdView
import kim.junil.banneradmediator.view.BannerAdView
import com.fsn.cauly.CaulyAdInfoBuilder
import com.fsn.cauly.CaulyAdViewListener


class CaulyBannerAdView(context: Context, visibleTime:Int,  adUnitId: String) : BannerAdView(context, visibleTime) {

    private val mAdView: CaulyAdView = CaulyAdView(context)

    override fun getAdView(): View {
        return mAdView
    }

    override fun loadAd() {
        super.loadAd()
        mAdView.reload()
    }

    init {
        val adInfo = CaulyAdInfoBuilder(adUnitId)
                .effect("RightSlide")
                .bannerHeight("Fixed_50")
                .enableDefaultBannerAd(true)
                .build()

        mAdView.setAdInfo(adInfo)
        mAdView.setAdViewListener(object: CaulyAdViewListener {
            override fun onFailedToReceiveAd(p0: CaulyAdView?, p1: Int, p2: String?) {
                isLoaded = false
                if (p2!=null)
                    adListener?.onAdFailedToLoad(this@CaulyBannerAdView, p2)
                else
                    adListener?.onAdFailedToLoad(this@CaulyBannerAdView, "errCode:$p1")
            }

            override fun onCloseLandingScreen(p0: CaulyAdView?) {
                adListener?.onAdClosed(this@CaulyBannerAdView)
            }

            override fun onShowLandingScreen(p0: CaulyAdView?) {
                adListener?.onAdOpened(this@CaulyBannerAdView)
            }

            override fun onReceiveAd(p0: CaulyAdView?, p1: Boolean) {
                isLoaded = true
                adListener?.onAdLoaded(this@CaulyBannerAdView)
            }

        })
    }

    override fun getAdTag(): String {
        return "cauly"
    }
}