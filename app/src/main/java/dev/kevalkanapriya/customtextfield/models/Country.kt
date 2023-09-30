package dev.kevalkanapriya.customtextfield.models

import java.util.Locale

data class Country(
    private var cCodes: String,
    val countryPhoneCode: String = "+91",
    val cNames:String = "in",
    val flagResID: Int = R.drawable.`in`
) {
    val countryCode = cCodes.lowercase(Locale.getDefault())
}