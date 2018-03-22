package kim.junil.banneradmediator.sample

import android.content.Context
import android.view.View
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import kim.junil.banneradmediator.view.BannerAdView

class AdmobBannerAdView(context: Context, visibleTime:Int,  adUnitId: String) : BannerAdView(context, visibleTime) {

    private val mAdView: AdView = AdView(context)

    private fun convertErrorMessage(errCode: Int): String {
        return when (errCode) {
            AdRequest.ERROR_CODE_INTERNAL_ERROR -> "INTERNAL_ERROR"
            AdRequest.ERROR_CODE_INVALID_REQUEST -> "INVALID_REQUEST"
            AdRequest.ERROR_CODE_NETWORK_ERROR -> "NETWORK_ERROR"
            AdRequest.ERROR_CODE_NO_FILL -> "NO_FILL"
            else -> "UNKNOWN_ERROR"
        }
    }

    private fun getAdRequest(): AdRequest {
        return AdRequest.Builder().addTestDevice("98B191FCA656DF8F632BA29FBB1F5431").build()
    }

    override fun getAdView(): View {
        return mAdView
    }

    override fun loadAd() {
        super.loadAd()
        mAdView.loadAd(getAdRequest())
    }

    init {
        mAdView.adSize = AdSize.SMART_BANNER
        mAdView.adUnitId = adUnitId
        mAdView.adListener = object : com.google.android.gms.ads.AdListener() {

            override fun onAdLeftApplication() {
                super.onAdLeftApplication()
                adListener?.onAdLeftApplication(this@AdmobBannerAdView)
            }

            override fun onAdClicked() {
                super.onAdClicked()
                adListener?.onAdClicked(this@AdmobBannerAdView)
            }

            override fun onAdFailedToLoad(p0: Int) {
                super.onAdFailedToLoad(p0)
                isLoaded = false
                adListener?.onAdFailedToLoad(this@AdmobBannerAdView, convertErrorMessage(p0))
            }

            override fun onAdClosed() {
                super.onAdClosed()
                adListener?.onAdClosed(this@AdmobBannerAdView)
            }

            override fun onAdOpened() {
                super.onAdOpened()
                adListener?.onAdOpened(this@AdmobBannerAdView)
            }

            override fun onAdLoaded() {
                super.onAdLoaded()
                isLoaded = true
                adListener?.onAdLoaded(this@AdmobBannerAdView)
            }
        }
    }


    override fun getAdTag(): String {
        return "admob"
    }
}