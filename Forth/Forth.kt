
class InvalidException(message: String): Exception(message)
val operations = mutableListOf<String>()

class Forth {


    fun evaluate(vararg line: String): List<Int> {
        val words = mutableListOf<String>()//val words = mutableListOf<String>("dup", "drop", "swap", "over","+","-","*","/")
        val emptyList = mutableListOf<String>()
        var stack = mutableListOf<Int>()


        //var wordName = ": word-name definition;"

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

        /*
        var listOfNum = emptyList.toString().split(" ", "[" , "]")
        listOfNum = listOfNum.slice(1..(listOfNum.size-2))
        println(listOfNum)

        stack = toInteger(listOfNum,stack)

         */

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
    //val operations = mutableListOf<String>()
     //"dup", "drop", "swap", "over","+","-","*","/"
    var isWord : Boolean = false
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
                        println(stack)
                        stack.add(stack.last())
                        println(stack)
                        println("--using-- dup ")
                        //stack.addAll(newStack)
                        //newStack.clear()
                    }

                }
                "drop"->
                {
                    if(stack.size == 0) throw InvalidException("empty stack")
                    else{
                        stack.removeAt(stack.size-1)



                    }

                }
                "swap"->
                {
                    if(stack.size == 1) throw InvalidException("only one value on the stack")
                    if(stack.size == 0) throw InvalidException("empty stack")
                    else{

                        newStack.add(stack.elementAt(((stack.size)-1)) )
                        newStack.add(stack.elementAt(((stack.size)-2)) )
                        stack.removeAt((stack.size-1))
                        stack.removeAt((stack.size-1))
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

                ":"->
                {
                    if(isNumeric(list.elementAt(index+1))) throw InvalidException("illegal operation")
                    else{

                        words.clear()  //flush list at each definition
                        operations.clear()
                        println("initial list is $list")
                        println(list.elementAt(1))
                        val name = list.elementAt(1).lowercase()
                        words.add("$name")
                        //var operationList = list.mapIndexed { index,item -> index > 1 }.toList()
                        operations.addAll(list.takeLast(list.size -2))
                        println("operation is $operations")
                        println("name is = $name")
                        isWord = true
                        println(words)
                        return stack


                    }

                }
                else -> {
                    println("stack is $stack")
                    println(words)
                    var nonExistent :Boolean = true
                    for (word in words){
                        println("[$word] in $words ")
                        if(element.equals(word))
                        {
                            nonExistent = false
                        }

                                        }
                    if(nonExistent) throw InvalidException("undefined operation")
                    else
                    {
                       val wordStack =  toInteger(operations, stack,words)
                        println("stack is $stack")
                        return stack



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

