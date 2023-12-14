import kotlinx.serialization.Serializable
import java.io.File
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


@Serializable
data class Product(val name: String, var quantity: Int, val price: Double)

@Serializable
data class User(val username: String, val password: String)

fun main() {
    val shop = Shop()

    var products = mutableListOf(
        Product("Egg", 35, 5.0),
        Product("Sweet", 55, 2.0),
        Product("Tree", 20, 20.0)
    )
    val serializedProducts = Json.encodeToString(products)
    File("products.json").writeText(serializedProducts)

    while (true) {
        println("\n1. Display Product List")
        println("2. Add products to cart")
        println("3. Pay for goods from cart")
        println("4. Register User")
        println("5. Login")
        println("6. Logout")
        println("0. Exit")

        print("\u001B[34mEnter your choice: \u001B[0m")
        when (readLine()?.toIntOrNull()) {
            1 -> shop.displayProductList()
            2 -> {
                println("Do you want to see the list of products first?")
                while (true) {
                    print("Write 'Yes' or 'No': ")
                    when (readLine()?.trim()) {
                        "Yes" -> {
                            shop.displayProductList()
                            break
                        }
                        "No" -> {break}
                        else -> {
                            println("\u001B[34mInvalid input\u001B[0m")
                        }
                    }
                }
                print("Enter the product index you want to buy: ")
                val productIndex = readLine()?.toIntOrNull() ?: continue

                print("Enter the quantity: ")
                val quantity = readLine()?.toIntOrNull() ?: continue

                shop.addProductToCart(productIndex, quantity)
            }
            3 -> {
                while (true) {
                    print("Enter card number(it must contain 16 digits): ")
                    val cardNumber = readLine() ?: continue

                    if (cardNumber.length == 16 && cardNumber.all { it.isDigit() }) {
                        shop.payCart(cardNumber)
                        break
                    } else {
                        println("\u001B[31mError: Invalid card number\u001B[0m")
                    }
                }
            }
            4 -> {
                print("Enter username: ")
                val username = readLine() ?: continue

                while (true) {
                    print("Enter password: ")
                    val password1 = readLine() ?: continue

                    print("Repeat password: ")
                    val password2 = readLine() ?: continue

                    if (password1 == password2) {
                        shop.registerUser(username, password1)
                        break
                    } else {
                        println("\u001B[31mError: Passwords do not match. Please try again.\u001B[0m")
                    }
                }
            }
            5 -> {
                print("Enter username: ")
                val username = readLine() ?: continue

                print("Enter password: ")
                val password = readLine() ?: continue

                shop.loginUser(username, password)
            }
            6 -> shop.logoutUser()
            0 -> {
                println("Exiting the program.")
                return
            }
            else -> println("\u001B[31mInvalid choice. Please try again. \u001B[0m")
        }
    }
}