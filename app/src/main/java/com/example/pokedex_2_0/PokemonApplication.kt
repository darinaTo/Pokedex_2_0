package com.example.pokedex_2_0

import android.app.Application
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit

@HiltAndroidApp
class PokemonApplication : Application() {

    //TODO:Also, please extract workManager-related functionality to separate files and inject it (!Do not forget required Hilt annotation!)
    override fun onCreate() {
        super.onCreate()
        val work = PeriodicWorkRequestBuilder<PokedexWorkManager>(1, TimeUnit.DAYS)
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(
                        NetworkType.CONNECTED
                    )
                    .build()
            )
            .build()

        val workManager = WorkManager.getInstance(applicationContext)

        workManager.enqueueUniquePeriodicWork(
            "pokedex",
            ExistingPeriodicWorkPolicy.KEEP,
            work
        )
    }
}
