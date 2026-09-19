package com.sentinelbank.core.storage.db

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * A single ledger entry for an [AccountEntity]. [amount] is minor units (cents) as a [Long] —
 * never a floating point type — to avoid rounding drift in money math.
 */
@Entity(
    tableName = "transactions",
    foreignKeys = [
        ForeignKey(
            entity = AccountEntity::class,
            parentColumns = ["id"],
            childColumns = ["accountId"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [Index("accountId"), Index("timestamp")],
)
data class TransactionEntity(
    @PrimaryKey
    val id: String,
    val accountId: String,
    val amount: Long,
    val type: TransactionType,
    val description: String,
    val counterpartyName: String,
    val timestamp: Long,
    val category: TransactionCategory,
    val status: TransactionStatus,
)
