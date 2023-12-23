package System

import kotlinx.serialization.Serializable

@Serializable
data class Film(
    val id: Int,
    var title: String,
    var durationMinutes: Int,
    // Другие свойства фильма
)