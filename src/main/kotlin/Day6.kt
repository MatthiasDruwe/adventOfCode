import java.util.*

class Day6 {

    fun start() {
        val data = getListOfData("Day6.txt")[0]
        var days: Map<Int, Long> = data.split(",").map{it.toInt()}.groupingBy { it }.eachCount().mapValues { it.value.toLong() }
        println(days)

        for(i in 1..256){
            var newDays = mutableMapOf<Int,Long>()
            for (i  in 0..8) {
                newDays[i]=0
            }
            days.forEach { (key, value) ->
               if(key == 0){
                   newDays[6]= newDays[6]!!+value
                   newDays[8] = newDays[8]!!+value
               }
                else {
                    newDays[key-1] = newDays[key-1]!! + value
               }
            }

            days = newDays
            println(days)
        }

        println("oplossing ${days.map { it.value}.sum()}")
    }
}