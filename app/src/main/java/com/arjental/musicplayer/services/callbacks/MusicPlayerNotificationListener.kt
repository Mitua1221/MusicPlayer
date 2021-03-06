package com.arjental.musicplayer.services.callbacks

import android.app.Notification
import android.content.Intent
import androidx.core.content.ContextCompat
import com.arjental.musicplayer.ConstantsAndKeys.NOTIFICATION_ID
import com.arjental.musicplayer.services.MusicPlayerService
import com.google.android.exoplayer2.ui.PlayerNotificationManager

class MusicPlayerNotificationListener(
    private val musicPlayerService: MusicPlayerService
) : PlayerNotificationManager.NotificationListener {

    override fun onNotificationCancelled(notificationId: Int, dismissedByUser: Boolean) {
        super.onNotificationCancelled(notificationId, dismissedByUser)
        musicPlayerService.apply {
            stopForeground(true)
            isForegroundService = false
            stopSelf()
        }
    }

    override fun onNotificationPosted(
        notificationId: Int,
        notification: Notification,
        ongoing: Boolean
    ) {
        super.onNotificationPosted(notificationId, notification, ongoing)
        musicPlayerService.apply {
            if(ongoing && !isForegroundService) {
                ContextCompat.startForegroundService(
                    this,
                    Intent(applicationContext, this::class.java)
                )
                startForeground(NOTIFICATION_ID, notification)
                isForegroundService = true
            }
        }
    }
}
