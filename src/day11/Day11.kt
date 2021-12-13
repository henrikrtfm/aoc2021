package day11

import utils.Resources.resourceAsListOfString

typealias Grid = Array<IntArray>
typealias Point = Pair<Int, Int>

fun main() {

    val neighbors = sequenceOf(-1 to -1, -1 to 0, -1 to 1, 0 to -1, 0 to 1, 1 to -1, 1 to 0, 1 to 1)

    fun returnNeighbors(point: Point, octopusGrid: Grid): List<Point>{
        return  neighbors.map { it + point}.filter { it in octopusGrid }.toList()
    }

    fun flashing(octopusGrid: Grid): List<Point>{
        return octopusGrid.flatMapIndexed { idx, row ->
            row.mapIndexed { idy, col ->
                when (col) {
                    10 -> Point(idx, idy)
                    else -> null
                }
            }
        }.filterNotNull()
    }

    fun increaseEnergyLevel(point: Point, octopusGrid: Grid, value: Int){
        octopusGrid[point.first][point.second] += value
    }

    fun setEnergyLevel(point: Point, octopusGrid: Grid, value: Int ){
        octopusGrid[point.first][point.second] = value
    }

    fun updateState(octopusGrid: Grid): Int{
        octopusGrid.mapIndexed() {idx, row -> row.mapIndexed{idy, _ -> octopusGrid[idx][idy] += 1 } }
        val flashed = mutableSetOf<Point>()
        val queue = mutableListOf<Point>()
        val flashing = flashing(octopusGrid)
        flashed.addAll(flashing)
        queue.addAll(flashing)
        while(queue.isNotEmpty()){
            val octopuses = returnNeighbors(queue.removeFirst(), octopusGrid).filter { it !in flashed }
            octopuses.forEach { octopus -> increaseEnergyLevel(octopus, octopusGrid, 1) }
            val newFlashed = flashing(octopusGrid).filter { it !in flashed }
            queue.addAll(newFlashed)
            flashed.addAll(newFlashed)
        }
        flashed.forEach{octopus -> setEnergyLevel(octopus, octopusGrid, 0)}
        return flashed.size
    }

    fun simulateSteps(steps: Int, octopusGrid: Grid): Int{
        var flashes = 0
        for(step in 1..steps){
            flashes += updateState(octopusGrid)
        }
        return flashes
    }

    fun findAllFlash(octopusGrid: Grid): Int{
        var flashes = 0
        var ctr = 0
        while(flashes != 100){
            flashes = updateState(octopusGrid)
            ctr += 1
        }
        return ctr
    }

    fun part1(octopusGrid: Grid): Int {
        return simulateSteps(100, octopusGrid)
    }

    fun part2(octopusGrid: Grid): Int {
        return findAllFlash(octopusGrid)
    }

    val input = resourceAsListOfString("src/day11/Day11.txt")
    val octopusGrid: Grid = input.map { row -> row.map { it.digitToInt()}.toIntArray() }.toTypedArray()

    println(part1(octopusGrid))
    println(part2(octopusGrid))
}

private operator fun Point.plus(that: Point): Point =
    Point(this.first + that.first, this.second + that.second)

private operator fun Grid.contains(point: Point): Boolean =
    point.first in this.indices && point.second in this.first().indices
