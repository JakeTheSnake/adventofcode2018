package com.jake.adventofcode

import java.io.File

private val input = File("input/day4").readLines().sorted()

fun main() {
    val entry = input.splitOn { s -> s.contains("Guard") }
        .map { toShift(it) }
        .groupBy { it.guardId }
        .mapValues { squash(it.value) }
        .maxBy { amountOfSleep(it.value.timeTable) }

    val max = entry!!.value.timeTable.max()
    val minute = entry.value.timeTable.indexOf(max)
    println(entry.key * minute)

}

fun amountOfSleep(timeTable: Array<Int>) = timeTable.reduce { acc, i -> acc + i }

fun squash(v: List<Shift>) =
    Shift(v[0].guardId,
            v.map { it.timeTable }
            .reduce { acc, shift -> mergeEachIndex(acc, shift) { a, b -> a + b } }
)

/**
 * Returns a new array by merging each index using the given transform-function
 */
fun <T> mergeEachIndex(a1: Array<T>, a2: Array<T>, f: (T, T) -> T): Array<T> {
    for (i in 0 until a1.size) {
        a1[i] = f.invoke(a1[i], a2[i])
    }
    return a1
}

fun toShift(shift: List<String>): Shift {
    val iterator = shift.iterator()
    val guardId = Regex("Guard #(\\d+)").find(iterator.next())!!.groupValues[1].toInt()
    val timeTable = Array(60) { 0 }
    while (iterator.hasNext()) {
        val fallsAsleep = getMinute(iterator.next())
        val wakesUp = getMinute(iterator.next())
        for (i in fallsAsleep until wakesUp)
            timeTable[i] = 1
    }
    return Shift(guardId, timeTable)
}

fun getMinute(line: String) = Regex("\\d{2}:(\\d{2})").find(line)!!.groupValues[1].toInt()

data class Shift(var guardId: Int, var timeTable: Array<Int>)

/**
 * Splits the list into chunks, where a new chunk is made every time a list item
 * fulfills a given predicate.
 */
fun <T> Iterable<T>.splitOn(t: (T) -> Boolean): List<List<T>> {
    val result = mutableListOf<List<T>>()
    val iterator = this.iterator()
    var next = iterator.next()
    while (iterator.hasNext()) {
        val chunk = mutableListOf(next)
        while (iterator.hasNext()) {
            next = iterator.next()
            if (t.invoke(next))
                break
            chunk.add(next)
        }
        result.add(chunk)
    }
    return result

}
