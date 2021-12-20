package day14

import utils.Resources.resourceAsListOfString

fun main() {

    val rules = mutableMapOf<String, String>()

    fun pairInsertion(pair: String):String {
        val insert = rules[pair]!!
        return pair.first()+insert+pair.last()
    }

    fun polymerTemplate(template:String): String {
        val pairs = template.windowed(2)
            .map { pairInsertion(it) }
        return (pairs.joinToString { it.dropLast(1) } + template.last()).replace(", ", "")
    }

    fun part1(template:String): Int {
        val polymer = (0..9).fold(template) {template, _ -> polymerTemplate(template)}
        val answer = polymer.groupingBy { it }.eachCount()
        return answer.maxOf { it.value }-answer.minOf { it.value }
    }

    fun part2(template:String): Int {
        val polymer = (0..39).fold(template) {template, _ -> polymerTemplate(template)}
        val answer = polymer.groupingBy { it }.eachCount()
        return answer.maxOf { it.value }-answer.minOf { it.value }
    }
    val template = "VNVVKSNNFPBBBVSCVBBC"

    val input = resourceAsListOfString("src/day14/Day14.txt")
    input.forEach { it -> rules[it.substringBefore(" -")] = it.substringAfter("> ") }

    //println(part1(template))
    println(part2(template))
}