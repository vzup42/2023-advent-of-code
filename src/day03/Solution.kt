package day03

import println
import readInput
import kotlin.math.abs

const val day = "03"

data class Coordinate(val x: Int, val y: Int)

fun Coordinate.isNeighbouringTo(lineIndex: Int, match: MatchResult): Boolean =
    abs(this.y - lineIndex) <= 1 &&
        (this.x >= match.range.first - 1) &&
        (this.x <= match.range.last + 1)

fun findSymbolCoordinates(input: List<String>, symbolCondition: (Char) -> Boolean) =
    input.flatMapIndexed { indexY, s ->
        s.mapIndexed { indexX, element ->
            Coordinate(indexX, indexY).takeIf { symbolCondition(element) }
        }.filterNotNull()
    }

fun String.findNumbers() = "\\d+".toRegex().findAll(this)

fun main() {

    fun part1(input: List<String>): Int {
        val symbolCoordinates = findSymbolCoordinates(input) { c -> !c.isDigit() && c != '.' }

        return input.mapIndexed { lineIndex, line ->
            line.findNumbers().mapNotNull { match ->
                match.value.toInt().takeIf {
                    symbolCoordinates.any { coordinate -> coordinate.isNeighbouringTo(lineIndex, match) }
                }
            }.sum()
        }.sum()
    }

    fun part2(input: List<String>): Int {
        val gearCoordinates = findSymbolCoordinates(input) { c -> c == '*' }
        val gears = gearCoordinates.associateWith { mutableSetOf<Int>() }

        input.mapIndexed { lineIndex, line ->
            line.findNumbers().forEach { match ->
                gearCoordinates.forEach { coordinate ->
                    if (coordinate.isNeighbouringTo(lineIndex, match)) {
                        gears[coordinate]?.add(match.value.toInt())
                    }
                }
            }
        }

        return gears.values.filter { it.size == 2 }.sumOf { it.first() * it.last() }
    }

    // Part 1
    val testInput1 = readInput(day, "test_input_1")
    check(part1(testInput1) == 4361)

    // Part 2
    val testInput2 = readInput(day, "test_input_2")
    check(part2(testInput2) == 467835)

    println("Solution:")
    val input = readInput(day, "input")
    part1(input).println()
    part2(input).println()
}
