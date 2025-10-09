package com.example.spyon

import android.content.Context

class GameEngine(val context: Context) {
    val dp = DbHelper(context, null)

    val playerList = dp.getAll()

    val placeList: Array<String> = context.resources.getStringArray(R.array.places_array)

    fun getRandomPlace(): String? {
        placeList.shuffle()
        return placeList.random()
    }

    fun getRandomPlayer(): String? {
        return playerList.random()
    }

    fun getAllPlayers(): MutableList<String> {
        return playerList
    }
}