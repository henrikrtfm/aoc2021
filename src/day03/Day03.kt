package day03

import utils.Resources.resourceAsListOfString

fun main() {
    fun part1(input: List<String>): Int {

        val gamma = input.first().mapIndexed {idx, column ->
            input.groupingBy { it[idx] }.eachCount().maxByOrNull { it.value }!!.key}.joinToString("")

        val epsilon = gamma.map{ if (it == '1') '0' else '1'}.joinToString("")

        return gamma.toInt(2) * epsilon.toInt(2)
    }

    /*fun part2(input: List<String>): Int {
        val oxygen = input.groupingBy { it.first()}.fold(listOf<String>()) { acc, e -> acc +e}
        val gamma = input.first().mapIndexed {idx, column ->
            input.groupingBy { it[idx].fold(listOf<String>()) { acc, e -> acc +e}}}
        println(oxygen)
        return 0
    }*/

    val input = resourceAsListOfString("src/day03/Day03.txt")
    println(part1(input))
    //println(part2(input))
}