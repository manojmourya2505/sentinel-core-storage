package com.sentinelbank.core.storage.db

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.sentinelbank.core.storage.db.converters.EncryptedField

/**
 * A mock debit/credit card linked to an [AccountEntity]. [maskedPan] is stored encrypted-at-rest
 * the same way as [AccountEntity.accountNumber].
 */
@Entity(
    tableName = "cards",
    foreignKeys = [
        ForeignKey(
            entity = AccountEntity::class,
            parentColumns = ["id"],
            childColumns = ["accountId"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [Index("accountId")],
)
data class CardEntity(
    @PrimaryKey
    val id: String,
    val accountId: String,
    val maskedPan: EncryptedField,
    val cardholderName: String,
    val expiryMonth: Int,
    val expiryYear: Int,
    val isFrozen: Boolean,
    val cardType: CardType,
)
