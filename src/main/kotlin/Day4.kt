class Day4 {

    fun start() {
        val data = getListOfData("Day4.txt")
        val bingoNumbers = data[0].split(",").map { it.toInt() };
        var bingoMatrices = loadMatrix(data)
        var bingomatrix: Array<Array<Number>> = Array(0){ Array(0){ Number(0) } }
        var bingoindex = 0;
        var bingonumber = 0
        while (bingoMatrices.size !=0) {
            bingonumber = bingoNumbers[bingoindex];
            bingoindex++

            var newBingoMatrices =  MutableList(0){Array(5){Array(5){Number(0)}}}
            for(matrix in bingoMatrices){
                matrix.forEach { row -> row.forEach { if(it.value == bingonumber) it.checked = true } }
                if(checkMatrixForBingo(matrix)){
                    bingomatrix = matrix
                } else {
                    newBingoMatrices.add(matrix)
                }
            }
            bingoMatrices = newBingoMatrices
        }

        val sum = bingomatrix.sumOf { row -> row.sumOf { if (!it.checked)it.value else 0  } }
        println("answer: ${sum * bingonumber}")
    }

    private fun checkMatrixForBingo(card: Array<Array<Number>>): Boolean {
        val a = card.forEachIndexed{ index, numbers ->

            var count = 0;
            for(i in 0..numbers.count()-1){
                if(card[i][index].checked) {
                    count++
                }
            }
            if(numbers.count{it.checked}==5 || count ==5)
            {
                return true
            }
        }
        return false
    }

    private fun loadMatrix(data: List<String>): MutableList<Array<Array<Number>>> {

        val matrix = MutableList(0){Array(5){Array(5){Number(0)}}}
        var cardNumber=0
        var rowNumber = 0
        data.drop(1).forEach { row ->
            if(row == ""){
                cardNumber++
                rowNumber = 0
            } else{
                if(matrix.size < cardNumber) {
                    matrix.add(Array(5){Array(5){Number(0)}})
                }
                matrix[cardNumber-1][rowNumber]= row.split(" ").filter { it.isNotBlank() }.map {Number(it.toInt()) }.toTypedArray()
                rowNumber++;
            }
        }

        return matrix

    }

    data class Number(val value:Int, var checked: Boolean = false){
        override fun toString(): String {
            return value.toString() + if(checked) "*" else ""
        }
    }
}