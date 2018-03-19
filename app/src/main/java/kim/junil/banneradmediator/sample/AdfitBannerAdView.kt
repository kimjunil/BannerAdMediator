package kim.junil.banneradmediator.sample

import android.content.Context
import android.view.View
import kim.junil.banneradmediator.view.BannerAdView



class AdfitBannerAdView(context: Context, visibleTime:Int, adUnitId: String) : BannerAdView(context, visibleTime), BannerAdView.ActivityLifeCycleListener {

    private var mAdView: com.kakao.adfit.ads.ba.BannerAdView? = com.kakao.adfit.ads.ba.BannerAdView(context)

    private fun convertErrorMessage(errCode: Int): String {
        /*
        code = 101, 필수 권한을 추가하지 않은 경우 전달되는 에러
        code = 201, AdFit으로부터 발급받은 유효한 client id가 없거나 틀린경우 전달되는 에러이다.
        code = 202, 광고 요청 도중 전달되는 에러. 보통 일시적인 네트워크 오류일 경우 발생한다.
        code = 301, 해당 애플리케이션에 유효하지 않은 광고가 수신된 경우 전달되는 에러.
        code = 302, 광고는 정상적으로 요청했으나 광고서버에서 보내줄 광고가 없는 경우. (NO_AD)
        code = 501, 광고를 보여줄 수 없는 경우에 호출된다.
        code = 601, SDK 내부에서 발생된 에러이다. SDK 배포자에게 알려주어 처리되도록 해야한다.
         */
        return when(errCode){
            101 -> "NEED_PERMISSION"
            201 -> "NOT_VALID_CLIENT_ID"
            202 -> "NETWORK_ERROR"
            301 -> "NOT_VALID_AD"
            302 -> "NO_FILL_AD"
            501 -> "CAN_NOT_SHOWING_AD"
            601 -> "ADFIT_SDK_ERROR"
            else -> "UNKNOWN_ERROR"
        }
    }

    override fun getAdView(): View {
        if (mAdView==null) {
            mAdView = com.kakao.adfit.ads.ba.BannerAdView(context)
        }
        return mAdView as com.kakao.adfit.ads.ba.BannerAdView
    }

    override fun loadAd() {
        mAdView?.loadAd()
    }

    init {
        mAdView?.setClientId(adUnitId)
        mAdView?.setRequestInterval(30)
        mAdView?.setAdUnitSize("320x50")

        mAdView?.setAdListener(object: com.kakao.adfit.ads.AdListener{
            override fun onAdFailed(p0: Int) {
                isLoaded = false
                adListener?.onAdFailedToLoad(this@AdfitBannerAdView, convertErrorMessage(p0))
            }

            override fun onAdClicked() {
                adListener?.onAdClicked(this@AdfitBannerAdView)
            }

            override fun onAdLoaded() {
                isLoaded = true
                adListener?.onAdLoaded(this@AdfitBannerAdView)
            }
        })

    }


    override fun getAdTag(): String {
        return "adfit"
    }

    override fun onResume() {
        mAdView?.resume()
    }

    override fun onPause() {
        mAdView?.pause()
    }

    override fun onDestroy() {
        mAdView?.destroy()
        mAdView = null
    }
}