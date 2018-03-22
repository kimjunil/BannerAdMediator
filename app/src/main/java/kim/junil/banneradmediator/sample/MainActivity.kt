package kim.junil.banneradmediator.sample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kim.junil.banneradmediator.BannerAdMediator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mediator = BannerAdMediator()
        val admob = AdmobBannerAdView(this, 15, "ca-app-pub-3940256099942544/6300978111")
        val adfit = AdfitBannerAdView(this, 15, "DAN-urjkqh0btdut")
        val cauly = CaulyBannerAdView(this, 15, "pT2d8M2M")

        mediator.addBanner(admob, 8)
        mediator.addBanner(adfit, 15)
        mediator.addBanner(cauly, 2)

        bannerAdMediationLayout.bannerAdMediator = mediator
        bannerAdMediationLayout.startMediation()
    }

    override fun onResume() {
        super.onResume()
        bannerAdMediationLayout.onResume()
    }

    override fun onPause() {
        super.onPause()
        bannerAdMediationLayout.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        bannerAdMediationLayout.onDestroy()
    }
}
