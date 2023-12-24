package System

import kotlinx.serialization.Serializable

@Serializable
data class Seat(
    val id: Int,
    val row: Int,
    val number: Int,
    var isOccupied: Boolean = false
)