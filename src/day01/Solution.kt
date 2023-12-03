package day01

import println
import readInput

const val day = "01"

fun main() {
    fun part1(input: List<String>): Int =
        input.sumOf(String::calibrate)

    fun part2(input: List<String>): Int =
        input.map {
            it
                .replace("one", "o1ne")
                .replace("two", "tw2o")
                .replace("three", "th3ree")
                .replace("four", "4")
                .replace("five", "fi5ve")
                .replace("six", "6")
                .replace("seven", "se7ven")
                .replace("eight", "eig8ht")
                .replace("nine", "ni9ne")
        }.sumOf(String::calibrate)

    // Part 1
    val testInput1 = readInput(day, "test_input_1")
    check(part1(testInput1) == 142)

    // Part 2
    val testInput2 = readInput(day, "test_input_2")
    check(part2(testInput2) == 281)

    println("Solution:")
    val input = readInput(day, "input")
    part1(input).println()
    part2(input).println()
}

val digits = '0'..'9'
fun String.calibrate() = this.find { c -> c in digits }!!.digitToInt() * 10+ this.findLast { c -> c in digits }!!.digitToInt()

