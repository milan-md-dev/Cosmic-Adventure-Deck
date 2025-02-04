package com.miles.cosmicadventuredeck.data

import android.util.Log
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface CardRepository {

    suspend fun getCard(id: Int): Result<Card>

    suspend fun getAllCards(): Result<List<Card>>

    suspend fun insertAllCards(cards: List<Card>)

    suspend fun deleteAllCards()
}

class CardRepositoryImplementation(
    private val cardDao: CardDao,
    private val supabaseClient: SupabaseClient
) : CardRepository {

    override suspend fun getCard(id: Int): Result<Card> {
        return withContext(Dispatchers.IO) {
            try {
                val card = cardDao.getCard(id = id)
                Result.Success(card)
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
    }

    override suspend fun getAllCards(): Result<List<Card>> {
        return withContext(Dispatchers.IO) {
            try {
                val cards = cardDao.getAllCards()
                Result.Success(cards)
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
    }

    override suspend fun insertAllCards(cards: List<Card>) {
        withContext(Dispatchers.IO) {
            Log.d("CardRepository", "Inserting ${cards.size} cards into Room")
            cardDao.deleteAllCards()
            cardDao.insertAllCards(cards)
        }
    }

    override suspend fun deleteAllCards() {
        withContext(Dispatchers.IO) {
            cardDao.deleteAllCards()
        }
    }

    suspend fun fetchAllCardsFromSupabase(): List<Card> {
        return withContext(Dispatchers.IO) {
            try {
                val result = supabaseClient.from("Cards")
                    .select().decodeList<Card>()
                Log.d("CardRepository", "Fetched ${result.size} cards from Supabase")
                result
            } catch (e: Exception) {
                Log.e("CardRepository", e.toString())
                emptyList()
            }
        }
    }
}