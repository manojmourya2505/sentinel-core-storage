package com.sentinelbank.core.storage.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sentinelbank.core.storage.db.converters.EncryptedFieldConverters
import com.sentinelbank.core.storage.db.converters.EnumConverters

/**
 * Local Room database holding mock banking data (accounts, cards, transactions) used until a
 * real backend exists. `core-storage` has no networking of its own.
 */
@Database(
    entities = [AccountEntity::class, CardEntity::class, TransactionEntity::class],
    version = 1,
    exportSchema = false,
)
@TypeConverters(EncryptedFieldConverters::class, EnumConverters::class)
abstract class SentinelDatabase : RoomDatabase() {

    abstract fun accountDao(): AccountDao
    abstract fun cardDao(): CardDao
    abstract fun transactionDao(): TransactionDao

    companion object {
        private const val DATABASE_NAME = "sentinel_bank.db"

        @Volatile
        private var instance: SentinelDatabase? = null

        fun getInstance(context: Context): SentinelDatabase =
            instance ?: synchronized(this) {
                instance ?: build(context).also { instance = it }
            }

        private fun build(context: Context): SentinelDatabase =
            Room.databaseBuilder(
                context.applicationContext,
                SentinelDatabase::class.java,
                DATABASE_NAME,
            ).build()
    }
}
