package com.dev.mahmoud_ashraf.weather_logger.presentation.core

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationAvailability
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.tasks.Task
import java.lang.ref.WeakReference
import java.util.ArrayList

class LocationApi(
    activity: Activity,
    private val shouldWeRequestPermissions: Boolean = true,
    private val shouldWeRequestOptimization: Boolean = true,
    private val callbacks: Callbacks
) {
    private var activityWeakReference = WeakReference<Activity>(activity)
    private var locationCallback: LocationCallback? = null
    private var fusedLocationClient: FusedLocationProviderClient? = null
    private val requestCheckSettings = 1235
    private val requestLocation = 1236

    interface Callbacks {
        fun onSuccess(location: Location)
        fun onFailed(locationFailedEnum: LocationFailedEnum)
    }

    enum class LocationFailedEnum {
        LocationPermissionNotGranted,
        LocationOptimizationPermissionNotGranted,
        HighPrecisionNA_TryAgainPreferablyWithInternet
    }

    init {
        // First try to get last available location, if it matches our precision level
        fusedLocationClient = activity.let { LocationServices.getFusedLocationProviderClient(it) }
        val task = fusedLocationClient?.lastLocation

        task?.addOnSuccessListener { location: Location? ->
            if (location != null) {
                callbacks.onSuccess(location)
                Log.d("locationUpdate", location.toString())
            } else {
                requestRealtime()
            }
        }
        task?.addOnFailureListener {
            requestRealtime()
        }
    }

    private fun requestRealtime() {
        // define location callback now
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                callbacks.onSuccess(locationResult.lastLocation)
                Log.d("locationUpdate", locationResult.lastLocation.toString())
                    fusedLocationClient?.removeLocationUpdates(locationCallback)
            }

            @SuppressLint("MissingPermission")
            override fun onLocationAvailability(locationAvailability: LocationAvailability?) {
                super.onLocationAvailability(locationAvailability)
                if (locationAvailability?.isLocationAvailable == false) {
                    callbacks.onFailed(
                        LocationFailedEnum.HighPrecisionNA_TryAgainPreferablyWithInternet
                    )
                    fusedLocationClient?.removeLocationUpdates(locationCallback)
                }
            }
        }

        // check flight mode
        if (activityWeakReference.get() == null) {
            return
        }

        else {
            // check location permissions
            val permissions = ArrayList<String>()
            permissions.add(Manifest.permission.ACCESS_FINE_LOCATION)
            permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION)
            var permissionGranted = true
            for (permission in permissions) {
                if (ContextCompat.checkSelfPermission(
                        activityWeakReference.get() as Activity,
                        permission
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    permissionGranted = false
                    break
                }
            }
            if (permissionGranted == false) {
                // request permissions as not present
                if (shouldWeRequestPermissions) {
                    val permissionsArgs = permissions.toTypedArray()
                    ActivityCompat.requestPermissions(
                        activityWeakReference.get() as Activity,
                        permissionsArgs,
                        requestLocation
                    )
                } else {
                    callbacks.onFailed(LocationFailedEnum.LocationPermissionNotGranted)
                }
            } else {
                getLocation()
            }
        }
    }

    fun stopLocationUpdates() = fusedLocationClient?.let {
        if (locationCallback != null)
            it.removeLocationUpdates(locationCallback)
    }

    fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        if (activityWeakReference.get() == null) {
            return
        }

        if (requestCode == requestLocation) {
            if (grantResults.isEmpty()) {
                callbacks.onFailed(LocationFailedEnum.LocationPermissionNotGranted)
                return
            }

            var granted = true
            for (grantResult in grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    granted = false
                    break
                }
            }
            if (granted) {
                getLocation()
            } else {
                callbacks.onFailed(LocationFailedEnum.LocationPermissionNotGranted)
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {

        if (activityWeakReference.get() == null) {
            return
        }

        val locationRequest = LocationRequest().apply {
            interval = 10000
            fastestInterval = 2000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        // check current location settings
        val task: Task<LocationSettingsResponse> =
            (LocationServices.getSettingsClient(activityWeakReference.get() as Activity))
                .checkLocationSettings(
                    (LocationSettingsRequest.Builder().addLocationRequest(
                        locationRequest
                    )).build()
                )

        task.addOnSuccessListener { _ ->
            fusedLocationClient?.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.myLooper()
            )
        }

        task.addOnFailureListener { exception ->
            if (exception is ResolvableApiException) {
                if (activityWeakReference.get() == null) {
                    return@addOnFailureListener
                }

                // Location settings are not satisfied, but this can be fixed by showing the user a dialog.
                try {
                    // Show the dialog by calling startResolutionForResult(), and check the result in onActivityResult().
                    if (shouldWeRequestOptimization) {
                        exception.startResolutionForResult(
                            activityWeakReference.get() as Activity,
                            requestCheckSettings)
                    } else {
                        callbacks.onFailed(
                            LocationFailedEnum.LocationOptimizationPermissionNotGranted
                        )
                    }
                } catch (sendEx: IntentSender.SendIntentException) {
                    // Ignore the error.
                }
            }
        }
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (activityWeakReference.get() == null) {
            return
        }

        if (requestCode == requestCheckSettings) {
            if (resultCode == Activity.RESULT_OK) {
                getLocation()
            } else {
                val locationManager =
                    (activityWeakReference.get() as Activity)
                        .getSystemService(
                            Context.LOCATION_SERVICE
                        ) as LocationManager
                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    callbacks.onFailed(
                        LocationFailedEnum.HighPrecisionNA_TryAgainPreferablyWithInternet
                    )
                } else {
                    callbacks.onFailed(LocationFailedEnum.LocationOptimizationPermissionNotGranted)
                }
            }
        }
    }
}