package ai.arturxdroid.gpsservice

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.*
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class GpsService : Service() {

    private val coordinates = ArrayList<CoordinatesData>()

    private val api = ApiFactory.getGpsApi()

    private val job = Job()

    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Default + job)

    private val CHANNEL_ID = "GpsServiceForeground"

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotifiacationChannel()
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Gps Service")
            .setContentText("service is running")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .build()

        startForeground(1, notification)

        scope.launch {
            while (true) {
                val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy-hh-mm-ss", Locale.getDefault())
                val ts = simpleDateFormat.format(Date())
                val location = GpsLocationListener.lastKnownLocation
                coordinates.add(
                    CoordinatesData(
                        ts,
                        location?.latitude.toString(),
                        location?.longitude.toString()
                    )
                )
                if (coordinates.size >= 60) {
                    api.postData(GpsData(coordinates.size, coordinates))
                }
                delay(1000*60)
            }
        }

        return Service.START_STICKY
    }


    private fun createNotifiacationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID
                , "foreground gps service",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }

    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStart(intent: Intent?, startId: Int) {
        scope.launch {
            while (true) {


            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}
