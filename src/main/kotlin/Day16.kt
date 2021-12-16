
class Day16 {
    fun start() {
        val data = getListOfData("Day16.txt")[0]
        val binary = data.toCharArray().map { it.toString().toInt(16).toString(2).padStart(4,'0') }
        val binaryString =  binary.joinToString("")
        println(binary)
        println(binaryString)
        val packet = Packet(binaryString)
        println(packet.value)
    }
}

abstract class Data(){

    var remainingString  = ""

    abstract val value: Long
}

class Packet(binaryString:String): Data() {
    private val version: Int
    private val type:Int
    private val subPackets: MutableList<Data?> = mutableListOf()

    init {
        remainingString=binaryString
        version = remainingString.take(3).toInt(2)
        remainingString = remainingString.drop(3)
        type = remainingString.take(3).toInt(2)
        remainingString = remainingString.drop(3)
        if(type != 4) {
            val length =  if(remainingString.first()=='1') 11 else 15
            remainingString = remainingString.drop(1)
            val subPacketLength = remainingString.take(length).toInt(2)
            remainingString = remainingString.drop(length)
            if(length==11){
                for (i in 0 until subPacketLength){
                    val p = Packet(remainingString)
                    remainingString = p.remainingString
                    subPackets.add(p)
                }
            } else
            {
                var workString = remainingString.take(subPacketLength)
                while (workString.isNotEmpty()){
                    val p = Packet(workString)
                    workString = p.remainingString
                    subPackets.add(p)
                }
                remainingString = remainingString.drop(subPacketLength)
            }

        } else {
            val l = Literal(remainingString)
            subPackets.add(l)
            remainingString = l.remainingString
        }
    }

    fun versionSum(): Int{
        return version + subPackets.filterIsInstance<Packet>().sumOf { it.versionSum() }
    }

    override val value: Long
        get() {
            return when (type) {
                0 -> subPackets.sumOf { it?.value ?: 0 }
                1 -> subPackets.product { it?.value ?: 0 }
                2 -> subPackets.minOf { it?.value ?: 0 }
                3 -> subPackets.maxOf { it?.value ?: 0 }
                4 -> subPackets.first()?.value ?: 0
                5 -> if ((subPackets.first()?.value ?: 0) > (subPackets.last()?.value ?: 0)) 1 else 0
                6 -> if ((subPackets.first()?.value ?: 0) < (subPackets.last()?.value ?: 0)) 1 else 0
                7 -> if ((subPackets.first()?.value ?: 0) == (subPackets.last()?.value ?: 0)) 1 else 0
                else -> 0
            }
        }

    override fun toString(): String {
        return "Packet version: $version, type: $type "
    }
}

private fun <E> MutableList<E>.product(function: (E) -> Long): Long {
    var product = 1L
    this.forEach {
        product *= function(it)
    }
    return product
}

class Literal(binaryString: String): Data(){
    var valueChars = ""

    init {
        var usedChars =""
        remainingString = binaryString
        while (remainingString.startsWith('1')){
            val chars = remainingString.take(5)
            valueChars +=chars.drop(1)
            usedChars +=chars
            remainingString = remainingString.drop(5)
        }
        val chars = remainingString.take(5)
        valueChars +=chars.drop(1)
        usedChars +=chars
        remainingString = remainingString.drop(5)

    }

    override val value: Long
        get() {
            return valueChars.toLong(2)
        }
}
