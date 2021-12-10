package day06

import utils.Resources.resourceAsListOfString

fun main() {

    fun updateState(input: Int): List<Int>{
        return when(input){
            0 -> listOf(6, 8)
            else -> listOf(input-1)
        }
    }

    fun simulateDays(initialstate: List<Int>, days: Int): List<Int>{
        return (1..days).fold(initialstate) { newstate, _ -> newstate.flatMap { currentstate -> updateState(currentstate) }}
    }

    fun part1(input: List<Int>): Int {
        val answer = simulateDays(input, 80)
        return answer.size
    }

    fun part2(input: List<Int>): Int {
        val answer = simulateDays(input, 256)
        return answer.size
    }

    val input = resourceAsListOfString("src/day06/Day06.txt")
    val initialstate = input.flatMap{it.split(",").map{ itx -> itx.toInt()}}
    //println(part1(initialstate))
    println(part2(initialstate))
}