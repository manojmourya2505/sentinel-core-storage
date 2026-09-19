package com.sentinelbank.core.storage.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sentinelbank.core.storage.db.converters.EncryptedField

/**
 * A mock bank account. [accountNumber] is stored encrypted-at-rest via the injected
 * [FieldCipher] (see [FieldCipherProvider]) through [EncryptedFieldConverters].
 *
 * [balance] is always minor units (cents) as a [Long] — never a floating point type — to avoid
 * rounding drift in money math.
 */
@Entity(tableName = "accounts")
data class AccountEntity(
    @PrimaryKey
    val id: String,
    val accountNumber: EncryptedField,
    val accountType: String,
    @ColumnInfo(name = "balance_minor_units")
    val balance: Long,
    val currency: String,
    val createdAt: Long,
)
