
class Day13 {

    fun start(){
        val data = getListOfData("Day13.txt")
        val points = data.filter { !it.startsWith("fold along") }
            .filter { it!="" }
            .map{
                Pair(it.split(",")[0].toInt(), it.split(",")[1].toInt())
            }

        val folds = data.filter { it.startsWith("fold along") }
            .map { it.removePrefix("fold along ").split("=") }
            .map {Pair(it[0],it[1].toInt())}

        val maxY = points.maxOf { it.second }
        val maxX = points.maxOf { it.first }
        val raster = MutableList(maxY+1) {MutableList(maxX+1){ "."} }

        points.forEach {
            raster[it.second][it.first] = "#"
        }

        println(points)
        println(folds)

        part1(raster, folds)
        part2(raster, folds)
    }

    private fun part1(raster: List<List<String>>, folds: List<Pair<String, Int>>) {


        val result = fold(raster, folds[0])
        println(raster)
        println(result)
        println(result.sumOf { row -> row.count { it=="#" } })

    }
    private fun part2(raster: List<List<String>>, folds: List<Pair<String, Int>>) {

        var result = raster
        folds.forEach {
            result = fold(result, it)
        }

        result.forEach { row ->
            println(row.joinToString("") { if (it == ".") " " else it })
        }

    }


    private fun fold(raster: List<List<String>>, pair: Pair<String, Int>): MutableList<MutableList<String>> {
        return when (pair.first){
            "y"->yFold(raster, pair.second)
            "x"->xFold(raster, pair.second)
            else -> throw Error()
        }
    }

    private fun xFold(raster: List<List<String>>, second: Int): MutableList<MutableList<String>> {
        val newList = MutableList(raster.size) {MutableList(second){ "."} }

        raster.forEachIndexed { i, strings ->
            strings.forEachIndexed { j, s ->
                if(s=="#"){
                    if(j < second){
                        newList[i][j] = "#"
                    } else if (j > second){
                        newList[i][second - (j-second)]="#"
                    }
                }
            }
        }
        return newList    }

    private fun yFold(raster: List<List<String>>, second: Int): MutableList<MutableList<String>> {
        val newList = MutableList(second) {MutableList(raster[0].size){ "."} }

        raster.forEachIndexed { i, strings ->
            strings.forEachIndexed { j, s ->
                if(s=="#"){
                    if(i < second){
                        newList[i][j] = "#"
                    } else if (i > second){
                        newList[second - (i-second)][j]="#"
                    }
                }
            }
        }
        return newList
    }
}