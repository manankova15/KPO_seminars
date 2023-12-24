package System

import kotlinx.serialization.Serializable
// var TicketId = (DataRepository(Ticket::class.java, "data").getAll().maxByOrNull { it.id }?.id ?: 0).toInt()

@Serializable
data class Ticket(
    val session: Session,
    val seat: Seat,
    val price: Int
): Identifiable {
    override var id : Int = 0
    init {
        id = session.id * 10000000 + seat.id
    }
    override fun toString(): String {
        return "Ticket: id=$id, session=${session.getFilmName()} at ${session.startTime}, ${seat.getPlace()}, price=$price"
    }
}