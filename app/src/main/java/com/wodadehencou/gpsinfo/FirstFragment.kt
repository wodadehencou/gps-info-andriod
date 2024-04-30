package com.wodadehencou.gpsinfo

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.wodadehencou.gpsinfo.databinding.FragmentFirstBinding


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {


    companion object {
        private const val TAG = "GPS.first"
        private const val UPDATE_INTERVAL_IN_MILLISECONDS: Long = 10000
        private const val FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
            UPDATE_INTERVAL_IN_MILLISECONDS / 2
        private const val LOCATION_PROVIDER = LocationManager.FUSED_PROVIDER
    }

    // create location manager
    private var locationManager: LocationManager?=null
    private var locationListener: LocationListener?=null

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "create first fragment")

        // 获取 FusedLocationProviderClient 实例
        locationManager = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (locationManager == null) {
            Log.e(TAG, "can not get location manager")
        }

        locationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                Log.i(TAG, "location changed $location")
                updateLocation(location)
            }

            override fun onProviderDisabled(provider: String) {
                super.onProviderDisabled(provider)
                Log.i(TAG, "location provider $provider disabled")
                updateLocation(null)
            }

            override fun onProviderEnabled(provider: String) {
                super.onProviderEnabled(provider)
                Log.i(TAG, "location provider $provider enabled")
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("MissingPermission")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        binding.buttonUpdate.setOnClickListener {

            Snackbar.make(view, "pushed update button", Snackbar.LENGTH_LONG)
                .setAnchorView(R.id.button_update)
                .setDuration(1000)
                .setAction("Action", null).show()


//            var text = "dummy"
//
//            val currentLocation = getCurrentLocation()
//            if (currentLocation != null) {
//                val latitude = currentLocation.latitude
//                val longitude = currentLocation.longitude
//                text = "CurrentLocation: Latitude: $latitude, Longitude: $longitude"
//            } else {
//                text = "Unable to get current location"
//            }
//
//            Snackbar.make(view, text, Snackbar.LENGTH_LONG)
//                .setAnchorView(R.id.button_update)
//                .setDuration(1000)
//                .setAction("Action", null).show()

        }

        val loc = locationManager?.getLastKnownLocation(LOCATION_PROVIDER)
        updateLocation(loc)


//
//
//        //调用getCurrentLocation方法
//        fusedLocationClient.getCurrentLocation(1, null)
//
//        // 创建一个 LocationCallback 对象
//        locationCallback = object : com.google.android.gms.location.LocationCallback() {
//            override fun onLocationResult(p0: LocationResult) {
//                Log.i(TAG, "onLocationResult")
//
//                if (p0 == null) {
//                    Log.e(TAG, "can not get location information")
//                    Snackbar.make(view, "can not get location information", Snackbar.LENGTH_LONG)
//                        .setAnchorView(R.id.button_update)
//                        .setDuration(1000)
//                        .setAction("Action", null).show()
//                }
//
//                for (location in p0.locations) {
//                    // 处理新的位置信息
//                    Log.i(TAG, "handle location information: $location")
//                    Snackbar.make(view, "handle location $location", Snackbar.LENGTH_LONG)
//                        .setAnchorView(R.id.button_update)
//                        .setDuration(1000)
//                        .setAction("Action", null).show()
//                    handleNewLocation(location)
//                }
//            }
//        }
//        locationManager = mContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "pause first fragment")
        // 在 Activity 不活动时停止位置更新
        if (locationListener == null) {
            Log.w(TAG, "location listener is null")
            return
        }
        locationManager?.removeUpdates(locationListener!!)
    }

    @SuppressLint("MissingPermission")
    override fun onResume() {
        super.onResume()
        Log.i(TAG, "resume first fragment")
        locationManager?.requestLocationUpdates(LocationManager.GPS_PROVIDER, 6000, 1.0f,
            locationListener!!)
    }


//    private fun getCurrentLocation() :Location? {
//
//
//        if (ActivityCompat.checkSelfPermission(
//                mContext,
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                mContext,
//                Manifest.permission.ACCESS_COARSE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            // Permission is not granted
//            Log.e(TAG, "no permission")
//            return null
//        }
//
//
//        if (locationManager==null) {
//            Log.e(TAG, "location manager is null")
//            return null
//        }
//
//        val gpsLocation = locationManager?.getLastKnownLocation(LocationManager.GPS_PROVIDER)
//        if (gpsLocation == null) {
//            Log.e(TAG, "can not get gps location")
//        }
//        val networkLocation =
//            locationManager?.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
//        if (networkLocation == null) {
//            Log.e(TAG, "can not get network location")
//        }
//
//        return when {
//            gpsLocation != null -> gpsLocation
//            networkLocation != null -> networkLocation
//            else -> null
//        }
//    }

//    private fun getCurrentLocation(): Location? {
//
//
//        if (ActivityCompat.checkSelfPermission(
//                requireContext(),
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                requireContext(),
//                Manifest.permission.ACCESS_COARSE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            // Permission is not granted
//            Log.e(TAG, "no permission")
//            return null
//        }
//
//
//        val gpsLocation = fusedLocationClient.getCurrentLocation()
//        if (gpsLocation == null) {
//            Log.e(TAG, "can not get gps location")
//        }
//        val networkLocation =
//            locationManager?.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
//        if (networkLocation == null) {
//            Log.e(TAG, "can not get network location")
//        }
//
//        return when {
//            gpsLocation != null -> gpsLocation
//            networkLocation != null -> networkLocation
//            else -> null
//        }
//    }


//    /**
//     * 创建 LocationRequest 对象
//     */
//    private fun createLocationRequest(): com.google.android.gms.location.LocationRequest {
////        return com.google.android.gms.location.LocationRequest.create().apply {
////            interval = UPDATE_INTERVAL_IN_MILLISECONDS
////            fastestInterval = FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS
////            priority = com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
////        }
//        var builder = com.google.android.gms.location.LocationRequest.Builder(
//            Priority.PRIORITY_HIGH_ACCURACY,
//            UPDATE_INTERVAL_IN_MILLISECONDS
//        )
//        builder.setMinUpdateIntervalMillis(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS)
//        return builder.build()
//    }


    // 处理新的位置信息
    private fun updateLocation(location: Location?) {
        if (location == null) {
            this.requireActivity().runOnUiThread {
                Log.d(TAG, "update location information to null")
                // 在这里处理新的位置信息
                binding.longitudeValue.text = "null"
                binding.latitudeValue.text = "null"
                binding.heightValue.text = "null"
                binding.speedValue.text = "null"
                binding.directionValue.text = "null"
            }
        } else {
            this.requireActivity().runOnUiThread {
                Log.d(TAG, "update location information")
                // 在这里处理新的位置信息
                val decimalPlaces = 3
                binding.longitudeValue.text = String.format("%.${decimalPlaces}f 度", location.longitude)
                binding.latitudeValue.text =  String.format("%.${decimalPlaces}f 度", location.latitude)
                binding.heightValue.text =    String.format("%.${decimalPlaces}f m", location.altitude)
                binding.speedValue.text =     String.format("%.${decimalPlaces}f km/h", location.speed * 3.6)
                binding.directionValue.text = String.format("%.${decimalPlaces}f 度", location.bearing)
            }

//        Toast.makeText(
//            requireContext(),
//            "经度：" + location.longitude.toString() + "，纬度：" + location.latitude.toString(),
//            Toast.LENGTH_SHORT
//        ).show()
        }
    }


}