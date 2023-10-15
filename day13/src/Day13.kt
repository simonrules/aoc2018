import java.io.File

data class Point2D(val x: Int, val y: Int)

//enum class Rotation(value: Char) { UP('^'), RIGHT('>'), DOWN('v'), LEFT('<') }

data class Cart(val pos: Point2D, val rotation: Char) {
    fun nextPos(): Point2D {
        return when (rotation) {
            '<' -> Point2D(pos.x - 1, pos.y)
            '>' -> Point2D(pos.x + 1, pos.y)
            '^' -> Point2D(pos.x, pos.y - 1)
            'v' -> Point2D(pos.x, pos.y + 1)
            else -> pos
        }
    }
}

class Day13(path: String) {
    private val map = mutableListOf<Char>()
    private val carts = mutableListOf<Cart>()
    private var height = 0
    private var width = 0

    init {
        var i = 0
        File(path).forEachLine { line ->
            width = line.length
            line.forEachIndexed { j, char ->
                if (char.isCart()) {
                    carts.add(Cart(Point2D(j, i), char))
                    if (char.isCartHorizontal()) {
                        map.add('-')
                    } else {
                        map.add('|')
                    }
                } else {
                    map.add(char)
                }
            }
            i++
        }
        height = i
    }

    private fun getMapAt(x: Int, y: Int): Char {
        return map[y * width + x]
    }

    private fun Char.isCart(): Boolean {
        return this in "<>^v"
    }

    private fun Char.isCartHorizontal(): Boolean {
        return this in "<>"
    }

    private fun printMap() {
        map.forEachIndexed { i, char ->
            print(char)
            if (i % width == width - 1) {
                println()
            }
        }
    }

    private fun move(cart: Cart) {
        val nextPos = cart.nextPos()
    }

    private fun moveAll() {
        carts.forEach { move(it) }
    }

    fun part1(): Int {
        printMap()

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