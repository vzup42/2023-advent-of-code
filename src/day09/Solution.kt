package day09

import println
import readInput

fun main() {
    val day = "09"

    fun part1(input: List<String>): Int =
        input.sumOf { it.split(" ").map(String::toInt).nextValue() }

    fun part2(input: List<String>): Int =
        input.sumOf { it.split(" ").map(String::toInt).previousValue() }

    // Part 1
    val testInput1 = readInput(day, "test_input_1")
    check(part1(testInput1) == 114)

    // Part 2
    val testInput2 = readInput(day, "test_input_2")
    check(part2(testInput2) == 2)

    println("Solution:")
    val input = readInput(day, "input")
    part1(input).println()
    part2(input).println()
}

fun List<Int>.nextValue(): Int =
    if (this.all { it == 0 }) 0 else this.last() + this.zipWithNext(difference).nextValue()

fun List<Int>.previousValue(): Int =
    if (this.all { it == 0 }) 0 else this.first() - this.zipWithNext(difference).previousValue()

private val difference = { a: Int, b: Int -> b - a }
