package day06

import println
import readInput

fun main() {
    val day = "06"

    fun part1(input: List<String>): Int {
        val times = input[0].substringAfter(delimiter = "Time:")
            .split(" ").filter(String::isNotBlank).map(String::toInt)
        val distances = input[1].substringAfter(delimiter = "Distance:")
            .split(" ").filter(String::isNotBlank).map(String::toInt)
        require(times.size == distances.size)

        val result = times.zip(distances).map { (time, distance) ->
            (0..time).map { speed ->
                speed * (time - speed)
            }.count { it > distance }
        }.reduce { mult, element -> mult * element }

        return result
    }

    fun part2(input: List<String>): Int {
        val time = input[0].substringAfter(delimiter = "Time:")
            .replace(" ", "").toLong()
        val distance = input[1].substringAfter(delimiter = "Distance:")
            .replace(" ", "").toLong()

        val result = (0..time).map { speed ->
            speed * (time - speed)
        }.count { it > distance }


        return result
    }

    // Part 1
    val testInput1 = readInput(day, "test_input_1")
    check(part1(testInput1) == 288)

    // Part 2
    val testInput2 = readInput(day, "test_input_2")
    check(part2(testInput2) == 71503)

    println("Solution:")
    val input = readInput(day, "input")
    part1(input).println()
    part2(input).println()
}
