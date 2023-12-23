package System

import java.time.LocalDateTime
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class Session(
    val id: Int,
    val film: Film,
    var startTime: String,
    val seats: List<Seat>,
    // Другие свойства сеанса
)