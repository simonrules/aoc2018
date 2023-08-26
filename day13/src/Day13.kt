import java.io.File

data class Point2D(val x: Int, val y: Int)

enum class Rotation(value: Char) { UP('^'), RIGHT('>'), DOWN('v'), LEFT('<') }

data class Cart(val pos: Point2D, val rotation: Rotation)

class Day13(path: String) {
    private val map = mutableListOf<Int>()
    private var height = 0
    private var width = 0

    init {
        var i = 0
        var j = 0
        File(path).forEachLine { line ->
            j = line.length
            line.forEach {
                map.add(Character.getNumericValue(it))
            }
            i++
        }
        height = i
        width = j
    }

    private fun getMapAt(x: Int, y: Int): Int {
        return map[y * width + x]
    }

    fun part1(): Int {
        return 0
    }

    fun part2(): Int {
        return 0
    }
}

fun main() {
    val aoc = Day13("day13/test1.txt")

    println(aoc.part1())
    println(aoc.part2())
}