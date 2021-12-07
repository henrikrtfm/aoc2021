import kotlin.math.absoluteValue
import kotlin.math.sign

fun main() {

    fun calculateDelta(input: Line): List<Point>{
        val xDelta = (input.endingPoint.first - input.startingPoint.first).sign
        val yDelta = (input.endingPoint.second - input.startingPoint.second).sign
        val distance = maxOf((input.startingPoint.first - input.endingPoint.first).absoluteValue, (input.startingPoint.second - input.endingPoint.second).absoluteValue)
        return (1..distance).scan(Point(input.startingPoint.first,input.startingPoint.second)) { last, _ -> Point(last.x+xDelta, last.y+yDelta)}
    }

    fun part1(input: List<String>): Int {
        val lines = input.map(Line::parse).filter { line -> line.startingPoint.first == line.endingPoint.first || line.startingPoint.second == line.endingPoint.second }
        val ovarlappingPoints = mutableMapOf<Point, Int>()
        val points = lines.flatMap { line -> calculateDelta(line) }
        points.forEach { point -> ovarlappingPoints.merge(point, 1, Int::plus) }

        return ovarlappingPoints.values.count { value -> value >= 2 }
    }

    fun part2(input: List<String>): Int {
        val lines = input.map(Line::parse)
        val ovarlappingPoints = mutableMapOf<Point, Int>()
        lines
            .flatMap { line -> calculateDelta(line) }
            .forEach { point -> ovarlappingPoints.merge(point, 1, Int::plus) }

        return ovarlappingPoints.values.count { value -> value >= 2 }
    }

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}
data class Point(
    val x: Int,
    val y: Int
)
data class Line(
    val startingPoint: Pair<Int, Int>,
    val endingPoint: Pair<Int, Int>
){
    companion object {
        fun parse(line: String) = Line(
            startingPoint = Pair(line.substringBefore(",").toInt(), line.substringAfter(",").substringBefore(" ").toInt()),
            endingPoint = Pair(line.substringAfterLast(" ").substringBeforeLast(",").toInt(), line.substringAfterLast(",").toInt())
        )
    }
}