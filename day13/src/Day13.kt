import java.io.File

data class Point2D(val x: Int, val y: Int)

//enum class Rotation(value: Char) { UP('^'), RIGHT('>'), DOWN('v'), LEFT('<') }

enum class Turn { LEFT, STRAIGHT, RIGHT }

data class Cart(var pos: Point2D, var direction: Char, var turn: Turn = Turn.LEFT) {
    fun nextPos(): Point2D {
        return when (direction) {
            '<' -> Point2D(pos.x - 1, pos.y)
            'v' -> Point2D(pos.x, pos.y + 1)
            '>' -> Point2D(pos.x + 1, pos.y)
            '^' -> Point2D(pos.x, pos.y - 1)
            else -> pos
        }
    }

    fun nextDirectionRotateLeft(): Char {
        return when (direction) {
            '<' -> 'v'
            'v' -> '>'
            '>' -> '^'
            '^' -> '<'
            else -> ' '
        }
    }

    fun nextDirectionRotateRight(): Char {
        return when (direction) {
            '<' -> '^'
            'v' -> '<'
            '>' -> 'v'
            '^' -> '>'
            else -> ' '
        }
    }

    fun nextTurn(): Turn {
        return when (turn) {
            Turn.LEFT -> Turn.STRAIGHT
            Turn.STRAIGHT -> Turn.RIGHT
            Turn.RIGHT -> Turn.LEFT
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

    private fun getMapAt(pos: Point2D): Char {
        return map[pos.y * width + pos.x]
    }

    private fun Char.isCart(): Boolean {
        return this in "<>^v"
    }

    private fun Char.isCartHorizontal(): Boolean {
        return this in "<>"
    }

    private fun Char.isTurn(): Boolean {
        return this in "/\\"
    }

    private fun Char.isIntersection(): Boolean {
        return this == '+'
    }

    private fun printMap() {
        map.forEachIndexed { i, char ->
            var printedCart = false
            val x = i % width
            val y = i / width
            carts.forEach {
                if (it.pos.x == x && it.pos.y == y && !printedCart) {
                    print(it.direction)
                    printedCart = true
                }
            }
            if (!printedCart) {
                print(char)
            }
            if (i % width == width - 1) {
                println()
            }
        }
    }

    private fun move(cart: Cart) {
        val nextPos = cart.nextPos()
        val nextMapItem = getMapAt(nextPos)
        var nextTurn = cart.turn

        val nextDirection = if (nextMapItem.isIntersection()) {
            nextTurn = cart.nextTurn()
            when (cart.turn) {
                Turn.LEFT -> cart.nextDirectionRotateLeft()
                Turn.RIGHT -> cart.nextDirectionRotateRight()
                else -> cart.direction
            }
        } else if (nextMapItem.isTurn()) {
            if (nextMapItem == '/') {
                when (cart.direction) {
                    '<' -> 'v'
                    'v' -> '<'
                    '>' -> '^'
                    '^' -> '>'
                    else -> cart.direction
                }
            } else { // \
                when (cart.direction) {
                    '<' -> '^'
                    'v' -> '>'
                    '>' -> 'v'
                    '^' -> '<'
                    else -> cart.direction
                }
            }
        } else {
            cart.direction
        }

        cart.pos = nextPos
        cart.direction = nextDirection
        cart.turn = nextTurn
    }

    private fun moveAll() {
        carts.forEach { move(it) }
    }

    fun part1(): Int {
        repeat(20) {
            printMap()
            moveAll()
            carts.forEach { a ->
                carts.forEach { b ->
                    if (a != b && a.pos == b.pos) {
                        println("Collision!")
                    }
                }
            }
        }

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