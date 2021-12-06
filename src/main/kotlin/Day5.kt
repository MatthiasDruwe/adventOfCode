import kotlin.math.abs

class Day5 {

    fun start() {
        var data = getListOfData("Day5.txt")
        var pairs = data
            .map { it.split(" -> ") }
            .map { list -> list.map { item -> item.split(",").map { it.toInt() } } }
        println(pairs)
        val maxX=pairs.maxOf { line -> line.maxOf { it[0] } }
        val maxY = pairs.maxOf { line -> line.maxOf { it[1] } }

        println(maxX)
        println(maxY)

        var linesArray=Array(maxX+1){Array(maxY+1){0}}

        pairs.forEach{
            val x1 = it[0][0]
            val x2 = it[1][0]
            val y1 = it[0][1]
            val y2 = it[1][1]
            if(x1==x2){
                if(y1>y2) {
                    for(j in y1 downTo y2){
                        linesArray[x1][j]++
                    }
                } else {
                    for(j in y1 .. y2){
                        linesArray[x1][j]++
                    }
                }
            }
            if(y1==y2){
                if(x1>x2) {
                    for(j in x1 downTo x2){
                        linesArray[j][y1]++
                    }
                } else {
                    for(j in x1 .. x2){
                        linesArray[j][y1]++
                    }
                }
            }

            if(x1-x2 == y1-y2){
                var x = minOf(x1,x2)
                var y = minOf(y1,y2)

                while(x <= maxOf(x1,x2) && y<=maxOf(y1,y2)){
                    linesArray[x][y]++
                    x++
                    y++
                }
            }

            if(x1-x2 == y2-y1){
                var x = minOf(x1,x2)
                var y = maxOf(y1,y2)

                while(x <= maxOf(x1,x2) && y>=minOf(y1,y2)){
                    linesArray[x][y]++
                    x++
                    y--
                }
            }
        }
        println(linesArray)

        val solution = linesArray.sumOf { item -> item.count { it>=2 } }

        println(solution)
    }


}
