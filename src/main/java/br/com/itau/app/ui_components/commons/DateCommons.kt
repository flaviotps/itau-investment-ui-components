package br.com.itau.app.ui_components.commons

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

fun dateToday(): String {
    val date = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("d 'de' MMMM", Locale("pt", "BR"))
    return formatter.format(date)
}