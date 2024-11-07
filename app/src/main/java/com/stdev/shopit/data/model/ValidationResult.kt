package com.stdev.shopit.data.model


// this is for validation purpose
data class ValidationResult(
    val successful: Boolean,
    val error: String? = null
)
