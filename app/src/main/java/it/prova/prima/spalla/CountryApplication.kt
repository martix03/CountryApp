package it.prova.prima.spalla

import android.app.Application
import it.prova.prima.spalla.di.clientModule
import it.prova.prima.spalla.di.repositoryModule
import it.prova.prima.spalla.di.serviceModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CountryApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CountryApplication)
            modules(clientModule, serviceModule, repositoryModule)
        }
    }
}