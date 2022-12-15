import java.io.File

class Day05(path: String) {
    private val originalList = mutableListOf<Char>()
    private var list = mutableListOf<Char>()
    init {
        val text = File(path).readText()
        text.forEach {
            originalList.add(it)
        }
    }

    private fun remove(location: Int) {
        list.removeAt(location)
        list.removeAt(location)
    }

    private fun react(): Boolean {
        if (list.size < 2) {
            return false
        }

        var a = list[0]
        for (i in 1 until list.size) {
            val b = list[i]

            if (a.equals(b, ignoreCase = true) && a != b) {
                remove(i - 1)
                return true
            }

            a = b
        }
        return false
    }

    fun part1(): Int {
        var count = 0
        while (react()) {
            count++
        }

        return list.size
    }

    fun part2(): Int {
        var shortest = 999999
        ('a'..'z').forEach { toRemove ->
            list = originalList.filter { it.lowercaseChar() != toRemove }.toMutableList()
            while (react()) {}
            if (list.size < shortest) {
                shortest = list.size
            }
        }
        return shortest
    }
}

fun main(args: Array<String>) {
    val aoc = Day05("day05/input.txt")
    //println(aoc.part1())
    println(aoc.part2())
}
