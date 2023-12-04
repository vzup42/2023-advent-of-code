package day04

import println
import readInput
import kotlin.math.pow

const val day = "04"

fun String.findNumbers() = "\\d+".toRegex().findAll(this).map { it.value.toInt() }

fun main() {
    fun part1(input: List<String>): Int =
        input.sumOf { line ->
            val (_, winningString, myCardsString) = line.split(":", "|")
            val winning = winningString.findNumbers().toSet()
            val myCards = myCardsString.findNumbers().toList()
            val amount = myCards.filter { winning.contains(it) }.size
            2.0.pow(amount - 1).toInt()
        }

    fun part2(input: List<String>): Int {

        val score = MutableList(input.size) {1}
        score[0] = 1

        input.mapIndexed { index, line ->
            val (_, winningString, myCardsString) = line.split(":", "|")
            val winning = winningString.findNumbers().toSet()
            val myCards = myCardsString.findNumbers().toList()
            val amount = myCards.filter { winning.contains(it) }.size
            (1 .. amount).forEach {
                score[index + it] += score[index]
            }
        }

        return score.sum()
    }

    // Part 1
    val testInput1 = readInput(day, "test_input_1")
    check(part1(testInput1) == 13)

    // Part 2
    val testInput2 = readInput(day, "test_input_2")
    check(part2(testInput2) == 30)

    println("Solution:")
    val input = readInput(day, "input")
    part1(input).println()
    part2(input).println()
}
