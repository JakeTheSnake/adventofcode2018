package com.jake.adventofcode

import java.io.File

val frequencies = File("input/day1").useLines { it.toList() }.map { it.toInt() }

fun main() {
    println(part1())
    println(part2())
}

private fun part1() = frequencies.sum()

private fun part2() = cycle(frequencies)
                .scanl { a, b -> a + b }
                .firstDuplicate()

fun cycle(list: List<Int>): Sequence<Int> {
    return sequence {
        while (true) {
            for (frequency in list) {
                yield(frequency)
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


