import kotlin.math.*

interface Movable {
    var x: Double
    var y: Double
    var current_speed: Double
    fun move(timeStep: Double)
}