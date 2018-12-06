package com.jake.adventofcode

import java.io.File

val input = File("input/day2").useLines { it.toList() }
    .map { it.toList() }

fun main() {
    println(part1())
    println(part2())
}

private fun part1() =
    toListOfCharCounts(input).flatten().filter { it == 2 }.count() *
    toListOfCharCounts(input).flatten().filter { it == 3 }.count()

private fun toListOfCharCounts(list: List<List<Char>>) =
    list.map { it.groupBy { c -> c } } // [{a: [a,a], b: [b]}]
        .map { toUniqueValueCount(it) } // [[2, 1]]

private fun toUniqueValueCount(it: Map<Char, List<Char>>) =
    it.values.map { it.size }.distinct()

private fun part2(): String {
    var leastErrors = 100
    var item1 = -1
    var item2 = -1
    for (i in 0 until input.size) {
        for (j in i+1 until input.size) {
            val errors = input[i].zip(input[j]).filter { (c1, c2) -> c1 != c2 }.count()
            if (errors < leastErrors) {
                leastErrors = errors
                item1 = i
                item2 = j
            }
        }
    }
    return input[item1].zip(input[item2]).filter { (c1, c2) -> c1 == c2 }
        .map { (c1, _) -> c1.toString()}
        .reduce { acc, c1 -> acc + c1 }

}


