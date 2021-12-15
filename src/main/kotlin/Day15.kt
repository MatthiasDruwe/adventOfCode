import kotlin.math.max

class Day15 {
    fun start() {
        val data = getListOfData("Day15.txt").map { row -> row.toCharArray().map { it.toString().toInt() } }
        part2(data)

    }

    private fun part1(data: List<List<Int>>) {

        println(data)
        var points = createPoints(data)

        val lastY = data.size - 1
        val lastX = data[lastY].size - 1
        val d = dijkstra(points, data.sumOf { it.sum() })
        val x = d[Pair(lastX,lastY)]
        println(x)
    }

    private fun createPoints(data: List<List<Int>>): MutableMap<Pair<Int, Int>, Point> {
        var points = mutableMapOf<Pair<Int,Int>,Point>()
        data.forEachIndexed { i, row ->
            row.forEachIndexed { j, _ ->
                val point = Point(Pair(i, j), mutableListOf())
                points[point.pair] = point
            }
        }
        val lastY = data.size - 1
        val lastX = data[lastY].size - 1
        var counter = 0
        points.forEach {
            counter++

            points[Pair(it.key.first, it.key.second - 1)]?.let { it1 ->
                it.value.connections.add(
                    Connection(
                        it1,
                        data[it.key.first][it.key.second - 1]
                    )
                )
            }

            points[Pair(it.key.first, it.key.second + 1)]?.let { it1 ->
                it.value.connections.add(
                    Connection(
                        it1,
                        data[it.key.first][it.key.second + 1]
                    )
                )
            }

            points[Pair(it.key.first-1, it.key.second)]?.let { it1 ->
                it.value.connections.add(
                    Connection(
                        it1,
                        data[it.key.first-1][it.key.second]
                    )
                )
            }

            points[Pair(it.key.first+1, it.key.second)]?.let { it1 ->
                it.value.connections.add(
                    Connection(
                        it1,
                        data[it.key.first+1][it.key.second]
                    )
                )
            }
        }
        return points
    }

    private fun part2(data: List<List<Int>>) {
        val sizeY = data.size
        val sizeX = data[sizeY - 1].size
        val newData = MutableList(5 * sizeY) {
            MutableList(5 * sizeX) {
                0
            }
        }

        for (i in 0..4) {
            for (j in 0..4) {
                data.forEachIndexed { newI, ints ->
                    ints.forEachIndexed { newj, value ->
                        newData[i * sizeY + newI][j * sizeX + newj] = (value + i + j - 1) % 9 + 1
                    }
                }
            }
        }

        val points = createPoints(newData)

        val d = dijkstra(points, newData.sumOf { it.sum() })
        val x = d[Pair(sizeX*5-1, sizeY*5-1)]
        println(x)
    }

    private fun dijkstra(points: MutableMap<Pair<Int, Int>, Point>, maxValue:Int): Map<Pair<Int,Int>, Int> {
        val distance = mutableMapOf<Pair<Int,Int>, Int>()
        val route = mutableMapOf<Pair<Int,Int>,Point?>()
        val sortingDistance= mutableMapOf<Pair<Int,Int>, Int>()

        points.forEach {
            route[it.key] = null
        }

        distance[Pair(0,0)] = 0
        sortingDistance[Pair(0,0)]=0

        val Q = points.toMutableMap()

        while(Q.isNotEmpty()) {
            val pair = sortingDistance.toList().minByOrNull { it.second }!!.first

            sortingDistance.remove(pair)
            val point=Q.remove(pair)
            if (point != null) {
                for (connection in point.connections){
                    if((distance[connection.to.pair]?:maxValue) > (distance[point.pair]?:maxValue) + connection.value){
                        distance[connection.to.pair] = (distance[point.pair]?:maxValue) + connection.value
                        sortingDistance[connection.to.pair] = (distance[point.pair]?:maxValue) + connection.value
                        route[connection.to.pair] = point
                    }
                }
            }
        }
        return distance
    }

    private fun findMinimal(q: MutableMap<Pair<Int, Int>, Point>, distance: MutableMap<Pair<Int,Int>, Int> ): Pair<Int,Int> {
        var min  = q.keys.first()

        for (point in q){
            if(distance[point.key]!! < distance[min]!!){
                min = point.key
            }
        }
        return min
    }
}

data class Point(val pair:Pair<Int,Int>, val connections: MutableList<Connection> )
data class Connection(val to: Point, val value:Int)