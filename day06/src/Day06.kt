import java.io.File
import kotlin.math.absoluteValue

data class Point(val x: Int, val y: Int) {
    fun manhattan(other: Point): Int {
        return (other.x - x).absoluteValue + (other.y - y).absoluteValue
    }
}

class Day06(path: String) {
    private val coords = mutableListOf<Point>()
    private val counts = mutableListOf<Int>()
    private var minX = Int.MAX_VALUE
    private var minY = Int.MAX_VALUE
    private var maxX = 0
    private var maxY = 0
    private val size = 360
    private val map = CharArray(size * size) { '.' }
    private val distance = IntArray(size * size) { Int.MAX_VALUE }

    init {
        File(path).forEachLine {
            val values = it.split(", ")
            val p = Point(values[0].toInt(), values[1].toInt())
            if (p.x < minX) {
                minX = p.x
            } else if (p.x > maxX) {
                maxX = p.x
            }
            if (p.y < minY) {
                minY = p.y
            } else if (p.y > maxY) {
                maxY = p.y
            }
            coords.add(p)
            counts.add(0)
        }
    }

    private fun getMapAt(p: Point): Char {
        return map[p.y * size + p.x]
    }

    private fun setMapAt(p: Point, value: Char) {
        map[p.y * size + p.x] = value
    }

    private fun getDistanceAt(p: Point): Int {
        return distance[p.y * size + p.x]
    }

    private fun setDistanceAt(p: Point, value: Int) {
        distance[p.y * size + p.x] = value
    }

    private fun printMap() {
        for (i in minY..maxY) {
            for (j in minX..maxX) {
                print(getMapAt(Point(j, i)))
            }
            println()
        }
    }

    fun part1(): Int {
        for (x in minX..maxX) {
            for (y in minY..maxY) {
                coords.forEachIndexed { index, coord ->
                    val location = Point(x, y)
                    val dist = coord.manhattan(location)

                    if (dist < getDistanceAt(location)) {
                        setDistanceAt(location, dist)
                        setMapAt(location, 'A' + index)
                    } else if (dist == getDistanceAt(location)) {
                        setMapAt(location, '.') // dupe
                    }
                }
            }
        }

        // Go around edges and remove anything there (they are infinite)
        val exclude = mutableSetOf<Char>()
        for (x in minX..maxX) {
            exclude.add(getMapAt(Point(x, minY)))
            exclude.add(getMapAt(Point(x, maxY)))
        }
        for (y in minY..maxY) {
            exclude.add(getMapAt(Point(minX, y)))
            exclude.add(getMapAt(Point(maxX, y)))
        }

        // count occurrences in map
        var max = 0
        for (p in coords.indices) {
            var count = 0
            for (x in minX..maxX) {
                for (y in minY..maxY) {
                    val c = 'A' + p
                    if (c !in exclude && (c == getMapAt(Point(x, y)))) {
                        count++
                    }
                }
            }
            if (count > max) {
                max = count
            }
        }

        return max
    }

    fun part2(): Int {
        val limit = 10000
        var count = 0

        for (x in minX..maxX) {
            for (y in minY..maxY) {
                var sum = 0
                coords.forEachIndexed { index, coord ->
                    val location = Point(x, y)
                    sum += coord.manhattan(location)
                }
                if (sum < limit) {
                    count++
                }
            }
        }

        return count
    }
}

fun main(args: Array<String>) {
    val aoc = Day06("day06/input.txt")
    println(aoc.part1())
    println(aoc.part2())
}
