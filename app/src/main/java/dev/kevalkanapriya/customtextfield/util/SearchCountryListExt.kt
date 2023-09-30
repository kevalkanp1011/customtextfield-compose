package dev.kevalkanapriya.customtextfield.util

import android.content.Context
import dev.kevalkanapriya.customtextfield.models.Country
import dev.kevalkanapriya.customtextfield.ui.textfield.util.getCountryName

fun List<Country>.searchCountry(key: String, context: Context): MutableList<Country> {
    val tempList = mutableListOf<Country>()
    this.forEach {
        if (context.resources.getString(getCountryName(it.countryCode)).lowercase().contains(key.lowercase())) {
            tempList.add(it)
        }
    }
    return tempList
}