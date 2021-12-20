import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.max

class Day18 {
    fun start() {
        val data = getListOfData("Day18.txt").map { Pair.createFromString(it, null) }

        var maxMag=0
        data.forEachIndexed { index1, value1 ->
            data.forEachIndexed { index2, value2->
                if(index1 != index2) {
                    val result = value1.copy().add(value2.copy())
                    maxMag = max(result.magnitude(), maxMag)
                }
            }
        }

        println(maxMag)
    }

    interface Value {
        fun copy(): Value
        var parent: Value?

        fun reduce()
        fun explode(level: Int):Boolean
        fun addToLeftParent(pair: Pair, value: Int)
        fun addToRightParent(pair: Pair, value: Int)
        fun getMostLeftChild(value: Int)
        fun getMostRightChild(value: Int)
        fun makeChildNull(pair: Pair)
        fun split(parent:Pair):Boolean
        fun add(value: Value): Value
        fun magnitude(): Int
    }

    class Pair(): Value {
        var leftValue: Value? = null
        var rightValue: Value? = null
        override fun copy(): Value {
            val pair = Pair()
            pair.leftValue = leftValue?.copy()
            pair.rightValue = rightValue?.copy()
            pair.leftValue?.parent = pair
            pair.rightValue?.parent = pair
            return pair
        }

        override var parent: Value? = null



        companion object {
            fun createFromString(string: String, parent: Pair?):Value {

                if(string.count() == 1) {
                    return Number(string.toInt())
                }
                val newString = string.removePrefix("[").removeSuffix("]")
                var i = 0
                var count = 0
                newString.forEach {
                    if(it == '['){
                        i++
                    } else if(it == ']')
                    {
                        i--
                    }
                    if (i==0){
                        val firstPart = newString.substring(0, count+1)
                        val secondPart = newString.substring(count+2, newString.length)
                        val pair = Pair()
                        pair.leftValue = createFromString(firstPart, pair)
                        pair.rightValue = createFromString(secondPart, pair)
                        pair.parent = parent
                        return pair
                    }
                    count++
                }

                println(string)
                throw error("Something went wrong")
            }
        }

        override fun add(value: Value): Value{
            val newPair =  Pair()
            this.parent = newPair
            value.parent = newPair
            newPair.leftValue = this
            newPair.rightValue = value
            newPair.reduce()
            return newPair
        }

        override fun magnitude(): Int {
            return 3* (leftValue?.magnitude() ?: 0) + 2* (rightValue?.magnitude() ?: 0)
        }

        override fun reduce() {
            var changes: Boolean
            do{
               changes = this.explode(0)
                if(!changes){
                     changes = this.split(this)
                }

            }while (changes)
        }
        override fun explode(level: Int):Boolean {
            return if(level>=4 && leftValue is Number && rightValue is Number){
                parent?.addToLeftParent(this, (leftValue as Number).value)
                parent?.addToRightParent(this, (rightValue as Number).value)
                parent?.makeChildNull(this)
                true
            } else {
                if(leftValue?.explode(level+1) == true) {
                    true
                } else {
                    rightValue?.explode(level+1) == true
                }
            }

        }

        override fun addToLeftParent(pair: Pair, value: Int){
            if (this.leftValue == pair ){
                if(parent!= null){

                    (this.parent as Pair).addToLeftParent(this, value)
                }
            } else {
                this.leftValue?.getMostRightChild(value)
            }
        }

        override fun getMostRightChild(value: Int) {

            this.rightValue?.getMostRightChild(value)

        }

        override fun makeChildNull(pair: Pair) {
            if(leftValue == pair) {
                leftValue = Number(0)
            } else
            {
                rightValue = Number(0)
            }
        }

        override fun split(parent: Pair): Boolean {
            return if(this.leftValue?.split(this) == true) {
                true
            } else {
                this.rightValue?.split(this) ?:false
            }
        }

        override fun addToRightParent(pair: Pair, value: Int){
            if (this.rightValue == pair){
                if(parent!=null){
                (this.parent as Pair).addToRightParent(this, value)

                }
            } else {
                this.rightValue?.getMostLeftChild(value)
            }
        }

        override fun getMostLeftChild(value: Int) {

            this.leftValue?.getMostLeftChild(value)

        }

        override fun toString(): String {
            return "[ $leftValue , $rightValue ]"
        }
    }

    class Number (var value: Int): Value {
        override fun copy(): Value {
            return Number(value)
        }

        override var parent: Value? = null

        override fun reduce() {

        }

        override fun explode(level: Int):Boolean {
            return false
        }

        override fun addToLeftParent(pair: Pair, value: Int) {
        }

        override fun addToRightParent(pair: Pair, value: Int) {
        }

        override fun getMostLeftChild(value: Int) {
            this.value += value
        }

        override fun getMostRightChild(value: Int) {
            this.value +=value
        }

        override fun makeChildNull(pair: Pair) {
        }

        override fun split(parent: Pair): Boolean {
            if(value > 9){
                val leftValue = floor((value.toDouble()/2))
                val rightValue = ceil((value.toDouble()/2))
                val pair = Pair()
                pair.leftValue = Number(leftValue.toInt())
                pair.rightValue = Number(rightValue.toInt())

                pair.parent = parent

                if(parent.leftValue == this) {
                    parent.leftValue = pair
                } else {
                    parent.rightValue = pair
                }

                return true
            }
            return false
        }

        override fun add(value: Value): Value {
            error("Nothing to do here")
        }

        override fun magnitude(): Int {
            return value
        }


        override fun toString(): String {
            return value.toString()
        }

    }
}

