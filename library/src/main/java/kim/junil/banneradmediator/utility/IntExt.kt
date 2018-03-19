package kim.junil.banneradmediator.utility

import java.util.*

fun Int.Companion.random(maxValue: Int): Int{
    val seed = System.currentTimeMillis()
    val r = Random(seed)
    return r.nextInt(maxValue)
}