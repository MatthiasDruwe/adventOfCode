import kotlin.math.abs
import kotlin.math.min

class Day7 {

    fun start() {
        val data = getListOfData("Day7.txt")[0].split(",").map { it.toInt() }
        val maxValue = data.maxOf { it }
        val minValue = data.minOf { it }

        var cheapestFuel = data.sumOf { facultySum(abs(it - minValue) )}

        for (i in minValue..maxValue) {

            cheapestFuel = min(data.sumOf { facultySum(abs(it - i)) }, cheapestFuel)
        }
        print(cheapestFuel)
    }

    private fun facultySum(num: Int):Int {
        var sum = 0
        for(i in num downTo 0){
            sum+=i
        }
        return sum
    }
}