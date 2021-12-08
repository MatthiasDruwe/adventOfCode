class Day8 {

    fun start() {
        val data = getListOfData("Day8.txt")
        val part1 = data.map { it.split(" | ")[0].split(" ") }
        val part2 = data.map { it.split(" | ")[1].split(" ") }

        println(part1)
        println(part2)
        val count = part2.sumOf { row -> row.count { listOf(2, 3, 4, 7).contains(it.length) } }
        println("solution part1: $count")

        var sum = 0L
        for (i in part1.indices) {
            val decoding = decode(part1[i])
            val number = part2[i].map { decoding[it.toCharArray().sorted().toString()] }.joinToString("").toInt()
            sum += number
        }
        println("solution part2: $sum")
    }

    private fun decode(strings: List<String>): Map<String, Int> {
        val decoding = mutableMapOf<Int, String>()

        decoding[1] = strings.first { it.count() == 2 }
        decoding[4] = strings.first { it.count() == 4 }
        decoding[7] = strings.first { it.count() == 3 }
        decoding[8] = strings.first { it.count() == 7 }

        val numbersCount5 = strings.filter { it.count() == 5 }
        val numbersCount6 = strings.filter { it.count() == 6 }


        decoding[3] = numbersCount5.first { word -> word.count { decoding[7]?.contains(it) ?: false } == 3 }
        decoding[9] = numbersCount6.first { word -> word.count { decoding[4]?.contains(it) ?: false } == 4 }

        val e = decoding[8]?.toCharArray()?.let { item -> item.first { !(decoding[9]?.contains(it) ?: true) } }

        decoding[2] = numbersCount5.filter { it != decoding[6] }.first { e?.let { it1 -> it.contains(it1) } ?: false }
        decoding[5] =
            numbersCount5.filter { it != decoding[3] }.first { !(e?.let { it1 -> it.contains(it1) } ?: false) }

        decoding[6] = numbersCount6.filter { it != decoding[9] }
            .first { word -> word.count { decoding[5]?.contains(it) ?: false } == 5 }
        decoding[0] = numbersCount6.first { it != decoding[9] && it != decoding[6] }

        return decoding.map { (key, value) -> Pair(value.toCharArray().sorted().toString(), key) }.toMap()
    }
}