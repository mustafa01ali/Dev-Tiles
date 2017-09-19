package xyz.mustafaali.devqstiles

import android.app.Application
import timber.log.Timber

class DevQSApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}