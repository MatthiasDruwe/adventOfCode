import kotlin.math.abs
import kotlin.math.max

class Day19 {

    fun start() {
        val data = getListOfData("Day19.txt")

        var counter = -1
        val scanners = mutableMapOf<Int, MutableList<Triple<Int,Int,Int>>>()
        data.forEach { row ->
            if(row.startsWith("---")){
                counter++
                scanners[counter] = mutableListOf()
            }
            else if(row.isNotBlank()){
                val ints = row.split(",").map { it.toInt() }
                val triple =  Triple(ints[0], ints[1], ints[2])
                scanners[counter]!!.add(triple)
            }
        }

        part1And2(scanners)
    }

    private fun part1And2(scans: MutableMap<Int, MutableList<Triple<Int, Int, Int>>>) {

        var original = scans.remove(0)
        var counter = -1
        val scanners = mutableListOf<Triple<Int, Int, Int>>(Triple(0,0,0))
        while(scans.isNotEmpty()){
            counter = (counter+1)%scans.size
            val key = scans.keys.toList()[counter]
            val differentOrientations = scans[key]?.let { generateOrientations(it) }
            differentOrientations?.forEach { item ->
                val overlapping = mutableListOf<Triple<Int, Int, Int>>()

                item.forEach { item1 ->
                    original?.forEach { item2 ->
                        overlapping.add(item2 - item1)
                    }
                }
                val difference =
                    overlapping.groupBy { it }.mapValues { it.value.size }.toList().sortedByDescending { it.second }
                        .first()
                if (difference.second >= 12) {
                    scanners.add(difference.first)
                    item.forEach {
                        original?.add(it + difference.first)
                    }
                    original= original?.distinct() as MutableList<Triple<Int, Int, Int>>
                    scans.remove(key)
                    return@forEach
                }
            }
        }

        println(original?.size)

        var maxDistance = 0
        scanners.forEach { item1->
            scanners.forEach { item2 ->
                maxDistance = max(item1.distanceTo(item2),maxDistance)
            }
        }
        println(maxDistance)
    }

    private fun generateOrientations(data :List<Triple<Int, Int, Int>>): MutableList<List<Triple<Int, Int, Int>>> {
        val differentOrientations = mutableListOf<List<Triple<Int,Int,Int>>>()
        var option = data
        for(i in 0..1){
            for (j in 0..2){
                for (k in 0..1) {
                    for (l in 0..1) {
                        for (m in 0..1) {
                            differentOrientations.add(option)
                            option = option.map { it.reverse(2) }
                        }
                        option = option.map { it.reverse(1) }
                    }
                    option = option.map { it.reverse(0) }
                }
                option = option.map { it.shift() }
            }
            option = option.map { it.mirror() }
        }
        return differentOrientations
    }
}

private fun Triple<Int, Int, Int>.distanceTo(item: Triple<Int, Int, Int>): Int {
    return abs(first-item.first)+ abs(second-item.second)+ abs(third-item.third)
}

private operator fun Triple<Int, Int, Int>.plus(value: Triple<Int, Int, Int>): Triple<Int, Int, Int> {
    return Triple(first+value.first, second+value.second, third+value.third)
}


private fun Triple<Int,Int, Int>.shift(): Triple<Int,Int,Int>{
    return Triple(third, first, second)
}

private fun Triple<Int, Int, Int>.mirror(): Triple<Int, Int, Int>{
    return Triple(third, second, first)
}

private fun Triple<Int, Int, Int>.reverse(index: Int): Triple<Int,Int,Int>{
    return when(index){
        0-> Triple(-first, second, third)
        1-> Triple(first,-second, third)
        2-> Triple(first, second, -third)
        else -> this
    }
}

operator fun Triple<Int, Int, Int>.minus(item2: Triple<Int, Int, Int>) :Triple<Int,Int,Int> {
    return Triple(this.first-item2.first, this.second - item2.second, this.third-item2.third)
}
