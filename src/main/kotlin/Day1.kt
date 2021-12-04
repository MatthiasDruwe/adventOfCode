import java.io.File

class Day1() {
    fun start() {
        println("Day1");
        val lines = File("src/main/resources", "Day1.txt").readLines().map { it.toInt() };
        val answer = lines.windowed(2).count{ (a,b) -> a < b}
        println("part 1: ${answer}");

        val answer2 = lines.windowed(3).windowed(2).count{(a,b ) -> a.sum() < b.sum()}
        println("part 2: ${answer2}");
    }
}