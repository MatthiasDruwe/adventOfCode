import kotlin.math.max

class Day22 {
    fun start() {
        val data = getListOfData("day22.txt")
        // part1(data)
        part2(data)
    }



    private fun part2(data: List<String>) {
        val cubicles = getCubicles(data)

        val xValues = cubicles.flatMap { listOf(it.minTriple.first, it.maxTriple.first+1) }.sorted().distinct()
        val yValues = cubicles.flatMap { listOf(it.minTriple.second, it.maxTriple.second+1) }.sorted().distinct()
        val zValues = cubicles.flatMap { listOf(it.minTriple.third, it.maxTriple.third+1) }.sorted().distinct()

        val ons = Array(xValues.size){ Array(yValues.size){ BooleanArray(zValues.size) } }
        println(xValues)
        println(yValues)
        println(zValues)


        for (cube in cubicles) {
            for (x in xValues.indexOf(cube.minTriple.first)until xValues.indexOf(cube.maxTriple.first+1)){
                for (y in yValues.indexOf(cube.minTriple.second)until yValues.indexOf(cube.maxTriple.second+1)){
                    for (z in zValues.indexOf(cube.minTriple.third)until zValues.indexOf(cube.maxTriple.third+1)){
                        ons[x][y][z] = cube.isOn;
                    }
                }
            }
        }
        var sum = 0L


        for(x in 0 until xValues.size-1){
            for(y in 0 until yValues.size-1){
                for (z in 0 until zValues.size-1){
                    if(ons[x][y][z]){
                        sum += (xValues[x+1]-xValues[x]).toLong() * (yValues[y+1]-yValues[y]).toLong() * (zValues[z+1]-zValues[z]).toLong()
                    }
                }
            }
        }

        println(sum)
    }

    private fun overlappingCubesArea(cubes: MutableList<Cubicle>, cube: Cubicle): Long {
        var intersectCubes = mutableListOf<Cubicle>()
        var sum =0L
        cubes.forEach {
            val remainingCube = cube.intersect(it)
            if(remainingCube.area() > 0){
                sum+=(remainingCube.area()-overlappingCubesArea(intersectCubes, remainingCube))

                intersectCubes.add(remainingCube)
            }
        }
        return sum;
    }

    private fun part1(data: List<String>) {

        val cubicles = getCubicles(data)

        println(countOns(cubicles.reversed(), Triple(-50, -50,-50),  Triple(50,50,50) ))
    }

    private fun getCubicles(data: List<String>) = data.map { row ->
        var add = false
        val coordinates = if (row.contains("on")) {
            add = true
            row.removePrefix("on ")
        } else {
            add = false
            row.removePrefix("off ")
        }

        val coordinate = coordinates.split(",").map { coordinate -> coordinate.split("=").last().split("..") }
        val x1 = coordinate[0][0].toInt()
        val x2 = coordinate[0][1].toInt()
        val y1 = coordinate[1][0].toInt()
        val y2 = coordinate[1][1].toInt()
        val z1 = coordinate[2][0].toInt()
        val z2 = coordinate[2][1].toInt()
        Cubicle(Triple(x1, y1, z1), Triple(x2, y2, z2), add)
    }

    private fun countOns(reverseData: List<Cubicle>, min: Triple<Int, Int, Int>, max: Triple<Int, Int, Int>): Long {
        var numberOfOns = 0L

        for (x in min.first .. max.first){
            for(y in min.second.. max.second){
                for(z in min.third..max.third){
                    val cubicle = reverseData.firstOrNull { it.inCubicle(Triple(x,y,z)) }
                    cubicle?.let {
                        if(it.isOn){
                            numberOfOns++
                        }
                    }
                }
            }
        }

        return numberOfOns
    }

    class Cubicle(val minTriple: Triple<Int,Int, Int>, val maxTriple: Triple<Int,Int, Int>, val isOn: Boolean) {
        fun inCubicle(triple: Triple<Int, Int, Int>):Boolean {
            return (triple.first >= minTriple.first && triple.second >= minTriple.second && triple.third >= minTriple.third &&
                    triple.first <= maxTriple.first && triple.second <= maxTriple.second && triple.third <= maxTriple.third)
        }

        fun area(): Long{
            val difference = maxTriple - minTriple
            return ((difference.first + 1) * (difference.second +1)  * (difference.third+1)).toLong()
        }



        fun intersect(it: Day22.Cubicle): Day22.Cubicle {
            val minX = max(minTriple.first, it.minTriple.first)
            val maxX = minOf(maxTriple.first, it.maxTriple.first)
            val minY = max(minTriple.second, it.minTriple.second)
            val maxY = minOf(maxTriple.second, it.maxTriple.second)
            val minZ = max(minTriple.third, it.minTriple.third)
            val maxZ = minOf(maxTriple.third, it.maxTriple.third)
            return Cubicle(Triple(minX,minY, minZ), Triple(maxX, maxY, maxZ),isOn)
        }

    }
}