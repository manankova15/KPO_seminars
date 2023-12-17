abstract class Figure (val height : Int, val diameter : Int) {
    abstract fun GetVolume(): Double
    abstract fun GetLateralSurfaceArea(): Double
    abstract fun GetBaseProjection(): Double
}

class Cone(height: Int, diameter: Int) : Figure(height, diameter) {
    override fun GetVolume(): Double {
        return (Math.PI * diameter * diameter * height) / 12.0
    }

    override fun GetLateralSurfaceArea(): Double {
        return (height * diameter) / 2.0
    }

    override fun GetBaseProjection(): Double {
        return Math.PI * diameter * diameter / 4.0
    }
}

class Cube(height : Int, diameter : Int) : Figure(height, diameter) {
    override fun GetVolume(): Double {
        return (height*height*height).toDouble()
    }

    override fun GetLateralSurfaceArea(): Double {
        return (height*height).toDouble()
    }

    override fun GetBaseProjection(): Double {
        return (height*height).toDouble()
    }
}

class Pyramid(height: Int, diameter: Int) : Figure(height, diameter) {
    override fun GetVolume(): Double {
        return (diameter * diameter * height) / 3.0
    }

    override fun GetLateralSurfaceArea(): Double {
        return (diameter * height / 2).toDouble()
    }

    override fun GetBaseProjection(): Double {
        return (diameter*diameter).toDouble()
    }
}

fun main(args: Array<String>) {
    print("Введите тип фигуры (конус/куб/пирамида): ")
    val figure = readln()
    print("Введите высоту фигуры: ")
    val height = readln().toInt()
    print("Введите основание/диаметр фигуры: ")
    val diameter = readln().toInt()
    println("")

    if (figure.lowercase() == "конус") {
        var f = Cone(height, diameter)
        println("Объем конуса: ${f.GetVolume()}")
        println("Площадь боковой проекции: ${f.GetLateralSurfaceArea()}")
        println("Площадь проекции сверху: ${f.GetBaseProjection()}")
    }
    else if (figure.lowercase() == "куб") {
        var f = Cube(height, diameter)
        println("Объем куба: ${f.GetVolume()}")
        println("Площадь боковой проекции: ${f.GetLateralSurfaceArea()}")
        println("Площадь проекции сверху: ${f.GetBaseProjection()}")
    }
    else if (figure.lowercase() == "пирамида") {
        var f = Pyramid(height, diameter)
        println("Объем пирамиды: ${f.GetVolume()}")
        println("Площадь боковой проекции: ${f.GetLateralSurfaceArea()}")
        println("Площадь проекции сверху: ${f.GetBaseProjection()}")
    }
    else {
        println("Введена некорректная фигура")
    }
}