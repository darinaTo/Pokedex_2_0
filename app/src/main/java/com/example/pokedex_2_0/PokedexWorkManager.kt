package com.example.pokedex_2_0

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.pokedex_2_0.data.constants.Constants.CHANNEL_ID
import com.example.pokedex_2_0.data.constants.Constants.NOTIFICATION_ID
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class PokedexWorkManager @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result {
        showNotification(applicationContext)
        return Result.success()
    }


    private fun showNotification(context: Context) {
        val notification =  buildNotification(context)

        val channel =
            NotificationChannel(CHANNEL_ID, "pokedex", NotificationManager.IMPORTANCE_DEFAULT)
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

        notificationManager.notify(NOTIFICATION_ID, notification.build())
    }

    private fun buildNotification(context: Context) : NotificationCompat.Builder {
        val intent = Intent(applicationContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.icon)
            .setContentTitle("Pokemon miss you")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
    }
}
