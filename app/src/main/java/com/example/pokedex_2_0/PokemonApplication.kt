package com.example.pokedex_2_0

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class PokemonApplication : Application(), Configuration.Provider {
    @Inject
    lateinit var hiltWorkerFactory: HiltWorkerFactory
    override val workManagerConfiguration: Configuration

        get() = Configuration.Builder()
            .setWorkerFactory(hiltWorkerFactory)
            .build()

}

