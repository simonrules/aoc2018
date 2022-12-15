import java.io.File

class Day03 {
    private val fabric = IntArray(1000 * 1000) { 0 }
    private val regex = "^#(\\d+) @ (\\d+),(\\d+): (\\d+)x(\\d+)\$".toRegex()

    private fun incAt(x: Int, y: Int) {
        fabric[y * 1000 + x]++
    }

    private fun getAt(x: Int, y: Int): Int {
        return fabric[y * 1000 + x]
    }

    fun part1(path: String): Int {
        File(path).forEachLine {
            var matchResult = regex.find(it)
            if (matchResult != null) {
                val (_id, _x, _y, _w, _h) = matchResult.destructured
                val x = _x.toInt()
                val y = _y.toInt()
                val w = _w.toInt()
                val h = _h.toInt()

                for (j in x until x + w) {
                    for (i in y until y + h) {
                        incAt(j, i)
                    }
                }
            }
        }

        return fabric.count { it > 1 }
    }


    fun part2(path: String) {
        return File(path).forEachLine {
            var matchResult = regex.find(it)
            if (matchResult != null) {
                val (_id, _x, _y, _w, _h) = matchResult.destructured
                val id = _id.toInt()
                val x = _x.toInt()
                val y = _y.toInt()
                val w = _w.toInt()
                val h = _h.toInt()

                var count = 0
                for (j in x until x + w) {
                    for (i in y until y + h) {
                        if (getAt(j, i) == 1) {
                            count++
                        }
                    }
                }
                if (count == w * h) {
                    println(id)
                }
            }
        }
    }
}

fun main(args: Array<String>) {
    val aoc = Day03()

    println(aoc.part1("day03/input.txt"))
    aoc.part2("day03/input.txt")
}
