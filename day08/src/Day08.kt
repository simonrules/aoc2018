import java.io.File

class Day08(path: String) {
    private val list: List<Int>
    private var sum = 0
    private val nodePos = mutableListOf<Int>()

    init {
        list = File(path).readText().split(" ").map { it.trim().toInt() }

        scanNode(list)
    }

    private fun scanNode(list: List<Int>): Int {
        val numChildren = list[0]
        val numMetadata = list[1]

        var next = 2
        for (i in 0 until numChildren) {
            nodePos.add(next)
            next += scanNode(list.subList(next, list.size))
        }

        for (i in 0 until numMetadata) {
            sum += list[next + i]
        }

        return next + numMetadata
    }

    data class Node(val children: MutableList<Node>, val metadata: MutableList<Int>)

    private fun buildTree(start: Int, list: List<Int>, node: Node): Int {
        val numChildren = list[start]
        val numMetadata = list[start + 1]

        var next = start + 2
        for (i in 0 until numChildren) {
            val child = Node(mutableListOf(), mutableListOf())
            next = buildTree(next, list, child)
            node.children.add(child)
        }

        for (i in 0 until numMetadata) {
            node.metadata.add(list[next + i])
        }

        return next + numMetadata
    }
    
    private fun getValue(node: Node): Int {
        if (node.children.size == 0) {
            return node.metadata.reduce { acc, i -> acc + i }
        }

        var sum = 0
        node.metadata.forEach { i ->
            if (i <= node.children.size) {
                val child = node.children[i - 1]
                sum += getValue(child)
            }
        }
        
        return sum
    }

    fun part1(): Int {
        return sum
    }

    fun part2(): Int {
        val tree = Node(mutableListOf(), mutableListOf())
        buildTree(0, list, tree)

        return getValue(tree)
    }
}

fun main() {
    val aoc = Day08("day08/input.txt")
    println(aoc.part1())
    println(aoc.part2())
}