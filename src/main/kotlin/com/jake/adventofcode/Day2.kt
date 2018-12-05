package com.jake.adventofcode

import java.io.File

val input = File("input/day2").useLines { it.toList() }
    .map { it.toList() }

fun main() {
    println(part1())
}

private fun part1() =
    toListOfCharSizes(input).flatten().filter { it == 2 }.count() *
    toListOfCharSizes(input).flatten().filter { it == 3 }.count()

private fun toListOfCharSizes(list: List<List<Char>>) = list
    .map { it.groupBy { c -> c } } // Groups every equal item
    .map { toUniqueValueCount(it) }

private fun toUniqueValueCount(it: Map<Char, List<Char>>) =
    it.values.map { it.size }.distinct()


