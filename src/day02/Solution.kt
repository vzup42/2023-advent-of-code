package day02

import println
import readInput
import kotlin.math.max

const val day = "02"

fun main() {

    val limits = mapOf(
        "green" to 13,
        "blue" to 14,
        "red" to 12
    )

    fun part1(input: List<String>): Int =
        input.sumOf { line ->
            val (id, game) = line.removeRange(0..4).split(":")

            var gamePossible = true

            game.split(";").map { iteration ->
                iteration.split(",").forEach {
                    val (_, value, color) = it.split(" ")
                    gamePossible = gamePossible && (limits[color]!! >= value.toInt())
                }
            }

            id.toInt().takeIf { gamePossible } ?: 0
        }

    fun part2(input: List<String>): Int =
        input.sumOf { line ->
            val memo = mutableMapOf(
                "green" to 0,
                "blue" to 0,
                "red" to 0
            )

            val (id, game) = line.removeRange(0..4).split(":")

            game.split(";").map { iteration ->
                iteration.split(",").forEach {
                    val (_, value, color) = it.split(" ")
                    memo[color] = max(memo[color]!!, value.toInt())
                }

            }

            memo.values.reduce { mult, element -> mult * element }
        }

    // Part 1
    val testInput1 = readInput(day, "test_input_1")
    check(part1(testInput1) == 8)

    // Part 2
    val testInput2 = readInput(day, "test_input_2")
    check(part2(testInput2) == 2286)

    println("Solution:")
    val input = readInput(day, "input")
    part1(input).println()
    part2(input).println()
}