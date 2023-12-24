package System

import java.time.LocalDateTime

fun main(args: Array<String>) {
    val filmRepository = DataRepository(Film::class.java, "data")
    val sessionRepository = DataRepository(Session::class.java, "data")

    val film1 = Film(id = 66, title = "Inception", durationMinutes = 150)
    filmRepository.add(film1)

    val seats = listOf(
        Seat(2, 2, true),
        Seat(2, 3, true),
        Seat(2, 4, false)
    )
    val session = Session(id = 55, film = film1, startTime = "2024-01-01T10:00", seats = seats)
    sessionRepository.add(session)

    val filmsList = filmRepository.getAll()
    println("Films: $filmsList")

    val sessionList = sessionRepository.getAll()
    println("Sessions: $sessionList")
}
