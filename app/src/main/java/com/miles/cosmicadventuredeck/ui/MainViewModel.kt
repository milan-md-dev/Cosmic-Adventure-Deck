package com.miles.cosmicadventuredeck.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miles.cosmicadventuredeck.data.Card
import com.miles.cosmicadventuredeck.data.CardRepositoryImplementation
import com.miles.cosmicadventuredeck.data.CardToShow
import com.miles.cosmicadventuredeck.data.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class MainViewModel(
    private val repository: CardRepositoryImplementation
) : ViewModel(), KoinComponent {

    private val defaultCardToShow = CardToShow()

    private val _cardToShow: MutableStateFlow<CardToShow> = MutableStateFlow(defaultCardToShow)
    val cardToShow: StateFlow<CardToShow> = _cardToShow.asStateFlow()

    init {
        putTheCardTogether()
    }

    fun putTheCardTogether() {
        viewModelScope.launch {
            // Generate random Card and Encounter numbers
            val cardNumber = generateCardNumber()
            val encounterNumber = generateRandomEncounterNumber()

            // Get the card with the generated Number
            val returnedCard = getTheCardWithSelectedNumber(id = cardNumber)

            //
            if (returnedCard != null) {
                val returnedEncounter = setTheCardEncounter(
                    returnedCard = returnedCard,
                    encounter = encounterNumber
                )
                if (returnedEncounter != null) {
                    generateCardToShow(
                        returnedCard = returnedCard,
                        returnedEncounter = returnedEncounter
                    )
                }
            } else {
                Log.e("putTheCardTogether", "Failed to fetch the card.")
            }
        }
    }

    private fun generateCardNumber(): Int {
        return (0..98).random()
    }

    private fun generateRandomEncounterNumber(): Int {
        return (0..2).random()
    }

    private suspend fun getTheCardWithSelectedNumber(id: Int): Card? {
        return when (val result = repository.getCard(id = id)) {
            is Result.Success -> result.data
            is Result.Error -> {
                val exception = result.exception
                Log.e("getTheCardWithSelectedNumber", exception.toString())
                null
            }
        }
    }

    private fun setTheCardEncounter(
        returnedCard: Card,
        encounter: Int
    ): String? {
        val encounters = listOf(
            returnedCard.cardEncounter1,
            returnedCard.cardEncounter2,
            returnedCard.cardEncounter3
        )
        return encounters.getOrNull(encounter)
    }

    private fun generateCardToShow(
        returnedCard: Card,
        returnedEncounter: String
    ) {
        val newCardToShow = CardToShow(
            cardName = returnedCard.cardName.toString(),
            cardImage = returnedCard.cardImage.toString(),
            cardEncounterText = returnedEncounter,
            cardReady = true
        )
        _cardToShow.value = newCardToShow
        Log.d(
            "generateCardToShow",
            "cardToShow: " +
                    "${_cardToShow.value.cardName}, " +
                    "${_cardToShow.value.cardImage}, " +
                    _cardToShow.value.cardEncounterText +
                    "${_cardToShow.value.cardReady}"
        )
    }


}