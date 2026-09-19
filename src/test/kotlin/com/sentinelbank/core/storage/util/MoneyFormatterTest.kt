package com.sentinelbank.core.storage.util

import com.sentinelbank.core.storage.db.TransactionCategory
import kotlin.test.Test
import kotlin.test.assertEquals

class MoneyFormatterTest {

    @Test
    fun `converts whole dollar minor units to display string`() {
        assertEquals("12.00", MoneyFormatter.minorUnitsToDisplayString(1200))
    }

    @Test
    fun `converts fractional minor units to display string`() {
        assertEquals("19.99", MoneyFormatter.minorUnitsToDisplayString(1999))
    }

    @Test
    fun `handles zero`() {
        assertEquals("0.00", MoneyFormatter.minorUnitsToDisplayString(0))
    }

    @Test
    fun `handles negative amounts`() {
        assertEquals("-5.50", MoneyFormatter.minorUnitsToDisplayString(-550))
    }

    @Test
    fun `prefixes currency symbol`() {
        assertEquals("$1234.56", MoneyFormatter.minorUnitsToCurrencyString(123456))
    }

    @Test
    fun `supports custom fraction digits`() {
        assertEquals("12", MoneyFormatter.minorUnitsToDisplayString(12, fractionDigits = 0))
    }
}

class CategoryInferenceTest {

    @Test
    fun `infers food from grocery keyword`() {
        assertEquals(TransactionCategory.FOOD, CategoryInference.infer("Whole Foods Grocery"))
    }

    @Test
    fun `infers transport from uber keyword`() {
        assertEquals(TransactionCategory.TRANSPORT, CategoryInference.infer("Uber Trip 4021"))
    }

    @Test
    fun `infers bills from utility keyword`() {
        assertEquals(TransactionCategory.BILLS, CategoryInference.infer("City Electric Utility"))
    }

    @Test
    fun `infers transfer from venmo keyword`() {
        assertEquals(TransactionCategory.TRANSFER, CategoryInference.infer("Venmo to Jane"))
    }

    @Test
    fun `is case insensitive`() {
        assertEquals(TransactionCategory.SHOPPING, CategoryInference.infer("AMAZON.COM PURCHASE"))
    }

    @Test
    fun `defaults to other when no keyword matches`() {
        assertEquals(TransactionCategory.OTHER, CategoryInference.infer("Mystery Corp 88213"))
    }
}
