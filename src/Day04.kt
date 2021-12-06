fun main() {

    fun createBoard(input: List<String>): List<List<Int>> =
        input.map { row -> row.split(" ").filter { it.isNotEmpty() }.map { it.toInt() } }

    fun part1(numbers: List<Int>, bingoBoards: Set<List<List<Int>>>): Int{
        val drawn = mutableSetOf<Int>()
        val columnList = listOf<Int>(0,1,2,3,4)
        val answer = numbers.firstNotNullOf { draw ->
            drawn += draw
            bingoBoards.firstOrNull { board ->
                board.any { row -> row.all { it in drawn } || columnList.any { col -> board.all { row -> row[col] in drawn }}
                }
            }?. let {winningBoard -> winningBoard.flatten().filterNot{it in drawn}.sum()*draw}
        }

        return answer
    }

    fun part2(numbers: List<Int>, bingoBoards: Set<List<List<Int>>>): Int{
        val drawn = mutableSetOf<Int>()
        val boards = bingoBoards.toMutableSet()
        val columnList = listOf<Int>(0,1,2,3,4)
        var answer = 0

        for(i in numbers){
            drawn += i
            val winners = boards.filter{ board ->
                board.any { row -> row.all { it in drawn } || columnList.any { col -> board.all { row -> row[col] in drawn }}
                }}.toMutableSet()
            boards.removeAll(winners)
            println(boards)
            when (boards.size) {
                0 -> {
                    answer = winners.flatten().flatten().filterNot { it in drawn }.sum()*i
                    break
                }
            }
        }
        return answer

    }


    val input = readInput("Day04")
    val numbers = input.first().split(",").map { it.toInt() }
    val bingoBoards = input.asSequence().drop(1).filter{ it.isNotEmpty() }.chunked(5).map { createBoard(it) }.toSet()
    println(part1(numbers, bingoBoards))
    println(part2(numbers, bingoBoards))

}