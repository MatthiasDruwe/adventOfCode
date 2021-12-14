import java.util.*

class Day14 {
    fun start()
    {
        val data = getListOfData("Day14.txt")
        val start = data[0]
        val rules =
            data.filter { it.contains(" -> ") }.map { it.split(" -> ") }.associate { Pair(it.first(), it.last()) }

        part1(start,rules)
        part2(start,rules)
    }

    private fun part2(start:String, rules: Map<String, String>){
        var counter = mutableMapOf<Char, Long>()
        start.windowed(2).forEach {
            count(it, rules, 40).forEach { (t, u) ->
                counter[t] = (counter[t]?:0) + u
            }
        }

        counter[start.first()] = (counter[start.first()] ?: 0)+1
        println(counter)
        val sortedCounter = counter.toList().sortedBy { it.second }

        println(sortedCounter.last().second-sortedCounter.first().second)

    }

    private fun count(it: String, rules: Map<String, String>, countDown:Int, map: MutableMap<Pair<String,Int>, Map<Char, Long>> = mutableMapOf()): Map<Char, Long> {
        if(countDown==0){
           return it.toCharArray().drop(1).groupBy { it }.mapValues { it.value.size.toLong() }
        }



        val counter = mutableMapOf<Char,Long>()
        val newChar1 = it.first() + rules[it].toString()
        val newChar2 = rules[it].toString() + it.last()
        val result1:Map<Char,Long>  = if (map.keys.contains(Pair(newChar1,countDown))) {
            map[Pair(newChar1,countDown)]!!
        } else {
            val result = count(newChar1, rules, countDown-1,map)
            map[Pair(newChar1,countDown)] = result
            result
        }

        val result2:Map<Char,Long> = if (map.keys.contains(Pair(newChar2, countDown))) {
            map[Pair(newChar2, countDown)]!!
        } else {
            val result = count(newChar2, rules, countDown-1,map)
            map[Pair(newChar2,countDown)] = result
            result
        }

        result1.forEach { (t, u) ->
                counter[t] = (counter[t]?:0) + u
        }
        result2.forEach { (t, u) ->
            counter[t] = (counter[t]?:0) + u
        }

//        counter[it.first()] = (counter[it.first()]?:0L) + 1L
//        counter[it.last()] = (counter[it.last()]?:0L) + 1L
        return counter
    }

    private fun part1(start:String, rules: Map<String, String>) {

        var result = start

        for (i in 0..9) {
            result = insertData(result, rules)

        }

        val group = result.toCharArray().groupBy { it }.mapValues { it.value.size }.toList().sortedBy { it.second }
        println(group.last().second - group.first().second)
    }

    private fun insertData(start: String, rules: Map<String, String>): String {
        var result=""
        start.windowed(2).forEach {
            result += it.first()
            result += rules[it]
        }
        result+=start.last()
        return result
    }
}