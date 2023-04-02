import java.io.File

data class Point2D(var x: Int, var y: Int) {
    fun plus(other: Point2D): Point2D {
        return Point2D(x + other.x, y + other.y)
    }
}

class Day10(input: String) {
    private val regex = "position=<(.+),(.+)> velocity=<(.+),(.+)>".toRegex()
    private val points = mutableListOf<Point2D>()
    private val velocities = mutableListOf<Point2D>()

    init {
        File(input).forEachLine {
            val (a, b, c, d) = regex.matchEntire(it)!!.destructured

            val point = Point2D(a.trim().toInt(), b.trim().toInt())
            points.add(point)
            velocities.add(Point2D(c.trim().toInt(), d.trim().toInt()))
        }
    }

    fun print(minX: Int, minY: Int, maxX: Int, maxY: Int) {
        for (y in minY..maxY) {
            for (x in minX..maxX) {
                if (Point2D(x, y) in points) {
                    print("#")
                } else {
                    print(".")
                }
            }
            println()
        }
        println()
    }

    fun part1() {
        var minX = 0
        var minY = 0
        var maxX = 0
        var maxY = 0

        for (i in 0 until 10243) {
            minX = Int.MAX_VALUE
            minY = Int.MAX_VALUE
            maxX = 0
            maxY = 0

            points.forEachIndexed { i, p ->
                p.x += velocities[i].x
                p.y += velocities[i].y

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
            }
        }
        print(minX, minY, maxX, maxY)
    }
}

fun main() {
    val aoc = Day10("day10/input.txt")
    aoc.part1()
}
