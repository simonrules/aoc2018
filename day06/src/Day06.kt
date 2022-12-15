import java.io.File
import kotlin.math.absoluteValue

class Day06(path: String) {
    private val points = mutableListOf<Pair<Int, Int>>()
    private val counts = mutableListOf<Int>()
    private var minX = 999999
    private var minY = 999999
    private var maxX = 0
    private var maxY = 0
    private val size = 360
    private val map = CharArray(size * size) { '.' }
    private val distance = IntArray(size * size) { 999999 }

    init {
        File(path).forEachLine {
            val values = it.split(',')
            val point = Pair(values[0].trim().toInt(), values[1].trim().toInt())
            if (point.first < minX) {
                minX = point.first
            } else if (point.first > maxX) {
                maxX = point.first
            }
            if (point.second < minY) {
                minY = point.second
            } else if (point.second > maxY) {
                maxY = point.second
            }
            points.add(point)
            counts.add(0)
        }
    }

    private fun getMapAt(x: Int, y: Int): Char {
        return map[y * size + x]
    }

    private fun setMapAt(x: Int, y: Int, value: Char) {
        map[y * size + x] = value
    }

    private fun getDistanceAt(x: Int, y: Int): Int {
        return distance[y * size + x]
    }

    private fun setDistanceAt(x: Int, y: Int, value: Int) {
        distance[y * size + x] = value
    }

    private fun printMap(w: Int, h: Int) {
        for (i in 0 until h) {
            for (j in 0 until w) {
                print(getMapAt(j, i))
            }
            println()
        }
    }

    private fun manhattanDistance(fromX: Int, fromY: Int, toX: Int, toY: Int): Int {
        return (toX - fromX).absoluteValue + (toY - fromY).absoluteValue
    }

    fun part1(): Int {
        for (x in minX..maxX) {
            for (y in minY..maxY) {
                points.forEachIndexed { index, p ->
                    //setMapAt(p.first, p.second, 'A' + index)
                    //if (p.first != x || p.second != y) {
                        val distance = manhattanDistance(p.first, p.second, x, y)
                        if (distance < getDistanceAt(x, y)) {
                            setDistanceAt(x, y, distance)
                            setMapAt(x, y, 'a' + index)
                        } else if (distance == getDistanceAt(x, y)) {
                            setMapAt(x, y, '.') // dupe
                        }
                    //}
                }
            }
        }
        printMap(10, 10)

        // count occurrences in map
        var max = 0
        for (p in points.indices) {
            var count = 0
            for (x in minX..maxX) {
                for (y in minY..maxY) {
                    if ('a' + p == getMapAt(x, y)) {
                        count++
                    }
                }
            }
            if (count > max) {
                max = count
            }
        }

        return max

        // 4503 is too high
    }

    fun part2(): Int {
        return 0
    }
}

fun main(args: Array<String>) {
    val aoc = Day06("day06/input.txt")
    println(aoc.part1())
    println(aoc.part2())
}
