import java.util.*

// Класс, представляющий процесс обработки жалоб
class ComplaintProcess {
    private var requestId: String? = null

    fun registerComplaint(): Boolean {
        // Операция зарегистрировать заявку
        requestId = generateRequestId()
        println("Заявка $requestId зарегистрирована")
        return true
    }

    fun processComplaint(): Boolean {
        // Операция для отправки заявки и рассмотрения заявки
        val paymentSuccessful = processPayment()
        if (paymentSuccessful) {
            // Если оплата прошла успешно, обработать заявку
            processOrder()
            return true
        } else {
            // Иначе, таймаут
            println("Таймаут: Заявка $requestId не обработана")
            return false
        }
    }

    private fun processPayment(): Boolean {
        // Метод для проверки оплаты заказчиком в течение трех банковских дней
        val random = Random()
        val paymentSuccessful = random.nextBoolean()
        if (paymentSuccessful) {
            println("Оплата заявки $requestId произведена успешно")
        } else {
            println("Таймаут: Оплата заявки $requestId не произведена в течение трех банковских дней")
        }
        return paymentSuccessful
    }

    private fun processOrder() {
        // Операция обработать заявку и выполнить заказ
        println("Заявка $requestId обработана. Заказ принят к выполнению.")
        performOrder()
    }

    private fun performOrder() {
        // Операция выполнить заказ
        println("Заказ для заявки $requestId выполнен. Контроль соответствия начат.")
        controlOrder()
    }

    private fun controlOrder() {
        // Операция контроля на соответствие
        val random = Random()
        val isSatisfactory = random.nextBoolean()
        if (isSatisfactory) {
            println("Контроль соответствия успешен для заявки $requestId.")
        } else {
            println("Контроль соответствия неудачен для заявки $requestId. Отказ в выполнении заказа.")
        }
        saveToDatabase(isSatisfactory)
    }

    private fun saveToDatabase(isSatisfactory: Boolean) {
        // Операция сохрания в БД
        val databaseEntry = DatabaseEntry(requestId!!, isSatisfactory)
        Database.entries.add(databaseEntry)
        if (isSatisfactory) {
            println("Заказ для заявки $requestId сохранен в БД!")
        } else {
            println("Отказ в выполнении заказа для заявки $requestId. Заказ не сохранен в БД :(")
        }
    }

    private fun generateRequestId(): String {
        return UUID.randomUUID().toString()
    }
}

// Имитация базы данных
data class DatabaseEntry(val requestId: String, val isSatisfactory: Boolean)

object Database {
    val entries: MutableList<DatabaseEntry> = mutableListOf()
}

fun main() {
    // Пример использования
    val complaintProcess = ComplaintProcess()
    if (complaintProcess.registerComplaint()) {
        complaintProcess.processComplaint()
    }

    println("\nРезультаты в базе данных:")
    if (Database.entries.size == 0) {
        println("База данных пустая")
    } else {
        for (entry in Database.entries) {
            println("Заявка ${entry.requestId}, Успешность обработки: ${entry.isSatisfactory}")
        }
    }
}
