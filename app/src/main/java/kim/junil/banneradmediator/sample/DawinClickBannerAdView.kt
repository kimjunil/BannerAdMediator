package kim.junil.banneradmediator.sample

import android.app.Activity
import android.view.View
import com.skplanet.tad.AdRequest
import com.skplanet.tad.AdSlot
import kim.junil.banneradmediator.view.BannerAdView
import com.skplanet.tad.AdView


class DawinClickBannerAdView(context: Activity, visibleTime:Int, adUnitId: String) : BannerAdView(context, visibleTime) {

    private val mAdView: AdView = AdView(context)

    override fun getAdView(): View {
        return mAdView
    }

    override fun loadAd() {
        mAdView.loadAd(null)
    }

    init {
        mAdView.setClientId(adUnitId)
        mAdView.setSlotNo(AdSlot.BANNER)
        mAdView.setAnimationType(AdView.AnimationType.ROTATE3D_180_VERTICAL)
        mAdView.setRefreshInterval(20)
        mAdView.setUseBackFill(true)
        mAdView.setTestMode(true)
        mAdView.setListener(object : com.skplanet.tad.AdListener{
            override fun onAdFailed(p0: AdRequest.ErrorCode?) {
                isLoaded = false
                adListener?.onAdFailedToLoad(this@DawinClickBannerAdView, p0.toString())
            }

            override fun onAdExpanded() {
                adListener?.onAdOpened(this@DawinClickBannerAdView)
            }

            override fun onAdLeaveApplication() {
                adListener?.onAdLeftApplication(this@DawinClickBannerAdView)
            }

            override fun onAdDismissScreen() {
            }

            override fun onAdLoaded() {
                isLoaded = true
                adListener?.onAdLoaded(this@DawinClickBannerAdView)
            }

            override fun onAdResized() {
            }

            override fun onAdWillLoad() {
            }

            override fun onAdClicked() {
                adListener?.onAdClicked(this@DawinClickBannerAdView)
            }

            override fun onAdResizeClosed() {}

            override fun onAdPresentScreen() {
            }

            override fun onAdExpandClosed() {
                adListener?.onAdClosed(this@DawinClickBannerAdView)
            }

        })
    }

    override fun getAdTag(): String {
        return "dawin"
    }
}