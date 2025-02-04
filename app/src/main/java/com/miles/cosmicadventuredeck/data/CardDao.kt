package com.miles.cosmicadventuredeck.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface CardDao {

    @Query("SELECT * FROM cards WHERE cardId = :id")
    fun getCard(id: Int): Card

    @Query("SELECT * FROM cards")
    fun getAllCards(): List<Card>

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCards(allCards: List<Card>)

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCard(card: Card)

    @Update
    suspend fun updateCard(card: Card)

    @Query ("DELETE FROM cards")
    suspend fun deleteAllCards()

    @Delete
    suspend fun deleteCard(card: Card)
}