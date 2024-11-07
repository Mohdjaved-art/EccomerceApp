package com.stdev.shopit.presentation.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.stdev.shopit.R
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


//class MainActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//    }
//}
@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnMapReadyCallback, View.OnClickListener, com.google.android.gms.location.LocationListener {
    private var myMap: GoogleMap? = null
//    private var btFetchLocation: Button?=null  // here we can fetch the user location
    private  var tvLatitude: TextView?=null
    private var tvLongitude: TextView?= null

    private  var countryName: TextView?=null
    private var locality: TextView?= null

    private  var Address: TextView?=null
    private var REQUEST_LOCATION_CODE = 101
    private var mGoogleApiClient: GoogleApiClient? = null
    private var mLocation: Location? = null
    private var mLocationRequest: LocationRequest? = null

    private val UPDATE_INTERVAL = (2 * 1000).toLong()  /* 10 secs */
    private val FASTEST_INTERVAL: Long = 2000 /* 2 sec */
//
//    val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?


    //    override fun onLocationChanged(p0: Location) {
//        // You can now create a LatLng Object for use with maps
//        // val latLng = LatLng(location.latitude, location.longitude)
//    }
    override fun onLocationChanged(location: Location) {
        // Create a LatLng object using the location's latitude and longitude
        val latLng = LatLng(location.latitude, location.longitude)

        // Update the UI with the new location
        updateUI(latLng)
    }

    private fun updateUI(latLng: LatLng) {
        // Here, you can update your UI to display the user's current location
        // For example, if you have a Google Map, you can move the camera to the new location
        myMap?.moveCamera(CameraUpdateFactory.newLatLng(latLng))

        // You can also add a marker at the new location
        myMap?.clear() // Clear previous markers
        myMap?.addMarker(MarkerOptions().position(latLng).title("Current Location"))
    }


    override fun onClick(v: View?) {
        if (!checkGPSEnabled()) {
            return
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //Location Permission already granted
            getLocation();
        } else {
            //Request Location Permission
            checkLocationPermission()
        }
    }


    @SuppressLint("MissingPermission")
    private fun getLocation() {
        mLocation = mGoogleApiClient?.let { LocationServices.FusedLocationApi.getLastLocation(it)
        };

        if (mLocation == null) {
            startLocationUpdates()
        }
        if (mLocation != null) {

            var geocoder = Geocoder(this, Locale.getDefault())
            val list:List<Address> =
                geocoder.getFromLocation(mLocation!!.latitude, mLocation!!.longitude,1) as List<Address>


            tvLatitude?.text = "latitude\n${list[0].latitude}"

            tvLongitude?.text = "latitude\n${list[0].longitude}"

            countryName?.text = "countryName\n${list[0].countryName}"

//            locality?.text = "\n${list[0].locality}"   // for testing purpose only

            Address?.text = "latitude\n${list[0].getAddressLine(0)}"





        } else {
            Toast.makeText(this, "Location not Detected", Toast.LENGTH_SHORT).show()
        }
    }




    private fun startLocationUpdates() {
        // Create the location request
        mLocationRequest = LocationRequest.create()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(UPDATE_INTERVAL)
            .setFastestInterval(FASTEST_INTERVAL)
        // Request location updates
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        mGoogleApiClient?.let { LocationServices.FusedLocationApi.requestLocationUpdates(it,
            mLocationRequest!!, this) }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //mapFragment!!.getMapAsync(this@MainActivity)
//        btFetchLocation = findViewById(R.id.btFetchLocation)
//        tvLatitude = findViewById(R.id.tvLatitude)
//        tvLongitude = findViewById(R.id.tvLongitude)
//
//        countryName = findViewById(R.id.country)
//        locality = findViewById(R.id.locality)

//        Address = findViewById(R.id.adress)


//        btFetchLocation?.setOnClickListener(this)
        buildGoogleApiClient()
    }

    @Synchronized
    private fun buildGoogleApiClient() {
        mGoogleApiClient = GoogleApiClient.Builder(this)
            .addApi(LocationServices.API)
            .build()

        mGoogleApiClient!!.connect()
    }

    private fun checkGPSEnabled(): Boolean {
        if (!isLocationEnabled())
            showAlert()
        return isLocationEnabled()
    }

    private fun showAlert() {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Enable Location")
            .setMessage("Your Locations Settings is set to 'Off'.\nPlease Enable Location to " + "use this app")
            .setPositiveButton("Location Settings") { paramDialogInterface, paramInt ->
                val myIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(myIntent)
            }
            .setNegativeButton("Cancel") { paramDialogInterface, paramInt -> }
        dialog.show()
    }

    private fun isLocationEnabled(): Boolean {
        var locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER)
    }

    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                AlertDialog.Builder(this)
                    .setTitle("Location Permission Needed")
                    .setMessage("This app needs the Location permission, please accept to use location functionality")
                    .setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION_CODE)
                    })
                    .create()
                    .show()

            } else ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION_CODE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_LOCATION_CODE -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "permission granted", Toast.LENGTH_LONG).show()
                    }
                } else {
                    // permission denied, boo! Disable the functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show()
                }
                return
            }
        }
    }

    override fun onStart() {
        super.onStart()
        mGoogleApiClient?.connect()
    }

    override fun onStop() {
        super.onStop()
        if (mGoogleApiClient!!.isConnected()) {
            mGoogleApiClient!!.disconnect()
        }
    }

    override fun onMapReady(p0: GoogleMap) {
        TODO("Not yet implemented")
    }
}