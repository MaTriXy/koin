package fr.ekito.myweatherapp

import android.app.Application
import com.joanzapata.iconify.Iconify
import com.joanzapata.iconify.fonts.WeathericonsModule
import fr.ekito.myweatherapp.di.roomWeatherApp
import org.koin.android.ext.android.startKoin
import org.koin.android.logger.AndroidLogger

/**
 * Main Application
 */
class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // start Koin context
        startKoin(this, roomWeatherApp, logger = AndroidLogger(showDebug = true))

        Iconify
            .with(WeathericonsModule())
    }
}
