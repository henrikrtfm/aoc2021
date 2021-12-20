package day13

import utils.Resources.resourceAsListOfString

fun main() {

    fun reversePoint(point: Point, fold: Point): Point{
        return when(fold.y){
            0 -> Point(x=(fold.x*2-point.x),y=point.y)
            else -> Point(x=point.x,y=(fold.y*2-point.y))
        }
    }

    fun foldManual(points: Set<Point>, instruction: Point): Set<Point> {
        val lowerFoldCopy = when(instruction.y){
            0 -> points.filter { point -> point.x < instruction.x }.toSet()
            else -> points.filter { point -> point.y < instruction.y}.toSet()
        }
        val upperFoldCopy = when(instruction.y){
            0 -> points.filter { point -> point.x > instruction.x }
            else -> points.filter { point -> point.y > instruction.y}
        }
        val newFoldedCopy = upperFoldCopy.map{ reversePoint(it, instruction)}.toSet()
        return lowerFoldCopy.plus(newFoldedCopy)
    }

    fun part1(points: Set<Point>, instructions: List<Point>): Int {
        return foldManual(points, instructions.first()).count()
    }

    fun printAnswer(answer: Set<Point>) {
        (0..answer.maxOf { it.y }).forEach { y ->
            (0..answer.maxOf { it.x }).forEach { x ->
                print(if (Point(x, y) in answer) "#" else " ")
            }
            println()
        }
    }

    fun part2(points: Set<Point>, instructions: List<Point>){
        printAnswer(instructions.fold(points) {points, instruction -> foldManual(points, instruction)})
    }

    val input = resourceAsListOfString("src/day13/Day13.txt")
    val instructions = parseInstructions(input)
    val points = parsePoints(input)
    //println(part1(points, instructions))
    part2(points, instructions)
}
fun parsePoints(input: List<String>): Set<Point> {
    return input.takeWhile { it.isNotEmpty() }
        .map{line -> Point(x=line.substringBefore(",").toInt(), y=line.substringAfter(",").toInt())}.toSet()
}
data class Point(
    val x: Int,
    val y: Int
)
fun parseInstructions(input: List<String>): List<Point> {
    return input.takeLastWhile { it.isNotEmpty() }
        .map{line ->
            when{
                line.substringBefore("=").endsWith("y") -> Point(x=0, y=line.substringAfter("=").toInt())
                else -> Point(x=line.substringAfter("=").toInt(), y=0 )
            }
        }
}
