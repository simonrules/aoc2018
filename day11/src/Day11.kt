data class Point2D(val x: Int, val y: Int)

class Day11(serial: Int) {
    private val size = 300
    private val maxSquareSize = 30
    private val cellPower = mutableMapOf<Point2D, Int>()

    init {
        for (y in 1..size) {
            for (x in 1..size) {
                cellPower[Point2D(x, y)] = power(serial, x, y)
            }
        }
    }

    private fun power(serial: Int, x: Int, y: Int): Int {
        val rack = x + 10
        var power = rack * y
        power += serial
        power *= rack
        val hundreds = (power / 100) % 10
        power = hundreds - 5

        return power
    }

    private fun maxPower(squareSize: Int): Pair<Int, Point2D> {
        var maxPower = 0
        var maxPoint = Point2D(-1, -1)
        for (y in 1 until size - squareSize) {
            for (x in 1 until size - squareSize) {
                var power = 0
                for (yy in 0 until squareSize) {
                    for (xx in 0 until squareSize) {
                        power += cellPower[Point2D(x + xx, y + yy)]!!
                    }
                }
                if (power > maxPower) {
                    maxPower = power
                    maxPoint = Point2D(x, y)
                }
            }
        }

        return Pair(maxPower, maxPoint)
    }

    fun part1() {
        val (_, point) = maxPower(3)

        println("${point.x},${point.y}")
    }

    fun part2() {
        var maxSize = 0
        var maxPower = 0
        var maxPoint = Point2D(0,0)
        for (s in 1 until maxSquareSize) {
            val (power, point) = maxPower(s)
            if (power > maxPower) {
                maxPower = power
                maxSize = s
                maxPoint = point
            }
        }

        println("${maxPoint.x},${maxPoint.y},$maxSize")
    }
}

fun main() {
    val aoc = Day11(1309)
    aoc.part1()
    aoc.part2()
}
