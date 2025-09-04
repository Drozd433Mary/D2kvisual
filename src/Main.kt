//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    var name = "Тевирп";

    for (i in name.reversed())
    {
        print(i);
    }
    println();

    for (i in 1..10)
    {
        if (i % 2 != 0) continue
        print("$i ");
    }
    println();

}