import kotlin.math.*

class Driver(
    name: String,
    surname: String,
    second_name: String,
    age: Int,
    current_speed: Double
) : Human(name, surname, second_name, age, current_speed) {

    override fun move(timeStep: Double) {
        val dx = current_speed * timeStep * cos(direction)
        val dy = current_speed * timeStep * sin(direction)
        x+=dx
        y+=dy
        val gr = Math.toDegrees(direction).toInt()
        val fullName = "$name $surname $second_name"
        println("$fullName (Driver): переместился в (${"%.1f".format(x)}, ${"%.1f".format(y)}), " +
                "скорость: ${"%.1f".format(current_speed)} м/с, направление: ${gr}°")
    }
}