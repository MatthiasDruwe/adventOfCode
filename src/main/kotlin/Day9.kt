class Day9 {
    fun start() {
        part1()
        part2()
    }

    private fun part2() {
        val data = getListOfData("Day9.txt")
        val values = data.map { row -> row.toList().map { it.toString().toInt() } }

        val group = values.mapIndexed { i, ints ->
            List(ints.size) { j ->
                findSmallestNumber(values, i, j)
            }
        }.flatten().groupBy { it }.filter { it.key != null }.values.sortedByDescending { it.size }.take(3)
            .multiply { it.size }

        println(group)
    }

    private fun findSmallestNumber(values: List<List<Int>>, i: Int, j: Int): Pair<Int, Int>? {
        if (values[i][j] == 9) {
            return null
        }
        if (isSmallestNummer(values, i, j)) {
            return Pair(i, j)
        } else {
            val currentValue = values[i][j]
            val top = values.getOrNull(i - 1)?.getOrNull(j) ?: (currentValue + 1)
            val bottom = values.getOrNull(i + 1)?.getOrNull(j) ?: (currentValue + 1)
            val left = values.getOrNull(i)?.getOrNull(j - 1) ?: (currentValue + 1)
            val right = values.getOrNull(i)?.getOrNull(j + 1) ?: (currentValue + 1)

            when (listOf(top, bottom, left, right).minOf { it }) {
                top -> return findSmallestNumber(values, i - 1, j)
                bottom -> return findSmallestNumber(values, i + 1, j)
                left -> return findSmallestNumber(values, i, j - 1)
                right -> return findSmallestNumber(values, i, j + 1)
            }
        }
        return null
    }

    private fun part1() {
        val data = getListOfData("Day9.txt")
        val values = data.map { row -> row.toList().map { it.toString().toInt() } }

        val lowestPoints = values.mapIndexed { i, ints ->
            ints.mapIndexed { j, number ->
                if (isSmallestNummer(values, i, j)) number + 1 else 0
            }
        }

        val sum = lowestPoints.sumOf { it.sum() }

        println("Solution part1: $sum")
    }

    private fun isSmallestNummer(values: List<List<Int>>, i: Int, j: Int): Boolean {
        val currentValue = values[i][j]
        val top = values.getOrNull(i - 1)?.getOrNull(j) ?: (currentValue + 1)
        val bottom = values.getOrNull(i + 1)?.getOrNull(j) ?: (currentValue + 1)
        val left = values.getOrNull(i)?.getOrNull(j - 1) ?: (currentValue + 1)
        val right = values.getOrNull(i)?.getOrNull(j + 1) ?: (currentValue + 1)

        return top > currentValue && bottom > currentValue && left > currentValue && right > currentValue
    }
}

fun <T> Iterable<T>.multiply(selector: (T) -> Int): Int {
    var multiplication = 1
    for (element in this) {
        multiplication *= selector(element)
    }
    return multiplication
}
