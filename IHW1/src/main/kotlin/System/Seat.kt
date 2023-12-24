package System

import kotlinx.serialization.Serializable

@Serializable
data class Seat(
    val row: Int,
    val place: Int,
    var isOccupied: Boolean = false
): Identifiable {
    override var id : Int = 0
    init {
        id = row * 1000 + place
    }
    override fun toString(): String {
        return "Seat: row=$row, place=$place"
    }
    fun getPlace() : String {
        return "row=$row, place=$place"
    }
}