class Day10 {

    fun start() {
        val data = getListOfData("Day10.txt")

        val sum = data.sumOf { checkData(it) }
        println(sum)

        part2(data)
    }

    private fun part2(data: List<String>) {
        val incompleteStrings = data.mapNotNull { isIncomplete(it) }
        val sum =  incompleteStrings.map { string ->
            string.toCharArray().reversed().map {
                when(it){
                    '('->')'
                    '{'-> '}'
                    '['->']'
                    '<'->'>'
                    else -> null
                }
            }.joinToString("")
        }.map { string ->
            string.toCharArray().map {
            when(it){
                '}'-> 3L
                ')'-> 1L
                ']'-> 2L
                '>'-> 4L
                else -> 0L
            }
        }.reduce { acc, i ->
                acc*5 + i
            }
        }.sorted().middle()

        println(sum)
    }

    private fun isIncomplete(s: String): String? {
        val signs = mutableListOf<Char>()
        s.forEach {
            when(it) {
                '{','(','[','<' -> signs.add(it)
                reverseOf(signs.last()) -> signs.removeLast()
                else ->  return null
            }
        }
        return signs.joinToString("")
    }

    private fun checkData(it: String): Int {
        val signs = mutableListOf<Char>()
        for(sign in it){
            when(sign){
                '{','(','[','<' -> signs.add(sign)
                reverseOf(signs.last()) -> signs.removeLast()
                else -> return calculateScore(sign)
            }
        }
        return 0
    }

    private fun reverseOf(last: Char): Any {
        return when(last){
            '{'-> '}'
            '('-> ')'
            '['-> ']'
            '<'-> '>'
            else -> 0
        }
    }

    private fun calculateScore(last: Char): Int {
        return when(last){
            '}'-> 1197
            ')'-> 3
            ']'-> 57
            '>'-> 25137
            else -> 0
        }
    }
}

private fun List<Long>.middle(): Long {
    val middleNumber = this.size/2
    return this[middleNumber]
}
