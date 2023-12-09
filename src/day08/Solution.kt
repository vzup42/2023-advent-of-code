package day08

import println
import readInput

data class Key(val source: String, val direction: Char)

fun main() {
    val day = "08"

    fun part1(input: List<String>): Int {
        val sequence = generateSequence(input[0])
        val map = input.subList(fromIndex = 2, toIndex = input.size).flatMap(String::parseDirections).toMap()
        return calculateStepsToEnd("AAA", sequence, map) { it == "ZZZ" }
    }

    fun part2(input: List<String>): Long {
        val sequence = generateSequence(input[0])
        val map = input.subList(fromIndex = 2, toIndex = input.size).flatMap(String::parseDirections).toMap()
        val starters = map.keys.map { it.source }.filter { it.endsWith("A") }.toSet()
        val counters = starters.map {starter ->
            calculateStepsToEnd(starter, sequence, map) { it.endsWith("Z") }.toLong()
        }
        return counters.reduce(::lcm)
    }

    // Part 1
    val testInput1 = readInput(day, "test_input_1")
    check(part1(testInput1) == 6)

    // Part 2
    val testInput2 = readInput(day, "test_input_2")
    check(part2(testInput2) == 6L)

    println("Solution:")
    val input = readInput(day, "input")
    part1(input).println()
    part2(input).println()
}

fun String.parseDirections(): List<Pair<Key, String>> = this.split(" ", "=", "(", ",", ")")
    .filter { it.isNotBlank() }
    .let { (source, leftDestination, rightDestination) ->
        listOf(
            Key(source, 'L') to leftDestination,
            Key(source, 'R') to rightDestination
        )
    }

fun generateSequence(directions: String) = sequence {
    val tmp = directions.toCharArray().asSequence()
    while (true) yieldAll(tmp)
}

fun calculateStepsToEnd(
    initial: String,
    sequence: Sequence<Char>,
    map: Map<Key, String>,
    stopCondition: (String) -> Boolean
) = sequence.foldIndexed(initial) { index, current, direction ->
    if (stopCondition(current)) return index
    else map.getValue(Key(current, direction))
}.toInt()

// ¯\_(ツ)_/¯ From https://www.baeldung.com/kotlin/lcm ¯\_(ツ)_/¯
fun lcm(a: Long, b: Long): Long {
    val larger = if (a > b) a else b
    val maxLcm = a * b
    var lcm = larger
    while (lcm <= maxLcm) {
        if (lcm % a == 0L && lcm % b == 0L) {
            return lcm
        }
        lcm += larger
    }
    return maxLcm
}
