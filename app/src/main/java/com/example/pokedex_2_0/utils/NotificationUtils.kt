package com.example.pokedex_2_0.utils

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.pokedex_2_0.PokedexWorkManager
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class NotificationUtils @Inject constructor(context: Context) {
    private val workManager = WorkManager.getInstance(context)

    private val work = PeriodicWorkRequestBuilder<PokedexWorkManager>(4, TimeUnit.SECONDS)
        .setConstraints(
            Constraints.Builder()
                .setRequiredNetworkType(
                    NetworkType.CONNECTED
                )
                .build()
        )
        .build()

    fun startNotification() {
        workManager.enqueueUniquePeriodicWork(
            "pokedex", ExistingPeriodicWorkPolicy.UPDATE, work
        )
    }
}