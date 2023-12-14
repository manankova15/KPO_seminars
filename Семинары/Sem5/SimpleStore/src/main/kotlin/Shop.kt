import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.io.IOException

class Shop {
    var products = mutableListOf(
        Product("Egg", 35, 5.0),
        Product("Sweet", 55, 2.0),
        Product("Tree", 20, 20.0)
    )

    private val cart = mutableListOf<Product>()

    private val users = mutableListOf<User>()

    private var currentUser: User? = null

    fun displayProductList() {
        println("\u001B[1m\n\tAvailable products:\u001B[0m")

        deserialize()

        val border = "+--------------------------------------------------------------------+"
        println(border)
        println("|  Index   |    Name       |   Quantity      |    Price per Unit    |")
        println(border)

        products.forEachIndexed { index, product ->
            println("|   ${index.toString().padEnd(7)}|   ${product.name.padEnd(12)}|   ${product.quantity.toString().padEnd(14)}|   $${product.price.toString().padEnd(18)}|")
        }

        println(border)
    }

    fun addProductToCart(productIndex: Int, quantity: Int) {
        if (productIndex < 0 || productIndex >= products.size) {
            println("\u001B[31mInvalid product index\u001B[0m")
            return
        }

        deserialize()

        val selectedProduct = products[productIndex]

        if (selectedProduct.quantity >= quantity && quantity >= 0) {
            cart.add(Product(selectedProduct.name, quantity, selectedProduct.price))
            println("You have successfully added ${quantity} ${selectedProduct.name}(s) to your cart.")
            selectedProduct.quantity -= quantity
            serialize()
        } else {
            println("Not enough stock available for ${selectedProduct.name}.")
        }
    }

    fun payCart(cardNumber: String) {
        val totalCost = cart.sumOf { it.price * it.quantity }
        println("Total cost: $$totalCost")

        println("Payment successful with card number: $cardNumber")
        cart.clear()
    }

    fun registerUser(username: String, password: String) {
        if (users.any { it.username == username }) {
            println("\u001B[31mUsername already exists. Please choose another one\u001B[0m")
        } else {
            val newUser = User(username, password)
            users.add(newUser)
            println("Registration successful.")
        }
    }

    fun loginUser(username: String, password: String) {
        val user = users.find { it.username == username && it.password == password }
        if (user != null) {
            currentUser = user
            println("Login successful.")
        } else {
            println("\u001B[31mInvalid username or password. Try to register first or enter correct data.\u001B[0m")
        }
    }

    fun logoutUser() {
        currentUser = null
        println("Logout successful.")
    }

    fun deserialize() {
        try {
            var read = File("products.json").readText(Charsets.UTF_8)
            if (read != "") {
                products = Json.decodeFromString<MutableList<Product>>(read)
            }
        } catch (ex : IOException) {
            println("\u001B[31mUnable to write files\u001B[0m")
        } catch (_ : Exception) {
            println("\u001B[31mWarnings while reading\u001B[0m")
        }
    }

    fun serialize() {
        try {
            File("products.json").writeText(Json.encodeToString<MutableList<Product>>(products))
        } catch (ex : IOException) {
            println("\u001B[31mUnable to write info to file\u001B[0m")
        } catch (_ : Exception) {
            println("\u001B[31mWarnings while reading\u001B[0m")
        }
    }
}
