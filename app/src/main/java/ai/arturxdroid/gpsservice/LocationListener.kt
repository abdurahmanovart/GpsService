package ai.arturxdroid.gpsservice

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle

internal class MyLocationListener : LocationListener {

    override fun onLocationChanged(loc: Location) {
        imHere = loc
    }

    override fun onProviderDisabled(provider: String) {}
    override fun onProviderEnabled(provider: String) {}
    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}

    companion object {

        var imHere: Location? =
            null // здесь будет всегда доступна самая последняя информация о местоположении пользователя.

        fun SetUpLocationListener(context: Context) // это нужно запустить в самом начале работы программы
        {
            val locationManager =
                context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

            val locationListener = MyLocationListener()

            if (locationManager != null) {
                if (context.checkPermission(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        1,
                        1
                    ) != PackageManager.PERMISSION_GRANTED
                ) {

                    return
                }
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    5000,
                    10f,
                    locationListener
                )
            }

            imHere = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        }
    }
}