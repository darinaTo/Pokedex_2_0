package com.example.pokedex_2_0

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class WorkManager @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters) :
    CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result {
        // TODO: Your Worker logic goes here.
        return Result.success()
    }
}