package com.sweak.githubtrends.features.repository_details.util

import java.text.DateFormat
import java.util.Date
import java.util.Locale

fun getFormattedDate(timestamp: Long): String {
    // Using Locale.ENGLISH instead of Locale.getDefault() for consistency with english strings:
    val formatter = DateFormat.getDateInstance(DateFormat.LONG, Locale.ENGLISH)
    val date = Date(timestamp)
    return formatter.format(date)
}