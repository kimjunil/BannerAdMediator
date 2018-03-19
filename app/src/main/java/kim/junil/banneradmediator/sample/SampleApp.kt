package kim.junil.banneradmediator.sample

import android.app.Application
import com.google.android.gms.ads.MobileAds

class SampleApp : Application(){

    override fun onCreate() {
        super.onCreate()

        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713")
    }

}