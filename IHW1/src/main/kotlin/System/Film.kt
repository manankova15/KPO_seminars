package System

import kotlinx.serialization.Serializable

var FilmId = (DataRepository(Film::class.java, "data").getAll().maxByOrNull { it.id }?.id ?: 0).toInt()

@Serializable
data class Film(
    var title: String,
    var durationMinutes: Int,
): Identifiable {
    override var id : Int = 0
    init {
        id = ++FilmId
    }
    override fun toString(): String {
        return "Film: id=$id, title=$title, duration(min)=$durationMinutes."
    }

    fun getFilmName(): String {
        return title
    }
}