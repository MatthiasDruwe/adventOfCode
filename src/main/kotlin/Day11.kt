class Day11 {
    fun start () {
        val data = getListOfData("Day11.txt").map { it.toCharArray().map { it.toString().toInt() } }
        part2(data)
    }

    private fun part1(data: List<List<Int>>) {
        println(data)
        var newData = data
        var count = 0
        for (i in 0..99){
            newData = newData.add1ToAll().flashes()
            count += newData.sumOf { row -> row.count{it==0} }
            println(count)
        }
        println(count)
    }

    private fun part2(data: List<List<Int>>) {
        println(data)
        var newData = data
        var count = 0
        while(newData.sumOf { it.sum() } != 0){
            newData = newData.add1ToAll().flashes()
            count++
        }
        println(count)
    }
}

private fun List<List<Int>>.add1ToAll(): List<List<Int>> {
    return this.map { row -> row.map { it+ 1 } }
}

private fun List<List<Int>>.flashes(): List<List<Int>> {
    var list = this.map { it.toMutableList() }
    while (list.sumOf { row -> row.count { it>9 } }>0){
        val yIndices = list.indices
        for (y in yIndices){
            val xIndices = list[y].indices
            for (x in xIndices){
                if(list[y][x] > 9) {
                    list[y][x] = 0
                    list = changeListFor(y,x,list)
                }
            }
        }
    }

    return list.map{it.toList()}
}

fun changeListFor(y: Int, x: Int, list: List<MutableList<Int>>): List<MutableList<Int>> {
    if(y-1 in list.indices){
        if(x-1 in list[y-1].indices){
            if(list[y-1][x-1] !=0) {
                list[y-1][x-1]+=1
            }
        }
        if(x in list[y-1].indices){
            if(list[y-1][x] !=0) {
                list[y-1][x]+=1
            }
        }
        if(x+1 in list[y-1].indices){
            if(list[y-1][x+1] !=0) {
                list[y-1][x+1]+=1
            }
        }
    }
    if(y in list.indices){
        if(x-1 in list[y].indices){
            if(list[y][x-1] !=0) {
                list[y][x-1]+=1
            }
        }
        if(x in list[y].indices){
            if(list[y][x] !=0) {
                list[y][x]+=1
            }
        }
        if(x+1 in list[y].indices){
            if(list[y][x+1] !=0) {
                list[y][x+1]+=1
            }
        }
    }
    if(y+1 in list.indices){
        if(x-1 in list[y+1].indices){
            if(list[y+1][x-1] !=0) {
                list[y+1][x-1]+=1
            }
        }
        if(x in list[y+1].indices){
            if(list[y+1][x] !=0) {
                list[y+1][x]+=1
            }
        }
        if(x+1 in list[y+1].indices){
            if(list[y+1][x+1] !=0) {
                list[y+1][x+1]+=1
            }
        }
    }
    return list
}
