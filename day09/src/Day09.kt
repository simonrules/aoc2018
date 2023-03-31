import java.io.File

class Day09(input: String) {
    private val players: Int
    private val lastMarble: Int

    init {
        val line = File(input).readLines()[0]
        val tokens = line.split(" ")
        players = tokens[0].toInt()
        lastMarble = tokens[6].toInt()
    }

    private fun result(lastMarbleValue: Int): Long {
        val list = mutableListOf<Int>()
        val scores = Array(players + 1) { 0L }

        list.add(0)
        var currentIndex = 0
        var player = 1
        for (m in 1..lastMarbleValue) {
            if (m % 23 == 0) {
                // Add to player's score
                scores[player] += m.toLong()

                // Remove marble 7 before current and add to score
                var removeIndex = currentIndex - 7
                if (removeIndex < 0) {
                    removeIndex += list.size
                }
                scores[player] += list[removeIndex].toLong()
                list.removeAt(removeIndex)

                // Assign next marble
                currentIndex = removeIndex
            } else {
                // Add marble between marbles 1 and 2 positions clockwise of current
                currentIndex = (currentIndex + 1) % list.size + 1
                list.add(currentIndex, m)
            }
            /*print("[$player] ")
            list.forEachIndexed { index, item ->
                if (index == currentIndex) {
                    print("($item) ")
                } else {
                    print("$item ")
                }
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
    // 1065924 too low
    println(aoc.part2())
}
