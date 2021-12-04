import java.io.File

fun getListOfData(filename:String):List<String>{
    return File("src/main/resources", filename).readLines()
}