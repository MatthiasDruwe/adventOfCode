import kotlin.math.abs

class Day23 {

    fun start() {
        val data = getListOfData("Day23.txt")
        val hallway = data[1].drop(1).dropLast(1).toCharArray()

        val room1 = listOf(data[2][3],data[3][3])
        val room2 = listOf(data[2][5],data[3][5])
        val room3 = listOf(data[2][7],data[3][7])
        val room4 = listOf(data[2][9],data[3][9])

        println(hallway)
        println(room1)
        println(room2)
        println(room3)
        println(room4)

        part1(hallway, room1,room2, room3, room4)
    }

    private fun part1(
        hallway: CharArray,
        room1: List<Char>,
        room2: List<Char>,
        room3: List<Char>,
        room4: List<Char>) {

        val score = calculateScore(hallway, room1, room2, room3, room4)

    }

    private fun calculateScore(hallway: CharArray, room1: List<Char>, room2: List<Char>, room3: List<Char>, room4: List<Char>): Int {
        if(solved(room1, room2, room3, room4)){
            return 0
        }

        var sum = 0
        println(hallway)
        sum = CheckRoom(hallway.concatToString(), room1, room2, room3, room4,'A')
        sum = CheckRoom(hallway.concatToString(), room1, room2, room3, room4,'B')
        sum = CheckRoom(hallway.concatToString(), room1, room2, room3, room4,'C')
        sum = CheckRoom(hallway.concatToString(), room1, room2, room3, room4,'D')

        hallway.forEachIndexed { index, c ->

        }

        return sum
    }

    private fun CheckRoom(
        hallwayString: String,
        room1: List<Char>,
        room2: List<Char>,
        room3: List<Char>,
        room4: List<Char>, roomId: Char
    ): Int {
        val hallway = hallwayString.toCharArray()
        val room =
            when(roomId) {
                'A' -> room1
                'B' -> room2
                'C' -> room3
                'D' -> room4
                else -> null
            }
        if (room != null) {
            if (room.count { it == '.' || it == roomId } < room.size) {
                val position = room.indexOfFirst { it != '.' }
                val start = when(roomId) {
                    'A'->2
                    'B'->4
                    'C'->6
                    'D' ->8
                    else -> 0
                }
                val value =
                    when(roomId) {
                        'A'->1
                        'B'->10
                        'C'->100
                        'D' ->1000
                        else -> 0
                    }

                hallway.forEachIndexed { index, _ ->
                    if (canMoveTo(index, hallway, start)) {
                        val x = (value * abs(index - start) + position + 1)
                        hallway[index] = room[position]
                        val newRoom = room?.toMutableList()
                        newRoom?.set(position, '.')
                        return x + when (roomId) {
                            'A' -> calculateScore(hallway, newRoom, room2, room3, room4)
                            'B' -> calculateScore(hallway, room1, newRoom, room3, room4)
                            'C' -> calculateScore(hallway, room1, room2, newRoom, room4)
                            'D' -> calculateScore(hallway, room1, room2, room3, newRoom)
                            else -> 0
                        }

                    }
                }
            }
        }
        return 0
        }



    private fun canMoveTo(index: Int, hallway: CharArray, start: Int): Boolean {
        if( listOf(2,4,6,8).contains(index)){
            return false
        }
        val partHallway = if(index < start) {
            hallway.toList().subList(index, start).toCharArray()
        } else {
            hallway.toList().subList(start, index).toCharArray()
        }

        return partHallway.all { it == '.' }
    }

    private fun solved(room1: List<Char>, room2: List<Char>, room3: List<Char>, room4: List<Char>): Boolean {
        return room1.count { it=='A' } == room1.size && room2.count { it=='B' } == room2.size && room3.count { it=='C' } == room3.size && room4.count { it=='D' } == room4.size
    }

}