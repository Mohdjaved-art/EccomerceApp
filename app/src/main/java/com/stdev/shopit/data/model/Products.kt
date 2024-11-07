package com.stdev.shopit.data.model



// best deal Adapter products:
data class Products(
    val category: String,
    val description: String,
    val id: Int,
    val image: String,
    val price: Int,
    val rating: Rating,
    val title: String
)