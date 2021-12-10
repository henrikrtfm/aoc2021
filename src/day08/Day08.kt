package day08

import utils.Resources.resourceAsListOfString

fun main() {

    fun uniqueCount(segment: String): Int{
        return when(segment.length){
            2,3,4,7 -> 1
            else -> 0
        }
    }

    fun uniquePatterns(patterns: List<String>): Map<String, String> {
        val uniquepatterns = mutableMapOf<String, String>()
        patterns.forEach { pattern ->
            when(pattern.length){
                2 -> uniquepatterns["1"] = pattern
                3 -> uniquepatterns["7"] = pattern
                4 -> uniquepatterns["4"] = pattern
                7 -> uniquepatterns["8"] = pattern
            }
        }
        return uniquepatterns
    }

    fun analyzePatterns(inputPatterns: List<String>, outputPatterns: List<String>): String{
        val uniques = uniquePatterns(inputPatterns)
        val numbers = outputPatterns.fold("") {acc, pattern -> acc +
                when(pattern.length){
                    6 -> {
                        when{
                            uniques["4"]?.toSet()?.subtract(pattern.toSet()).isNullOrEmpty() -> "9"
                            uniques["7"]?.toSet()?.subtract(pattern.toSet()).isNullOrEmpty() -> "0"
                            else -> "6"
                        }
                    }
                    5 -> {
                        when{
                            uniques["1"]?.toSet()?.subtract(pattern.toSet()).isNullOrEmpty() -> "3"
                            uniques["4"]?.toSet()?.subtract(pattern.toSet())?.size == 1  -> "5"
                            else -> "2"
                        }

                    }
                    2 -> "1"
                    3 -> "7"
                    4 -> "4"
                    else -> "8"

                }
        }
        return numbers
    }

    fun part1(input: List<String>): Int {
        val outputSegments = input.flatMap{it.substringAfter("| ").split(" ")}
        return outputSegments.sumOf { segment -> uniqueCount(segment) }
    }

    fun part2(input: List<String>): Int {
        val parsedPatterns = input.map { it.split(" | ").map { it2 -> it2.split(" ") }}
        val answer = parsedPatterns.map { pattern -> analyzePatterns(pattern.first(), pattern.last()) }
        return answer.sumOf { it.toInt() }
    }

    val input = resourceAsListOfString("src/day08/Day08.txt")
    //println(part1(input))
    println(part2(input))
}