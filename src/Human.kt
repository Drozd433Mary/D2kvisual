import kotlin.math.*
import kotlin.random.Random

open class Human(
    var name: String,
    var surname: String,
    var second_name: String,
    var age: Int ,
    override var current_speed: Double
) :Movable {
    override var x: Double = 0.0
    override var y: Double = 0.0
    protected var direction: Double = Random.nextDouble(0.0, 2 * PI)

    init {
        println("Создан объект: $name $surname $second_name, возраст: $age, скорость: $current_speed м/с")
    }

    override fun move(timeStep: Double) { // move с Random Walk
        val direction_Change = Random.nextDouble(-PI/4, PI/4) // случайное изменение направления 45 градусов
        direction = (direction + direction_Change) % (2 * PI)

        val speedChange = current_speed * Random.nextDouble(-0.3, 0.3) // случайное изменение скорости ±30%
        current_speed = max(0.1, current_speed + speedChange)

        val dx = current_speed * timeStep * cos(direction) // расчет перемещения по осям
        val dy = current_speed * timeStep * sin(direction)

        x += dx // меняем координаты
        y += dy
        val gr = Math.toDegrees(direction).toInt()
        val fullName = "$name $surname $second_name"
        println("$fullName: переместился в (${"%.1f".format(x)}, ${"%.1f".format(y)}), " +
                "скорость: ${"%.1f".format(current_speed)} м/с, направление: ${gr}°")
    }

    fun moveTo(_toX: Int, _toY: Int) {
        x = _toX.toDouble()
        y = _toY.toDouble()
        println("$name $surname $second_name: перемещен в: $x, $y")
    }
}