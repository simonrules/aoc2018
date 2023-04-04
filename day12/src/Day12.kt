import java.io.File

class Day12(filename: String) {
    private val initialState = mutableMapOf<Int, Boolean>()
    private val rule = mutableMapOf<String, Boolean>()
    private var min = 0
    private var max = 0
    private val PART1_GENERATIONS = 20
    private val PART2_GENERATIONS = 50000000000L
    private val PART2_STABILISATION_GENERATIONS = 200

    init {
        val input = File(filename).readText()
        val blocks = input.split("\n\n")

        val initial = blocks[0].substring(15)
        initial.forEachIndexed { index, c -> initialState[index] = c == '#' }
        max = initial.length - 1

        val rules = blocks[1].split("\n")
        rules.forEach {
            if (it.isNotEmpty()) {
                val parts = it.split(" => ")
                rule[parts[0]] = parts[1].first() == '#'
            }
        }
    }

    private fun getStateString(state: Map<Int, Boolean>, position: Int): String {
        val ret = StringBuilder()
        for (i in -2..2) {
            ret.append(if (state[position + i] == true) '#' else '.')
        }

        return ret.toString()
    }

    fun part1(): Int {
        var state = initialState.toMap()

        repeat(PART1_GENERATIONS) {
            val newState = mutableMapOf<Int, Boolean>()
            for (i in min - 2..max + 2) {
                val plant = rule[getStateString(state, i)]
                newState[i] = plant == true
            }
            state = newState.toMap()
            min -= 2
            max += 2
        }

        var potSum = 0
        state.forEach { (pot, plant) ->
            if (plant) {
                potSum += pot
            }
        }

        return potSum
    }

    fun part2(): Long {
        var state = initialState.toMap()
        val potSums = mutableListOf<Int>()

        repeat(PART2_STABILISATION_GENERATIONS) {
            val newState = mutableMapOf<Int, Boolean>()
            for (i in min - 2..max + 2) {
                val plant = rule[getStateString(state, i)]
                newState[i] = plant == true
            }
            state = newState.toMap()
            min -= 2
            max += 2

            var potSum = 0
            state.forEach { (pot, plant) ->
                if (plant) {
                    potSum += pot
                }
            }
            potSums.add(potSum)
        }

        val finalPotSum = potSums[PART2_STABILISATION_GENERATIONS - 1]
        val diff = potSums[PART2_STABILISATION_GENERATIONS - 1] - potSums[PART2_STABILISATION_GENERATIONS - 2]

        return (PART2_GENERATIONS - PART2_STABILISATION_GENERATIONS.toLong()) * diff.toLong() + finalPotSum.toLong()
    }
}

fun main() {
    val aoc = Day12("day12/input.txt")
    println(aoc.part1())
    println(aoc.part2())
}