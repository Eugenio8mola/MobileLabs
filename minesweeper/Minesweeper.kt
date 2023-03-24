import java.util.*
import kotlin.collections.ArrayList


fun countMines(index:Int,R:Int,C:Int,board: List<Char>): Int{
    var countAdjacent= 0

    val size = R*C -1

    var i = index
    //vertical
    if ((i!= 0 || i>=R)  && (i - R) >= 0 && (i - R) <= size && board[i-R].compareTo('*') == 0)
        countAdjacent++;
    if (i<=(size-R) && (i + R) >= 0 && (i + R) <= size && board[i+R].compareTo('*') == 0)
        countAdjacent++;

    //horizontal
    if ((i%R != 0) && (i - 1) >= 0 && (i - 1) <= size && board[i-1].compareTo('*') == 0)
        countAdjacent++;
    if ((i%R != (R-1)) && (i + 1) >= 0 && (i + 1) <= size && board[i+1].compareTo('*') == 0)
        countAdjacent++;

    //diag1
    if ((i-R+1) >= 0 && (i-R+1) <= size && board[i-R+1].compareTo('*') == 0)
        countAdjacent++;
    if (i<=(size-R) && (i%R != 0) && (i+R-1) >= 0 && (i+R-1) <= size && board[i+R-1].compareTo('*') == 0)
        countAdjacent++;

    //diag2
    if ((i%R != 0) && (i-R-1) >= 0 && (i-R-1) <= size && board[i-R-1].compareTo('*') == 0)
        countAdjacent++;
    if (i<=(size-R) && (i%R != (R-1)) && (i+R+1) >= 0 && (i+R+1) <= size && board[i+R+1].compareTo('*') == 0)
        countAdjacent++;


    return countAdjacent
}

fun computeBoard(inputBoard: List<String>): List<String> {

    //get dims
    val R = inputBoard.size
    val C = inputBoard.firstOrNull()?.length ?: 0

    var newBoard = inputBoard.flatMap{ char -> char.toList() }// convert to single elements
    val emptyBoard = mutableListOf<String>()

    var myString :String = ""

    var count = 1

    for((index,i) in newBoard.withIndex()) {

        if (i.compareTo(' ') == 0) {
            //algorithm

            var value = countMines(index,R,C,newBoard)
            if(value != 0)
               myString =  myString + "$value"
            else
                myString =   myString + " "
        }
        else{
            myString =   myString + "$i"
        }
        if(count % C == 0 ) {
            emptyBoard.add("$myString")
            myString = ""     //flush the string
        }
        count++

    }

//print Result
    for(i in emptyBoard)
    {
        println(i)
    }


    if(R == 1 && C == 0)
    {
        var myNewBoard = Collections.singletonList<String>("")
        return myNewBoard
    }


    return emptyBoard


}

data class MinesweeperBoard( val inputBoard: List<String>) {
    fun withNumbers(): List<String> {

        var Board = computeBoard(inputBoard)

        return Board
    }

}
