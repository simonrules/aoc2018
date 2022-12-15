import java.io.File

class Day01(path: String) {
    private val list = mutableListOf<Int>()
    private val seen = mutableSetOf<Int>()

    init {
        File(path).forEachLine { list.add(it.toInt()) }
    }

    fun part1(): Int {
        var freq = 0

        list.forEach { freq += it }

        return freq
    }

    fun part2(): Int {
        var freq = 0

        while (true) {
            list.forEach {
                freq += it
                if (seen.contains(freq)) {
                    return freq
                }
                seen.add(freq)
            }
        }

        return 0
    }
}

fun main(args: Array<String>) {
    val aoc = Day01("day01/input.txt")

    println(aoc.part1())
    println(aoc.part2())
}
