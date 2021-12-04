import kotlin.math.pow
import kotlin.math.roundToInt

class Day3 {
    fun start() {
        println("Day3")

//        part1()
        part2()
    }

    private fun part2(){
        val lines = getListOfData("Day3.txt")

        var dict = lines.map { item -> item.toList().map { it.toString().toInt() } }
        var prefix = "";

        while (dict.size != 1) {
            val newList = MutableList<MutableList<Int>>(dict[0].size) { MutableList(0) { 0 } }
            val newdict = dict.fold(newList) { acc: MutableList<MutableList<Int>>, ints: List<Int> ->
                ints.forEachIndexed { index, element ->
                    if (acc[index] == null) {
                        acc[index] = mutableListOf(element)
                    } else {
                        acc[index].add(element)
                    }
                }
                acc
            }

            prefix += if(newdict[prefix.length].average()>=0.5) 1 else 0
            dict = dict.filter { it.joinToString("").startsWith(prefix)}
            println(dict)
            println(prefix)
        }

        var binary = 0.0
        dict[0].forEachIndexed { index, value ->
            if (value == 1) {
                binary += 2.0.pow((dict[0].size - index - 1).toDouble())
            }
        }

        dict = lines.map { item -> item.toList().map { it.toString().toInt() } }
        prefix = "";
        while (dict.size != 1) {
            val newList = MutableList<MutableList<Int>>(dict[0].size) { MutableList(0) { 0 } }
            val newdict = dict.fold(newList) { acc: MutableList<MutableList<Int>>, ints: List<Int> ->
                ints.forEachIndexed { index, element ->
                    if (acc[index] == null) {
                        acc[index] = mutableListOf(element)
                    } else {
                        acc[index].add(element)
                    }
                }
                acc
            }

            prefix += if(newdict[prefix.length].average()>=0.5) 0 else 1
            dict = dict.filter { it.joinToString("").startsWith(prefix)}
            println(dict)
            println(prefix)
        }

        var binary2 = 0.0
        dict[0].forEachIndexed { index, value ->
            if (value == 1) {
                binary2 += 2.0.pow((dict[0].size - index - 1).toDouble())
            }
        }




        println(binary)
        println(binary2)
        println("solution: ${binary*binary2}")
    }

    private fun part1() {
        val lines = getListOfData("Day3.txt")


        val dict = lines.map { item -> item.toList().map { it.toString().toInt() } }
        val newList = MutableList<MutableList<Int>>(dict[0].size) { MutableList(0) { 0 } }


        val newdict = dict.fold(newList) { acc: MutableList<MutableList<Int>>, ints: List<Int> ->
            ints.forEachIndexed { index, element ->
                if (acc[index] == null) {
                    acc[index] = mutableListOf(element)
                } else {
                    acc[index].add(element)
                }
            }
            acc
        }
        println(dict)
        println(newdict)

        val avg = newdict.map { it.average().roundToInt() }
        var binary1 = 0.0
        var binary2 = 0.0
        avg.forEachIndexed { index, value ->
            if (value == 1) {
                binary1 += 2.0.pow((avg.size - index - 1).toDouble())
            } else {
                binary2 += 2.0.pow((avg.size - index - 1).toDouble())
            }
        }

        println("Binary1:$binary1")
        println("Binary2:$binary2")

        println("Binary1*binary2: ${binary1 * binary2}")
    }

}
