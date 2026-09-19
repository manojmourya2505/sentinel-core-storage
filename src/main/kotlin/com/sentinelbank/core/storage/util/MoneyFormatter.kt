package com.sentinelbank.core.storage.util

import com.sentinelbank.core.storage.db.TransactionCategory
import java.math.BigDecimal
import java.math.RoundingMode

/**
 * Pure helpers for working with money stored as minor units (cents) and for inferring a mock
 * [TransactionCategory] from free-text merchant/description strings. Kept dependency-free so it
 * can be unit tested on the plain JVM without an Android/Robolectric runtime.
 */
object MoneyFormatter {

    /** Converts minor units (e.g. cents) to a "1,234.56" style major-unit decimal string. */
    fun minorUnitsToDisplayString(minorUnits: Long, fractionDigits: Int = 2): String {
        require(fractionDigits >= 0) { "fractionDigits must be >= 0" }
        val divisor = BigDecimal.TEN.pow(fractionDigits)
        val major = BigDecimal(minorUnits).divide(divisor, fractionDigits, RoundingMode.HALF_UP)
        return major.toPlainString()
    }

    /** Prefixes [minorUnitsToDisplayString] with a currency symbol, e.g. "$1,234.56". */
    fun minorUnitsToCurrencyString(
        minorUnits: Long,
        currencySymbol: String = "$",
        fractionDigits: Int = 2,
    ): String = "$currencySymbol${minorUnitsToDisplayString(minorUnits, fractionDigits)}"
}

/**
 * Very small keyword-based category inference for mock transactions, used to seed/label demo
 * data before a real categorization backend exists.
 */
object CategoryInference {

    private val keywordMap: List<Pair<TransactionCategory, List<String>>> = listOf(
        TransactionCategory.FOOD to listOf(
            "restaurant", "cafe", "coffee", "grocery", "grocer", "diner", "eatery",
        ),
        TransactionCategory.TRANSPORT to listOf(
            "uber", "lyft", "taxi", "transit", "fuel", "gas station", "parking",
        ),
        TransactionCategory.SHOPPING to listOf(
            "mall", "store", "shop", "amazon", "retail", "market",
        ),
        TransactionCategory.BILLS to listOf(
            "utility", "utilities", "electric", "water bill", "internet", "insurance", "rent",
        ),
        TransactionCategory.TRANSFER to listOf(
            "transfer", "wire", "p2p", "venmo", "zelle",
        ),
    )

    /** Infers a [TransactionCategory] from a merchant/description string, defaulting to OTHER. */
    fun infer(description: String): TransactionCategory {
        val lower = description.lowercase()
        return keywordMap.firstOrNull { (_, keywords) ->
            keywords.any { keyword -> lower.contains(keyword) }
        }?.first ?: TransactionCategory.OTHER
    }
}
