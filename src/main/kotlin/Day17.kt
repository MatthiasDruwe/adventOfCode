import kotlin.math.abs
import kotlin.math.max

class Day17 {

    fun start() {
        val data = getListOfData("Day17.txt")[0]
        val target = data.removePrefix("target area:").split(",").map { Pair(it.split("=")[0].trim(),it.split("=")[1].split("..").map { number-> number.toInt() })}

        part1(target)
    }

    private fun part1(target: List<Pair<String, List<Int>>>) {
        println(target)
        var minX = 0
        val maxX=0
        val maxY= abs(target[1].second.first()) -1
        val minY = target[1].second.first()
        val minTargetX = target[0].second.first()
        val maxTargetX = target[0].second.last()

        while(minX.facultySum()<minTargetX) {
            minX++
        }
        var maxHeight = 0


        println(minX)
        println(maxX)
        var count = 0
        for (x in minX..maxTargetX){
            for(y in minY..maxY){
                val height = checkTarget(Pair(x,y),target)
                if(height != -1){
                    println("$x-$y -> $height")
                    count++
                    maxHeight = max(maxHeight, height)
                }

            }
        }
        println(maxHeight)
        println(count)



    }

    private fun checkTarget(pair: Pair<Int, Int>, target: List<Pair<String, List<Int>>>):Int {
        var x = 0
        var y = 0
        var velocityX = pair.first
        var velocityY = pair.second
        val minTargetX = target[0].second.first()
        val maxTargetX = target[0].second.last()
        val minTargetY = target[1].second.first()
        val maxTargetY = target[1].second.last()
        var maxY=0


        while(x < maxTargetX && y > minTargetY ){
            x+=velocityX
            y+=velocityY

            velocityX = max(0, velocityX-1)
            velocityY -= 1
            if(maxY<y){
                maxY=y
            }

            if(x in minTargetX..maxTargetX && y in minTargetY..maxTargetY){
                return maxY
            }
        }

        return -1
    }
}

private fun Int.facultySum(): Int {
    var loop = this
    var sum = loop
    while (loop != 0) {
        loop--
        sum+=loop
    }
    return sum
}
