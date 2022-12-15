import java.io.File

class Day04(path: String) {
    // Maps guard ID to total duration asleep
    private val sleepDuration = mutableMapOf<Int, Int>()
    // Maps guard ID to frequency of each minute asleep
    private val sleepTimes = mutableMapOf<Int, IntArray>()

    private val regex = "^\\[\\d+-\\d+-\\d+ \\d+:(\\d+)\\] (.+)\$".toRegex()

    init {
        var id = -1
        var startTime = -1

        var lines = File(path).readLines()

        lines.sorted().forEach {
            val matchResult = regex.find(it)
            if (matchResult != null) {
                val (_min, occurrence) = matchResult.destructured
                val min = _min.toInt()

                with (occurrence) {
                    when {
                        contains ("Guard") -> {
                            val sub = occurrence.substring(7)
                            val index = sub.indexOf(" ")
                            id = sub.substring(0, index).toInt()
                        }

                        contains("falls") -> startTime = min

                        else -> {
                            addGuard(id)

                            // Now update data
                            val duration = min - startTime
                            sleepDuration[id] = sleepDuration[id]!! + duration

                            val mins = sleepTimes[id]!!
                            for (i in startTime until startTime + duration) {
                                mins[i] = mins[i]!! + 1
                            }
                        }
                    }
                }
            }
        }
    }

    fun part1() {
        val id = sleepDuration.maxByOrNull { it.value }?.key

        if (id != null) {
            val mins = sleepTimes[id]!!
            println(id * mins.indices.maxByOrNull { mins[it] }!!)
        }
    }

    fun part2() {
        val guards = sleepDuration.keys
        var maxSleepTime = -1
        var mostAsleepGuard = -1
        guards.forEach { guard ->
            val mins = sleepTimes[guard]!!
            val max = mins.maxByOrNull { it }

            if (max != null && max > maxSleepTime) {
                maxSleepTime = max
                mostAsleepGuard = guard
            }
        }
        //println(mostAsleepGuard)

        val mins = sleepTimes[mostAsleepGuard]!!
        val minute = mins.indices.maxByOrNull { mins[it] }

        if (minute != null) {
            println(mostAsleepGuard * minute)
        }
    }

    private fun addGuard(id: Int) {
        sleepDuration.putIfAbsent(id, 0)
        sleepTimes.putIfAbsent(id, IntArray(60))
    }
}

fun main(args: Array<String>) {
    val aoc = Day04("day04/input.txt")
    aoc.part2()
}
