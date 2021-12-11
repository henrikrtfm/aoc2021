package day10

import utils.Resources.resourceAsListOfString

fun main() {

    val open = "{([<".toCharArray()
    val close = "]})>".toCharArray()
    val chunks = listOf("{}","<>", "[]", "()")

    fun median(list: List<Long>) = list.sorted().let {
        if (it.size % 2 == 0)
            (it[it.size / 2] + it[(it.size - 1) / 2]) / 2
        else
            it[it.size / 2]
    }

    fun syntaxErrorScore(character: String): Int {
        return when(character){
            ")" -> 3
            "]" -> 57
            "}" -> 1197
            ">" -> 25137
            else -> 0
        }
    }

    fun autoCompleteScore(line: String): Long{
        var score: Long = 0
        for(idx in line.indices){
            score *= 5
            when(line[idx].toString()){
                "(" -> score += 1
                "[" -> score += 2
                "{" -> score += 3
                "<" -> score += 4
            }
        }
        return score
    }

    fun parseChunks(line: String): String? {
        val openChunks = mutableListOf<Char>()
        for(idx in line.indices){
            when(line[idx]) {
                in open -> openChunks.add(line[idx])
                in close -> {
                    val chunk = ""+openChunks.last()+line[idx]
                    if(chunk !in chunks)
                        return line[idx].toString()
                    else{
                        openChunks.removeLast()
                    }
                }

            }
        }
        return null
    }

    fun parseIncompleteLine(line: String): String {
        val openChunks = mutableListOf<String>()
        for(idx in line.indices){
            when(line[idx]) {
                in open -> openChunks.add(line[idx].toString())
                in close -> openChunks.removeLast()
            }
        }
        return openChunks.reduce{acc, char -> acc + char}.reversed()
    }

    fun part1(input: List<String>): Int {
        val illegalCharacters = input.mapNotNull { line -> parseChunks(line) }
        return illegalCharacters.sumOf { syntaxErrorScore(it) }
    }

    fun part2(input: List<String>): Long {
        val answer = input
            .filter { line -> parseChunks(line) == null }
            .map { parseIncompleteLine(it)}
            .map {autoCompleteScore(it)}
        return median(answer)
    }

    val input = resourceAsListOfString("src/day10/Day10.txt")
    //println(part1(input))
    println(part2(input))
}