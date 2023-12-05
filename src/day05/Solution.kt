package day05

import chunkedBy
import println
import readInput

fun String.findLongs() = this.split(" ").filter { it.isNotBlank() }.map { it.toLong() }

data class Instruction(val destination: Long, val source: Long, val range: Long)

fun List<String>.parseInstructions() =
    this.subList(fromIndex = 1, toIndex = this.size).map(String::findLongs)
        .map { (destination, source, range) -> Instruction(destination, source, range) }

fun resolve(value: Long, instructions: List<Instruction>): Long =
    instructions.find { value in it.source until it.source + it.range }
        ?.let { it.destination + (value - it.source) }
        ?: value

fun rearrange(range: LongRange, instructions: List<Instruction>): List<LongRange> {
    return instructions.asSequence().sortedBy { it.source }.fold(listOf(range.first, range.last)) { acc, instruction ->
        listOfNotNull(
            instruction.source, // start
            instruction.source - 1, //start - 1
            instruction.source + instruction.range - 1, // end
            instruction.source + instruction.range, // end + 1
        ).filter { it.isIncluded(range) } + acc
    }.sorted().chunked(2).map { LongRange(it.first(), it.last()) }.toList()
}

private fun Long.isIncluded(range: LongRange) = this in range && this != range.first && this != range.last

fun resolve(ranges: List<LongRange>, instructions: List<Instruction>): List<LongRange> =
    ranges.flatMap { range -> rearrange(range, instructions) }.map {
        with(resolve(it.first, instructions)) {
            LongRange(this, this + it.last - it.first)
        }
    }

fun main() {
    val day = "05"

    fun part1(input: List<String>): Long {
        val inputChunks = input.chunkedBy { it.isBlank() }
        val seeds = inputChunks[0][0].substring(startIndex = 6).findLongs()

        val foldResult = seeds.map { seed ->
            (1..7).fold(seed) { currentInput, index ->
                val instruction = inputChunks[index].parseInstructions()
                resolve(currentInput, instruction)
            }
        }

        return foldResult.min()
    }

    fun part2(input: List<String>): Long {
        val inputChunks = input.chunkedBy { it.isBlank() }
        val seeds =
            inputChunks[0][0].substring(startIndex = 6).findLongs()
                .chunked(2)
                .map {
                    LongRange(it.first(), it.first() + it.last() - 1)
                }.toList()

        val foldResult =
            (1..7).fold(seeds) { currentInput, index ->
                val instruction = inputChunks[index].parseInstructions()
                resolve(currentInput, instruction)
            }

        return foldResult.minOf { it.first }
    }

    // Part 1
    val testInput1 = readInput(day, "test_input_1")
    check(part1(testInput1) == 35L)

    // Part 2
    val testInput2 = readInput(day, "test_input_2")
    check(part2(testInput2) == 46L)

    println("Solution:")
    val input = readInput(day, "input")
    part1(input).println()
    part2(input).println()
}
