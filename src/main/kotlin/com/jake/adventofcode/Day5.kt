package com.jake.adventofcode

import java.io.File
import kotlin.math.abs
import kotlin.math.max

private val input = File("input/day5").readText()

fun main() {
    val length1 = part1(input)
    println(length1)
}

private fun part1(str: String): Int {
    var result = str
    var i = 0
    while (i < result.length-1) {
        if (shouldBeDestroyed(result[i], result[i+1])) {
            result = result.removeRange(i..i+1)
            i = max(i-1, 0)
        } else {
            i += 1
        }
    }
    return result.length
}

fun shouldBeDestroyed(c1: Char, c2: Char) = abs(c1.toInt() - c2.toInt()) == 32
