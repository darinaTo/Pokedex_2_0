package com.example.pokedex_2_0

import android.app.Application
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit

@HiltAndroidApp
class PokemonApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val work = OneTimeWorkRequestBuilder<PokedexWorkManager>()
            .setInitialDelay(1,TimeUnit.DAYS)
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(
                        NetworkType.CONNECTED
                    )
                    .build()
            )
            .build()

        val workManager = WorkManager.getInstance(applicationContext)

        workManager.beginUniqueWork(
            "pokedex",
            ExistingWorkPolicy.KEEP,
            work
        )
            .enqueue()
    }
}
