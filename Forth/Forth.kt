import com.sun.jdi.request.InvalidRequestStateException

class InvalidException(message: String): Exception(message)



class Forth {

    fun evaluate(vararg line: String): List<Int> {
        val words = mutableListOf<String>("DUP", "DROP", "SWAP", "OVER","+","-","*","/")
        val emptyList = mutableListOf<String>()
        var stack = mutableListOf<Int>()

        //var wordName = ": word-name definition;"

        for(point in line)
        {

            emptyList.add("${point}")
            println("point is $point")
            println(emptyList)
            /*
            val isWord =   point.lowercase().contains(": word-") && point.lowercase().contains(";")
            if(isWord) {
                val name = defineNewWord(point)
                words.add("$name")
            }

             */
        }

        var listOfNum = emptyList.toString().split(" ", "[" , "]")
        listOfNum = listOfNum.slice(1..(listOfNum.size-2))
        println(listOfNum)

        stack = toInteger(listOfNum,stack)

        return stack
    }
}



fun defineNewWord(word:String):String
{
    //var word = ": word-name definition;"
    var list = word.split(" ", ":", "-",";")
    // new word
    val myList = list.slice(2..4)
    val name = myList.elementAt(1)
    return name.uppercase()
}



fun isNumeric(str: String): Boolean {
    return str.toDoubleOrNull() != null
}



fun toInteger(list : List<String>, stack : MutableList<Int>) :MutableList<Int>
{
    val newStack = mutableListOf<Int>()
    for((index,element) in list.withIndex()) {
        if(isNumeric(element))
        {
            stack.add(element.toInt())
            //plus( list.elementAt(index).toBigInteger() )
        }
        else
        {
            when (element.lowercase())
            {
                "+" -> {
                    if(stack.size == 1) throw InvalidException("only one value on the stack")
                    if(stack.size == 0) throw InvalidException("empty stack")
                    else {
                        newStack.add(stack.elementAt(0) + stack.elementAt( 1))
                        stack.clear()
                        stack.addAll(newStack)
                        newStack.clear()
                    }
                }
                "-"-> {
                    if(stack.size == 1) throw InvalidException("only one value on the stack")
                    if(stack.size == 0) throw InvalidException("empty stack")

                    else{
                        newStack.add(stack.elementAt(0) - stack.elementAt(1))
                        stack.clear()
                        stack.addAll(newStack)
                        newStack.clear()
                    }
                }
                "*"-> {
                    if(stack.size == 1) throw InvalidException("only one value on the stack")
                    if(stack.size == 0) throw InvalidException("empty stack")
                    else {
                        newStack.add(stack.elementAt(0) * stack.elementAt(1))
                        stack.clear()
                        stack.addAll(newStack)
                        newStack.clear()
                    }
                }
                "/"-> {
                    if(stack.size == 1) throw InvalidException("only one value on the stack")
                    if(stack.size == 0) throw InvalidException("empty stack")
                    if(stack.elementAt(index - 1) == 0) throw InvalidException("divide by zero")
                    else{
                        newStack.add(stack.elementAt(0) / stack.elementAt(1))
                        stack.clear()
                        stack.addAll(newStack)
                        newStack.clear()
                    }
                }
                "dup"->
                {
                    if(stack.size == 0) throw InvalidException("empty stack")
                    else{
                        newStack.add(stack.elementAt(index - 1) )
                        stack.addAll(newStack)
                        newStack.clear()
                    }

                }
                "drop"->
                {
                    if(stack.size == 0) throw InvalidException("empty stack")
                    else{
                        stack.removeAt(index - 1)

                    }

                }
                "swap"->
                {
                    if(stack.size == 1) throw InvalidException("only one value on the stack")
                    if(stack.size == 0) throw InvalidException("empty stack")
                    else{
                        newStack.add(stack.elementAt(index - 1) )
                        newStack.add(stack.elementAt(index - 2) )
                        stack.removeAt(index-1)
                        stack.removeAt(index-2)
                        stack.addAll(newStack)
                        newStack.clear()

                    }

                }
                "over"->
                {
                    if(stack.size == 1) throw InvalidException("only one value on the stack")
                    if(stack.size == 0) throw InvalidException("empty stack")
                    else{
                        stack.add(stack.elementAt(index - 2) )

                    }

                }
            }
        }


    }

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

