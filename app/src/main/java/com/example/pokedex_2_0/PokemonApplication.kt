package com.example.pokedex_2_0

import android.app.Application
import com.example.pokedex_2_0.utils.NotificationUtils
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class PokemonApplication : Application() {
    @Inject
    lateinit var notificationUtils: NotificationUtils
    override fun onCreate() {
        super.onCreate()
        notificationUtils.startNotification()
    }
}
