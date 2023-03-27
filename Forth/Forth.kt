
class InvalidException(message: String): Exception(message)
val operations = mutableListOf<String>()
val definitions = mutableListOf<String>()
val temp = mutableListOf<String>()
var isRedefined : Boolean = false
var isWord : Boolean = false

class Forth {


    fun evaluate(vararg line: String): List<Int> {
        val words = mutableListOf<String>()//val words = mutableListOf<String>("dup", "drop", "swap", "over","+","-","*","/")
        val emptyList = mutableListOf<String>()
        var stack = mutableListOf<Int>()
        definitions.clear()

        for(point in line)
        {

            emptyList.add("${point}")
            println("point is $point")
            println(emptyList)
            /*
            val isWord =   point.lowercase().contains(":") && point.lowercase().contains(";")
            if(isWord) {

            }
             */
            var listOfNum = emptyList.toString().split(" ", "[" , "]")
            emptyList.clear()
            listOfNum = listOfNum.slice(1..(listOfNum.size-2))
            println(listOfNum.elementAt((listOfNum.size)-1))

            if(listOfNum.last().equals(";"))
            {
                println("enters")
                listOfNum = listOfNum.slice(0..(listOfNum.size-2))
            }

            println("content of listNum $listOfNum")

            stack = toInteger(listOfNum,stack,words)
        }


        return stack
    }
}



fun defineNewWord(word:String):String
{
    //var word = ": word-name definition;"
    var list = word.split(" ", ":", ";")
    // new word
    val myList = list.slice(2..4)
    val name = myList.elementAt(1)
    return name.lowercase()
}



fun isNumeric(str: String): Boolean {
    return str.toDoubleOrNull() != null
}



fun toInteger(list : List<String>, stack : MutableList<Int>,words : MutableList<String>) :MutableList<Int>
{

    isWord = false

    val newStack = mutableListOf<Int>()
    for((index,element) in list.withIndex()) {
        if(isNumeric(element))
        {
            stack.add(element.toInt())
        }
        else {
            var myEl = element.lowercase()
            println("-----value of element is $myEl  ---------")
            if (!isWord){


                when (element.lowercase()) {

                    "+" -> {
                        if (stack.size == 1) throw InvalidException("only one value on the stack")
                        if (stack.size == 0) throw InvalidException("empty stack")
                        else {
                            newStack.add(stack.elementAt(0) + stack.elementAt(1))
                            stack.clear()
                            stack.addAll(newStack)
                            newStack.clear()
                        }
                    }
                    "-" -> {
                        if (stack.size == 1) throw InvalidException("only one value on the stack")
                        if (stack.size == 0) throw InvalidException("empty stack")
                        else {
                            newStack.add(stack.elementAt(0) - stack.elementAt(1))
                            stack.clear()
                            stack.addAll(newStack)
                            newStack.clear()
                        }
                    }
                    "*" -> {
                        if (stack.size == 1) throw InvalidException("only one value on the stack")
                        if (stack.size == 0) throw InvalidException("empty stack")
                        else {

                            newStack.add(stack.elementAt(stack.size - 2) * stack.elementAt(stack.size - 1))
                            stack.clear()
                            stack.addAll(newStack)
                            newStack.clear()
                        }
                    }
                    "/" -> {
                        if (stack.size == 1) throw InvalidException("only one value on the stack")
                        if (stack.size == 0) throw InvalidException("empty stack")
                        if (stack.elementAt(stack.size - 1) == 0) throw InvalidException("divide by zero")
                        else {

                            newStack.add(stack.elementAt(stack.size - 2) / stack.elementAt(stack.size - 1))
                            stack.clear()
                            stack.addAll(newStack)
                            newStack.clear()
                        }
                    }
                    "dup" -> {
                        if (stack.size == 0) throw InvalidException("empty stack")
                        else {
                            println(stack)
                            stack.add(stack.last())
                            println(stack)
                            println("--using-- dup ")

                        }

                    }
                    "drop" -> {
                        if (stack.size == 0) throw InvalidException("empty stack")
                        else {
                            stack.removeAt(stack.size - 1)


                        }

                    }
                    "swap" -> {
                        if (stack.size == 1) throw InvalidException("only one value on the stack")
                        if (stack.size == 0) throw InvalidException("empty stack")
                        else {

                            newStack.add(stack.elementAt(((stack.size) - 1)))
                            newStack.add(stack.elementAt(((stack.size) - 2)))
                            stack.removeAt((stack.size - 1))
                            stack.removeAt((stack.size - 1))
                            stack.addAll(newStack)
                            newStack.clear()


                        }

                    }
                    "over" -> {
                        if (stack.size == 1) throw InvalidException("only one value on the stack")
                        if (stack.size == 0) throw InvalidException("empty stack")
                        else {
                            stack.add(stack.elementAt(index - 2))

                        }

                    }

                    ":" -> {
                        if (isNumeric(list.elementAt(index + 1))) throw InvalidException("illegal operation")
                        else {

                            isRedefined = false
                            var value: String = ""
                            //words.clear()  //flush list at each definition
                            //operations.clear()
                            println("initial list is $list")
                            println(list.elementAt(1))
                            val name = list.elementAt(1).lowercase()


                            //set value
                            for (i in 2..list.size - 1) {
                                value += list.elementAt(i).lowercase()
                                if (i != (list.size - 1)) {
                                    value += " "
                                }
                                println("partial value is $value")

                            }

                            //redefinition of name
                            for ((index, word) in words.withIndex()) {
                                if (word.equals(name)) {
                                    definitions.removeAt(index)
                                    temp.add(value)   //push new value
                                    temp.addAll(definitions)
                                    definitions.clear()
                                    definitions.addAll(temp)
                                    temp.clear()
                                    isRedefined = true

                                }
                            }
                            if (!isRedefined)
                                words.add("$name")

                            //redefinition of value
                            for ((index, word) in words.withIndex()) {
                                if (word.equals(value)) {
                                    value = definitions.elementAt(index)

                                }
                            }
                            println("value to be pushed is $value")
                            if (!isRedefined)
                                definitions.add("$value")
                            println("definitions is:  $definitions")

                            //var operationList = list.mapIndexed { index,item -> index > 1 }.toList()
                            //operations.addAll(list.takeLast(list.size -2))

                            println("name is = $name")
                            isWord = true
                            println(words)

                            //var tmpList = definitions.toString().split(" ", "[" , "]")
                            //tmpList = tmpList.slice(1..(tmpList.size-3))
                            // println("tmpList is : ${tmpList.toString()}")
                            //definitions.clear()
                            //definitions.addAll(tmpList)
                            println("definit SIZE is ${definitions.size}")
                            operations.clear()
                            operations.addAll(definitions)
                            println("operation is $operations")
                            return stack   //ricevi prossima stringa
                        }
                    }
                    else -> {
                        println("stack is $stack")
                        println(words)
                        var nonExistent: Boolean = true
                        for ((index, word) in words.withIndex()) {
                            println("[$word] in $words ")
                            if (element.lowercase().equals(word)) {
                                nonExistent = false
                                temp.add(definitions.elementAt(index))
                            }

                        }
                        if (nonExistent) throw InvalidException("undefined operation")
                        else {

                            println("temp is:--> $temp")
                            var tmpList = temp.toString().split(" ", "[", "]")
                            tmpList = tmpList.slice(1..(tmpList.size - 2))
                            println("tmpList is : ${tmpList}")



                            operations.clear()
                            operations.addAll(tmpList)
                            temp.clear()
                            val wordStack = toInteger(operations, stack, words)
                            println("stack is $stack")


                        }
                    }

                }
            // end while

        }else // end if
            {
                println("---------ENTERS HERE ---------")
                println("stack is $stack")
                println(words)
                var nonExistent: Boolean = true
                for ((index, word) in words.withIndex()) {
                    println("[$word] in $words  and elem is: --> $myEl and $element")
                    println("operations ---> $operations")
                    if (element.lowercase().equals(word)) {
                        nonExistent = false
                        temp.add(definitions.elementAt(index))
                    }

                }
                if (nonExistent) throw InvalidException("undefined operation")
                else {

                    println("temp is:--> $temp")
                    var tmpList = temp.toString().split(" ", "[", "]")
                    tmpList = tmpList.slice(1..(tmpList.size - 2))
                    println("tmpList is : ${tmpList}")



                    operations.clear()
                    operations.addAll(tmpList)
                    temp.clear()
                    val wordStack = toInteger(operations, stack, words)
                    println("stack is $stack")

                }
            }  // end ELSE


            }

    }

    //operations.clear()

    return stack
}
/*
fun main()
{

     //-----// getName //--------
    var wordName = ": word-name definition;"
    var new = wordName.split(" ", ":", "-",";")
    println(new)
    val list = mutableListOf<String>()
    for((index,element) in new.withIndex())
    {
        println("element[$index] =$element")
        list.add("$element")
    }
    println(list)
    // new word
    val myList = list.slice(2..4)
    println(myList)
    val name = myList.elementAt(1)
    println(name)


    //val evaluateString = "1 2 3 4 5"
    val evaluateString = "1 2 +"


    val numList = evaluateString.split(" ")
    //println(numList)

    var myStack = mutableListOf<Int>()
    myStack = toInteger(numList,myStack)
    println(myStack)

    val res = numList.elementAt(0).toBigInteger() + numList.elementAt(1).toBigInteger()
    println(res)

    var sent = "hello"
    val sentUpper= sent.uppercase()
    println(sentUpper)

}

 */

/*
fun main()
{

    val evaluateString = "1 2 +"
    var myfour = Forth()
    val stack =  myfour.evaluate(evaluateString)
    println(stack)
}

 */

