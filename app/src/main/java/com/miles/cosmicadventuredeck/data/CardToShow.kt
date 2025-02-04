package com.miles.cosmicadventuredeck.data

data class CardToShow(
    val cardName: String = "The Decadent Monk",
    val cardImage: String = "the_decadent_monk",
    val cardEncounterText: String = "The party encounters the Decadent Monk in a small tavern, attempting to create a “virtuous feast” to prove to his former order that he has overcome his gluttonous tendencies. However, his culinary skills are abysmal, and he begs the party for help.\n" +
            "\n" +
            "Encounter Details:\n" +
            "\n" +
            "The Puzzle: \n" +
            "The party must assist the monk in preparing a meal that represents balance and restraint by:\n" +
            "1. Gathering ingredients from the local market, avoiding distractions like free samples of exotic snacks or pushy vendors.\n" +
            "2. Solving humorous food-related puzzles, such as deciphering a recipe written in an ancient dialect or figuring out how to repair a broken oven.\n" +
            "3. Managing the monk’s tendency to sneak bites of the dishes, requiring the party to stay vigilant.\n" +
            "\n" +
            "Outcome: \n" +
            "The feast either impresses a visiting member of the monk’s order or hilariously backfires, depending on the party’s success.\n" +
            "\n" +
            "Reward: \n" +
            "The monk shares his gratitude with a satirical “blessing of balance,” offering the party a minor buff to charisma or luck.",
    val cardReady: Boolean = false
)
