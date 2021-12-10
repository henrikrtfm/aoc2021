package day02

import utils.Resources.resourceAsListOfString

fun main() {
    fun part1(input: List<String>): Int {
        val commandList = input.map(Command.Companion::parse)
        var horizontal = 0
        var depth = 0
        commandList.forEach {
            when(it.direction){
                "forward" -> horizontal += it.value
                "up" -> depth -= it.value
                "down" -> depth += it.value
            }
        }
        return horizontal*depth
    }

    fun part2(input: List<String>): Int {
        val commandList = input.map(Command.Companion::parse)
        var horizontal = 0
        var depth = 0
        var aim = 0
        commandList.forEach {
            when(it.direction){
                "forward" -> {
                    horizontal += it.value
                    depth += aim*it.value
                }
                "up" -> aim -= it.value
                "down" -> aim += it.value
            }
        }
        return horizontal*depth
    }

    val input = resourceAsListOfString("src/day02/Day02.txt")
    println(part1(input))
    println(part2(input))
}
data class Command(
    val direction: String,
    val value: Int
){
    companion object {
        fun parse(line: String) = Command(
            direction = line.substringBefore(" "),
            value = line.substringAfter(" ").toInt()
        )
    }
}