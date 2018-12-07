package com.jake.adventofcode

import java.io.File

private val input = File("input/day3")

private val claims = input.readLines().map { parseClaim(it) }

val fabric = Array(1500) { Array(1500) { 0 } }

fun parseClaim(claim: String): Claim {
    val groups = Regex("#(\\d+) @ (\\d+),(\\d+): (\\d+)x(\\d+)").find(claim)!!.groupValues
    return Claim(
        id = groups[1].toInt(),
        left = groups[2].toInt(),
        top = groups[3].toInt(),
        width = groups[4].toInt(),
        height = groups[5].toInt()
    )
}

data class Claim(val id: Int, val left: Int, val top: Int, val width: Int, val height: Int, var overlaps: Boolean = false)

fun main() {
    forEachClaimSquare(incrementFabricSquare())
    println(part1())

    forEachClaimSquare(evaluateIfClaimOverlaps())
    println(part2())
}

private fun evaluateIfClaimOverlaps(): (Int, Int, Claim) -> Unit =
    { i, j, c -> if (fabric[i][j] > 1) c.overlaps = true }

private fun incrementFabricSquare(): (Int, Int, Claim) -> Unit =
    { i, j, _ -> fabric[i][j]++ }

private fun part1() = fabric.flatten().filter { it >= 2 }.count()

private fun part2() = claims.find { !it.overlaps }?.id

private fun forEachClaimSquare(f: (i: Int, j: Int, c: Claim) -> Unit) {
    for (claim in claims) {
        for (i in claim.left until claim.left + claim.width) {
            for (j in claim.top until claim.top + claim.height) {
                f.invoke(i, j, claim)
            }
        }
    }
}

