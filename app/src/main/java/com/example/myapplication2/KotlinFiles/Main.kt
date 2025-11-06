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
        Driver("Мария", "Дроздова", "Геннадьевна", 19, 1.6)
    )
    val sTime = 30.0
    val step = 1.0
    println("\nНачало симуляции на $sTime секунд (шаг: $step сек)")
    println("=".repeat(60))
    var time_0 = 0.0
    while (time_0 < sTime) {
        println("\n--- Время: ${"%.1f".format(time_0)} сек ---")

        val threads = humans.map { human ->
            Thread {
                human.move(step)
            }
        }
        threads.forEach { it.start() }
        threads.forEach { it.join() }
        Thread.sleep(500)
        time_0 += step }
    println("\n" + "=".repeat(60))
    println("\nEnd") }