package day01

import utils.Resources.resourceAsListOfInt

fun main() {
    fun part1(input: List<Int>): Int {
        val listPairs = input.zipWithNext()
        return listPairs.filter { it.first < it.second }.size
    }

    fun part2(input: List<Int>): Int {
        val listWindow = input.windowed(3).map { it.sum() }
        return part1(listWindow)
    }

    val input = resourceAsListOfInt("src/day01/Day01.txt")
    println(part1(input))
    println(part2(input))
}
