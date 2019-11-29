package com.constancio

import android.app.Application
import com.constancio.di.appModule
import com.constancio.di.dataModule
import com.constancio.di.domainModule
import com.constancio.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ConstancioApp : Application() {
    override fun onCreate() {
        super.onCreate()

        /** Start Koin */
        startKoin {
            androidLogger()
            androidContext(this@ConstancioApp)
            modules(
                appModule,
                dataModule,
                domainModule,
                presentationModule
            )
        }
    }
}