class Day20 {
    fun start() {
        val data = getListOfData("Day20.txt")
        val encoding = data.first().toCharArray()
        val image = data.drop(2).map { it.toCharArray() }
        part1(encoding,image)
    }

    private fun part1(encoding: CharArray, image: List<CharArray>) {
        val resizeValue = 50

        var imageWithMargin = resizeImage(image, resizeValue)
        imageWithMargin.forEach { println(it) }
        var imageResize : MutableList<CharArray> = mutableListOf()
        for(k in 0 until resizeValue) {
             imageResize = MutableList(imageWithMargin.size) {
                CharArray(imageWithMargin[0].size){'.'}
            }
            imageWithMargin.indices.drop(1).dropLast(1).forEach { i ->
                imageWithMargin[i].indices.drop(1).dropLast(1).forEach { j ->
                    val value = imageWithMargin[i-1][j-1].toString() + imageWithMargin[i-1][j] + imageWithMargin[i-1][j+1] + imageWithMargin[i][j-1].toString() + imageWithMargin[i][j] + imageWithMargin[i][j+1] + imageWithMargin[i+1][j-1].toString() + imageWithMargin[i+1][j] + imageWithMargin[i+1][j+1]
                    val char = decode(value, encoding)
                    imageResize[i][j] = char
                }
            }

            imageResize[0]=imageResize[1]
            imageResize[imageResize.indices.last] = imageResize[imageResize.indices.last-1]

            imageResize.forEach {
                it[0] = it[1]
                it[it.indices.last] = it[it.indices.last-1]
            }

            imageWithMargin = imageResize
            println("-----")

            imageResize.forEach { println(it) }
        }

        println(imageResize.sumOf { chars -> chars.count { it=='#' } })
    }

    private fun decode(value: String, encoding: CharArray): Char {
        val number = value.toCharArray().map { if(it == '#') '1' else '0' }.joinToString("").toInt(2)
        return encoding[number]
    }

    private fun resizeImage(image: List<CharArray>, resizeValue: Int): List<CharArray> {

        val resizedImage = List(image.size+4*resizeValue+2) {
            CharArray(image[0].size + 4*resizeValue+2){'.'}
        }

        image.forEachIndexed { i, chars ->
            chars.forEachIndexed { j, c ->
                resizedImage[i+2*resizeValue+1][j+2*resizeValue+1] = c
            }
        }

        return resizedImage
    }
}