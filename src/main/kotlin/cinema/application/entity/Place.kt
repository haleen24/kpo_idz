package org.example.cinema.application.entity

import kotlinx.serialization.Serializable

@Serializable
data class Place(var row: Int, var place: Int, var isBought: Boolean, var isOccupied: Boolean)