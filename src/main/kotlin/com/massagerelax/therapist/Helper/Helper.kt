package com.massagerelax.therapist.Helper

fun Boolean.toInt() = if (this) 1 else 0

fun Int.toBoolean(): Boolean = this > 0

// Returns modified n.
/**
 * @param n: input number
 * @param p: bit position
 * @param b: bit value
 */
fun modifyBit(n: Int,
              p: Int,
              b: Boolean): Int {
    val mask = 1 shl p
    return n and mask.inv() or (b.toInt() shl p and mask)
}