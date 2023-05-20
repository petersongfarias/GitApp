package br.com.github

import android.app.Application
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import timber.log.Timber.DebugTree
import timber.log.Timber.Forest.plant

class GitApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@GitApplication)
            // modules(niaAppModule)
        }
        if (BuildConfig.DEBUG) {
            plant(DebugTree())
        }
    }
}
