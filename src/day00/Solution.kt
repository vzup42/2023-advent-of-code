package day00

import println
import readInput

const val day = "00"

fun main() {
    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // Part 1
    val testInput1 = readInput(day01.day, "test_input_1")
    check(part1(testInput1) == 1)

    // Part 2
    val testInput2 = readInput(day01.day, "test_input_2")
    check(part2(testInput2) == 1)

    println("Solution:")
    val input = readInput(day01.day, "input")
    part1(input).println()
    part2(input).println()
}
