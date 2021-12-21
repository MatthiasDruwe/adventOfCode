class Day21 {
    fun start() {
        val data = getListOfData("Day21.txt")
        val player1 = Player(data.first().last().toString().toInt())
        val player2 = Player(data[1].last().toString().toInt())

//        part1(listOf(player1, player2))
        part2(listOf(player1, player2))
    }

    private fun part2(players: List<Player>) {
        val universes = findUniverses()
        println(countWins(players, 0,universes).toList().sortedByDescending { it.second }.first().second)
     }

    private fun countWins(players: List<Player>, currentPlayer:Int, universes: Map<Int, Int>):Map<Int, Long>  {
        val sum = mutableMapOf<Int, Long>()
        for(i in 3..9){
            val option = players[currentPlayer].copy()
            option.position = (option.position+i -1)%10+1
            option.score +=  option.position
            if(option.score >= 21){
                sum[currentPlayer] = (sum[currentPlayer] ?:0)  + (universes[i] ?:0)
            } else {
                val newPlayers = players.toMutableList()
                newPlayers[currentPlayer] = option
                val subtotal = countWins(newPlayers, (currentPlayer+1)%2, universes)

                subtotal.forEach { (t, u) ->
                    sum [t] = (sum[t]?:0) + (universes[i] ?:0) * u
                }
            }
        }

        return sum
    }

    private fun findUniverses(): Map<Int, Int> {
        val list = mutableListOf<Int>()
        for(i in 1..3){
            for (j in 1..3){
                for (k in 1..3){
                    list.add(i+j+k)
                }
            }
        }

        return list.groupBy { it }.mapValues { it.value.count() }
    }

    private fun part1(players: List<Player>) {

        var currentPlayer = 0
        val dice = Dice()

       while (players.count { it.score>=1000 } == 0){
            players[currentPlayer].position = (players[currentPlayer].position + dice.next() +dice.next() + dice.next() -1)%10 + 1
            players[currentPlayer].score+=players[currentPlayer].position

           currentPlayer = (currentPlayer + 1)%players.count()
           println(players)
       }

        println(players.minByOrNull { it.score }!!.score * dice.rolled)

    }



    data class Player(var position: Int, var score:Int = 0){
        override fun toString(): String {
            return "$position - $score"
        }
    }

    class Dice(private var currentValue: Int = 1, var rolled: Int = 0) {
        fun next(): Int {
            val value = currentValue
            currentValue= (currentValue)%100 +1
            rolled++
            return value
        }
    }
}