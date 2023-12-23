package System

import kotlinx.serialization.Serializable

@Serializable
data class Ticket(
    val id: Int,
    val session: Session,
    val seat: Seat,
    val price: Double,
    // Другие свойства билета
)