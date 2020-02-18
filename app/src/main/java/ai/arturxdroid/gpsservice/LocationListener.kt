package ai.arturxdroid.gpsservice

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle

internal class GpsLocationListener : LocationListener {

    override fun onLocationChanged(loc: Location) {
        lastKnownLocation = loc
    }

    override fun onProviderDisabled(provider: String) {}
    override fun onProviderEnabled(provider: String) {}
    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}

    companion object {

        var lastKnownLocation: Location? =
            null

        fun SetUpLocationListener(context: Context)
        {
            val locationManager =
                context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

            val locationListener = GpsLocationListener()

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

            lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        }
    }
}