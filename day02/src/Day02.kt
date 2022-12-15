import java.io.File

class Day02(path: String) {
    private val list = mutableListOf<String>()

    init {
        File(path).forEachLine { list.add(it) }
    }

    fun hasTwo(input: String): Boolean {
        val chars = mutableMapOf<Char, Int>()

        input.forEach {
            chars.putIfAbsent(it, 0)
            chars[it] = chars[it]!! + 1
        }

        return chars.any { it.value == 2 }
    }

    fun hasThree(input: String): Boolean {
        val chars = mutableMapOf<Char, Int>()

        input.forEach {
            chars.putIfAbsent(it, 0)
            chars[it] = chars[it]!! + 1
        }

        return chars.any { it.value == 3 }
    }

    fun part1(): Int {
        val twos = list.count { hasTwo(it) }
        val threes = list.count {hasThree(it) }

        return twos * threes
    }

    fun differBy(in1: String, in2: String): Int {
        var diff = 0

        in1.forEachIndexed { index, c ->
            if (c != in2[index]) {
                diff++
            }
        }

        return diff
    }

    fun part2(): Int {
        list.forEach { in1 ->
            list.forEach { in2 ->
                if (differBy(in1, in2) == 1) {
                    println(in1)
                    println(in2)
                }
            }
        }


        return 0
    }
}

fun main(args: Array<String>) {
    val aoc = Day02("day02/input.txt")

    //println(aoc.part1())
    println(aoc.part2())
}
