package System

import kotlinx.serialization.Serializable

var SessionId = (DataRepository(Session::class.java, "data").getAll().maxByOrNull { it.id }?.id ?: 0).toInt()

@Serializable
data class Session(
    val film: Film,
    var startTime: String,
): Identifiable {
    override var id : Int = 0
    var seats: List<Seat>
    init {
        id = ++SessionId
        seats = createEmptySeats(maxRow, maxNumber)
    }
    private fun createEmptySeats(maxRow: Int, maxNumber: Int): List<Seat> {
        val emptySeats = mutableListOf<Seat>()
        for (row in 1..maxRow) {
            for (number in 1..maxNumber) {
                emptySeats.add(Seat(row, number))
            }
        }
        return emptySeats
    }
    fun getFilmName(): String {
        return film.getFilmName()
    }
    override fun toString(): String {
        return "Session: id=$id, film=${film.getFilmName()}, start time=$startTime"
    }
    fun printSeats() {
        for (row in 1..maxRow) {
            for (number in 1..maxNumber) {
                val seat = seats.find { it.row == row && it.place == number }
                val seatSymbol = if (seat?.isOccupied == true) "[X]" else "[ ]"
                print("$seatSymbol ")
            }
            println()
        }
    }
}