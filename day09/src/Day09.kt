import java.io.File
import java.util.*

class Day09(input: String) {
    private val players: Int
    private val lastMarble: Int

    class CircularLinkedList<T> : LinkedList<T>() {
        init {

        }

        fun rotate(positions: Int) {
            if (positions >= 0) {
                repeat(positions) {
                    this.addLast(this.removeFirst())
                }
            } else {
                repeat(-positions) {
                    this.addFirst(this.removeLast())
                }
            }
        }
    }

    init {
        val line = File(input).readLines()[0]
        val tokens = line.split(" ")
        players = tokens[0].toInt()
        lastMarble = tokens[6].toInt()
    }

    private fun result(lastMarbleValue: Int): Long {
        val list = CircularLinkedList<Int>()
        val scores = Array(players + 1) { 0L }

        list.add(0)
        var player = 1
        for (m in 1..lastMarbleValue) {
            if (m % 23 == 0) {
                // Remove marble 7 before current and add to score
                list.rotate(-7)

                // Add to player's score and remove marble
                scores[player] += m.toLong() + list.removeFirst()
            } else {
                // Add marble between marbles 1 and 2 positions clockwise of current
                list.rotate(2)
                list.addFirst(m)
            }
            /*print("[$player] ")
            list.forEach { item ->
                print("$item ")
            }
            println()*/
            player++
            if (player > players) {
                player = 1
            }

        }

        return scores.maxOf { it }
    }

    fun part1(): Long {
        return result(lastMarble)
    }

    fun part2(): Long {
        return result(lastMarble * 100)
    }
}

fun main() {
    val aoc = Day09("day09/input.txt")
    println(aoc.part1())
    println(aoc.part2())
}
