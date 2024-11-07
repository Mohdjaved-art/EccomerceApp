package com.stdev.shopit.data.model


import com.google.gson.annotations.SerializedName

// for ordering purpose can be implement later

data class Geolocation(
    @SerializedName("lat")
    val lat: String,
    @SerializedName("long")
    val long: String
)