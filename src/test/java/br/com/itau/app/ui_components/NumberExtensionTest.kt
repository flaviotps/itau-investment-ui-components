package br.com.itau.app.ui_components

import br.com.itau.app.ui_components.commons.formatCurrency
import org.junit.Assert.assertEquals
import org.junit.Test


class NumberExtensionTest {

    @Test
    fun formatCurrency_CorrectLocale() {
        val number = 1000.0
        val expectedCurrency = "R$ 1.000,00"

        val formattedCurrency = number.formatCurrency().replace("\u00A0", " ")

        assertEquals(expectedCurrency, formattedCurrency)
    }

}
