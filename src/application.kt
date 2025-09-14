import kotlin.math.*
import kotlin.random.Random

class Human(
    var name: String,
    var surname: String,
    var second_name: String,
    var age: Int,
    var speed: Double
) {
    var x: Double = 0.0
    var y: Double = 0.0
    private var kuda: Double = Random.nextDouble(0.0, 2 * PI)

    init {
        println("Создан объект: $name $surname $second_name, возраст: $age, скорость: $speed м/с")
    }

    fun move(timeStep: Double) { // move с Random Walk
        val kudaChange = Random.nextDouble(-PI/4, PI/4) // случайное изменение направления 45 градусов
        kuda = (kuda + kudaChange) % (2 * PI)

        val speedChange = speed * Random.nextDouble(-0.3, 0.3) // случайное изменение скорости ±30%
        speed = max(0.1, speed + speedChange)

        val dx = speed * timeStep * cos(kuda) // расчет перемещения по осям
        val dy = speed * timeStep * sin(kuda)

        x += dx // меняем координаты
        y += dy
        val gr = Math.toDegrees(kuda).toInt()
        val fullName = "$name $surname $second_name"
        println("$fullName: переместился в (${"%.1f".format(x)}, ${"%.1f".format(y)}), " +
                "скорость: ${"%.1f".format(speed)} м/с, направление: ${gr}°")
    }

    fun moveTo(_toX: Int, _toY: Int) {
        x = _toX.toDouble()
        y = _toY.toDouble()
        println("$name $surname $second_name: перемещен в: $x, $y")
    }

    fun printStatus() {
        val fullName = "$name $surname $second_name"
        val gr = Math.toDegrees(kuda).toInt()
        println("$fullName: позиция (${"%.1f".format(x)}, ${"%.1f".format(y)}), " +
                "скорость ${"%.1f".format(speed)} м/с, направление: ${gr}°")
    }
}

fun main() {
    println("=== СИМУЛЯЦИЯ RANDOM WALK ===")

    val petya = Human("ааа", "ааа", "аа", 18, 0.9)
    petya.moveTo(10, 100)
    println("Координата X: ${petya.x}")

    println()

    val humans = arrayOf(
        Human("Эртине", "Артаа", "Адыгжыевич", 19, 1.0),
        Human("Арина", "Бубенина", "Ивановна", 18, 1.1),
        Human("Дмитрий", "Воробьев", "Андреевич", 19, 1.2),
        Human("Егор", "Григорьев", "Васильевич", 19, 1.3),
        Human("Сергей", "Демин", "Алексеевич", 19, 1.4),
        Human("Артем", "Добромилов", "Александрович", 19, 1.5),
        Human("Мария", "Дроздова", "Геннадьевна", 19, 1.6)
    )

    val sTime = 30.0
    val step = 1.0

    println("\nНачало симуляции на $sTime секунд (шаг: $step сек)")
    println("=".repeat(60))

    var time_0 = 0.0
    while (time_0 < sTime) {
        println("\n--- Время: ${"%.1f".format(time_0)} сек ---")

        humans.forEach { it.move(step) } // шаги
        Thread.sleep(500)
        time_0 += step
    }

    println("\n" + "=".repeat(60))
    println("\nEnd")
}
