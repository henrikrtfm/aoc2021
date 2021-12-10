package day07

import utils.Resources.resourceAsString
import kotlin.math.absoluteValue

fun main() {
    fun median(list: List<Double>) = list.sorted().let {
        if (it.size % 2 == 0)
            (it[it.size / 2] + it[(it.size - 1) / 2]) / 2
        else
            it[it.size / 2]
    }

    fun part1(input: List<Double>): Int {
        return input.sumOf { (it - median(input)).absoluteValue}.toInt()
    }

    fun part2(input: List<Int>): Int {
        val mean = input.sum()/input.size
        return input.sumOf { ((it-mean).absoluteValue * (it-mean).absoluteValue + (it-mean).absoluteValue) /2 }
    }

    val input1 = resourceAsString("src/day07/Day07.txt").split(",").map{it.toDouble()}
    val input2 = resourceAsString("src/day07/Day07.txt").split(",").map{it.toInt()}
    println(part1(input1))
    println(part2(input2))
}
