import java.io.File
import java.util.PriorityQueue

class Day07(path: String) {
    private val dependencies = mutableListOf<Pair<Char, Char>>()

    init {
        File(path).forEachLine { dependencies.add(Pair(it[5], it[36])) }
    }

    fun part1(): String {
        val result = StringBuilder()
        val blockedByCount = mutableMapOf<Char, Int>()
        val blocking = mutableMapOf<Char, Set<Char>>()
        val notBlocked = PriorityQueue<Char>()

        // Pre-process
        dependencies.forEach {
            blockedByCount[it.second] = if (it.second in blockedByCount) blockedByCount[it.second]!! + 1 else 1
            blocking[it.first] = if (it.first in blocking) blocking[it.first]!!.plus(it.second) else setOf(it.second)
            if (it.first !in notBlocked) {
                notBlocked.add(it.first)
            }
        }
        blockedByCount.keys.forEach { notBlocked.remove(it) }

        while (notBlocked.isNotEmpty()) {
            val firstNotBlocked = notBlocked.remove()
            result.append(firstNotBlocked)

            blocking[firstNotBlocked]?.forEach {
                blockedByCount[it] = blockedByCount[it]!! - 1
                if (blockedByCount[it]!! == 0) {
                    blockedByCount.remove(it)
                    notBlocked.add(it)
                }
            }
        }

        return result.toString()
    }

    @OptIn(ExperimentalStdlibApi::class)
    private fun stepFinishedBy(step: Char, start: Int, duration: Int): Int {
        return step.code - 'A'.code + 1 + start + duration
    }

    data class Step(val blockedBy: MutableSet<Char>)

    data class Worker(val step: Char, val finishedBy: Int)

    fun part2(numWorkers: Int, duration: Int): Int {
        var time = 0
        val workers = PriorityQueue<Worker> { a, b -> a.finishedBy - b.finishedBy }
        val notBlocked = PriorityQueue<Char>()
        val steps = mutableMapOf<Char, Step>()

        // Pre-process
        dependencies.forEach {
            if (it.first !in steps) {
                steps[it.first] = Step(mutableSetOf())
            }
            if (it.second !in steps) {
                steps[it.second] = Step(mutableSetOf(it.first))
            } else {
                steps[it.second]?.blockedBy?.add(it.first)
            }
        }

        while (steps.isNotEmpty()) {
            steps.forEach {
                if (it.value.blockedBy.isEmpty()) {
                    notBlocked.add(it.key)
                }
            }
            notBlocked.forEach { steps.remove(it) }

            // Queue up any unblocked steps on available workers
            while (workers.size < numWorkers && notBlocked.isNotEmpty()) {
                val step = notBlocked.remove()
                val worker = Worker(step, stepFinishedBy(step, time, duration))

                workers.add(worker)
            }

            // Finish the next worker
            val finished = workers.remove()
            time = finished.finishedBy

            // Remove blockers from any waiting steps
            steps.forEach {
                it.value.blockedBy.remove(finished.step)
            }
        }

        return time
    }
}

fun main() {
    val aoc = Day07("day07/input.txt")
    println(aoc.part1())
    println(aoc.part2(5, 60))
}
