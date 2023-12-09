package day07

import println
import readInput

fun main() {
    val day = "07"

    fun part1(input: List<String>): Int {
        val result = input.map { line ->
            val (hand, bid) = line.split(" ")
            val case = hand.evaluateHand()
            Input(hand, bid.toInt(), case)
        }.sortedWith(compareBy<Input> { it.case.value }.thenComparator { a, b ->
            compareHands(a.hand, b.hand) {
                when (this) {
                    '2' -> 2
                    '3' -> 3
                    '4' -> 4
                    '5' -> 5
                    '6' -> 6
                    '7' -> 7
                    '8' -> 8
                    '9' -> 9
                    'T' -> 10
                    'J' -> 11
                    'Q' -> 12
                    'K' -> 13
                    'A' -> 14
                    else -> throw IllegalArgumentException("")
                }
            }
        })

        return result.mapIndexed { index, foo ->
            foo.bid * (index + 1)
        }.sum()
    }

    fun part2(input: List<String>): Int {
        val result = input.map { line ->
            val (hand, bid) = line.split(" ")
            val case = hand.evaluateHand2()
            Input(hand, bid.toInt(), case)
        }.sortedWith(compareBy<Input> { it.case.value }.thenComparator { a, b ->
            compareHands(a.hand, b.hand) {
                when (this) {
                    'J' -> 1
                    '2' -> 2
                    '3' -> 3
                    '4' -> 4
                    '5' -> 5
                    '6' -> 6
                    '7' -> 7
                    '8' -> 8
                    '9' -> 9
                    'T' -> 10
                    'Q' -> 12
                    'K' -> 13
                    'A' -> 14
                    else -> throw IllegalArgumentException("")
                }
            }
        })

        return result.mapIndexed { index, foo ->
            foo.bid * (index + 1)
        }.sum()
    }

    // Part 1
    val testInput1 = readInput(day, "test_input_1")
    check(part1(testInput1) == 6440)

    // Part 2
    val testInput2 = readInput(day, "test_input_2")
    check(part2(testInput2) == 5905)

    println("Solution:")
    val input = readInput(day, "input")
    part1(input).println()
    part2(input).println()
}

enum class Case(val value: Int) {
    FIVE_OF_THE_KIND(6),
    FOUR_OF_THE_KIND(5),
    FULL_HOUSE(4),
    THREE_OF_THE_KIND(3),
    TWO_PAIRS(2),
    ONE_PAIR(1),
    HIGH_CARD(0);
}

fun String.evaluateHand(): Case {
    val chars = this.toCharArray().groupBy { it }

    return when {
        chars.keys.size == 1 -> Case.FIVE_OF_THE_KIND
        chars.values.any { it.size == 4 } -> Case.FOUR_OF_THE_KIND
        chars.keys.size == 2 -> Case.FULL_HOUSE
        chars.values.any { it.size == 3 } -> Case.THREE_OF_THE_KIND
        chars.values.count { it.size == 2 } == 2 -> Case.TWO_PAIRS
        chars.values.count { it.size == 2 } == 1 -> Case.ONE_PAIR
        else -> Case.HIGH_CARD
    }
}

fun String.evaluateHand2(): Case {
    val jCount = this.toCharArray().count { it == 'J' }
    val chars = this.toCharArray().filter { it != 'J' }.groupBy { it }

    return when {
        chars.keys.size <= 1 -> Case.FIVE_OF_THE_KIND
        chars.values.any { it.size + jCount == 4 } -> Case.FOUR_OF_THE_KIND
        chars.keys.size == 2 -> Case.FULL_HOUSE
        chars.values.any { it.size + jCount == 3 } -> Case.THREE_OF_THE_KIND
        chars.values.count { it.size == 2 } == 2 -> Case.TWO_PAIRS
        chars.values.any { it.size + jCount == 2 } -> Case.ONE_PAIR
        else -> Case.HIGH_CARD
    }
}

data class Input(val hand: String, val bid: Int, val case: Case)

fun compareHands(first: String, second: String, cardValue: Char.() -> Int): Int {
    (0 until 5).forEach { i ->

        if (first[i].cardValue() < second[i].cardValue()) {
            return -1
        } else if (first[i].cardValue() > second[i].cardValue()) {
            return 1
        }
    }
    return 0
}