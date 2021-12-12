class Day12 {
    fun start() {
        val data  = getListOfData("Day12.txt")
        val paths = convertToPath(data)
        println(paths)
        part1(paths)
    }

    private fun convertToPath(data: List<String>): MutableList<Path> {
        val paths = mutableListOf<Path>()
        data.forEach { row ->
            val items = row.split("-")

            val path1 = paths.find { it.from == items[0] }
            val path2 = paths.find { it.from == items[1] }

            if(path1 != null){
                path1.to.add(items[1])
            } else {
                paths.add(Path(items[0], mutableListOf(items[1])))
            }

            if(path2 != null){
                path2.to.add(items[0])
            } else {
                paths.add(Path(items[1], mutableListOf(items[0])))
            }
        }
        return paths
    }

    private fun part1(data: MutableList<Path>) {
        val routes = data.first { it.from == "start" }.calculateRoutesTo("end", mutableListOf("start"), data)
        println(routes)
        println(routes.count())
    }
}

class Path( val from: String, val to: MutableList<String>) {
    override fun toString(): String {
        return "$from - $to"
    }

    fun calculateRoutesTo(to: String, stops: List<String>, paths: List<Path>): List<List<String>> {

        if(stops.isNotEmpty() && stops.last() == to) {
            return listOf(stops)
        }

        val routes = mutableListOf<List<String>>()
        this.to.forEach { destination ->
            if (checkDestination(destination, stops)) {
                val nextPath = paths.first{it.from == destination}
                val newStops = mutableListOf<String>()
                newStops.addAll(stops)
                newStops.add(destination)
                val x = nextPath.calculateRoutesTo(to,newStops.toList(),paths )
                routes.addAll(x)
            }
        }
        return routes
    }

    private fun checkDestination(
        destination: String,
        stops: List<String>
    ): Boolean {
        if(destination == "start")
            return false
        if(destination == destination.lowercase()){
            val lowerCaseItems = stops.groupBy{it}.filter { it.key == it.key.lowercase() }
            if(lowerCaseItems.filter { it.value.size == 2 }.isNotEmpty()) {
                return !stops.contains(destination)
            }

        }
        return true
    }
}