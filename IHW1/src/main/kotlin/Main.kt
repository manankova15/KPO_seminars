package System
import java.time.LocalDateTime
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

fun main(args: Array<String>) {
    val dataRepository = DataRepository("data")
    val objectMapper = ObjectMapper().registerModule(JavaTimeModule())

    val film1 = Film(id = 999, title = "Inception", durationMinutes = 150)
    dataRepository.addFilm(film1)

    val time = LocalDateTime.of(2023, 12, 31, 18, 0,0)
    val seats = listOf(
        Seat(2,2,true),
        Seat(2,3,true),
        Seat(2,4,false)
    )
    val session = Session(id = 1, film = film1, startTime = "2024-01-01T10:00", seats = seats)
    dataRepository.addSession(session)
    //val Ticket = Ticket(id = 1, session = "Inception", durationMinutes = 150)
    //dataRepository.addFilm(film)

    val filmsList = dataRepository.getFilms()
    println("Films: $filmsList")

    val sessionList = dataRepository.getSessions()
    println("Sessions: $sessionList")
}