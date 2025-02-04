package com.miles.cosmicadventuredeck.data


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "cards")
data class Card(
    @SerialName("cardId")@PrimaryKey(autoGenerate = true) val cardId: Int? = 0,
    @SerialName("cardName")@ColumnInfo(name = "cardName") val cardName: String?,
    @SerialName("cardImage") @ColumnInfo(name = "cardImage") val cardImage: String?,
    @SerialName("cardEncounter1") @ColumnInfo(name = "cardEncounter1") val cardEncounter1: String?,
    @SerialName("cardEncounter2") @ColumnInfo(name = "cardEncounter2") val cardEncounter2: String?,
    @SerialName("cardEncounter3") @ColumnInfo(name = "cardEncounter3") val cardEncounter3: String?
) {
    constructor() : this(
        null,
        null,
        null,
        null,
        null,
        null
    )
}