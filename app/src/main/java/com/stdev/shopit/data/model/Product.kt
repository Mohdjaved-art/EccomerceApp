package com.stdev.shopit.data.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("productId")
    val productId: Int,
    @SerializedName("quantity")
    val quantity: Int,
)

