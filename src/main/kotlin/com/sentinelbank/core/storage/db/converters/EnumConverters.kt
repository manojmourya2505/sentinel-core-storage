package com.sentinelbank.core.storage.db.converters

import androidx.room.TypeConverter
import com.sentinelbank.core.storage.db.CardType
import com.sentinelbank.core.storage.db.TransactionCategory
import com.sentinelbank.core.storage.db.TransactionStatus
import com.sentinelbank.core.storage.db.TransactionType

/** Room [TypeConverter]s for the plain enums used across storage entities. */
class EnumConverters {

    @TypeConverter
    fun fromCardType(value: CardType): String = value.name

    @TypeConverter
    fun toCardType(value: String): CardType = CardType.valueOf(value)

    @TypeConverter
    fun fromTransactionType(value: TransactionType): String = value.name

    @TypeConverter
    fun toTransactionType(value: String): TransactionType = TransactionType.valueOf(value)

    @TypeConverter
    fun fromTransactionCategory(value: TransactionCategory): String = value.name

    @TypeConverter
    fun toTransactionCategory(value: String): TransactionCategory =
        TransactionCategory.valueOf(value)

    @TypeConverter
    fun fromTransactionStatus(value: TransactionStatus): String = value.name

    @TypeConverter
    fun toTransactionStatus(value: String): TransactionStatus = TransactionStatus.valueOf(value)
}
