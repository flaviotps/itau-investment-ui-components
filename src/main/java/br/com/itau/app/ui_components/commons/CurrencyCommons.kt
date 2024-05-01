package br.com.itau.app.ui_components.commons

import android.icu.text.DecimalFormat
import android.icu.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.Locale


/**
 * Extension function to format a number as currency.
 *
 * @receiver The number to be formatted.
 * @return The formatted currency string.
 */
fun Number.formatCurrency(): String {
    val numberFormat = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
    return numberFormat.format(this)
}



/**
 * Extension function to format a float.
 *
 * @receiver The float to be formatted.
 * @return The formatted string.
 */
fun Float.format(locale: Locale = Locale.getDefault()): String {
    val symbols = DecimalFormatSymbols(locale)
    val decimalFormat = DecimalFormat("#.##", symbols)
    return decimalFormat.format(this)
}