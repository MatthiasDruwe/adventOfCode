import java.io.File

class Day2 {
    fun start() {
        println("Day2");
        val steps = File("src/main/resources", "Day2.txt")
            .readLines()
            .map { line -> line.split(" ")
                .let{  Pair<String, Int>(it[0].toString(), it[1].toInt()) }
            }
        var verticalPosition = 0;
        var horizontalPosition = 0 ;
        steps.forEach {
            when(it.first) {
                "forward" -> horizontalPosition += it.second
                "down" -> verticalPosition+=it.second
                "up" -> verticalPosition-=it.second
            }
        }

        println(verticalPosition)
        println(horizontalPosition)
        println("answer part1: ${verticalPosition*horizontalPosition}")

        var verticalPositionPart2 = 0;
        var horizontalPositionPart2 = 0;
        var aim=0
        steps.forEach {
            when(it.first) {
                "forward" -> {
                    horizontalPositionPart2 += it.second
                    verticalPositionPart2 += (it.second * aim)
                }
                "down" -> aim+=it.second
                "up" -> aim-=it.second
            }
        }

        println(verticalPositionPart2)
        println(horizontalPositionPart2)
        println("answer part2: ${verticalPositionPart2*horizontalPositionPart2}")

    }
}