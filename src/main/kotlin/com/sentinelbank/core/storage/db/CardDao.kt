package com.sentinelbank.core.storage.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CardDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(card: CardEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(cards: List<CardEntity>)

    @Update
    suspend fun update(card: CardEntity)

    @Delete
    suspend fun delete(card: CardEntity)

    @Query("DELETE FROM cards WHERE id = :cardId")
    suspend fun deleteById(cardId: String)

    @Query("SELECT * FROM cards WHERE id = :cardId")
    suspend fun getById(cardId: String): CardEntity?

    @Query("SELECT * FROM cards WHERE accountId = :accountId")
    suspend fun getForAccount(accountId: String): List<CardEntity>

    @Query("SELECT * FROM cards WHERE accountId = :accountId")
    fun observeForAccount(accountId: String): Flow<List<CardEntity>>

    @Query("SELECT * FROM cards")
    fun observeAll(): Flow<List<CardEntity>>

    @Query("UPDATE cards SET isFrozen = :frozen WHERE id = :cardId")
    suspend fun setFrozen(cardId: String, frozen: Boolean)
}
