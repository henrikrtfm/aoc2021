package day09

import utils.Resources.resourceAsListOfString

typealias Heightmap = Array<IntArray>
typealias Point = Pair<Int, Int>

fun main() {

    val neighbors = sequenceOf(-1 to 0, 0 to -1, 0 to 1, 1 to 0)
    val input = resourceAsListOfString("src/day09/Day09.txt")
    val heightmap: Heightmap = input
        .map { it.chunked(1) }
        .map { it -> it.map { it -> it.toInt() }.toIntArray() }
        .toTypedArray()

    fun returnNeighbors(point: Point): List<Point>{
        return  neighbors.map { it + point}.filter { it in heightmap }.toList()
    }

    fun findLowestPoints(point: Point): Point? {
        val self = heightmap[point.first][point.second]
        val neighborsList = returnNeighbors(point).map { nb -> heightmap[nb.first][nb.second] }.toList() + self
        return if (neighborsList.minOrNull() == self && neighborsList.groupingBy { it }.eachCount()[self]==1) {
            point
        } else
            null
    }

    fun calculateBasin(point: Point): Int {
        val visited = mutableSetOf(point)
        val queue = mutableListOf(point)
        while(queue.isNotEmpty()){
            val neighborPoints = returnNeighbors(queue.removeFirst())
                .filter { it !in visited }
                .filter { heightmap[it.first][it.second] != 9 }
            visited.addAll(neighborPoints)
            queue.addAll(neighborPoints)
        }
        return visited.size
    }

    fun part1(): Int {
        val lowestPoints = heightmap
            .flatMapIndexed { indexrow, row -> row.mapIndexed {indexcol, _ -> findLowestPoints(Point(indexrow, indexcol))}}
            .filterNotNull()
        return lowestPoints.sumOf { point -> heightmap[point.first][point.second] +1 }
    }

    fun part2(): Int {
        val lowestpoints = heightmap
            .flatMapIndexed { indexrow, row -> row.mapIndexed {indexcol, _ -> findLowestPoints(Point(indexrow, indexcol))}}
            .filterNotNull()
        val basins = lowestpoints.map { point -> calculateBasin(point)}
        val largestBasins = basins.sortedDescending().take(3)
        return largestBasins.reduce { acc, basin -> acc * basin }
    }

    //println(part1())
    println(part2())
}

private operator fun Point.plus(that: Point): Point =
    Point(this.first + that.first, this.second + that.second)

private operator fun Heightmap.contains(point: Point): Boolean =
    point.first in this.indices && point.second in this.first().indices