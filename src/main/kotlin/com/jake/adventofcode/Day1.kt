package com.jake.adventofcode

import java.lang.IllegalArgumentException

fun main() {
    val day1 = Day1()
    println(day1.part1())
    println(day1.part2())
}

class Day1 {
    private var frequencies: List<Int> = Day1::class.java.getResource("day1").readText().split("\n").map { toInt(it) }

    fun part1() = frequencies.sum()

    private fun toInt(s: String) = s.replace("+", "").toInt()

    fun part2() =
        cycle(frequencies)
            .scanl { a, b -> a + b }
            .firstDuplicate()

    private fun cycle(list: List<Int>): Sequence<Int> {
        return sequence {
            while (true) {
                for (frequency in list) {
                    yield(frequency)
                }
            }
        }
    }
}

private fun <T> Sequence<T>.scanl(f: ((T, T) -> T)): Sequence<T> {
    val iterator = this.iterator()
    var last = iterator.next()
    return sequence {
        yield(last)
        while(iterator.hasNext()) {
            last = f.invoke(last, iterator.next())
            yield(last)
        }
    }
}

private fun <T> Sequence<T>.firstDuplicate(): T {
    val previous = emptySet<T>().toMutableSet()
    val iterator = this.iterator()
    while (iterator.hasNext()) {
        val next = iterator.next()
        if (previous.contains(next))
            return next
        else
            previous.add(next)
    }
    throw IllegalArgumentException("oops")
}


