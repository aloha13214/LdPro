package com.fsoc.template.common.service

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.os.Parcelable
import android.provider.Settings
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.fsoc.template.R
import com.fsoc.template.presentation.main.MainActivity
import java.util.*

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
class MessageService : NotificationListenerService() {

    private object ApplicationPackageNames {
        const val INSTAGRAM_PACK_NAME = "com.instagram.android"
        const val ZALO_PACK_NAME = "com.zing.zalo"
    }

    object InterceptedNotificationCode {
        const val INSTAGRAM_CODE = 3
        const val ZALO_CODE = 5
        const val OTHER_NOTIFICATIONS_CODE = 4 // We ignore all notification with code == 4
    }

    override fun onBind(intent: Intent): IBinder? {
        return super.onBind(intent)
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    override fun onNotificationPosted(sbn: StatusBarNotification) {
        val notificationCode = matchNotificationCode(sbn)
        if (notificationCode == InterceptedNotificationCode.ZALO_CODE) {
            val pack = sbn.packageName
            val extras = sbn.notification.extras
            val title = extras.getCharSequence("android.title").toString()
            val text = extras.getCharSequence("android.text").toString()
            var subtext = ""
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                /* Used for SendBroadcast */
                val b = extras[Notification.EXTRA_MESSAGES] as Array<Parcelable>?
                if (b != null) {
                    for (tmp in b) {
                        val msgBundle = tmp as Bundle
                        subtext = msgBundle.getString("text") ?: ""
                    }
                    Log.d("DetailsEzra1 :", subtext)
                }
                if (subtext.isEmpty()) {
                    subtext = text
                }
                Log.d("DetailsEzra2 :", subtext)
                val intent = Intent("com.example.ssa_ezra.whatsappmonitoring")
                intent.putExtra("Notification Code", notificationCode)
                intent.putExtra("package", pack)
                intent.putExtra("title", title)
                intent.putExtra("text", subtext)
                intent.putExtra("id", sbn.id)
                intent.putExtra("time", Calendar.getInstance().timeInMillis)
                sendBroadcast(intent)
                /* End */

                /* Used Used for SendBroadcast */
                if (!text.contains("new messages")) {
                    val intentPending = Intent(applicationContext, MainActivity::class.java)
                    val pendingIntent = PendingIntent.getActivity(this, 0, intentPending, 0)
                    val builder = NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentTitle(resources.getString(R.string.app_name))
                        .setContentText("This is income messagess : $text")
                    builder.setWhen(System.currentTimeMillis())
                    builder.setSmallIcon(R.mipmap.ic_launcher)
                    val largeIconBitmap =
                        BitmapFactory.decodeResource(resources, R.drawable.ic_zalo)
                    builder.setLargeIcon(largeIconBitmap)
                    // Make the notification max priority.
                    builder.priority = Notification.PRIORITY_MAX
                    // Make head-up notification.
                    builder.setFullScreenIntent(pendingIntent, true)
                    val notificationManager =
                        getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                    notificationManager.notify(1, builder.build())
                }
            }
        }
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification) {
        val notificationCode = matchNotificationCode(sbn)
        if (notificationCode != InterceptedNotificationCode.OTHER_NOTIFICATIONS_CODE) {
            val activeNotifications = this.activeNotifications
            if (activeNotifications != null && activeNotifications.isNotEmpty()) {
                for (i in activeNotifications.indices) {
                    if (notificationCode == matchNotificationCode(activeNotifications[i])) {
                        val intent = Intent("com.example.ssa_ezra.whatsappmonitoring")
                        intent.putExtra("Notification Code", notificationCode)
                        sendBroadcast(intent)
                        break
                    }
                }
            }
        }
    }

    private fun matchNotificationCode(sbn: StatusBarNotification): Int {
        return when (sbn.packageName) {
            ApplicationPackageNames.INSTAGRAM_PACK_NAME -> InterceptedNotificationCode.INSTAGRAM_CODE
            ApplicationPackageNames.ZALO_PACK_NAME -> InterceptedNotificationCode.ZALO_CODE
            else -> InterceptedNotificationCode.OTHER_NOTIFICATIONS_CODE
        }
    }
}